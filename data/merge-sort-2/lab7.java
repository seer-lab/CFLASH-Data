// This program did not function correctly
class mergeThread extends Thread {

    // create array 

    private int arr [];
    // create constructor
    public mergeThread (int arr []) {
        this.arr = arr;
    }

    // tell threads to use this function
    public void run () {
        mergeSort (arr);
    }

    // merges all split arrays
    public void merge (int arr [], int lowTemp [], int highTemp []) {

        // System.out.println(lowTemp[0] + " " + highTemp[0]);
        int lowIndex = 0;
        int highIndex = 0;
        
        // loop for array of length
        for (int i = 0; i < arr.length; i ++) {
            
            if (lowIndex < lowTemp.length && highIndex < highTemp.length) {
            
                if (lowTemp [lowIndex] < highTemp [highIndex]) {
            
                    arr [i] = lowTemp [lowIndex];
                    lowIndex ++;
            
                } else {
            
                    arr [i] = highTemp [highIndex];
                    highIndex ++;
            
                }
            
            } else {
            
                // check if the lower index is smaller than length of lower temp array
                // if smaller then copy values into old array
                if (lowIndex < lowTemp.length) {
            
                    arr [i] = lowTemp [lowIndex];
                    lowIndex ++;

                // check if the highre index is smaller than length of highre temp array
                // if smaller then copy values into old array
                } else if (highIndex < highTemp.length) {
            
                    arr [i] = highTemp [highIndex];
                    highIndex ++;
            
                }
            }
        }
    }

    // merge sort function
    public void mergeSort (int arr []) {

        if (arr.length > 1) {

            int middle = arr.length / 2;
            int lowTemp [] = new int [middle];
            
            for (int i = 0; i < middle; i ++) {
                lowTemp [i] = arr [i];
            }
            
            int highTemp [] = new int [arr.length - middle];

            for (int i = 0; i < highTemp.length; i ++) {
                highTemp [i] = arr [middle + i];
            }
            
            mergeThread lowThread = new mergeThread (lowTemp);
            mergeThread highThread = new mergeThread (highTemp);
            
            // start threads
            // lowThread.start();
            // highThread.start();
            lowThread.run ();
            highThread.run ();
            
            // Need barrier here
            // try {
            //     lowThread.join();
            //     highThread.join();
            // } catch (InterruptedException ie) {}
            merge (arr, lowTemp, highTemp);

        }
    }
}


public class lab7 {

    // this function is used to pring array
    static void printArray (int arr []) {

        int n = arr.length;
        for (int i = 0; i < n; ++ i) System.out.print (arr [i] + " ");

        System.out.println ();

    }

    public static void main (String [] args) {

        int arr [] = {12, 11, 13, 5, 6, 7};
        
        System.out.println ("Given Array");
        printArray (arr);
        mergeThread ob = new mergeThread (arr);
        
        ob.run ();
        
        System.out.println ("\nSorted array");
        printArray (arr);
    
    }
}