import org.junit.*;
import static org.junit.Assert.*;

public class FibonacciTest {

    private Fibonacci testClass;
    private ActualFibonacci actualClass;

    public void testCalcFib (int n) {

        actualClass = new ActualFibonacci ();
        testClass = new Fibonacci (n);
        
        if (n == 0) assertEquals (0, testClass.calc_fib (n));
        else {
            assertEquals (actualClass.fib (n), testClass.calc_fib (n));
        }

    }

    @Test
    public void test1 () {
        testCalcFib (5);
    }

    @Test
    public void test2 () {
        testCalcFib (12);
    }

    @Test
    public void test3 () {
        testCalcFib (0);
    }

    @Test
    public void test4 () {
        testCalcFib (1);
    }

    @Test
    public void test5 () {
        testCalcFib (-5);
    }
}

class ActualFibonacci {

    public int fib (int n) {
        if (n <= 1) return n;

        return fib (n - 1) + fib (n - 2);
    }
}