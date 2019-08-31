package lab07;
import java.util.Scanner;

class Multithreading extends Thread {

    public static float total_bank = 0;
    public static float loan_val = 0;

    public Multithreading (String user_input) {

        if (user_input.equals ("loan")) {
            loan ();
        }
        
        if (user_input.equals ("exit")) {
            System.exit (0);
        }
        
        if (user_input.equals ("deposit")) {
            deposit ();
        }
        
        if (user_input.equals ("withdraw")) {
            withdraw ();
        }

    }

    public void run (String user_input) {
        
        try {
        } catch (Exception e) {
            // Throwing an exception     
            System.out.println ("Exception is caught");
        }

    }

    public static void loan () {

        Scanner myObj = new Scanner (System.in); // Create a Scanner object

        System.out.println (" option input a loan amount \n");
        
        float vals = myObj.nextInt (); // Read user input
        
        // I used a flat interest fee from loans
        loan_val = loan_val + ((float) (vals + (vals * 0.13)));

        System.out.println ("You owe $ " + loan_val + "\n");

    }

    public static void deposit () {

        Scanner myObj = new Scanner (System.in); // Create a Scanner object
        
        System.out.println (" option input a deposit amount" + "\n");
        float deposit = myObj.nextInt (); // Read user input
        
        total_bank = deposit + total_bank;

        System.out.println ("You now have $ " + total_bank + "\n");

    }

    public static void withdraw () {

        Scanner myObj = new Scanner (System.in); // Create a Scanner object
        
        System.out.println (" option input a withdraw amount" + "\n");
        float withdraw = myObj.nextInt (); // Read user input
        
        if (withdraw <= total_bank) {
            
            total_bank = total_bank - withdraw;
            System.out.println ("You now have $" + total_bank + "\n");
        
        } else {
            System.out.println (" Taylor you are too broke to get this you have $ " + total_bank + " yikes \n");
        }

    }
}

public class lab07 extends Thread {

    public static void main (String [] args) throws InterruptedException {

        boolean poor_man_ui = true; // the user can exit by typing exit
        
        while (poor_man_ui != false) {

            System.out.println ("type either loan, deposit ,  or withdraw. " + "To  terimate the program type exit" + "\n");
            
            Scanner myObj = new Scanner (System.in); // Create a Scanner object
            String user_input = myObj.nextLine (); // Read user input
            
            System.out.print (user_input);
            
            Multithreading object = new Multithreading (user_input);
            
            object.run ();

        }
    }
}