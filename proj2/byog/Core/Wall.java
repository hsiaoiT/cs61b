package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;
import org.junit.Test;

/** Builds the wall for the game.
 *  Checks all the position from the first row,
 *  if the tile is not NOTHING or WALL, build the wall in top/bottom/left/right;
 */
public class Wall {
    private TETile[][] world;
    private int width;
    private int height;

    public Wall(TETile[][] world, int width, int height) {
        this.world = world;
        this.width = width;
        this.height = height;
    }

    /** Checks if need to build wall for p. */
    public boolean checkToBuildWall(Position p) {
        if (world[p.x][p.y].equals(Tileset.NOTHING)) {
            return false;
        }
        if (world[p.x][p.y].equals(Tileset.WALL)) {
            return false;
        }
        return true;
    }
    /** Checks if could build wall at p. */
    private boolean checkCanWall(Position p) {
        return world[p.x][p.y].equals(Tileset.NOTHING);
    }

    private void buildWall(Position p) {
        int xL = p.x - 1;
        int xR = p.x + 1;
        int yTop = p.y + 1;
        int yBottom = p.y - 1;
        Position lTop = new Position(xL, yTop);
        Position left = new Position(xL, p.y);
        Position lBottom = new Position(xL, yBottom);
        Position top = new Position(p.x, yTop);
        Position bottom = new Position(p.x, yBottom);
        Position rTop = new Position(xR, yTop);
        Position right = new Position(xR, p.y);
        Position rBottom = new Position(xR, yBottom);
        Position[] pNeighbor = {lTop, left, lBottom, top, bottom, rTop, right, rBottom};
        for (Position pN : pNeighbor) {
            if (checkCanWall(pN)) {
                world[pN.x][pN.y] = Tileset.WALL;
            }
        }

    }

    public void wall(){
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                Position p = new Position(x, y);
                if (checkToBuildWall(p)) {
                    buildWall(p);
                }
            }
        }
    }

}
