import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
//import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;

class MultithreadTest {

    private Multithread MultithreadTest;
    private Multithread counter = new Multithread ();

    @Before
    public void getval () {
        counter.getval (0, 0);
    }

    @Before
    public void setUp () throws Exception {
        MultithreadTest = new Multithread ();
    }

    @Test
    void testMain () {
        assertTrue ("2 Threads running addOne in parallel should lead to 2", 2);
    }

    private void assertTrue (String string, int i) {
        assertTrue ("2 Threads running addOne in parallel should lead to 2", 2);
    }

}