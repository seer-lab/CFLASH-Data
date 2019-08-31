import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerController extends Thread {

    Thread [] t;
    private ConcurrentLinkedQueue <File> queue;
    private ConcurrentLinkedQueue <String> results;
    private String directory;
    private String pattern;
    private int numThread;

    //Constructor
    WorkerController (String directory, String pattern, int numThread) {

        this.queue = new ConcurrentLinkedQueue <> ();
        this.results = new ConcurrentLinkedQueue <> ();
        this.numThread = numThread;
        this.pattern = pattern;
       
        this.t = new Thread [this.numThread];
        this.directory = directory;
       
        queue.add (new File (this.directory));
    
    }

    //This function will start the threads by adding the runnable class
    public void startWorker () {

        for (int i = 0; i < numThread; i ++) {
            
            try {
            
                t [i] = new Thread (new Worker (this.queue, this.results, this.pattern, Integer.toString (i)));
                t [i].start ();
                //System.out.println("Thread " + i + " has started!");
                Thread.sleep (10);
            
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
    }

    public void joinThread () {
        
        for (int i = 0; i < numThread; i ++) {
        
            try {
                t [i].join ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
    }

    public String getDirectory () {
        return directory;
    }

    public String getPattern () {
        return pattern;
    }

    public int getNumThread () {
        return numThread;
    }

    public Thread [] getT () {
        return t;
    }

    public ConcurrentLinkedQueue < String > getResults () {
        return results;
    }
}