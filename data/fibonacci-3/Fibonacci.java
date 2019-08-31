import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Fibonacci {

    public static final int nThreads = 35;

    public static void main (String [] args) {

        System.out.println ("Inside : " + Thread.currentThread ().getName ());
        
        int n = 25;
        ExecutorService es = Executors.newFixedThreadPool (nThreads);
        
        System.out.println (MultiThreadFibonacci.fibonacci (n, es));
        
        es.shutdown ();
    
    }

    public static class MultiThreadFibonacci implements Runnable {

        int fib;
        ExecutorService es;

        public MultiThreadFibonacci (int fib) {
            this.fib = fib;
        }

        @Override
        public void run () {
            this.fib = fibonacci (fib, es);
        }

        public static int fibonacci (int fib, ExecutorService es) {

            System.out.println ("Inside : " + Thread.currentThread ().getName ());
            
            if (fib == 1 || fib == 2) {
                return 1;
            }
            
            MultiThreadFibonacci x = new MultiThreadFibonacci (fib - 2);
            x.es = es;
            Future future = es.submit (x);
            
            try {
                future.get ();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace ();
            }
            
            return x.fib + fibonacci (fib - 1, es);
        
        }
    }
}