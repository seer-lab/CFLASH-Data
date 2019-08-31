import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class runGA {
    //the size of each population
    static int popSize = 30;
    
    //the city to start and end the path at
    static int startCity = 0;
    
    static int stopGen = 20;
    static int numElite = 5;
    
    //number of threads
    static int nThreads = 4;

    public static void main (String [] args) {
        String [] cityNames;
        int [] [] distances;
        File file = new File ("Map Data.csv");
        Scanner input;
        try {
            input = new Scanner (file);
            String cities = input.nextLine ();
            cityNames = cities.split (",");
            distances = new int [cityNames.length] [cityNames.length];
            String line;
            String [] tempSplit;
            int currentRow = 0;
            while (input.hasNextLine ()) {
                line = input.nextLine ();
                tempSplit = line.split (",");
                for (int j = 0; j < cityNames.length; j ++) {
                    distances [currentRow] [j] = Integer.valueOf (tempSplit [j]);
                }
                currentRow ++;
            }
            System.out.println ("\nNumber of Threads: " + nThreads);
            
            //create threads
            ReentrantLock lock = new ReentrantLock ();
            ArrayList < pathData > finalPopulation = new ArrayList < > ();
            ArrayList < pathData > tempSelection = new ArrayList < > ();
            int path [] = new int [9];
            pathData p = new pathData (path);
            tempSelection.add (p);
            Thread threads [] = new Thread [nThreads];
            for (int i = 0; i < nThreads; i ++) {
                threads [i] = new Thread (new GA (cityNames, distances, finalPopulation, tempSelection, popSize, startCity, numElite, stopGen, lock));
                threads [i].start ();
            }
            for (int i = 0; i < nThreads; i ++) {
                threads [i].join ();
            }
            System.out.println ("\n\nFinal Output:\n");
            System.out.println ("Number of Threads: " + nThreads);
            System.out.println ("Size of each Population: " + popSize);
            System.out.println ("Path length: " + (cityNames.length + 1));
            System.out.println ("Start City: " + startCity);
            System.out.println ("Total Generations per thread : " + stopGen);
            System.out.println ("\nBest path found by each thread: ");
            for (pathData i : finalPopulation) {
                i.printPath ();
                System.out.println (" fitness = " + i.getFitness ());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
            System.out.println ("unable to read file");
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }
}

