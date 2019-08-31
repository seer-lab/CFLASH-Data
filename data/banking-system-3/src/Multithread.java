import java.util.Scanner;

class BankDetails {

    private int remaining = 3000;

    public int getbalance () {
        return remaining;
    }

    public synchronized int getvals () {

        Scanner reader = new Scanner (System.in);
        System.out.println ("Please enter the amount");
        int val = reader.nextInt ();

        return val;

    }

    public synchronized int getchoice () {

        Scanner reader = new Scanner (System.in);
        
        System.out.println ("Please Enter if Making Deposit by pressing 1 or withdrawl by pressing 0");
        
        int n = reader.nextInt ();
        
        return n;
    
    }

    public void withdraw (int withdraw) {
        remaining = remaining - withdraw;
    }

    public void Deposit (int Deposit) {
        remaining = remaining + Deposit;
    }

}

class accountholder implements Runnable {
    
    private BankDetails banking;
    private int n;
    private int val;

    public accountholder (BankDetails banking) {
        this.banking = banking;
    }

    public accountholder (int n, int val) {
        this.n = n;
        this.val = val;
    }

    public void run () {

        try {

            //Static variables to better show the multithreading             
            //int n = 0; //Choice for Withdrawl or Deposit (Set to withdrawl)
            //int val = 100;
            //For user input if wanted
            int n = banking.getchoice ();

            //Can be used for user choice on both threads sep
            //int val = banking.getvals(); //Can be used for user choice on both threads sep
            //Shows that 2 threads are working
            //System.out.println ("Thread " + Thread.currentThread().getId() +" is running");
            
            int count = 3;

            //Runs the withdraw function
            if (n == 0) {

                for (int i = 1; i <= count; i ++) {
                    
                    //MakeDeposit(100);
                    int val = banking.getvals ();
                    
                    MakeWithdrawl (val);
                    n = banking.getchoice ();
                    
                    if (banking.getbalance () < 0) {
                        System.out.println ("account is overdrawn!");
                    }

                }

                //Runs the Deposit function
            }

            if (n == 1) {

                for (int i = 1; i <= count; i ++) {

                    int val = banking.getvals ();
                    MakeDeposit (val);
                    n = banking.getchoice ();
                    //MakeWithdrawl(2000);
                    
                }
            }

        } catch (Exception e) {
            System.out.println ("Exception is caught");
        }
    }

    //Used Synchronized for data to be correct thread (fixing output)
    private synchronized void MakeWithdrawl (int withdrawAmount) {

        if (banking.getbalance () >= withdrawAmount) { //Checks balance stored in banking    
            
            System.out.println (Thread.currentThread ().getName () + " Is going to withdraw $" + withdrawAmount); //Prints the Name of thread and the amount withdrawed

            try {
                Thread.sleep (2000); //Helps for showing output
            } catch (Exception ex) { }

            banking.withdraw (withdrawAmount); // Withdrawing the amount
            
            System.out.println (Thread.currentThread ().getName () + " completed withdrawl of $" + withdrawAmount); //Shows it has been completed 
            
        } else {
            System.out.println (Thread.currentThread ().getName () + " Not enough $ remaining"); //If not enough remanining
        }
    }

    //Used Synchronized for data to be correct thread (fixing output)
    private synchronized void MakeDeposit (int DepositAmount) {
        
        if (banking.getbalance () >= DepositAmount) { //Checks balance stored in banking
            
            System.out.println (Thread.currentThread ().getName () + " Is going to Deposit $" + DepositAmount); // Prints the Name of thread and the amount deposited
            
            try {
                Thread.sleep (2000);
            } catch (Exception ex) { }
            
            banking.Deposit (DepositAmount); // deposits the amount
            
            System.out.println (Thread.currentThread ().getName () + " completed Deposit of $" + DepositAmount); //Shows it has been completed 
            
        } else {
            System.out.println (Thread.currentThread ().getName () + " Not enough $ remaining"); //If not enough remanining
        }
    }

    //Used Synchronized for data to be correct thread (fixing output)
    private synchronized void GetBalance () {
        
        try {
            Thread.sleep (2000);
        } catch (Exception ex) { }

        System.out.println (Thread.currentThread ().getName () + " completed Deposit of $" + banking.getbalance ()); //Shows it has been completed

    }
}

// Main Class
class Multithread {

    public static void main (String [] args) {

        System.out.println ("Banking Transactions");
        BankDetails banking = new BankDetails ();
        accountholder Accountholder = new accountholder (banking);

        //Using 2 threads to better show the account detail
        //Implementing for loop can increase the amount of threads if wanted (not necscarry in this case) 
        Thread thread1 = new Thread (Accountholder);
        Thread thread2 = new Thread (Accountholder);
        
        thread1.setName ("sam");
        thread2.setName ("Tom");
        
        thread1.start ();
        thread2.start ();
        
        try {
            thread1.join ();
        } catch (InterruptedException ex) { }
        
        try {
            thread2.join ();
        } catch (InterruptedException ex) { }
        
        //getval(n,val);
        
    }

    static void getval (int n, int val) {

        int i, j;
        i = n;
        j = val;

    }
}