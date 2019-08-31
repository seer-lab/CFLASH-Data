public class Search implements Runnable {

    private Thread t;
    String id;
    int index;
    static String needle = "ABA";
    static String stack = "BBBBBBBBBBBBABABBBBBBBBBBBB";
    static boolean result2 = false;

    Search (String id, int index) {

        this.id = id;
        this.index = index;
        System.out.println ("Creating Thread: " + id);

    }

    // Creates thread
    public void start () {

        System.out.println ("Starting " + id);
        
        if (t == null) {
        
            t = new Thread (this, id);
            t.start ();
        
        }
    }

    public void search () {

        if (! result2) {
        
            int n_size = needle.length ();
            for (int i = index; i <= index + n_size; i ++) {
                
                int j;
                
                for (j = 0; j < n_size; j ++) {
                
                    if (stack.charAt (i + j) != needle.charAt (j)) {
                        break;
                    }

                }
                
                if (j == n_size) {
                    
                    result2 = true;
                    return;
                
                }
            
            }
        }
    }

    //Run search
    public void run () {
        
        try {
            search ();
        } catch (Exception e) {
            System.out.println ("Error!: " + id);
        }
        
        System.out.println ("Thread: " + id + " complete.");
    
    }

    static void search_parallel (String needle, String stack) {
    
        int n_size = needle.length ();
        int s_size = stack.length ();
        int n_threads = (int) Math.floor (s_size / n_size);
        boolean extra = false;
    
        if (s_size > n_size * n_threads) {
            extra = true;
        }
    
        System.out.println ("Stack: " + s_size);
        System.out.println ("Needle: " + n_size);
        System.out.println ("Threads: " + n_threads);
    
        for (Integer i = 0; i < n_threads; i ++) {
            
            Search a = new Search (i.toString (), n_size * i);
            a.start ();
            
            if (extra && i == n_threads - 1) {
            
                i ++;
                Search b = new Search ("Ex:" + i.toString (), n_size - n_threads);
                b.start ();
            
            }
        
        }
    }

    public static void main (String [] args) {

        search_parallel (needle, stack);
        System.out.println (result2);

    }
}