import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class Main {

    private static <T> List <T> search (List <T> list, Predicate <T> predicate, int start, int end) {
        List <T> results = new ArrayList <> ();
        for (int i = start; i < end; i ++) {
            T t = list.get (i);
            
            if (predicate.test (t)) {
                results.add (t);
            }

        }

        return results;

    }

    public static <T> List <T> search (List <T> list, Predicate <T> predicate) {
        return search (list, predicate, 0, list.size ());
    }

    public static <T> List <T> searchMT (List <T> list, Predicate <T> predicate, int numThreads) throws InterruptedException {

        final List <Thread> threads = new ArrayList <> ();
        final List <T> results = Collections.synchronizedList (new ArrayList <> ());
        final int size = (list.size () / numThreads) + 1;
        
        for (int t = 0; t < numThreads; t ++) {
        
            final int start = t * size;
            final int end = Math.min (start + size, list.size ());
            Thread thread = new Thread (() -> results.addAll (search (list, predicate, start, end)));
        
            threads.add (thread);
            thread.start ();
        
        }
        
        for (Thread thread : threads) {
            thread.join ();
        }
        
        return results;

    }

    public static <T> List <T> searchMT (List <T> list, Predicate <T> predicate) throws InterruptedException {
        
        int numThreads = Runtime.getRuntime ().availableProcessors ();

        return searchMT (list, predicate, numThreads);

    }

    private static class Pair < T1, T2 > {

        public final T1 x;
        public final T2 y;

        public Pair (T1 x, T2 y) {

            this.x = x;
            this.y = y;

        }
    }

    public static <T> Pair <T, Long> measureTime (Callable <T> m) throws Exception {
        
        long start = System.currentTimeMillis ();
        T res = m.call ();
        long elapsed = System.currentTimeMillis () - start;
        
        return new Pair <> (res, elapsed);

    }

    // Performance testing
    private static List <String> generateStrings (int count, int length) {
        
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        List < String > result = new ArrayList <> ();
        Random r = new Random (System.currentTimeMillis ());
        int len = alphabet.length ();
        
        for (int i = 0; i < count; i ++) {
            
            StringBuilder string = new StringBuilder ();
            
            for (int j = 0; j < length; j ++) {
                string.append (alphabet.charAt (r.nextInt (len)));
            }

            result.add (string.toString ());
        }

        return result;

    }

    public static void main (String [] args) {

        // Generate large list of random numbers
        final int stringCount = 10000000;
        System.out.printf ("Generating %d strings, please wait...\r\n", stringCount);
        
        List < String > strings = generateStrings (stringCount, 8);
        System.out.printf ("Randomly generated %d strings.\r\n", strings.size ());
        
        try {
            
            // Search for strings that start with "ab"
            Pair < List < String >, Long > stResult = measureTime (() -> search (strings, s -> s.startsWith ("ab")));
            
            // Print the amount of strings found
            System.out.printf ("ST: Found %d strings (took %dms)\r\n", stResult.x.size (), stResult.y);
            
            // Search for strings that start with "ab"
            Pair < List < String >, Long > mtResult = measureTime (() -> searchMT (strings, s -> s.startsWith ("ab")));
            
            // Print the amount of strings found
            System.out.printf ("MT: Found %d strings (took %dms)\r\n", mtResult.x.size (), mtResult.y);

        } catch (Exception ex) {
            
            System.out.println ("Unable to complete search");
            ex.printStackTrace ();

        }
    }
}