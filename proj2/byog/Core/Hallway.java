package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

public class Hallway {
    int len;
    Position entrance;
    Position exit;

    public Hallway(Position entrance, Position exit) {
        this.entrance = entrance;
        this.exit = exit;
    }

    /** Three ways to build:
     * 1. The row hallways;
     * 2. The volumn hallways;
     * 3. The \ / hallways;
     * @param world
     */
    public void buildWay(TETile[][] world) {
        if (ifStraightRow()) {
            len = Math.abs(entrance.x - exit.x);
            if (entrance.x < exit.x) {
                for (int x = 0; x < len; x += 1) {
                    world[x+entrance.x][entrance.y] = Tileset.TREE;
                }
            } else {
                for (int x = 0; x < len; x += 1) {
                    world[x+exit.x][entrance.y] = Tileset.WATER;
                }
            }
        } else if (ifStraightCol()) {
            len = Math.abs(entrance.y - exit.y);
            if (entrance.y < exit.y) {
                for (int y = 0; y < len; y += 1) {
                    world[entrance.x][y+entrance.y] = Tileset.TREE;
                }
            } else {
                for (int y = 0; y < len; y += 1) {
                    world[entrance.x][y+exit.y] = Tileset.WATER;
                }
            }
        } else {
            if (ifRightBottom()) {
                Position originExit = this.exit;
                this.exit = this.entrance;
                this.entrance = originExit;
            }
            if (ifLeftBottom()) {
                Position originExit = this.exit;
                this.exit = this.entrance;
                this.entrance = originExit;
            }
            Position intersect = new Position(exit.x, entrance.y);
            Hallway entrayInter = new Hallway(entrance, intersect);
            Hallway exitInter = new Hallway(exit, intersect);
            entrayInter.buildWay(world);
            exitInter.buildWay(world);
        }
    }

    private boolean ifStraightRow() {
        if (entrance.y == exit.y) {
            return true;
        }
        return false;
    }

    private boolean ifStraightCol() {
        if (entrance.x == exit.x) {
            return true;
        }
        return false;
    }

    private boolean ifLeftTop() {
        if (entrance.x > exit.x) {
            if (entrance.y < exit.x) {
                return true;
            }
        }
        return false;
    }
    private boolean ifRightBottom() {
        if (entrance.x < exit.x) {
            if (entrance.y > exit.y) {
                return true;
            }
        }
        return false;
    }

    private boolean ifLeftBottom() {
        if (entrance.x > exit.x) {
            if (entrance.y > exit.y) {
                return true;
            }
        }
        return false;
    }
    private boolean ifRightTop() {
        if (entrance.x < exit.x) {
            if (entrance.y < exit.y) {
                return true;
            }
        }
        return false;
    }


}
