import org.junit.Test;
import org.junit.Assert;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.junit.Assert.assertEquals;

public class SearchTest {

    @Test
    public void testOutput () {

        String str = "./Search.java" + "\n" + "WorkerController.java" + "\n" + "Worker.java" + "\n";
        String expectedFileDirectory = "./";
        String expectedPattern = ".java";
        int expectedNumThread = 20;
        WorkerController workerTest;
        
        workerTest = new WorkerController (expectedFileDirectory, ".java", 20);
        
        assertEquals (expectedFileDirectory, workerTest.getDirectory ());
        assertEquals (expectedPattern, workerTest.getPattern ());
        assertEquals (expectedNumThread, workerTest.getNumThread ());
        
        workerTest.startWorker ();
        workerTest.joinThread ();
        
        ConcurrentLinkedQueue <String> results = workerTest.getResults ();
        String testOutput = "";
        
        System.out.println ("Found file's containing the word: " + workerTest.getPattern ());
        
        for (String fileDirectory : results) {
            
            System.out.println (fileDirectory);
            testOutput += fileDirectory + "\n";
        
        }
        
        assertEquals (str, testOutput);

    }
}