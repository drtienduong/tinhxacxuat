// v1.0 with 4 core
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors

public class App {
    public static int inp_winrate = 50;
    public static int inp_i = 2;
    public static int inp_n  = 4;
    public static int inp_oneTest = 1000;
    public static int inp_allTest = 2500;
    

    public static void main(String[] args) throws Exception {
        long startTime, endTime;

        // multi thread
        startTime = System.nanoTime();

        thread t1 = new thread(inp_i , inp_n , inp_oneTest , inp_allTest , inp_winrate) ;
        t1.start();
        thread t2 = new thread(inp_i , inp_n , inp_oneTest , inp_allTest , inp_winrate);
        t2.start();
        thread t3 = new thread(inp_i , inp_n , inp_oneTest , inp_allTest , inp_winrate);
        t3.start();
        thread t4 = new thread(inp_i , inp_n , inp_oneTest , inp_allTest , inp_winrate);
        t4.start();
        

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (Exception e) {
            System.out.println(e);
        }

        double[] allrate = mergeArray(t1.getallrate(), t2.getallrate());
        allrate = mergeArray(allrate, t3.getallrate());
        allrate = mergeArray(allrate, t4.getallrate());
        
        writeTotxt(allrate);

        endTime = System.nanoTime();
        System.out.println("run time = " + (endTime - startTime) / 1000000);

    }

    public static double[] mergeArray(double[] a, double[] b) {
        // determines length of firstArray
        int a1 = a.length;
        int b1 = b.length;

        // resultant array size
        int c1 = a1 + b1;

        // create the resultant array
        double[] c = new double[c1];

        // using the pre-defined function arraycopy
        System.arraycopy(a, 0, c, 0, a1);
        System.arraycopy(b, 0, c, a1, b1);

        // prints the resultant array
        // System.out.println(Arrays.toString(c));
        return c;
    }

    public static void writeTotxt(double[] allrate) {
        try {
            String filename = "result_" + String.format("wr_%d_%d_%d_%d_%d", inp_winrate , inp_i, inp_n,inp_oneTest, inp_allTest*4) + ".txt";
            FileWriter myWriter = new FileWriter(filename);
            String str;
            for (int j = 0; j < allrate.length; j++) {
                str = String.format("%.2f", allrate[j]) + "\n";
                myWriter.write(str);
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
