package ee.ut.jf2016.Homework8;

import ee.ut.jf2016.Main;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Erdem on 20-Oct-16.
 */
public class Donator implements Callable<Void> {
    List<BankAccount> recipients;
    BankAccount source;
    public Donator(List<BankAccount> recipients,BankAccount source) {
        super();
        this.recipients = recipients;
        this.source = source;
    }

    @Override
    public Void call() throws Exception {
        recipients.forEach(recipient -> {
            Main.transferMoney(source,recipient,1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return null;
    }
}
