/*
    Banking system where threads make deposits/withdrawls of random amounts of money from an account.
    
    Some print statements out of order, tried to include them in the thread execution but that caused threads to execute in sequence due to buffered nature of System.out.println(). So occasionally the last few transactions will be printed out of order, the true final transaction will have a matching balance with the final balance.
*/

import java.util.concurrent.ThreadLocalRandom;

class Account {

    int balance;

    Account (int b) {
        balance = b;
    }

    public int getBalance () {
        return balance;
    }

    public void applyTransaction (String threadName, int amount, boolean task) {
    
        try {
    
            if (task) balance += amount;
            else if (amount < balance) balance -= amount;

        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }
}

class BankThread extends Thread {

    private Thread t;
    private String threadName;
    private boolean task;
    Account account;

    BankThread (String name, Account a, boolean tk) {
        
        account = a;
        threadName = name;
        task = tk;
    
    }

    public void run () {

        for (int i = 0; i < 100; i ++) {
            int amt;
            int balance;

            synchronized (account) {

                amt = ThreadLocalRandom.current ().nextInt (1, 1000 + 1);
                balance = account.getBalance ();
                account.applyTransaction (threadName, amt, task);

            }

            if (task) {
                System.out.println (threadName + " deposited $" + amt + "\nNew balance is $" + (balance + amt) + "\n");
            } else {
            
                if (amt > balance) System.out.println ("Error: " + threadName + " trying to withdraw $" + amt + " when balance is only $" + balance + "\n");
                else {
                    System.out.println (threadName + " withdrew $" + amt + "\nNew balance is $" + (balance - amt) + "\n");
                }

            }

            try {
                Thread.sleep (10);
            } catch (Exception e) {
                System.out.println (e.getMessage ());
            }
        }
    }

    public void start () {

        System.out.println ("Initializing thread " + threadName);
        
        if (t == null) {
        
            t = new Thread (this, threadName);
            t.start ();
        
        }
    }
}

public class Bank {

    public static String main (String [] args) {

        int balance = Integer.parseInt (args [0]);
        
        if (balance < 0) {

            String message = "Error: Initial balance should be positive.";
            System.out.println (message);
            return message;
        
        }
        
        int numThreads = Integer.parseInt (args [1]);
        
        if (numThreads < 2) {

            String message = "Error: Should have at least 2 threads.";
            System.out.println (message);
            return message;

        }
        
        Account account = new Account (balance);
        Thread threads [] = new Thread [numThreads];
        int depThreads = 0;
        int withThreads = 0;
        
        System.out.println ("Initial balance: $" + balance);
        
        for (int i = 0; i < numThreads; i ++) {
            
            if (i % 2 == 0) {
            
                depThreads ++;
                threads [i] = new Thread (new BankThread ("Deposit Thread " + depThreads, account, true));
  
            } else {
  
                withThreads ++;
                threads [i] = new Thread (new BankThread ("Withdraw Thread " + withThreads, account, false));
  
            }
  
            threads [i].start ();
        }
  
        for (int i = 0; i < numThreads; i ++) {
            
            try {
                threads [i].join ();
            } catch (Exception e) {
                System.out.println (e.getMessage ());
            }

        }

        String message = "Final balance: $" + account.getBalance ();
        System.out.println (message);
        
        return message;
        
    }
}