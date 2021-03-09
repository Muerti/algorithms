import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private int[] tarray;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.tarray = new int[trials];
        this.trials = trials;

        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                System.out.println(row + " " + col);
                p.open(row, col);
            }
            this.tarray[t] = p.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double m = 0;
        for (int i = 0; i < trials; i++) {
            m += tarray[i];
        }

        return m / trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double d = 0;
        double x = mean();
        for (int i = 0; i < trials; trials++) {
            d += (tarray[i] - x) * (tarray[i] - x);
        }
        return Math.sqrt(d / trials - 1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}