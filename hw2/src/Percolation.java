import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// TODO: Add any other necessary imports.

public class Percolation {
    boolean[][] sites;
    int size;
    WeightedQuickUnionUF quickUnion;

    int numberOfOpenSites=0;
    int topSite;
    int bottomSite;

    public Percolation(int N) {
        // 用元素index表示site坐标，元素值表示site的开关状态
        // 未给数组元素赋值前默认元素都是false, 符合默认所有 site 都关闭
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        sites = new boolean[N][N];
        size = N;
        quickUnion = new WeightedQuickUnionUF(N*N+2);
        topSite = N*N;
        connectTop();
        bottomSite = N*N+1;
        connectBottom();


    }

    private void connectTop(){
        for(int i=0;i<size;i++){
            quickUnion.union(xyTo1d(0,i), topSite);
        }
    }
    private void connectBottom(){
        for(int i=0;i<size;i++){
            quickUnion.union(xyTo1d(size-1,i), bottomSite);
        }
    }

    public void open(int row, int col) {
        if(isOpen(row,col)){
            return;
        }
        checkIndex(row,col);
        sites[row][col] = true;
        numberOfOpenSites++;
        checkThenConnectIfNeed(row, col);

    }

    private void checkThenConnectIfNeed(int row, int col){
        if(row>0&&isOpen(row-1,col)){
            quickUnion.union(xyTo1d(row-1,col),xyTo1d(row,col));
        }
        if(row+1<size&&isOpen(row+1,col)){
            quickUnion.union(xyTo1d(row+1,col),xyTo1d(row,col));
        }
        if(col>0&&isOpen(row,col-1)){
            quickUnion.union(xyTo1d(row,col-1),xyTo1d(row,col));
        }
        if(col+1<size&&isOpen(row,col+1)){
            quickUnion.union(xyTo1d(row,col+1),xyTo1d(row,col));
        }
    }

    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return sites[row][col];
    }

    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return isOpen(row,col)&&quickUnion.connected(topSite,xyTo1d(row,col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return quickUnion.connected(topSite, bottomSite);
    }


    private int xyTo1d(int x, int y){
        return x*size+y;
    }

    private void checkIndex(int row, int col){
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

}
