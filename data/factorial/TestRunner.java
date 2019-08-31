/**
 * Run this class to test Junit test
 */
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main (String [] args) {

        Result result = JUnitCore.runClasses (JunitTests.class);
        
        for (Failure failure : result.getFailures ()) {
            System.out.println (failure.toString ());
        }
        
        System.out.println ("\nTests Ran Successful = " + result.wasSuccessful ());
    
    }
}