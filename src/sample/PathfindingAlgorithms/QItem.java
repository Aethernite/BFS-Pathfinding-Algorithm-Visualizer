package sample.PathfindingAlgorithms;

public class QItem {
    private int r;
    private int c;
    private int dist;

    public QItem(int r, int c, int dist) {
        this.r = r;
        this.c = c;
        this.dist = dist;
    }

    public int getRow() {
        return r;
    }

    public int getCol() {
        return c;
    }

    public int getDist() {
        return dist;
    }

    public void setRow(int r) {
        this.r = r;
    }

    public void setCol(int c) {
        this.c = c;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

}
