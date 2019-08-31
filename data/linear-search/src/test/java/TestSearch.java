import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSearch {

    @Test
    public void testSearchSingleThreaded1 () {

        List < String > list = Arrays.asList ("found1", "notfound2", "notfound3", "notfound4");

        assertTrue (Main.search (list, s -> s.equals ("found1")).size () == 1);

    }

    @Test
    public void testSearchSingleThreaded2 () {

        List < String > list = Arrays.asList ("found1", "notfound2", "notfound3", "notfound4");

        assertEquals (Main.search (list, s -> s.equals ("found1")).get (0), "found1");

    }

    @Test
    public void testSearchSingleThreaded3 () {

        List < String > list = Arrays.asList ("found1", "notfound2", "notfound3", "notfound4");
        
        assertTrue (Main.search (list, s -> s.equals ("notfound1")).size () == 0);
    
    }

    @Test
    public void testSearchMultiThreaded1 () throws Exception {
    
        List < String > list = Arrays.asList ("found1", "notfound2", "notfound3", "notfound4");
    
        assertTrue (Main.searchMT (list, s -> s.equals ("found1")).size () == 1);
    
    }

    @Test
    public void testSearchMultiThreaded2 () throws Exception {
    
        List < String > list = Arrays.asList ("found1", "notfound2", "notfound3", "notfound4");
    
        assertEquals (Main.searchMT (list, s -> s.equals ("found1")).get (0), "found1");
    
    }

    @Test
    public void testSearchMultiThreaded3 () throws Exception {
    
        List < String > list = Arrays.asList ("found1", "notfound2", "notfound3", "notfound4");
    
        assertTrue (Main.searchMT (list, s -> s.equals ("notfound1")).size () == 0);
    
    }
}