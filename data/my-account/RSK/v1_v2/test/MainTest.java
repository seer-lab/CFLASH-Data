import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.*;

public class MainTest {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Please provide the filename for the failure log output.");
            System.exit(-1);
        }

        Result result = JUnitCore.runClasses(AccountTest.class);

        // print final test results (e.g. pass/fail)
        System.out.print("\n\nTest run status: ");
        if (result.wasSuccessful()) System.out.println("\u001B[1;32mPASSED\u001B[0m");
        else System.out.println("\u001B[1;31mFAILED\u001B[0m");
        // System.out.println("------------------------------------");
        // System.out.println("Test suite run time: " + result.getRunTime() + " milliseconds");
        // System.out.println("Number of tests ran: " + result.getRunCount());
        // System.out.println("Number of failed tests: " + result.getFailureCount() + " (" + (result.getFailureCount()/result.getRunCount() * 100) + "%)");

        // print failures, if any
        for (Failure failure : result.getFailures()) {
            System.out.println("-> " + failure.toString());
        }

        // output results to file
        String output_filename = args[0];

        FileWriter fw = null;
        try {
            fw = new FileWriter(output_filename, true); // append new data
            if (result.wasSuccessful()) fw.write("P");
            else fw.write("F");
        } catch(IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        System.out.println();
    }
}
