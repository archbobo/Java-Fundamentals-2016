package ee.ut.jf2016;

import ee.ut.jf2016.Homework8.BankAccount;
import ee.ut.jf2016.Homework8.Donator;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Main {
    static List<BankAccount> bankAccounts = new ArrayList<>();
    static ReadWriteLock lock = new ReentrantReadWriteLock();
    static List<Callable> donators = new ArrayList<>();
    static List<Future<Void>> futures = new ArrayList<>();

    static PrintStream out = new PrintStream(new BufferedOutputStream(System.out));

    public static void main(String[] args) throws InterruptedException {
        /*if (args.length != 1) {
            System.out.println("One argument is expected but found " + args.length);
            return;
        }*/
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        int bankNumber =80;// Integer.parseInt(args[0]);
        for (int i = 0; i <bankNumber ; i++) {
            BankAccount b = new BankAccount(bankNumber, i+1);
            bankAccounts.add(b);
        }
        bankAccounts.forEach(source ->{
            List<BankAccount> others = bankAccounts.stream().filter(bankAccount -> !source.equals(bankAccount)).collect(Collectors.toList());
            Collections.shuffle(others);
            Donator donator = new Donator(others,source);
            donators.add(donator);
        });
        donators.forEach(donator -> futures.add(executorService.submit(donator)));
        executorService.shutdown();
        while (true) {
           Thread.sleep(100);
            printAllBalances();
            if(futures.stream().allMatch(Future::isDone)) break;
        }
        out.flush();
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void transferMoney(BankAccount from, BankAccount to, int amount){
        lock.readLock().lock();
        from.decrementBalanceBy(amount);
        to.incrementBalanceBy(amount);
        lock.readLock().unlock();
    }

    public static void printAllBalances(){
        lock.writeLock().lock();
        int sum =bankAccounts.stream().mapToInt(BankAccount::getBalance).sum();
        StringBuilder builder = new StringBuilder();
        bankAccounts.forEach(account -> {builder.append(account.getBalance());builder.append(',');});
        builder.append(sum);
        out.println(builder.toString());
        lock.writeLock().unlock();
    }
}
