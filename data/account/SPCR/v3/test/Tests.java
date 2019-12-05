import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Tests {

    @Test(timeout = 120000) // used to test for deadlock
    public void testBalance() {

        // int numAccounts = 4;
        int numAccounts = Runtime.getRuntime().availableProcessors() + 1;

        Account[] bank = new Account[numAccounts];
        AccountThread[] threads = new AccountThread[numAccounts];

        char letter = 'A'; // name all accounts as A to Z
        for (int i = 0; i < numAccounts; i++) {
            String accName = String.valueOf(letter);
            bank[i] = new Account(accName, i+1, 100);
            threads[i] = new AccountThread(bank[i], bank);
            letter ++;
        }

        // get all threads started
        for (AccountThread thread : threads) thread.start();

        // wait for all threads to finish
        try {
            for (AccountThread thread : threads) thread.join();
        } catch (Exception e) { }

        for (Account a : bank) {
            assertEquals(300, a.balance, 0.001);
        }
    }
}
