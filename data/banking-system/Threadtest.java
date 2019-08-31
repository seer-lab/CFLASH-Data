package lab07;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Threadtest {

    @Test
    void test () {

        Multithreading test = new Multithreading ("loan");
        test.loan ();
       
        Multithreading test1 = new Multithreading ("deposit");
        test1.deposit ();
       
        Multithreading test2 = new Multithreading ("withdraw");
        test1.withdraw ();
    
    }
}