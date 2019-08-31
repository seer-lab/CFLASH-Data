import java.util.concurrent.ConcurrentLinkedQueue;

public class Search {

    public static void main (String [] args) {

        WorkerController workerController;

        if (args.length == 3) {
            workerController = new WorkerController (args [0], args [1], Integer.parseInt (args [2]));
        } else {
            workerController = new WorkerController ("./", ".java", 1);
        }

        workerController.startWorker ();
        workerController.joinThread ();

        ConcurrentLinkedQueue <String> results = workerController.getResults ();

        System.out.println ("Found file's containing the word: " + workerController.getPattern ());

        for (String fileDirectory : results) {
            System.out.println (fileDirectory);
        }

    }
}
