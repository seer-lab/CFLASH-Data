import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable {

    private String threadNum;
    private ConcurrentLinkedQueue <File> queue;
    private ConcurrentLinkedQueue <String> results;
    private String pattern;

    Worker (ConcurrentLinkedQueue <File> queue, ConcurrentLinkedQueue <String> results, String pattern, String threadNum) throws InterruptedException {

        this.queue = queue;
        this.results = results;
        this.pattern = pattern;
        this.threadNum = threadNum;

    }

    @Override
    public void run () {

        try {
        
            while (queue.isEmpty () == false) {
        
                File currentDirectory;
        
                //Locks the queue varaiable
                synchronized (this.queue) {
        
                    if (this.queue.isEmpty ()) {
                        break;
                    }
        
                    currentDirectory = this.queue.remove ();
                }
        
                //System.out.println("Thread: " + threadNum+ " Searching in " + currentDirectory.getAbsolutePath());
                //Check if the current path contains what the pattern has
        
                if (currentDirectory.getAbsolutePath ().toLowerCase ().contains (this.pattern.toLowerCase ())) {
        
                    //If it does lock the result variable
                    synchronized (this.results) {
        
                        try {
                            //Add the path that contains the pattern in the queue
                            this.results.add (currentDirectory.getAbsolutePath ());
                        } catch (Exception e) {
                            e.printStackTrace ();
                        }
                    }
                }
        
                //Checks if the current path is a directory, and if it is add each directory in a queue so that
                // other thread can go through that queue and find it
                if (currentDirectory.isDirectory () || isRoot (currentDirectory)) {
        
                    File childDirectory [] = currentDirectory.listFiles ();
        
                    if (childDirectory != null) {
        
                        for (File i : childDirectory) {
        
                            synchronized (this.queue) {
        
                                try {
                                    this.queue.add (i);
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                }
                            
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    //This function will return a bool val, if the current directory is root or not
    public boolean isRoot (File file) {

        File root [] = File.listRoots ();
        
        for (File i : root) {
        
            if (file.getAbsolutePath ().equals (i.getAbsolutePath ())) {
                return true;
            }

        }
        
        return false;
    }
}