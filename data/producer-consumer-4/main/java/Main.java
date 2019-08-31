import java.util.LinkedList;
import java.util.Random;

public class Main {

    static int data_size = 0;
    static int max_queue_size = 100;

    public static class Producer extends Thread {

        Random rand = new Random ();
        LinkedList data = new LinkedList < Integer > ();
        int produced_count = 0;
        int max_data = 0;
        int last_produced = 0;

        public Producer (LinkedList data, int max_data) {

            this.data = data;
            this.max_data = max_data;

        }

        public int getCount () {
            return this.produced_count;
        }

        public int getLastProduced () {
            return this.last_produced;
        }

        public void run () {
            
            for (int i = 0; i < this.max_data; i ++) {
                
                try {
            
                    synchronized (data) {

                        // since data is shared between threads we must synchronize on its Object Monitor
                        // to ensure only one thread is manipulating it at a time
                        while (data_size >= max_queue_size) {
                            // if the queue is full wait with data's Object Monitor until a 
                            // notify() is sent by the consumer thread.
                            data.wait ();
                        }

                        int produced = rand.nextInt (100);
                        data.add (produced);
                        data_size ++;
                        produced_count ++;
                        this.last_produced = produced;
                        
                        System.out.println ("Produced: " + produced);
                        
                        // notify the Object Monitor, in case the Consumer thread is waiting for new data
                        data.notify ();
                    
                    }
                    
                    Thread.sleep (1);

                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        }

    }

    public static class Consumer extends Thread {

        LinkedList data = new LinkedList < Integer > ();
        int consumed_count = 0;
        int max_data = 0;
        int last_consumed = 0; // used for testing

        public Consumer (LinkedList data, int max_data) {
        
            this.data = data;
            this.max_data = max_data;
        
        }

        public int getCount () {
            return this.consumed_count;
        }

        public int getLastConsumed () {
            return this.last_consumed;
        }

        public void run () {

            for (int i = 0; i < this.max_data; i ++) {
            
                try {
                    
                    synchronized (data) {

                        // since data is shared between threads we must synchronize on its Object Monitor
                        // to ensure only one thread is manipulating it at a time
                        while (data_size == 0) {
                            // if the queue is empty wait with data's Object Monitor until a 
                            // notify() is sent by the producer thread.
                            data.wait ();
                        }
                        
                        int consumed = (Integer) data.poll ();
                        data_size --;
                        consumed_count ++;
                        this.last_consumed = consumed;
                        
                        System.out.println ("Consumed: " + consumed);
                        
                        // notify the Object Monitor, in case the Producer thread is waiting for available
                        // space in the queue
                        data.notify ();
                    }

                    Thread.sleep (1);

                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        }

    }

    public static void main (String [] args) {

        int max_data = 1000;
        
        LinkedList sharedData = new LinkedList <Integer> ();
        Thread producer = new Producer (sharedData, max_data);
        Thread consumer = new Consumer (sharedData, max_data);
        
        try {
        
            producer.start ();
            consumer.start ();
            producer.join ();
            consumer.join ();
        
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }
}