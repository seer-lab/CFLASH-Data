import java.util.Arrays;
/** 
 * This program uses threads to search arrays for a number.
 */
public class HelloWorld {

    public static int array [] = {24, 52, 62, 6, 1, 63, 6, 2, 7, 6, 1, 63, 6, 2, 7}; //this array of
    
    public static int find = 6; //find this number
    
    public static int number_of_threads = 3; //total number of threads
    
    public static void main (String [] args) {

        //creating and running the number of threads
        RunnableDemo r;

        for (int a = 1; a <= number_of_threads; a ++) {
            
            // System.out.println(a + "-----------");
            r = new RunnableDemo (a, array, find, number_of_threads);
            r.start ();

        }
    }
}

class RunnableDemo implements Runnable {

    private Thread t;
    // private String threadName;
    
    private int threadName;
    private int array [];
    private int find;
    private int number_of_total_threads;

    RunnableDemo (int name, int array [], int find, int number_of_total_threads) {

        threadName = name;
        this.array = array;
        this.find = find;
        this.number_of_total_threads = number_of_total_threads;

        System.out.println ("Creating " + threadName);

    }

    public void run () {

        System.out.println ("Running " + threadName);
        
        try {

            //Round Robin    
            for (int i = threadName - 1; i < array.length; i += this.number_of_total_threads) {
                
                System.out.println ("Thread: " + threadName + ", " + i + " " + array [i] + " " + this.find);
                
                if (array [i] == this.find) {
                    System.out.println ("\t\t\t\tFound " + this.find + " at index " + i + ".");
                }

                // Let the thread sleep for a while.
                Thread.sleep (0);

            }

        } catch (InterruptedException e) {
            System.out.println ("Thread " + threadName + " interrupted.");
        }

        System.out.println ("Thread " + threadName + " exiting.");
    
    }

    public void start () {

        System.out.println ("Starting " + threadName);
        
        if (t == null) {
        
            t = new Thread (this, threadName + "");
            t.start ();
        
        }
    }
}