import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Random;
import java.util.*;
import java.lang.*;

public class Lab7 {

    public class Simulator extends Thread {

        ArrayList <int []> population;

        public void setPopulation (ArrayList <int []> population) {
            this.population = population;
        }

        public ArrayList <int []> getPopulation () {
            return this.population;
        }

        // Returns an int for the fitness of the individual, the higher the better
        private int fitnessRating (int [] individual) {

            int total = 0;

            for (int i = 0; i < individual.length; i ++) {
                total += individual [i];
            }

            return total;
        }

        private ArrayList <int []> sort () {
            
            ArrayList <Integer> newPopulationFitness = new ArrayList <Integer> ();
            ArrayList <int []> newPopulation = new ArrayList <int []> ();
            
            for (int [] individual : population) {

                int fitness = fitnessRating (individual);
                
                if (newPopulationFitness.size () == 0) {

                    newPopulationFitness.add (0, fitness);
                    newPopulation.add (0, individual);
                    continue;

                }
                
                boolean added = false;

                for (int i = 0; i < newPopulationFitness.size (); i ++) {

                    if (fitness >= newPopulationFitness.get (i)) {

                        newPopulationFitness.add (i, fitness);
                        newPopulation.add (i, individual);
                        added = true;
                        break;

                    }
                }

                if (added == false) {

                    newPopulationFitness.add (fitness);
                    newPopulation.add (individual);

                }
            }

            System.out.println ("Overall island fitness: " + newPopulationFitness.toString ());
            
            return newPopulation;
        }

        private int [] [] crossover (int [] individual1, int [] individual2) {

            Random rand = new Random ();
            int temp;
            int [] child1 = new int [individual1.length];
            int [] child2 = new int [individual1.length];
            
            System.arraycopy (individual1, 0, child1, 0, individual1.length);
            System.arraycopy (individual2, 0, child2, 0, individual1.length);
            
            for (int i = 0; i < individual1.length; i ++) {
                
                if (rand.nextInt (2) == 0) {
                    continue;
                }
                
                temp = child1 [i];
                child1 [i] = child2 [i];
                child2 [i] = temp;

            }

            child1 = mutate (child1);
            child2 = mutate (child2);
            int [] [] result = {child1, child2};
            
            return result;
        
        }

        private int [] mutate (int [] individual) {

            Random rand = new Random ();
            int [] newIndividual = individual;
            
            for (int i = 0; i < newIndividual.length; i ++) {
                
                if (rand.nextInt (10) == 0) {
            
                    if (newIndividual [i] == 1) {
                        newIndividual [i] = 0;
                    } else {
                        newIndividual [i] = 1;
                    }
                }
            }
            
            return newIndividual;
        }

        public void run () {

            population = sort ();
            int topNum = population.size () / 2;
            
            // make sure even population of top individuals
            if ((topNum - 1) % 2 == 0) {
                topNum --;
            }
            
            // replace weakest individuals
            for (int i = 0; i < topNum; i += 2) {
                
                int [] [] offspring = crossover (population.get (i), population.get (i + 1));
                population.set (population.size () - 1 - i, offspring [0]);
                population.set (population.size () - 2 - i, offspring [1]);
            
            }
            
            // System.out.println();
            // for ( int i = 0; i < population.size(); i++){
            //     System.out.println(Arrays.toString(population.get(i)));
            // }
            
            return;
        }
    }

    // Generates a random population
    private ArrayList < int [] > generatePopulation (int individualLength, int popNum) {

        ArrayList < int [] > population = new ArrayList < int [] > ();
        Random rand = new Random ();
        
        // Randomize initial population
        for (int pop = 0; pop < popNum; pop ++) {
            
            int [] individual = new int [individualLength];
            
            for (int gene = 0; gene < individualLength; gene ++) {
            
                if (rand.nextInt (10) == 0) {
                    individual [gene] = 1;
                } else {
                    individual [gene] = 0;
                }
            }
            
            population.add (individual);
        }
        
        return population;
    }

    // Creates a series of "genetic islands" that can have several generations each
    // Allows individuals from each island to crossover with other islands
    // Repeats island process until done
    public Lab7 (int numIslands, int generations, int individualLength, int popNum) throws Exception {

        ArrayList <Simulator> islands = new ArrayList < Simulator > (numIslands);
        for (int g = 0; g < generations; g ++) {

            System.out.println ("Generation " + g);
            
            // Run simulations using threads
            for (int i = 0; i < numIslands; i ++) {
                
                ArrayList <int []> population = new ArrayList < int [] > (popNum);
                
                if (g == 0) {
                    population = generatePopulation (individualLength, popNum);
                } else {
                    population = islands.get (i).getPopulation ();
                }
                
                Simulator simulation = new Simulator ();
                simulation.setPopulation (population);
                simulation.start ();
                
                if (g == 0) {
                    islands.add (simulation);
                } else {
                    islands.set (i, simulation);
                }

            }

            // Create barrier
            for (int i = 0; i < numIslands; i ++) {
                islands.get (i).join ();
            }

            ArrayList <int []> totalPopulation = new ArrayList < int [] > ();
            
            for (int i = 0; i < numIslands; i ++) {
                totalPopulation.addAll (islands.get (i).getPopulation ());
            }

            Collections.shuffle (totalPopulation);

            for (int i = 0; i < numIslands; i ++) {
            
                ArrayList < int [] > newPopulation = new ArrayList < int [] > (totalPopulation.subList (i * popNum, i * popNum + popNum));
                islands.get (i).setPopulation (newPopulation);

            }
        }
    }

    public static void main (String [] args) throws Exception {

        if (args.length == 0) {
        
            System.out.println ("Usage: java Lab7 islands generations arrayLength individialsPerIsland");
            System.exit (1);
        
        }
        
        int arg0 = Integer.valueOf (args [0]);
        
        // islands
        int arg1 = Integer.valueOf (args [1]);
        
        // generations
        int arg2 = Integer.valueOf (args [2]);
        
        // array length
        int arg3 = Integer.valueOf (args [3]);
        
        // pop per island
        Lab7 lab = new Lab7 (arg0, arg1, arg2, arg3);
        
    }
}