package lab7;
import java.awt.font.NumericShaper.Range;
import java.util.function.*;

class Producer <T> implements Runnable {

    public Manage manager;
    public T current_value;

    public Producer (Manage <T> manager) {
        this.manager = manager;
    }

    @Override
    public void run () {

        for (Integer i = 0; i < 11; i ++) {
            manager.produce (i);
        }

    }
}