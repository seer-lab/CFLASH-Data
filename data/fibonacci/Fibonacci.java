public class Fibonacci extends Thread {

    private int n;
    public int num;

    public Fibonacci (int n) {
        this.n = n;
    }

    public void run () {

        try {

            Fibonacci n1 = new Fibonacci (n - 1);
            Fibonacci n2 = new Fibonacci (n - 2);
            n1.start ();
            n2.start ();
            n1.join ();
            n2.join ();
            num = n1.num + n2.num;

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public int calc_fib (int start_num) {

        try {

            if (start_num == 0) {
                return 0;
            } else if (n <= 2) {
                return 1;
            }

            Fibonacci f = new Fibonacci (start_num);
            f.start ();
            f.join ();
            
            return f.num;

        } catch (Exception e) {
            e.printStackTrace ();
        }

        return - 1;
    }
}