/*
    Banking system where threads make deposits/withdrawls of random amounts of money from an account.
    
    Some print statements out of order, tried to include them in the thread execution but that caused threads to execute in sequence due to buffered nature of System.out.println(). So occasionally the last few transactions will be printed out of order, the true final transaction will have a matching balance with the final balance.
*/

// import java.util.concurrent.ThreadLocalRandom; // REMOVED: Commented out, as it's not used anymore

class Account {

    int balance;

    Account (int b) {
        // balance = b; // CHANGED: Referring to all member variables using "this."
        this.balance = b;
    }

    public int getBalance () {
        // return balance; // CHANGED: Referring to all member variables using "this."
        return this.balance;
    }

    public void applyTransaction (String threadName, int amount, boolean task) {
    
        try {
    
            // if (task) balance += amount; // CHANGED: Referring to all member variables using "this."
            if (task) this.balance += amount;
            // else if (amount < balance) balance -= amount; // CHANGED: Referring to all member variables using "this."
            else if (amount < this.balance) this.balance -= amount;

        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }
}

class BankThread extends Thread {

    // private Thread t; // REMOVED: Not used
    private String threadName;
    private boolean task;
    Account account;
    
    int amt; // ADDED: Amount property, so that it can be provided to the thread and not generate it randomly
    int numberOfTransactions; // ADDED: Provided to the thread, as opposed to set inside run(). This is so that testing can be automated further such that the expected final balance can be calculated

    // BankThread (String name, Account a, boolean tk) { // ADDED: Amount property, so that it can be provided to the thread and not generate it randomly
    BankThread (String name, Account a, boolean tk, int amount, int nTransactions) {
        
        // account = a; // CHANGED: Referring to all member variables using "this."
        this.account = a;
        // threadName = name; // CHANGED: Referring to all member variables using "this."
        this.threadName = name;
        // task = tk; // CHANGED: Referring to all member variables using "this."
        this.task = tk;

        this.amt = amount; // ADDED: Amount property, so that it can be provided to the thread and not generate it randomly
        this.numberOfTransactions = nTransactions; // ADDED: Provided to the thread, as opposed to set inside run(). This is so that testing can be automated further such that the expected final balance can be calculated
    }

    public void run () {
        
        // for (int i = 0; i < 100; i ++) { // CHANGED: The number of times to perform a transaction is now provided to the thread, as opposed to set inside run(). This is so that testing can be automated further such that the expected final balance can be calculated
        for (int i = 0; i < this.numberOfTransactions; i ++) {

            // int amt; // REMOVED: Added as a class member instead
            // int balance; // REMOVED: Not sure why this is stored here, and not just read-in inside the critical section...

            // synchronized (account) { // CHANGED: Referring to all member variables using "this."
            synchronized (this.account) {

                // amt = ThreadLocalRandom.current ().nextInt (1, 1000 + 1); // CHANGED: Switching randomly-generated values out for constant variables so they can be properly tested. See BankThread constructor.
                // balance = account.getBalance (); REMOVED: Not needed - just access it through the account object
                // account.applyTransaction (threadName, amt, task); // CHANGED: Referring to all member variables using "this."
                this.account.applyTransaction (threadName, amt, task);

            }

            if (task) {
                // System.out.println (threadName + " deposited $" + amt + "\nNew balance is $" + (balance + amt) + "\n"); // CHANGED: Access the account balance through the object member or getter, as opposed to first storing it in an intermediate variable outside of the critical region
                System.out.println (threadName + " deposited $" + amt + "\nNew balance is $" + (account.getBalance() + amt) + "\n");
            } else {
            
                // if (amt > balance) System.out.println ("Error: " + threadName + " trying to withdraw $" + amt + " when balance is only $" + balance + "\n"); // CHANGED: Access the account balance through the object member or getter, as opposed to first storing it in an intermediate variable outside of the critical region
                if (amt > account.getBalance()) System.out.println ("Error: " + threadName + " trying to withdraw $" + amt + " when balance is only $" + account.getBalance() + "\n");
                else {
                    // System.out.println (threadName + " withdrew $" + amt + "\nNew balance is $" + (balance - amt) + "\n"); // CHANGED: Access the account balance through the object member or getter, as opposed to first storing it in an intermediate variable outside of the critical region
                    System.out.println (threadName + " withdrew $" + amt + "\nNew balance is $" + (account.getBalance() - amt) + "\n");
                }

            }
            
            // REMOVED: This is not necessary - just more noise
            // try {
            //     Thread.sleep (10);
            // } catch (Exception e) {
            //     System.out.println (e.getMessage ());
            // }
        }
    }

    // REMOVED: This is not necessary - only used for printing
    // public void start () {
    //     System.out.println ("Initializing thread " + threadName);
    //     if (t == null) {
    //         t = new Thread (this, threadName);
    //         t.start ();
    //     }
    // }
}

public class Bank {

    // public static String main (String [] args) { // CHANGED: The main method must return void
    public static void main (String [] args) {

        int balance = Integer.parseInt (args [0]);
        
        if (balance < 0) {

            String message = "Error: Initial balance should be positive.";
            System.out.println (message);
            // return message; // REMOVED: The main method must return void
        
        }
        
        int numThreads = Integer.parseInt (args [1]);
        
        if (numThreads < 2) {

            String message = "Error: Should have at least 2 threads.";
            System.out.println (message);
            // return message; // REMOVED: The main method must return void

        }
        
        Account account = new Account(balance);
        // Thread threads [] = new Thread [numThreads]; // CHANGED: Array of Thread to array of BankThread
        BankThread threads [] = new BankThread[numThreads];
        // int depThreads = 0; // REMOVED: Not needed. Only used to name threads.
        // int withThreads = 0; // REMOVED: Not needed. Only used to name threads.

        // ADDED: Set to "constants" as opposed to randomly generated inside run()
        int depositAmount = 100;
        int withdrawalAmount = 20;

        int numberOfTransactions = 100; // ADDED: Provided to the thread, as opposed to set inside run(). This is so that testing can be automated further such that the expected final balance can be calculated
        
        System.out.println ("Initial balance: $" + balance);
        
        for (int i = 0; i < numThreads; i ++) {
            
            // CHANGED: Naming threads using the index number "i"
            if (i % 2 == 0) { // COMMENT: All even-numbered threads will perform deposits
            
                // depThreads ++; // REMOVED: Not needed. Only used to name threads.
                // threads [i] = new Thread (new BankThread ("Deposit Thread " + (i+1), account, true)); // CHANGED: Create BankThread objects, not Thread objects directly
                // threads [i] = new BankThread ("Deposit Thread " + (i+1), account, true); // ADDED: Amount to be deposited is passed to the thread
                threads [i] = new BankThread ("Deposit Thread " + (i+1), account, true, depositAmount, numberOfTransactions);
  
            } else { // COMMENT: All odd-numbered threads will perform widthrawals
  
                // withThreads ++; // REMOVED: Not needed. Only used to name threads.
                // threads [i] = new Thread(new BankThread ("Withdraw Thread " + (i+1), account, false)); // CHANGED: Create BankThread objects, not Thread objects directly
                // threads [i] = new BankThread ("Withdraw Thread " + (i+1), account, false); // ADDED: Amount to be deposited is passed to the thread
                threads [i] = new BankThread ("Withdraw Thread " + (i+1), account, false, withdrawalAmount, numberOfTransactions);
  
            }
  
            threads [i].start ();
        }
        
        // COMMENT: Wait for all threads to finish before fetching the final account balance
        for (int i = 0; i < numThreads; i ++) {
            
            try {
                threads [i].join ();
            } catch (Exception e) {
                System.out.println (e.getMessage ());
            }

        }

        String message = "Final balance: $" + account.getBalance ();
        System.out.println(message);
        
        // return message; // REMOVED: The main method must return void
        
    }
}