// Parking Lot Monitoringn System
// To implement a parking lot monitoring system that gives us 
// performance benefit and deficit from changing the number of
// vehicles, parking spaces, and threads.

import java.lang.Thread;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ParkingSystem {
    
    public static final int NUM_THREADS = 4;
    public static final int PARKING_SPACES = 20;
    public static Semaphore parking_sem = new Semaphore (PARKING_SPACES, true);
    public static carQueue cQueue;

    public static void main (String [] args) {
        int i;
        cQueue = new carQueue (new car [PARKING_SPACES], - 5, 0);
        Thread threads [] = new Thread [NUM_THREADS];
        System.out.println ("Running parking lot system");
    
        //Threads creation 
        // Parking Lot Thread
        threads [0] = new parkingLot ();
    
        // Car spawner threads
        for (i = 1; i < NUM_THREADS; i ++) {
            threads [i] = new newCar ();
        }

        for (i = 0; i < NUM_THREADS; i ++) {
            threads [i].start ();
        }

        // Used for Thread Creation Timing
        //System.exit(0);
    }

}

class parkingLot extends Thread {

    parkingLot () {
        System.out.println ("Parking lot open for today");
    }

    public void run () {
        boolean lotSize = true;
        int sem_value = - 1;
        try {
            Thread.sleep (2000);
        } catch (Exception e) {
            System.out.println (Thread.currentThread ().getName () + " " + e);
        }
        while (lotSize) {
            sem_value = ParkingSystem.parking_sem.availablePermits ();
            if (ParkingSystem.parking_sem.availablePermits () < ParkingSystem.PARKING_SPACES) {
                ParkingSystem.parking_sem.release ();
            }
            if (ParkingSystem.cQueue.getCurrentLocation () == - 5) {
                ParkingSystem.cQueue.setCurrentLocation (0);
                // Only if current location of the queue is not -5
            } else {
                System.out.println ("Car released");
                // WAIT THE TIME OF THE CAR STAY TIME
                sem_value = ParkingSystem.parking_sem.availablePermits ();
                try {
                    System.out.println ("Time to wait: " + ParkingSystem.cQueue.carStayTime (ParkingSystem.cQueue.getCurrentLocation ()));
                    Thread.sleep (ParkingSystem.cQueue.carStayTime (ParkingSystem.cQueue.getCurrentLocation ()) * 1000);
                } catch (Exception e) {
                    System.out.println (Thread.currentThread ().getName () + " Sleeping " + e);
                    lotSize = false;
                }
            }
            if (sem_value == 1) {
                lotSize = false;
                System.out.println ("LOT FULL, EXIT");
            } else if (sem_value < ParkingSystem.PARKING_SPACES) {
                ParkingSystem.cQueue.setCurrentLocation (ParkingSystem.cQueue.getCurrentLocation () + 1);
            }

        }
    }

}

class newCar extends Thread {

    newCar () {
        System.out.println ("Thread created");
    }

    public void run () {
        boolean lotSize = true;
        int sem_value = 0;
        //System.out.println("Car queue at location: " + ParkingSystem.cQueue.getNextLocation());
        while (lotSize) {
            //System.out.println("Thread running: " + Thread.currentThread().getName());
            // Checking/acquiring semaphore
            sem_value = ParkingSystem.parking_sem.availablePermits ();
            if (ParkingSystem.parking_sem.availablePermits () == 0) {
                lotSize = false;
                System.out.println ("LOT FULL, EXIT SPAWNER: " + Thread.currentThread ().getName ());
            } else {
                if (ParkingSystem.parking_sem.tryAcquire ()) {
                    System.out.println ("Spot available: " + ParkingSystem.parking_sem.availablePermits ());
                } else {
                    System.out.println ("Couldn't acquire semaphore!");
                }
            }
            // Make the car wait a random amount of time
            // between 1 and 4
            int TTS = ThreadLocalRandom.current ().nextInt (1, 4 + 1);
            // Create a new car
            car newCar;
            newCar = new car (TTS);
            // Initialize queue
            if (ParkingSystem.cQueue.getCurrentLocation () == - 5) {
                ParkingSystem.cQueue.addCar (newCar, ParkingSystem.cQueue.getNextLocation ());
                ParkingSystem.cQueue.setNextLocation (ParkingSystem.cQueue.getNextLocation () + 1);
            // Add car
            } else if (ParkingSystem.cQueue.getNextLocation () != ParkingSystem.cQueue.getCurrentLocation ()) {
                ParkingSystem.cQueue.addCar (newCar, ParkingSystem.cQueue.getNextLocation ());
                ParkingSystem.cQueue.setNextLocation (ParkingSystem.cQueue.getNextLocation () + 1);
                if (ParkingSystem.cQueue.getNextLocation () >= ParkingSystem.PARKING_SPACES) {
                    ParkingSystem.cQueue.setNextLocation (0);
                }
            }

            sem_value = ParkingSystem.parking_sem.availablePermits ();
            System.out.println ("Location: " + (ParkingSystem.cQueue.getNextLocation () + 1) + " | " + ParkingSystem.cQueue.getCurrentLocation ());
            
            // Time between spawning cars for a random time between 1-6 seconds
            try {
                System.out.println ("Sleep for few seconds.");
                Thread.sleep (ThreadLocalRandom.current ().nextInt (1, 6 + 1) * 1000);
            } catch (Exception e) {
                System.out.println (Thread.currentThread ().getName () + " " + e);
            }
        }
        //System.out.println(Thread.currentThread().getName());
    }

}

class car {
    private int timeToStay;

    car (int timeToStay) {
        this.timeToStay = timeToStay;
    }

    public int getTime () {
        return timeToStay;
    }

}

class carQueue {
    private car carList [];
    private int currentLocation;
    private int nextLocation;

    carQueue (car [] carList, int currentLocation, int nextLocation) {
        this.carList = carList;
        this.currentLocation = currentLocation;
        this.nextLocation = nextLocation;
    }

    public void addCar (car newCar, int index) {
        this.carList [index] = newCar;
    }

    public int carStayTime (int index) {
        return this.carList [index].getTime ();
    }

    public int getNextLocation () {
        return nextLocation;
    }

    public int getCurrentLocation () {
        return currentLocation;
    }

    public void setNextLocation (int nextLocation) {
        this.nextLocation = nextLocation;
    }

    public void setCurrentLocation (int currentLocation) {
        this.currentLocation = currentLocation;
    }

}