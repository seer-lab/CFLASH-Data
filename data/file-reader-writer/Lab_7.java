/*
    A program that reads files in Read_Files folder based on the file names given in fileNames array in main, and then writes the contents of those files into two different files in Write_Files in different ways.

   Combined_File_Threads.txt was written to by each thread after they read their respective file Combined_File_Main.txt was written to by the main thread after it had obtained the values of the files read by each thread.
*/

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Lab_7 {

    // The function that each thread will use, returns a String
    public static class ThreadFunction implements Callable <String> {

        String readFile, writeFile, content;

        ThreadFunction (String in, String out) {

            readFile = in;
            writeFile = out;
            content = "";

        }

        public String call () {

            String line = "";

            // Reading the file that was passed to this thread and putting its contents into content variable
            try {
            
                BufferedReader reader = new BufferedReader (new FileReader (readFile));
            
                while ((line = reader.readLine ()) != null) {
                    content += line + "\n";
                }
            
                reader.close ();
            
            } catch (FileNotFoundException e) {
                System.out.println ("Unable to open file: " + readFile);
            } catch (IOException e) {
                System.out.println ("Error reading file: " + readFile);
            }
            
            // Making the thread sleep based on its thread ID, first thread sleeps the least, last thread sleeps the most
            try {
                Thread.sleep (Thread.currentThread ().getId () * 20);
            } catch (Exception e) {
                System.out.println ("Exception when getting thread to sleep.");
            }

            // Writing to a file, making sure this section is protected by a lock 
            synchronized (this) {

                try {
                    BufferedWriter writer = new BufferedWriter (new FileWriter (writeFile, true));
                    writer.append (content);
                    writer.close ();
                } catch (FileNotFoundException e) {
                    System.out.println ("Unable to open file: " + writeFile);
                } catch (IOException e) {
                    System.out.println ("Error reading file: " + writeFile);
                }

            }
            
            return content;
        
        }
    }

    public static void main (String [] args) throws IOException {

        // Files that will be read
        String [] fileNames = {"Read_Files/File_One.txt", "Read_Files/File_Two.txt", "Read_Files/File_Three.txt", "Read_Files/File_Four.txt", "Read_Files/File_Five.txt"};
        // Files to which the read contents will be written to
        
        String threadWriteFile = "Write_Files/Combined_File_Threads.txt";
        String mainWriteFile = "Write_Files/Combined_File_Main.txt";
        String content = "";
        
        // Clearing the write files
        clearFile (threadWriteFile);
        clearFile (mainWriteFile);

        // Defining and initializing variables related to the threads
        List <Future<String>> list = new ArrayList <Future<String>> ();
        ExecutorService executorService = Executors.newFixedThreadPool (fileNames.length);
        Callable <String> threadFunctions;
        
        // Getting each thread to write a specific file and then write its contents to the
        // file represented by threadWriteFile. I am also obtaining the thread's return values
        for (int i = 0; i < fileNames.length; i ++) {
            
            threadFunctions = new ThreadFunction (fileNames [i], threadWriteFile);
            Future < String > future = executorService.submit (threadFunctions);
            list.add (future);

        }
        
        executorService.shutdown ();

        // Getting the return value of each thread and appending it to content variable
        for (Future < String > f : list) {
            
            try {
                content += f.get ();
            } catch (Exception e) {
                System.out.println ("Exception when getting value of future.");
            }

        }
        
        // Writing the contents of the threads obtained above to the file represented
        // by mainWriteFile
        try {
            
            BufferedWriter writer = new BufferedWriter (new FileWriter (mainWriteFile, true));
            writer.append (content);
            writer.close ();
        
        } catch (FileNotFoundException e) {
            System.out.println ("Unable to open file: " + mainWriteFile);
        } catch (IOException e) {
            System.out.println ("Error reading file: " + mainWriteFile);
        }
        
        checkFileContents (threadWriteFile, mainWriteFile);
    
    }

    // Function that will clear the file called fileName 
    public static void clearFile (String fileName) throws IOException {

        PrintWriter writer = new PrintWriter (fileName);
        writer.print ("");
        writer.close ();

    }

    // I was unable to do/learn JUnit testing due to time constraints so I am using this 
    // function to compare the two files 
    public static void checkFileContents (String fileOne, String fileTwo) throws IOException {

        String line = null, contentOne = "", contentTwo = "";
        BufferedReader reader;

        // Getting contents of the first file
        reader = new BufferedReader (new FileReader (fileOne));
        
        while ((line = reader.readLine ()) != null) {
            contentOne += line + "\n";
        }
        
        reader.close ();
        line = null;

        // Getting contents of the second file
        reader = new BufferedReader (new FileReader (fileTwo));
        
        while ((line = reader.readLine ()) != null) {
            contentTwo += line + "\n";
        }
        
        reader.close ();
        
        System.out.println ("Contents of " + fileOne + "\n" + contentOne);
        System.out.println ("Contents of " + fileTwo + "\n" + contentTwo);
        
        // Checking if the contents of the file are the same
        if (contentOne.equals (contentTwo)) {
            System.out.println ("The two files have the same content.");
        } else {
            System.out.println ("The two files do not have the same content.");
        }

    }
}