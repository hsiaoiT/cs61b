package byog.lab5;

public class Position {
    public int x;
    public int y;
    public Position(int xP, int yP){
        this.x = xP;
        this.y = yP;
    }

    public boolean equalsTo(Position p2) {
        if (p2 == null) {
            return false;
        }
        return x == p2.x && y == p2.y;
    }

    public boolean equalsTo(Position[] p3) {
        for(int i = 0; i < p3.length; i += 1) {
            if(equalsTo(p3[i])) {
                return true;
            }
        }
        return false;
    }
}
