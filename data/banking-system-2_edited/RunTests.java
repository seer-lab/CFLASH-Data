import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.*;

public class RunTests {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(Tests.class);

        // Print final test results (e.g. pass/fail)
        System.out.print("\n\nTest run status: ");
        if (result.wasSuccessful()) System.out.println("\u001B[1;32mPASSED\u001B[0m");
        else System.out.println("\u001B[1;31mFAILED\u001B[0m");

        // Print failures, if any
        for (Failure failure : result.getFailures()) {
            System.out.println("-> " + failure.toString());
        }
        
        System.out.println();

        try {
            // Output results to file
            String outputFilename = args[0];

            FileWriter fileWriter;

            try {

                fileWriter = new FileWriter(outputFilename, true);
                
                // Append new test outcome to the file
                if (result.wasSuccessful()) fileWriter.write("P");
                else fileWriter.write("F");

            } catch(IOException e) {

                System.err.println(e.getMessage());

            } finally {
                
                try {
                    if (fileWriter != null) fileWriter.close();

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            }
        } catch (Exception e) {}
    }
}
