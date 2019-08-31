package lab7;

public class App {

    public String getGreeting () {
        return "Hello world.";
    }

    public static void main (String [] args) {

        Manage < Integer > manager = new Manage ();
        
        Producer p = new Producer <Integer> (manager);
        Producer p2 = new Producer <Integer> (manager);
        
        Consumer c = new Consumer <Integer> (manager);
        
        // test.test();
        // test.test();

        (new Thread (p)).start ();
        (new Thread (p2)).start ();
        (new Thread (c)).start ();
        (new Thread (c)).start ();
        
        System.out.println (new App ().getGreeting ());
    
    }
}