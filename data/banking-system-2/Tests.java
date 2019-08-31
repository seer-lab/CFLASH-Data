import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Tests {

    Bank b = new Bank ();

    @Test
    public void testIncorrectBalance () {

        String [] args;
        args = new String [] {"-1", "2"};
    
        assertEquals ("Error: Initial balance should be positive.", b.main (args));
    
    }

    @Test
    public void testIncorrectThreadCount () {

        String [] args;
        args = new String [] {"1", "-1"};
        
        assertEquals ("Error: Should have at least 2 threads.", b.main (args));
    
    }

    @Test
    public void testCorrectOutput () {

        String [] args;
        args = new String [] {"1000", "2"};
        
        assertEquals (true, b.main (args).matches ("Final balance: $[0-9]+"));
    
    }
}