/**
 * Junit test for testing the factorial for correct output
 * Note: when both tests are run at the same time, the output is mismatched due
 * to the thread's random order
 */
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class JunitTests {
    
    // Testing the factorial function runs with maximum=100000
    @Test
    public void testFactorial () {
    
        System.out.println ("Testing the factorial function:\n-----------------------------------");
        Factorial f = new Factorial ();
        List < Integer > expected_list = new ArrayList < Integer > ();
        long threadID = 1;
    
        expected_list.add (1);
        expected_list.add (2);
        expected_list.add (6);
        expected_list.add (24);
        expected_list.add (120);
        expected_list.add (720);
        expected_list.add (5040);
        expected_list.add (40320);
    
        assertEquals (expected_list, f.factorial (100000, threadID));
    
    }

    // @Test
    // // Testing how many threads get created, should be equal to NUM_THREADS
    // public void testThreads() {
    //   System.out.println("Testing the thread count function:\n-----------------------------------");
    //   String[] args = null;
    //   Factorial.main(args);
    //   assertEquals(Factorial.NUM_THREADS, Factorial.thread_count);
    // }
    
}

