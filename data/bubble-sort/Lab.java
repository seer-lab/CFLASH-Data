// java Lab <numThreads> <listSize> <randomNumberUpperBound>
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Lab {

    public static class BubbleSort implements Runnable {

        public List <Integer> list;
        int id;

        public BubbleSort (List <Integer> list, int id) {

            this.list = list;
            this.id = id;
        
        }

        public void sort () {

            try {
                System.out.printf ("Doing work in thread %d\n", this.id);
            } catch (Exception e) {}

            for (int i = 0; i < this.list.size () - 1; i ++) {

                for (int j = 0; j < this.list.size () - i - 1; j ++) {

                    if (this.list.get (j) > this.list.get (j + 1)) {

                        int tmp = this.list.get (j);
                        this.list.set (j, this.list.get (j + 1));
                        this.list.set (j + 1, tmp);

                    }
                }
            }
        }

        public void run () {
            this.sort ();
        }

    }

    public static void main (String [] args) {

        Random random = new Random (System.currentTimeMillis ());
        
        int numThreads = Integer.parseInt (args [0]);
        int listSize = Integer.parseInt (args [1]);
        int upperBound = Integer.parseInt (args [2]);
        int stepSize = listSize / numThreads;
        
        ArrayList < Thread > threads = new ArrayList <> ();
        ArrayList < Integer > list = new ArrayList <> ();
        
        for (int i = 0; i < listSize; i ++) {

            list.add (random.nextInt (upperBound));
            System.out.println (list.get (i));

        }
        
        for (int i = 0; i < numThreads; i ++) {
            
            // last thread will handle the case of handling any work that cannot be split evenly amongst all threads
            if (i == (numThreads - 1)) {

                int startIndex = i * stepSize;
                int endIndex = list.size ();

                threads.add (new Thread (new BubbleSort (list.subList (startIndex, endIndex), i)));

            } else {

                int startIndex = i * stepSize;
                int endIndex = i * stepSize + stepSize;
                threads.add (new Thread (new BubbleSort (list.subList (startIndex, endIndex), i)));

            }

            threads.get (i).start ();
        }

        for (int i = 0; i < numThreads; i ++) {
            
            try {
                
                threads.get (i).join ();
                System.out.printf ("Thread %d joined\n", i);
            
            } catch (Exception e) {
                System.err.println (e);
            }
        }

        // now that the list is guaranteed not to be worst case complexity scenario, we can bubble sort it again!!
        Thread lastThread = new Thread (new BubbleSort (list, 0));
        lastThread.start ();

        try {
            
            lastThread.join ();
            
            for (int i = 0; i < list.size (); i ++) {
                System.out.println (list.get (i));
            }

        } catch (Exception e) {
            System.err.println (e);
        }
    }
}