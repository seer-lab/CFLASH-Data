import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Helper {

    public Helper () {}

    public BlockingQueue <Integer> addToQueue (BlockingQueue queue) {

        Random randomCooking = new Random ();

        try {

            queue.put (randomCooking.nextInt (5));
            System.out.println (added ());
        
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        return queue;
    }

    public BlockingQueue <Integer> removeFromQueue (BlockingQueue <Integer> queue) {
        
        try {

            Integer value = queue.take ();
            System.out.println (eaten (value, queue.size (), queue));

        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        return queue;
    }

    public String added () {
        return "Producer added a dish";
    }

    public int size (BlockingQueue <Integer> queue) {
        return queue.size ();
    }

    public String eaten (int value, int size, BlockingQueue < Integer > queue) {

        switch (value) {

            case 0 :
                return ("Dish 0: Shrimp & Rice, eaten by consumer! Dishes left: " + size (queue));
            case 1 :
                return ("Dish 1: Mashed Potatoes and Gravey, eaten by consumer! Dishes left: " + size (queue));
            case 2 :
                return ("Dish 2: Spaghetti and Meatballs, eaten by consumer! Dishes left: " + size (queue));
            case 3 :
                return ("Dish 3: Burger & Fries, eaten by consumer! Dishes left: " + size (queue));
            case 4 :
                return ("Dish 4: Salad & Tofu, eaten by consumer! Dishes left: " + size (queue));
            default :
                return "nothing";
        
            }
    }
}