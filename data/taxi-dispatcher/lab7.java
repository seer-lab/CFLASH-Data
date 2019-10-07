import java.util.ArrayList;
import java.util.Random;
import java.lang.*;
import java.util.concurrent.locks.ReentrantLock;

// Busy customers with places to be
class Customer {

    private int customerId;
    private int location;
    private int destination;

    public Customer (int id, int loc, int dest) {
    
        this.customerId = id;
        this.location = loc;
        this.destination = dest;
    
    }

    // getter functions
    public int getId () {
        return this.customerId;
    }

    public int getLocation () {
        return this.location;
    }

    public int getDestination () {
        return this.destination;
    }

}

// drops customers off at locations
class Taxi implements Runnable {

    private Dispatcher myDispatcher;

    // a taxi's customer that will be dropped off at a location
    private Customer myCustomer;
    
    // location of the taxi
    int location;

    // constructor for taxi thread
    public Taxi (Dispatcher dispatch) {

        // initialize taxi's base
        this.myDispatcher = dispatch;

        // taxi starts off at the taxi garage
        this.location = 0;
    
    }

    // function that will have a taxi go to a locaton
    private void gotoLocation (int dest) {

        try {

            // calculate distance to destination
            int deltaLocation = Math.abs (dest - location);
            
            // go to destination
            Thread.sleep (deltaLocation);
        
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        // update taxi location
        this.location = dest;
    }

    public void run () {

        // check if dispatcher has customers
        while (! this.myDispatcher.checkCustomers ()) {

            // get a customer from dispatch
            this.myCustomer = myDispatcher.dispatchResp ();

            // get taxi to go to customer location
            gotoLocation (this.myCustomer.getLocation ());
            
            System.out.printf ("%s arrived at customer %d location!\n", Thread.currentThread ().getName (), this.myCustomer.getId ());
            
            // deliver customer to their destination
            gotoLocation (this.myCustomer.getDestination ());
            
            System.out.printf ("%s delivered customer %d to their destination (at %d km)!\n", 
            
            Thread.currentThread ().getName (), this.myCustomer.getId (), this.myCustomer.getDestination ());
        
        }

        // return to dispatch once the day is done
        gotoLocation (0);
        
        System.out.printf ("%s has returned to garage\n", Thread.currentThread ().getName ());

    }
}

// generates customers for taxis to drop off
// calls taxis back to garage at the end of the day
class Dispatcher {

    private static ArrayList <Customer> customers = new ArrayList <> ();
    
    // create java locks for threads
    private final ReentrantLock customerLock = new ReentrantLock ();
    private final ReentrantLock checkLock = new ReentrantLock ();
    
    // generates customers to get picked up and dropped off within a range
    public Dispatcher (int numCustomers, int range) {

        Random rand = new Random ();

        // generate random customers
        for (int i = 0; i < numCustomers; i ++) {
            
            int locationDist = Math.abs (rand.nextInt () % range) + 1;
            int destinationDist = Math.abs (rand.nextInt () % range) + 1;
            
            // add customers to list of customers
            customers.add (new Customer (i + 1, locationDist, destinationDist));
        
        }
    }

    public Customer dispatchResp () {

        Customer assignedCustomer = new Customer (0, 0, 0);

        // lock customer list to prent race condition
        customerLock.lock ();

        // get customer from list
        assignedCustomer = customers.remove (0);
        customerLock.unlock ();
        
        System.out.println (Thread.currentThread ().getName () + " will get customer " + 
        assignedCustomer.getId () + " (" + assignedCustomer.getLocation () + " km away)");
        
        return assignedCustomer;
    
    }

    // check for available customers
    public boolean checkCustomers () {

        boolean check;
        checkLock.lock ();
        check = customers.isEmpty ();
        checkLock.unlock ();
        
        return check;
    
    }
}

class lab7 {

    public static void main (String [] args) {

        // initialize dispatcher location and customers
        Dispatcher taxiHQ = new Dispatcher (10, 1500);

        // initialize 5 taxis (threads)
        final int numTaxis = 5;

        Thread taxis [] = new Thread [numTaxis];
        
        // start up taxis for the work day
        for (int i = 0; i < numTaxis; i ++) {
            taxis [i] = new Thread (new Taxi (taxiHQ), "Taxi " + i);
        }

        for (int i = 0; i < numTaxis; i ++) {
            taxis [i].start ();
        }

    }
}