import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static boolean eating = true;
    private static BlockingQueue <Integer> buffet = new ArrayBlockingQueue <> (10);

    public static void main (String [] args) throws InterruptedException {

        //make our producer
        Thread producerThread = new Thread (new Runnable () {

            @Override
            public void run () {

                producer ();
                System.out.println ("BUFFET IS CLOSED, NO MORE FOOD TO MAKE");

            }
        });
        
        //make our consumer
        Thread consumerThread = new Thread (new Runnable () {

            @Override
            public void run () {
                consumer ();
            }
        });
        
        producerThread.start ();
        consumerThread.start ();
        consumerThread.join ();
        producerThread.join ();
    
    }

    private static void producer () {
    
        Random randomCooking = new Random ();
        Helper helper = new Helper ();
    
        while (true) {
    
            if (buffet.size () < 10) {
    
                try {
    
                    synchronized (buffet) {
                        buffet = helper.addToQueue (buffet);
                    }
    
                    Thread.sleep (1000);
    
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }

            }
        }
    }

    private static void consumer () {

        Random randomEating = new Random ();
        Helper helper = new Helper ();
        
        while (true) {
        
            try {
        
                Thread.sleep (500);
        
                if (randomEating.nextInt (5) % 2 == 0) {
        
                    synchronized (buffet) {
        
                        if (buffet.size () > 0) {
                            buffet = helper.removeFromQueue (buffet);
                        } else {
                            System.out.println ("Food is all finished");
                        }
                    }

                } else {
                    System.out.println ("consumer decided not to eat! Dishes left: " + buffet.size ());
                }
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }
}