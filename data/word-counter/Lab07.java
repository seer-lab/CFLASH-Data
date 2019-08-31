import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.Map.Entry;

public class Lab07 {

    private static Map <Character, Integer> charList = new HashMap <Character, Integer> ();
    private static Map <String, Integer> stringList = new HashMap <String, Integer> ();
    private static ArrayList <String> wordList = new ArrayList <String> ();
    private static Semaphore mutex = new Semaphore (1);
    private static int charCount = 0;

    public static class WordThread implements Runnable {
    
        private String word;

        public WordThread (String word) {
            this.word = word;
        }

        public void run () {

            // Try to aquire Mutex
            for (int c = 0; c < word.length (); c ++) {
                
                try {

                    mutex.acquire ();
                    //System.out.println(Thread.currentThread().getId() + " AQUIRED");
                
                    try {

                        // Adds character is not yet found
                        updateChar (word.charAt (c));
                    
                    } finally {
                    
                        mutex.release ();
                        //System.out.println(Thread.currentThread().getId() + " RELEASED");
                    
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        }

        public void updateChar (char toAdd) {

            if (! charList.containsKey (toAdd)) {
                charList.put (toAdd, 0);
            }
            
            // Increments character count by 1
            charList.put (toAdd, charList.get (toAdd) + 1);
            charCount ++;
        }
    }

    public static void main (String [] args) throws IOException {

        // Initialize Counts
        int wordCount = 0;
        
        // Variables for reading file
        File file = new File (args [0]);
        Scanner input = new Scanner (file);
        
        while (input.hasNext ()) {
        
            //System.out.println(input.next());
            wordCount ++;
            wordList.add (input.next ());
            updateString (input.next ());
        
        }
        
        // Threads
        Thread [] tids = new Thread [wordCount];
        
        for (int i = 0; i < wordCount; i ++) {
        
            tids [i] = new Thread (new WordThread (wordList.get (i)));
            tids [i].start ();

        }

        System.out.println ("----------SUMMARY----------");
        System.out.println ("WORDS:");
        
        Set <Entry<String, Integer>> stringSet = stringList.entrySet ();
        
        for (Entry entry : stringSet) {
            System.out.println (entry.getKey () + "\t-----\t" + entry.getValue ());
        }
        
        System.out.println ("Total words: " + wordCount);
        System.out.println ("---------------------------");
        System.out.println ("CHARACTERS:");
        
        Set <Entry <Character, Integer>> hashSet = charList.entrySet ();
        
        for (Entry entry : hashSet) {
            System.out.println (entry.getKey () + "\t-----\t" + entry.getValue ());
        }
        
        System.out.println ("Total characters: " + charCount);
        System.out.println ("---------------------------");
    
    }

    public static void updateString (String toAdd) {
    
        if (! stringList.containsKey (toAdd)) {
            stringList.put (toAdd, 0);
        }
    
        stringList.put (toAdd, stringList.get (toAdd) + 1);
    
    }
}

