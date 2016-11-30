package com.zeroturnaround.concurrency;

import com.zeroturnaround.concurrency.util.BramHash;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Consumer class, will keep reading in new random words, feeding them to the hashing algorithm,
 * and checking the result for collisions.
 * The method "run()" should never return, unless a proper shutdown is initiated.
 */
public class RandomStringConsumer implements Runnable {
    private  ConcurrentHashMap<String, String> saltToPassword;
    private ArrayBlockingQueue<String> randomPasswords;
    private ConcurrentHashMap<String,String> saltToHash;

    public RandomStringConsumer(ArrayBlockingQueue<String> randomPasswords, ConcurrentHashMap<String, String> saltToHash, ConcurrentHashMap<String, String> saltToPassword) {
        this.randomPasswords = randomPasswords;
        this.saltToHash = saltToHash;
        this.saltToPassword = saltToPassword;
    }


    /**
     * This should contain the actual hash testing + result recording logic.
     */
    @Override
    public void run() {
        while(true){
            try {
                String password = randomPasswords.take();
                saltToHash                                                       // for every pair of salt,hash
                        .entrySet()
                        .parallelStream()
                        .filter(e -> !saltToPassword.containsKey(e.getKey()))   // filter out those we already cracked
                        .filter(e->BramHash.hash(e.getKey()+password).equals(e.getValue()))// try to crack with the password and filter out if failure
                        .forEach(entry -> saltToPassword.putIfAbsent(entry.getKey(), password));// with those we cracked, update the saltToPassword concurrent map
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // if we're interrupted it means our work has been completed or we are out of time
                break;// time to go out of the loop and finish the execution
            }
        }



    }

    /**
     * This should allow you to (thread-safely!) signal this consumer to halt its operations,
     * and thus return from the run() method.
     * Implementation is NOT mandatory if you supply an equally thread-safe alternative.
     */
    public void shutdown() {

    }
}
