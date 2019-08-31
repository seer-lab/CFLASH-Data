import static org.junit.Assert.*;
import org.junit.Test;

public class FibonacciTest {

    //Tests standard number
    @Test
    public void fibTest1 () {
        assertEquals (21, Fibonacci.runFib (8));
    }

    //Tests large number
    @Test
    public void fibTest2 () {
        assertEquals (75025, Fibonacci.runFib (25));
    }

    //Tests a small number
    @Test
    public void fibTest3 () {
        assertEquals (1, Fibonacci.runFib (2));
    }

    //Tests a number <2
    @Test
    public void fibTest4 () {
        assertEquals (0, Fibonacci.runFib (0));
    }

    //Tests a negative number
    @Test
    public void fibTest5 () {
        
        boolean exception = false;
        
        try {
            //Should fail since fibonacci only runs on numbers 0+    
            Fibonacci.runFib (- 1);
        } catch (Exception e) {
            //Exception was caught
            exception = true;
        }

        assertTrue (exception);

    }
}