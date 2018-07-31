package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

public class PlayGameByKeyboard {
    private char input;
    TERenderer ter = new TERenderer();
    Position player;

    char[] playChar = {'W', 'A', 'S', 'D'};

    public PlayGameByKeyboard(char input) {
        this.input = input;
    }

    public TETile[][] inputSeed(int seed) {
        BuildWorld buildWorld = new BuildWorld(seed);
        TETile[][] finalWorldFrame = buildWorld.built();
        player = buildWorld.player;
        ter.initialize(buildWorld.WIDTH, buildWorld.HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    public Position play(TETile[][] world, Position player, char move) {
        Position newPlayerP;
        TETile playerThe = world[player.x][player.y];
        if (move == playChar[0]) { // 'W'
            TETile newPlayerThe = world[player.x][player.y+1];
            if (!newPlayerThe.equals(Tileset.WALL)) {
                world[player.x][player.y] = Tileset.FLOOR;
                newPlayerP = new Position(player.x, player.y + 1);
                if (newPlayerThe.equals(Tileset.LOCKED_DOOR)) {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.UNLOCKED_DOOR;
                } else {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.PLAYER;
                }
                //System.out.println("The player moves to (" + newPlayerP.x + ", " + newPlayerP.y + ").");
                return newPlayerP;
            } else {
                //System.out.println("The player can't move!");
                return player;
            }
        }

        if (move == playChar[1]) { // 'A'
            TETile newPlayerThe = world[player.x-1][player.y];
            if (!newPlayerThe.equals(Tileset.WALL)) {
                world[player.x][player.y] = Tileset.FLOOR;
                newPlayerP = new Position(player.x-1, player.y);
                if (newPlayerThe.equals(Tileset.LOCKED_DOOR)) {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.UNLOCKED_DOOR;
                    //System.out.println("THE DOOR OPEN");
                } else {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.PLAYER;
                }
                //System.out.println("The player moves to (" + newPlayerP.x + ", " + newPlayerP.y + ").");
                return newPlayerP;
            } else {
                //System.out.println("The player can't move!");
                return player;
            }
        }

        if (move == playChar[2]) { // 'S'
            TETile newPlayerThe = world[player.x][player.y-1];
            if (!newPlayerThe.equals(Tileset.WALL)) {
                world[player.x][player.y] = Tileset.FLOOR;
                newPlayerP = new Position(player.x, player.y-1);
                if (newPlayerThe.equals(Tileset.LOCKED_DOOR)) {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.UNLOCKED_DOOR;
                } else {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.PLAYER;
                }
                //System.out.println("The player moves to (" + newPlayerP.x + ", " + newPlayerP.y + ").");
                return newPlayerP;
            } else {
                //System.out.println("The player can't move!");
                return player;
            }
        } else { // 'D'
            TETile newPlayerThe = world[player.x+1][player.y];
            if (!newPlayerThe.equals(Tileset.WALL)) {
                world[player.x][player.y] = Tileset.FLOOR;
                newPlayerP = new Position(player.x+1, player.y);
                if (newPlayerThe.equals(Tileset.LOCKED_DOOR)) {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.UNLOCKED_DOOR;
                } else {
                    world[newPlayerP.x][newPlayerP.y] = Tileset.PLAYER;
                }
                //System.out.println("The player moves to (" + newPlayerP.x + ", " + newPlayerP.y + ").");
                return newPlayerP;
            } else {
                //System.out.println("The player can't move!");
                return player;
            }
        }
    }

    public Position findPlayer() {
        return player;
    }
}
