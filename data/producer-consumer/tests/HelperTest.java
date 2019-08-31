import org.junit.jupiter.api.Test;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    BlockingQueue <Integer> queue = new ArrayBlockingQueue (10);
    
    //test case for adding an item
    @Test
    public void added () {

        Helper helper = new Helper ();
        helper.addToQueue (queue);
    
        assertEquals ("Producer added a dish", helper.added ());
    
    }

    //Test case for removing an item
    @Test
    void name () throws InterruptedException {

        queue.add (1);
        queue.add (5);
        
        Helper helper = new Helper ();
        helper.removeFromQueue (queue);
        
        Integer value = queue.take ();
        
        assertTrue (helper.eaten (value, queue.size (), queue).equals ("Dish 0: Shrimp & Rice, eaten by consumer! Dishes left: " + queue.size ()) || helper.eaten (value, queue.size (), queue).equals ("Dish 1: Mashed Potatoes and Gravey, eaten by consumer! Dishes left: " + queue.size ()) || helper.eaten (value, queue.size (), queue).equals ("Dish 2: Spaghetti and Meatballs, eaten by consumer! Dishes left: " + queue.size ()) || helper.eaten (value, queue.size (), queue).equals ("Dish 3: Burger & Fries, eaten by consumer! Dishes left: " + queue.size ()) || helper.eaten (value, queue.size (), queue).equals ("Dish 4: Salad & Tofu, eaten by consumer! Dishes left: " + queue.size ()) || helper.eaten (value, queue.size (), queue).equals ("nothing"));
    
    }
}