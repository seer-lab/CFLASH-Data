package GeneticAlgorithm;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import static org.junit.jupiter.api.Assertions.*;

class GATest {

    @Test
    void ranPath() {

        String[] cityNames = {"Toronto", "Oshawa", "Whitby"};
        int[][] distances = new int[3][3];
        
        distances[0][0] = 2;
        distances[0][1] = 2;
        distances[0][2] = 2;

        distances[1][0] = 2;
        distances[1][1] = 2;
        distances[1][2] = 2;

        distances[2][0] = 2;
        distances[2][1] = 2;
        distances[2][2] = 2;

        ReentrantLock lock = new ReentrantLock();
        ArrayList<pathData> finalPopulation = new ArrayList<>();

        //the size of each population
        int popSize = 3;

        //the city to start and end the path at
        int startCity = 0;
        GA ga = new GA(cityNames, distances,finalPopulation,popSize,startCity,lock);
        
        assertEquals(cityNames.length+1,ga.ranPath().length);
    }

    @Test
    void initialize(){
        
        String[] cityNames = {"Toronto", "Oshawa", "Whitby"};
        int[][] distances = new int[3][3];
        
        distances[0][0] = 2;
        distances[0][1] = 2;
        distances[0][2] = 2;

        distances[1][0] = 2;
        distances[1][1] = 2;
        distances[1][2] = 2;

        distances[2][0] = 2;
        distances[2][1] = 2;
        distances[2][2] = 2;

        ReentrantLock lock = new ReentrantLock();
        ArrayList<pathData> finalPopulation = new ArrayList<>();

        //the size of each population
        int popSize = 3;

        //the city to start and end the path at
        int startCity = 0;
        GA ga = new GA(cityNames, distances,finalPopulation,popSize,startCity,lock);
        ga.initialization();
        assertEquals(popSize,ga.getPopulationSize());

    }

    @Test
    void main() {

        String[] cityNames;
        int[][] distances;

        File file = new File("Map Data.csv");
        Scanner input;

        try {

            input = new Scanner(file);
            String cities = input.nextLine();
            cityNames = cities.split(",");
            distances = new int[cityNames.length][cityNames.length];

            String line;
            String[] tempSplit;

            int currentRow = 0;

            while (input.hasNextLine()) {

                line = input.nextLine();
                tempSplit = line.split(",");
                
                for (int j = 0; j < cityNames.length; j++) {
                    distances[currentRow][j] = Integer.valueOf(tempSplit[j]);
                }
                
                currentRow++;
            }

            //number of threads
            int nThreads;

            //get the amount of cores available on the cpu
            // Genetic Algorithm is CPU intensive so no point in having more threads then the amount of cores
            nThreads = Runtime.getRuntime().availableProcessors();

            System.out.println("\nNumber of Threads: " + nThreads);

            //create threads
            ReentrantLock lock = new ReentrantLock();
            ArrayList<pathData> finalPopulation = new ArrayList<>();

            //the size of each population
            int popSize = 30;

            //the city to start and end the path at
            int startCity = 0;

            Thread threads[] = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                
                threads[i] = new Thread(new GA(cityNames, distances,finalPopulation,popSize,startCity,lock));
                threads[i].start();
            
            }

            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }

            System.out.println("\n\nFinal Output:\n");
            System.out.println("Size of each Population: " + popSize);
            System.out.println("Path length: " + cityNames.length);
            System.out.println("Start City: " + startCity);
            System.out.println("Best path found by each thread: ");
            
            for (pathData i : finalPopulation) {
                
                i.printPath();
                System.out.println(" fitness = " + i.getFitness());
            
            }

            assertEquals(nThreads,finalPopulation.size());

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("unable to read file");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}