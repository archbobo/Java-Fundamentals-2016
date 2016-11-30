package ee.ut.jf2016.Homework8;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Erdem on 20-Oct-16.
 */
public class BankAccount {

    private int ID;
    private int balance;
    ReadWriteLock lock;

    public BankAccount(int balance, int ID) {
        this.balance = balance;
        this.lock = new ReentrantReadWriteLock();
        this.ID = ID;
    }

    public int getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        }finally {
            lock.readLock().unlock();
        }
    }
    public void incrementBalanceBy(int n){
        lock.writeLock().lock(); // read properly
        try {
            this.balance +=  n;
        }finally {
            lock.writeLock().unlock(); // unlock the read
        }
    }
    public void decrementBalanceBy(int n){
        lock.writeLock().lock(); // read properly
        try {
            this.balance -=  n;
        }finally {
            lock.writeLock().unlock(); // unlock the read
        }
    }
}
