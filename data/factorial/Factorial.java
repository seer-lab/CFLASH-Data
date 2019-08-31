/**
 * CSCI4060u Lab07 - Number Generation (Factorial)
 * Time with 4 Threads:
 * real    0m0.386s
 * user    0m0.516s
 * sys     0m0.172s
 * Time with 8 Threads:
 * real    0m0.417s
 * user    0m0.750s
 * sys     0m0.156s
*/

// Used to store factorials in a list for testing purposes
import java.util.ArrayList;
import java.util.List;

public class Factorial extends Thread {

    public static final int NUM_THREADS = 4;
    public static int thread_count = 0;
    
    // override run function inherited from Threads
    @Override
    public void run () {

        try {
            System.out.println ("Thread " + Thread.currentThread ().getId () + ": started");
            factorial (100000, Thread.currentThread ().getId ());
        } catch (Exception e) {
            System.out.println ("Exception e has been caught");
        }

        System.out.println ("Thread " + Thread.currentThread ().getId () + ": finished.");

    }

    // finds factorials less than or equal to max starting at !1 = 1
    // returns an arraylist so Junit can check the method works
    public List < Integer > factorial (int max, long threadID) 
    {
        List < Integer > fact_list = new ArrayList <Integer> ();
        
        // f is the factorial, index is kept for printing
        int f = 1, increment = 2, index = 1;

        while (f <= max) {
        
            System.out.println ("Thread " + threadID + ": !" + index + " is " + f + " ");
            fact_list.add (f);
            f *= increment;
            increment ++;
            index ++;
        
        }
        
        return fact_list;
    
    }

    public static void main (String args []) {
    
        // start NUM_THREADS threads    
        for (int i = 0; i < NUM_THREADS; i ++) {
            
            Factorial curr_thread = new Factorial ();
            
            // start the thread
            curr_thread.start ();
            
            thread_count ++;
        }
    }
}