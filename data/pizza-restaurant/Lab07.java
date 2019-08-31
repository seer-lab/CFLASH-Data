/*
    Pizza Restaurant example used to demonstrate producer and consumer for threading
    in Java.

    There is a single thread as the "pizza maker" who makes pizzas and then adds
    them to the end of queue, or waiting to make pizza if queue is full at 10.
    There is another thread as the "pizza seller" who sells the pizzas that have

    been added to the queue or is waiting if the queue is empty.
    The methods are synchronized to avoid race conditions and the threads
*/

import java.util.Random;

public class Lab07 {

    public static void main (String [] args) {
        
        //"business" of shared information between threads        
        
        PizzaBusiness restaurant = new PizzaBusiness ();
        PizzaThread pizzaMaker = new PizzaThread ("maker", restaurant);
        PizzaThread pizzaSeller = new PizzaThread ("seller", restaurant);
        
        pizzaMaker.start ();
        pizzaSeller.start ();
    
    }
}

//storage of shared variables
class PizzaBusiness {

    //pizza queue
    public int [] queue;

    //amount of pizzas made
    public int pizzaMade;

    //amount of pizzas sold
    public int pizzaSold;

    //pizza queue index to place pizzas
    public int index;

    PizzaBusiness () {

        queue = new int [10];
        pizzaMade = 0;
        pizzaSold = 0;
        index = 0;

    }
}

class PizzaThread extends Thread {

    //thread to be used
    
    public Thread t;
    public String workerName;
    
    //link to same business of stored information
    public PizzaBusiness rest;

    //if stuck waiting, only prints it once
    public boolean printWait = true;

    //random for pizza type or wait time
    public Random rand = new Random ();
    public String [] choices = new String [] {"cheese", "pepperoni", "veggie", "meat lovers"};

    PizzaThread (String name, PizzaBusiness restaurant) {

        workerName = name;
        rest = restaurant;
        System.out.println (workerName + " has started working");

    }

    public void run () {

        //sets pizza maker thread to make pizzas
        if (workerName == "maker") {

            while (rest.pizzaMade < 50) {
                makePizza ();
            }

            //sets pizza seller thread to sell pizzas
        } else if (workerName == "seller") {

            while (rest.pizzaSold < 50) {
                sellPizza ();
            }

        }

        System.out.println (workerName + " is done working");
    }

    //synchronized to prevent race conditions, makes pizza and adds to queue
    //unless queue full, then waits.
    public synchronized void makePizza () {

        //while queue full
        while (rest.index > 9) {

            try {

                //single print    
                if (printWait) {
                
                    System.out.println ("pizza queue FULL, waiting until space");
                    printWait = false;
                
                }
                
                t.wait ();
            
            } catch (Exception e) { }

        }
        
        try {
        
            //reset pizza print if waiting    
            printWait = true;

            //random pizza type
            rest.queue [rest.index] = rand.nextInt (choices.length);
            
            //associated random delay time to make certain pizza
            sleep (rest.queue [rest.index] * 10);
            rest.pizzaMade ++;
            
            System.out.println ("Pizza #" + rest.pizzaMade + ": " + choices [rest.queue [rest.index]] + " Made!");

            //increase queue index
            rest.index ++;

            //notify other thread if waiting to sell
            t.notify ();
        
        } catch (Exception e) { }

    }

    //synchronized to prevent race conditions, sells pizza and removes from queue
    //unless queue empty, then waits.
    public synchronized void sellPizza () {

        //while queue empty
        while (rest.index < 1) {
            
            try {

                //single print    
                if (printWait) {
                
                    System.out.println ("pizza queue EMPTY, waiting until pizzas made");
                    printWait = false;
                
                }
                
                t.wait ();

            } catch (Exception e) { }
        }

        try {
        
            //random sleep time required to sell pizza,    
            sleep (rand.nextInt (choices.length) * 10);
            rest.pizzaSold ++;

            //reset waiting print message
            printWait = true;

            System.out.println ("Pizza #" + rest.pizzaSold + ": " + choices [rest.queue [0]] + " Sold! ");

            rest.index --;

            //moves queue forward when pizza sold
            for (int i = 0; i < rest.index; i ++) {
                rest.queue [i] = rest.queue [i + 1];
            }

            //notify other thread if waiting to make pizza
            t.notify ();

        } catch (Exception e) { }

    }

    //call to initialize threads
    public void start () {

        if (t == null) {

            //starts threads    
            t = new Thread (this, workerName);
            t.start ();

        }
    }
}
