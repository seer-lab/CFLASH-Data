package lab7;

class Manage <T> {

    public boolean ready = true;
    public T current_value;

    public synchronized void produce (T value) {
    
        while (! ready) {
    
            try {
                // System.out.println("waiting for consumption");
                wait ();
            } catch (Exception e) {
                System.out.println (e.getStackTrace ());
            }
    
        }
    
        ready = false;
        current_value = value;
    
        notifyAll ();
    }

    public synchronized T consume () {
    
        while (ready) {
    
            try {

                // System.out.println("waiting for val");
                wait ();

            } catch (Exception e) {
                System.out.println (e.getStackTrace ());
            }
        }

        ready = true;
        
        notifyAll ();
        
        return current_value;
    }
}