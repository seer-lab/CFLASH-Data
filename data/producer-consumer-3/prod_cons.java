/*
    Producer and Consumer implemented in java using threads
*/

import java.util.LinkedList;

class buffer {

    LinkedList <Integer> buffer;
    int max;

    public buffer () {

        //linked list as buffer
        buffer = new LinkedList <> ();
        
        //maximum size allowed in buffer
        max = 3;

    }

    /*
        this function will add values 1000 items to the buffer, before adding it will check if the buffer is full, if the buffer is full it will wait for the consumer to consume a value then it will produce more
        it also uses synchonization so that it wont modify the buffer at the same time as the consumer 
    */
    public void add () throws InterruptedException {

        for (int i = 1000; i > 0; i --) {
            
            //waits for synchonization
            synchronized (this) {

                //if the buffer is full it will wait until there is room
                int wait = 0;
                
                while (max <= buffer.size ()) {
                
                    //prints out how many iterations its waited for (making sure its not waiting too long)
                    System.out.println ("Producer waiting: " + wait);
                    wait ++;
                    wait ();
                
                }
                
                System.out.println ("Produced: " + i);
                buffer.add (i);
                
                //notifies the consumer to start consuming 
                notify ();
            }
        }
    }

    /*
        this function will remove 1000 items from the buffer, before removing any items it will ensure the buffer
        
        has enough items to remove, it also uses synchrnization to ensure it wont modify the buffer before the producer is done with it 
    */
    public void consume () throws InterruptedException {
        
        for (int i = 1000; i > 0; i --) {
            
            //waits for synchonization
            synchronized (this) {
            
                //prints out how many iterations its waited for (making sure its not waiting too long)
                int wait = 0;
            
                while (buffer.size () <= 0) {
            
                    System.out.println ("Consumer waiting: " + wait);
                    wait ++;
                    wait ();
            
                }
            
                System.out.println ("Consumed: " + buffer.removeFirst ());
            
                //notifies the producer to start producing 
                notify ();
            }
        }
    }

}

class prod_cons {

    public static void main (String [] args) throws InterruptedException {

        buffer buff = new buffer ();
        
        //a consumer thread which will consume 1000 items every time it has been started
        Thread consThread = new Thread (new Runnable () {

            @Override
            public void run () {
        
                try {
                    buff.consume ();
                } catch (Exception e) {
                    System.out.println (e);
                }
            }
        });
        
        //a producer thread will produce 1000 items every time it has been started 
        Thread prodThread = new Thread (new Runnable () {

            @Override
            public void run () {

                try {
                    buff.add ();
                } catch (Exception e) {
                    System.out.println (e);
                }

            }
        });

        //starts a producer and a consumer
        System.out.println ("prod start");
        prodThread.start ();
        System.out.println ("cons start");
        consThread.start ();
        
        //joins both threads when complete 
        prodThread.join ();
        consThread.join ();

    }
}