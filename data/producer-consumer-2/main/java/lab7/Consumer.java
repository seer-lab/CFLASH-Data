package lab7;
import java.util.ArrayList;

class Consumer <T> implements Runnable {

    public Manage <T> manager;
    public ArrayList <T> consumed;

    public Consumer (Manage <T> manager) {
        this.manager = manager;
    }

    @Override
    public void run () {

        for (Integer i = (Integer) manager.consume (); i < 10; i = (Integer) manager.consume ()) {
            System.out.println ("Consumed: " + i);
            // consumed.add(i);
        }
        
    }
}

