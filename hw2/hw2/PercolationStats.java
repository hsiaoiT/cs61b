package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double[] percol;
    private double mean;
    private double stddev;
    StdRandom random;
    /** Performs T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        percol = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation percolation = pf.make(N);
            int threshold = 0;
            while(!percolation.percolates()) {
                int site = random.uniform(N*N);
                int[] pos = oneDToXy(site);

                while (percolation.isOpen(pos[0], pos[1])) {
                    site = random.uniform(N*N);
                    pos = oneDToXy(site);
                }
                percolation.open(pos[0], pos[1]);
                threshold += 1;
            }
            percol[i] = threshold;
        }

        mean = StdStats.mean(percol);
        stddev = StdStats.stddev(percol);
    }
    private int[] oneDToXy(int site) {
        int[] position = new int[2];
        position[0] = site/N;
        position[1] = site%N;
        return position;
    }
    /** Sample mean of percolation threshold. */
    public double mean() {

        return mean;
    }
    /** Sample standard deviation of percolation threshold. */
    public double stddev() {
        return stddev;
    }

    private double helpConfidence() {
        double twoItem = 1.96 * stddev/Math.pow(T, 0.5);
        return twoItem;
    }
    /** Low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        double twoItem = helpConfidence();
        return mean-twoItem;
    }
    /** High endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        double twoItem = helpConfidence();
        return mean+twoItem;
    }
}
