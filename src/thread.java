import java.util.Random;
import java.util.Arrays;
public class thread extends Thread {
    int inp_i, inp_n, inp_oneTest, inp_allTest, inp_winrate;
    double[] allrate = new double[0];

    public thread(int i, int n, int oneTest, int allTest, int winrate) {
        inp_i = i;
        inp_n = n;
        inp_oneTest = oneTest;
        inp_allTest = allTest;
        inp_winrate = winrate;
        allrate = Arrays.copyOf(allrate, allTest);
    }

    public void run() {
        
        int tradeloss, cons, max_cons, k;
        Random rand = new Random();

        for (int test = 0; test < inp_allTest; test++) {
            // this is all test
            tradeloss = 0;
            for (int trade = 0; trade < inp_oneTest; trade++) {
                // this is one test
                cons = 0;
                max_cons = 0;
                for (int i = 0; i < inp_n; i++) {
                    // this is one dice
                    k = rand.nextInt(100) + 1; // get random int from 1 to 100
                    cons = k > inp_winrate ? cons + 1 : 0  ;

                    if (max_cons < cons)
                        max_cons = cons;
                }

                if (max_cons >= inp_i)
                    tradeloss += 1;
            }
            allrate[test] = 1.0 * tradeloss / inp_oneTest * 100;
        }
    }

    public double[] getallrate() {
        return allrate;
    }
}