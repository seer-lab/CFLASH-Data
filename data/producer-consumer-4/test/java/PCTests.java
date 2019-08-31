import org.junit.*;
import static org.junit.Assert.*;
import java.util.LinkedList;

public class PCTests {

    // test that the number of objects produced is the same as the number of objects consumed
    @Test
    public void test1 () {

        LinkedList <Integer> data = new LinkedList <Integer> ();
        Main.Producer producer = new Main.Producer (data, 1000);
        Main.Consumer consumer = new Main.Consumer (data, 1000);
        
        producer.start ();
        consumer.start ();
        
        try {
        
            producer.join ();
            consumer.join ();
        
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        
        assertEquals (producer.getCount (), 1000);
        assertEquals (consumer.getCount (), 1000);

    }

    // test that the last object produced is equal to the last object consumed
    @Test
    public void test2 () {

        LinkedList < Integer > data = new LinkedList < Integer > ();
        Main.Producer producer = new Main.Producer (data, 10);
        Main.Consumer consumer = new Main.Consumer (data, 10);
        
        producer.start ();
        consumer.start ();
        
        try {
        
            producer.join ();
            consumer.join ();
        
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        
        assertEquals (producer.getLastProduced (), consumer.getLastConsumed ());
    }
}