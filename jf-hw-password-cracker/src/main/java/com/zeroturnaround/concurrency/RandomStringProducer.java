package com.zeroturnaround.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Producer class, will keep generating new random words to serve as the input for the hashing algorithm.
 * The method "run()" should never return, unless a proper shutdown is initiated.
 */
public class RandomStringProducer implements Runnable {
    private  ConcurrentHashMap<String, String> saltToPassword;
    private ArrayBlockingQueue<String> tasks;
    private ConcurrentHashMap<String,String> saltToHash;
    private char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private StringBuffer sb = new StringBuffer();
    public RandomStringProducer(ArrayBlockingQueue<String> randomPasswords, ConcurrentHashMap<String, String> saltToHash, ConcurrentHashMap<String, String> saltToPassword) {
        this.tasks = randomPasswords;
        this.saltToHash = saltToHash;
        this.saltToPassword = saltToPassword;


    }
    /**
     * This should contain the actual String generation logic.
     */
    @Override
    public void run() {
        while (true)
            try {
                tasks.put(randomStringGenerate());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // if we're interrupted it means our work has been completed
                break;// time to go out of the loop and finish the execution
            }
    }

    private String randomStringGenerate(){

        sb.delete(0, sb.length());
        ThreadLocalRandom random =ThreadLocalRandom.current();
        int length = random.nextInt(32);// max length of 32
        for (int i = 0; i < length; i++) {
            char c = this.chars[random.nextInt(this.chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * This should allow you to (thread-safely!) signal this producer to halt its operations,
     * and thus return from the run() method.
     * Implementation is NOT mandatory if you supply an equally thread-safe alternative.
     */
    public void shutdown() {
        // TODO: Implement this, IF needed
    }
}
