package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.Random;

public class BuildWorld {
    long SEED;
    private Random RANDOM;
    //TERenderer ter = new TERenderer();
    int WIDTH;
    int HEIGHT;
    TETile[][] world;
    Position player = new Position(1, 1);
    /** Check if the WIDTH & HEIGHT > 0, if not
     *  find new value. */
    private void checkWorld() {
        while (WIDTH == 0 || WIDTH < 20) {
            WIDTH = RANDOM.nextInt(100);
        }
        while (HEIGHT == 0 || HEIGHT < 10) {
            HEIGHT = RANDOM.nextInt(50);
        }
    }


    public BuildWorld(long SEED) {
        this.SEED = SEED;
        RANDOM = new Random(SEED);
        WIDTH = RANDOM.nextInt(100);
        HEIGHT = RANDOM.nextInt(50);
        checkWorld();
        //ter.initialize(WIDTH, HEIGHT);
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        System.out.println("The world is built by " + WIDTH + " * " + HEIGHT +'.');
    }

    /** Builds rooms and hallways for the world.
     * 1. Random number's rooms.
     * 2. Random Positions for the rooms sorted.
     * 3. Builds hallways for the rooms.
     * 4. Random widths and heights for the rooms sorted. */
    private void buildRoom() {
        Room room = new Room();
        int nRooms = RANDOM.nextInt(WIDTH*HEIGHT/50);
        /** Checks if has positive number room. */
        while (nRooms < 10 || nRooms > 20) {
            nRooms = RANDOM.nextInt(WIDTH*HEIGHT/20);
        }
        System.out.println("The world has " + nRooms + " rooms.");

        /** Has nRooms rooms at random position. */
        Position[] nP = nPosRooms(nRooms);
        Room[] rooms = new Room[nRooms];
        for (int i = 0; i < rooms.length; i += 1) {
            rooms[i] = new Room(1, 1, nP[i]);
            rooms[i].floorRoom(world);
            //System.out.println("The " + i + "th room is at Position(" + nP[i].x + ", " + nP[i].y + ").");
        }

        /** Sorts rooms by distance. */
        rooms = room.sortRoom(rooms);
        //System.out.println(rooms[0].p.x);

        /** Builds (nRooms-1)hallways for rooms. */
        Hallway[] ways = new Hallway[nRooms-1];
        for (int i = 0; i < nRooms-1; i += 1) {
            int next = i + 1;
            ways[i] = new Hallway(rooms[i].p, rooms[next].p);
        }
        for (Hallway way : ways) {
            way.buildWay(world);
        }
        //System.out.println("Has built hallways");

        /** Gets width and height for rooms from 0th to the last. */
        for (int i = 0; i < nRooms; i += 1) {
            int width = RANDOM.nextInt(7);
            int height = RANDOM.nextInt(7);
            rooms[i] = new Room(width, height, nP[i]);
            while (!checkRoom(rooms[i])) {
                width = RANDOM.nextInt(7);
                height = RANDOM.nextInt(7);
                rooms[i] = new Room(width, height, nP[i]);
            }
            rooms[i].floorRoom(world);
            //System.out.println("The " + i + "th room is is at Position(" + nP[i].x + ", " + nP[i].y + ") by" + rooms[i].width + " * " + rooms[i].height + ".");
        }

        /** Sorts rooms by distance. */
        rooms = room.sortRoom(rooms);

    }

    /** Has n random positions,
     *  all the Positions must be different.
     *  all the Position can not touch the side.*/
    private Position[] nPosRooms(int n) {
        Position[] nPos = new Position[n];
        for(int i = 0; i < n; i += 1) {
            int x = RANDOM.nextInt(WIDTH);
            int y = RANDOM.nextInt(HEIGHT);
            Position posI = new Position(x, y);
            while(posI.equalsTo(nPos)) {
                x = RANDOM.nextInt(WIDTH);
                y = RANDOM.nextInt(HEIGHT);
                posI = new Position(x, y);
            }
            while (touchSide(posI)) {
                x = RANDOM.nextInt(WIDTH);
                y = RANDOM.nextInt(HEIGHT);
                posI = new Position(x, y);
            }
            nPos[i] = posI;
        }
        return nPos;
    }

    private boolean touchSide(Position p) {
        if (p.x < 2 || p.x >= WIDTH - 2) {
            return true;
        }
        if (p.y < 2 || p.y >= HEIGHT - 1) {
            return true;
        }
        return false;
    }

    /** Checks the width and height for room.
     * the room cannot touch other room.
     * the room cannot touch sides. */
    private boolean checkRoom(Room room) {
        if(room.width < 1 || room.height < 1) {
            return false;
        }
        for (int x = 0; x < room.width; x += 1) {
            for (int y = 0; y < room.height; y += 1) {
                if (world[x][y].equals(Tileset.FLOOR)) {
                    return false;
                }
            }
        }
        if(checkRoomRow(room) && checkRoomCol(room)) {
            return true;
        }
        return false;
    }

    /** the room's width height > 1;
     * the room cannot touch right world side. */
    private boolean checkRoomRow(Room room) {
        if (room.p.x + room.width >= WIDTH-1 || room.p.x == 0) {
            return false;
        }
        return true;
    }

    /** the room's height > 1;
     * the room cannot touch world top side. */
    private boolean checkRoomCol(Room room) {
        if (room.p.y + room.height >= HEIGHT - 1) {
            return false;
        }
        return true;
    }

    private void buildWall() {
        Wall wall = new Wall(world, WIDTH, HEIGHT);
        wall.wall();
    }

    private void paintHallways() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y].equals(Tileset.WATER) || world[x][y].equals(Tileset.TREE)) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

  private void finishWorld() {

        int doorY = RANDOM.nextInt(HEIGHT);
        while (!hasWall(doorY) && doorY != 0 && doorY != HEIGHT) {
            doorY = RANDOM.nextInt(WIDTH);
        }
        for (int x = 0; x < WIDTH; x += 1) {
            if (world[x][doorY].equals(Tileset.WALL)) {
                if (world[x+1][doorY].equals(Tileset.WALL)) {
                    if (world[x][doorY+1].equals(Tileset.WALL)) {
                        world[x][doorY+1] = Tileset.LOCKED_DOOR;
                    } else if (world[x][doorY-1].equals(Tileset.WALL)) {
                        world[x][doorY-1] = Tileset.LOCKED_DOOR;
                    }
                } else {
                    world[x][doorY] = Tileset.LOCKED_DOOR;
                }
            //System.out.println("The door is at Position(" + x + ", " + doorY + ").");
            break;
            }
        }
    }

    private boolean hasWall(int y) {
        for (int x = 0; x < WIDTH; x ++) {
            if (world[x][y].equals(Tileset.WALL)) {
                return true;
            }
        }
        return false;
    }

    public void player() {
        int xPos = RANDOM.nextInt(WIDTH);
        int yPos = RANDOM.nextInt(HEIGHT);
        while (!world[xPos][yPos].equals(Tileset.FLOOR)) {
            xPos = RANDOM.nextInt(WIDTH);
            yPos = RANDOM.nextInt(HEIGHT);
        }
        player = new Position(xPos, yPos);
        world[xPos][yPos] = Tileset.PLAYER;

    }

    public TETile[][] built() {
        //BuildWorld buildWorld = new BuildWorld(31231234);
        buildRoom();
        //System.out.println("Has built the room for the world!");
        buildWall();
        //System.out.println("Need to finish the world!!");

        paintHallways();
        finishWorld();
        player();
        //System.out.println("The world has been built!");
        return world;
    }

}
