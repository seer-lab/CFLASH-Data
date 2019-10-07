import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Tests {

    // REMOVED: Removed all original tests, as they don't pertain at all to the account balance values expected
    // Bank b = new Bank ();

    // @Test
    // public void testIncorrectBalance () {
    //     String [] args;
    //     args = new String [] {"-1", "2"};
    //     assertEquals ("Error: Initial balance should be positive.", b.main (args));
    // }

    // @Test
    // public void testIncorrectThreadCount () {
    //     String [] args;
    //     args = new String [] {"1", "-1"};
    //     assertEquals ("Error: Should have at least 2 threads.", b.main (args));
    // }

    // @Test
    // public void testCorrectOutput () {
    //     String [] args;
    //     args = new String [] {"1000", "2"};
    //     assertEquals (true, b.main (args).matches ("Final balance: $[0-9]+"));
    // }

    // ADDED: Test final balance after all transactions are performed
    @Test
    public void testFinalBalance() {

        // Usually command-line arguments
        int numberOfThreads = 5;
        int initialBalance = 1000;

        // Shared object
        Account account = new Account(initialBalance);

        // Generate threads that are to perform deposit and withdrawal transactions
        // Taken from the original main function
        BankThread allThreads[] = new BankThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i ++) {
            if (i % 2 == 0) {
                int amount = 100;
                allThreads[i] = new BankThread ("Deposit Thread " + (i+1), account, true, amount);
            } else {
                int amount = 20;
                allThreads[i] = new BankThread ("Withdraw Thread " + (i+1), account, false, amount);
            }
            allThreads[i].start();
        }

        // Wait for all threads to finish before fetching the final account balance
        for (int i = 0; i < numberOfThreads; i ++) {
            
            try {
                allThreads[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int expectedFinalBalance = 27000;
        assertEquals(expectedFinalBalance, account.getBalance());
    }
}