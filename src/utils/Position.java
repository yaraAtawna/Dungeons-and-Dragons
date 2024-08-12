package utils;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double range(Position other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setPos(int x,int y){
        this.x=x;
        this.y=y;
    }
    @Override
    public int compareTo(Position other) {
        // First compare by x, then by y if x values are equal
        int cmp = Integer.compare(this.x, other.x);
        if (cmp == 0) {
            cmp = Integer.compare(this.y, other.y);
        }
        return cmp;
    }
}
