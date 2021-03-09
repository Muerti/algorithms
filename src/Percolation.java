import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int [][] narray;
    private final WeightedQuickUnionUF w;
    private int numberOfOpenSites;
    // creates n-by-n grid, with all sites initially blocked

    public Percolation(int n) {
        narray = new int[n][n];
        w = new WeightedQuickUnionUF(n*n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        narray[row][col] = 1;
        numberOfOpenSites++;

        int size = narray.length - 1;
        int flatIndex = row * size + col + 1;

        if (row > 0 && isOpen(row - 1, col))
            w.union(flatIndex, (row - 1) * size + col);

        if (row < size && isOpen(row + 1, col))
            w.union(flatIndex, (row + 1) * size + col);

        if (col > 0 && isOpen(row, col - 1))
            w.union(flatIndex, row * size + col - 1);

        if (col < size && isOpen(row, col+1))
            w.union(flatIndex, row * size + col + 1);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return narray[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        for (int i = 0; i < narray.length; i++) {
            if (w.find(i) == w.find(row * narray.length + col)) {
                return true;
            }

        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < narray.length; i++) {
            if (isFull(narray.length - 1, i)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}