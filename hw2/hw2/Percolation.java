package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int N;
    boolean sites[][];
    int numberOfOpenSites;
    WeightedQuickUnionUF nthSitesFromTop;
    WeightedQuickUnionUF nthSites;
    WeightedQuickUnionUF nthSitesFromBottom;

    /** Creates N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        sites = new boolean[N * N][2];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < 2; j += 1) {
                sites[i][j] = false;
            }
        }

        numberOfOpenSites = 0;

        nthSitesFromTop = new WeightedQuickUnionUF(N * N);
        for (int i = 0; i < N - 1; i += 1) {
            nthSitesFromTop.union(i, i + 1);
        }

        //nthSites = new WeightedQuickUnionUF(N * N);

        nthSitesFromBottom = new WeightedQuickUnionUF(N * N);
        for (int i = 0; i < N - 1; i += 1) {
            nthSitesFromBottom.union(i, i + 1);
        }
        for (int i = N * N - N; i < N * N - 1; i += 1) {
            nthSitesFromBottom.union(i, i + 1);
        }
    }

    /** Returns the number of site given the row and column. */
    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    /** open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (row > N-1 || col > N-1) {
            throw new IndexOutOfBoundsException();
        }
        if (N == 1) {
            sites[0][0] = true;
            sites[0][1] = true;
            return;
        }
        int ithSite = xyTo1D(row, col);
        if (!isOpen(row, col)) {
            sites[ithSite][0] = true;
            numberOfOpenSites += 1;
        }
        int[] neighbors = findNeighbors(ithSite);
        if (ithSite < N) {
            sites[ithSite][1] = true;
        }

        for(int neighbor : neighbors) {
            if(isOpen(neighbor)) {
                nthSitesFromTop.union(ithSite, neighbor);
                nthSitesFromBottom.union(ithSite, neighbor);
            }
        }

    }

    /** If the site (row, col) opens. */
    public boolean isOpen(int row, int col) {
        if (row > N-1 || col > N-1) {
            throw new IndexOutOfBoundsException();
        }
        int ithSite = xyTo1D(row, col);
        return sites[ithSite][0];
    }
    private boolean isOpen(int ithSite) {
        return sites[ithSite][0];
    }

    /** Finds neightbors of ithSite as N > 1. */
    private int[] findNeighbors(int ithSite) {
        int[] neighbors;
        //Check the 0 site.
        if (ithSite < 1) {
            neighbors = new int[2];
            neighbors[0] = 1;
            neighbors[1] = ithSite+N;
        }
        // Check the 0th row sites except N-1;
        else if (ithSite < N - 1) {
            neighbors = new int[3];
            neighbors[0] = ithSite-1;
            neighbors[1] = ithSite+1;
            neighbors[2] = ithSite+N;
        }
        // Check (0, N-1);
        else if (ithSite == N -1) {
            neighbors = new int[2];
            neighbors[0] = ithSite-1;
            neighbors[1] = ithSite+N;
        }
        // Check (N-1, 0);
        else if (ithSite == N * (N-1)) {
            neighbors = new int[2];
            neighbors[0] = ithSite-N;
            neighbors[1] = ithSite+1;
        }
        // Check the N-1 th row sites except N^2-1
        else if (ithSite > N*N - N - 1 && ithSite < N*N-1) {
            neighbors = new int[3];
            neighbors[0] = ithSite-N;
            neighbors[1] = ithSite-1;
            neighbors[2] = ithSite+1;
        }
        // Check (N-1, N-1)
        else if (ithSite == N*N-1) {
            neighbors = new int[2];
            neighbors[0] = ithSite-N;
            neighbors[1] = ithSite-1;
        }
        // Check 0th col
        else if (ithSite%N == 0) {
            neighbors = new int[3];
            neighbors[0] = ithSite-N;
            neighbors[1] = ithSite+N;
            neighbors[2] = ithSite+1;
        }
        // Check N-1 th col;
        else if (ithSite%N == N-1) {
            neighbors = new int[3];
            neighbors[0] = ithSite-N;
            neighbors[1] = ithSite+N;
            neighbors[2] = ithSite-1;
        } else {
            neighbors = new int[4];
            neighbors[0] = ithSite+1;
            neighbors[1] = ithSite-1;
            neighbors[2] = ithSite+N;
            neighbors[3] = ithSite-N;
        }
        return neighbors;
    }

    /** If the site (row, col) full. */
    public boolean isFull(int row, int col) {
        if (row > N-1 || col > N-1) {
            throw new IndexOutOfBoundsException();
        }
        int ithSite = xyTo1D(row, col);
        if (ithSite >= N) {
            if (nthSitesFromTop.connected(ithSite, 0)) {
                sites[ithSite][1] = true;
            }
        }
        return sites[ithSite][1];
    }

    /** Returns the number of open sites. */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    /** If the system percolate. */
    public boolean percolates() {
        return nthSitesFromBottom.connected(0, N*N-1) ;
    }

}
