package ee.ut.jf2016.homework1;

import static org.junit.Assert.assertEquals;

import ee.ut.jf2016.Homework8.BankAccount;
import ee.ut.jf2016.Main;
import org.junit.Test;

public class HomeworkTest {

  @Test
  public void testTransfer() {
    BankAccount accountOne = new BankAccount(150,1);
    BankAccount accountTwo = new BankAccount(200,1);
    Main.transferMoney(accountOne,accountTwo,30);
    assertEquals(accountOne.getBalance(),120);
    assertEquals(accountTwo.getBalance(),230);
  }




}
