package com.zeroturnaround.concurrency;

import com.zeroturnaround.concurrency.data.Hash;
import com.zeroturnaround.concurrency.data.Salt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Sets up the required number of producer and consumer threads,
 * coordinates their information exchange,
 * keeps track of the discovered collisions (and only stores the FIRST one found!),
 * and takes care of a clean shutdown, once all passwords have a found collision.
 */
public class CollisionFinder {
    final private Integer Consumer_Number = 4;
    final private Integer Producer_Number = 2;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ArrayBlockingQueue<String> tasks = new ArrayBlockingQueue<>(100);
    private List<Runnable> workers = new ArrayList<>();
    private ConcurrentHashMap<String, String> saltToHash = new ConcurrentHashMap<>(); // from salt to hash values for workers to check
    private ConcurrentHashMap<String, String> saltToPassword = new ConcurrentHashMap<>();//from salt to generated password, workers will use this as well
    private RandomStringProducer p = new RandomStringProducer(tasks,saltToHash,saltToPassword);
    private RandomStringConsumer c = new RandomStringConsumer(tasks,saltToHash,saltToPassword);
    /**
     * Read the data provided by the "Salt" and "Hash" classes, and for each find a collision.
     * A collision happens when 2 different inputs generate the same hash.
     * This is dangerous in password-validation systems that use hash-codes (and in many other applications),
     * as the system will wrongfully assume the user to have supplied the correct password, allowing an attacker to login.
     *
     * N.B. 1: If by chance your randomly generate the actual original password, then this is fine too of course!
     * N.B. 2: As you by now, no doubt have seen, the elements in "Password", "Salt" and "Hash" are all in the same order,
     *         as in, the first password, with the first salt, results in the first hash.
     *         When returning from this method, make sure your password guesses are in the same order!
     */


    public List<String> findCollidingPasswords() {
        for (int i = 0; i <Salt.salts.size() ; i++) saltToHash.put(Salt.salts.get(i),Hash.hashes.get(i)); // populate the saltToHash concurrent map
        for (int i = 0; i <Consumer_Number ; i++) workers.add(c); // four counsumers
        for (int i = 0; i <Producer_Number ; i++) workers.add(p); // two producers
        workers.stream().forEach(executorService::execute); // run them all
        executorService.execute(()-> {
                while(!saltToHash.keySet().parallelStream().allMatch(saltToPassword::containsKey)); //while there is a salt not matched with a password
                executorService.shutdownNow();// kill them all
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(1,TimeUnit.MINUTES); // let threads to crack passwords for A minute
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Salt.salts.stream().map(saltToPassword::get).collect(Collectors.toList());
    }

}
