//Subclass for threads
class FibThread extends Thread {

    private int n;
    private int fib;
    
    //initializes Thread with fib number desired
    FibThread (int n) {
        this.n = n;
    }

    public void run () {

        try {
            //runs the recursive function fib to begin    
            this.setFib (fibonacci (n));
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    //standard recursive fib
    public int fibonacci (int n) {
        
        if (n < 2) {
            return n;
        }

        return fibonacci (n - 1) + fibonacci (n - 2);

    }

    //getter
    public int getFib () {
        return this.fib;
    }

    //and setter
    public void setFib (int fib) {
        this.fib = fib;
    }

}

//Fibonacci Class
public class Fibonacci {

    public static void main (String [] args) {

        int findFib = 8; //fibonacci number wanted
        System.out.println (runFib (findFib));

    }

    public static int runFib (int findFib) {

        int value = 0; //resulting value of fibonacci
        
        //no negative fib numbers
        if (findFib < 0) {

            //Handles errors
            throw new IllegalArgumentException ();
            //no work required if number is below 2 exclusive
            
        } else if (findFib < 2) {
            
            value = findFib;

        //the multithreaded part
        } else {

            //Initializes thread 1 to handle half the problem
            FibThread thread = new FibThread (findFib - 1);
            thread.start ();

            //Initializes thread 2 to handle the other half
            FibThread thread2 = new FibThread (findFib - 2);
            thread2.start ();

            try {
            
                //waits for threads to finish to allow them to join    
                thread.join ();
                thread2.join ();

                //combines results
                value = thread.getFib () + thread2.getFib ();

            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        //prints final answer
        return value;
    }
}