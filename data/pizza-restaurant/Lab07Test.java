import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Lab07Test {

    @Test
    public void testPizzaBusinessConstrucor () {

        int pizzaMadeExpected = 0;
        int pizzaSoldExpected = 0;
        int indexExpected = 0;
        PizzaBusiness testBusiness = new PizzaBusiness ();
        
        assertEquals (pizzaMadeExpected, testBusiness.pizzaMade);
        assertEquals (pizzaSoldExpected, testBusiness.pizzaSold);
        assertEquals (indexExpected, testBusiness.index);
    
    }

    @Test
    public void testPizzaThreadConstructor () {

        PizzaBusiness testBusiness = new PizzaBusiness ();
        String expectedName = "maker";
        boolean expectedPrint = true;
        String [] expectedChoices = new String [] {"cheese", "pepperoni", "veggie", "meat lovers"};
        PizzaThread testThreads = new PizzaThread ("maker", testBusiness);
        
        assertEquals (expectedName, testThreads.workerName);
        assertEquals (expectedPrint, testThreads.printWait);
        assertEquals (expectedChoices [0], testThreads.choices [0]);
    }

    @Test
    public void testMakePizza () {

        int expectedPizzaMade = 1;
        PizzaBusiness testBusiness = new PizzaBusiness ();
        PizzaThread testThreads = new PizzaThread ("maker", testBusiness);
        testThreads.makePizza ();
        
        assertEquals (expectedPizzaMade, testBusiness.pizzaMade);
    
    }

    @Test
    public void testSellPizza () {

        int expectedPizzaSold = 1;
        PizzaBusiness testBusiness = new PizzaBusiness ();
        PizzaThread testThreads = new PizzaThread ("maker", testBusiness);
        testThreads.makePizza ();
        testThreads.sellPizza ();
        
        assertEquals (expectedPizzaSold, testBusiness.pizzaMade);
    
    }
}