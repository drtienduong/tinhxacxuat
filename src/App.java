
// v2.0 input with array
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors

public class App {
    public static int[] inp_winrate = { 51, 52 , 53 , 54 , 55 };
    public static int[] inp_i = { 3,4,5,6,7,8,9,10 };
    public static int[] inp_n = { 11,26,57,120,247,502,1013,2036 };
    public static int inp_oneTest = 1000;
    public static int inp_allTest = 2500;

    public static void main(String[] args) throws Exception {
        long startTime, endTime;
        startTime = System.nanoTime();

        for (int w = 0; w < inp_winrate.length; w++) {
            for (int i = 0; i < inp_i.length; i++) {
                // multi thread
                thread t1 = new thread(inp_i[i], inp_n[i], inp_oneTest, inp_allTest, inp_winrate[w]);
                t1.start();
                thread t2 = new thread(inp_i[i], inp_n[i], inp_oneTest, inp_allTest, inp_winrate[w]);
                t2.start();
                thread t3 = new thread(inp_i[i], inp_n[i], inp_oneTest, inp_allTest, inp_winrate[w]);
                t3.start();
                thread t4 = new thread(inp_i[i], inp_n[i], inp_oneTest, inp_allTest, inp_winrate[w]);
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

                writeTotxt(allrate, inp_winrate[w], inp_i[i], inp_n[i], inp_oneTest, inp_allTest*4);

                endTime = System.nanoTime();
                System.out.println("one opt = " + (endTime - startTime) / 1000000);

            }
        }

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

    public static void writeTotxt(double[] allrate, int _winrate, int _i, int _n, int _oneTest, int _allTest) {
        try {
            String filename = "result_"
                    + String.format("wr_%d_%d_%d_%d_%d", _winrate, _i, _n, _oneTest, _allTest)
                    + ".txt";
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
