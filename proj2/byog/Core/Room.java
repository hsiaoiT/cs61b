package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

public class Room {
    int width;
    int height;
    Position p;

    public Room(){
        this.width = 0;
        this.height = 0;
        this.p = null;
    }

    /** Build a room by width*height with an entra and an exit at Position p.*/
    public Room(int width, int height, Position p) {
        this.width = width;
        this.height = height;
        this.p = p;
    }



    /** The rooms starts from the most left and bottom,
     * then the neighbor.
     */
    public Room[] sortRoom(Room[] rooms) {
        Room[] sortR = new Room[rooms.length];
        for (int i = 0; i < rooms.length; i += 1) {
            if (i == 0) {
                sortR[i] = rooms[startRoom(rooms)];
                rooms[startRoom(rooms)] = null;
               // System.out.println("Start room is at (" + sortR[0].p.x + ", " + sortR[0].p.y + ").");
            } else {
                int neighbor = sortR[i-1].roomNeighbor(rooms);
               // System.out.println("The origin is " + rooms[neighbor].p.x + " " + rooms[neighbor].p.y);
                sortR[i] = rooms[neighbor].copyRoom();
                rooms[neighbor] = null;
              //  System.out.println("The " + i + "th room is at (" + sortR[i].p.x + ", " + sortR[i].p.y + ").");
            }
        }
        return sortR;
    }
    /** Finds the start room of rooms. */
    private int startRoom(Room[] rooms) {
        if (rooms == null) {
            return 0;
        }
        int[] xRooms = new int[rooms.length];
        int[] yRooms = new int[rooms.length];
        for (int i = 0; i < rooms.length; i += 1) {
            xRooms[i] = rooms[i].p.x;
            yRooms[i] = rooms[i].p.y;
        }
        int start = 0;
        int xMin = xRooms[0];
        int yMin = yRooms[0];
        for (int i = 0; i < rooms.length; i += 1) {
            if(xRooms[i] < xMin) {
                xMin = xRooms[i];
                yMin = yRooms[i];
                start = i;
            }
        }
        for (int i = 0; i < rooms.length; i += 1) {
            if(xRooms[i] == xMin) {
                if(yRooms[i] < yMin) {
                    yMin = yRooms[i];
                    start = i;
                }
            }
        }
        return start;
    }

    /** Finds the neighbor of the room */
    private int roomNeighbor(Room[] rooms) {
        double realXP = p.x + (width-1)/2;
        double realYP = p.y + (height-1)/2;
        double[] realX = new double[rooms.length];
        double[] realY = new double[rooms.length];
        double[] distancesPow = new double[rooms.length];
        for(int i = 0; i < rooms.length; i += 1) {
            if (rooms[i] == null) {
                continue;
            }
            realX[i] = rooms[i].p.x + (rooms[i].width-1)/2;
            realY[i] = rooms[i].p.y + (rooms[i].height-1)/2;
        }
        for(int i = 0; i < rooms.length; i += 1) {
            if(realX[i] == 0) {
                continue;
            }
            distancesPow[i] = Math.pow((realXP - realX[i]), 2) +
                            Math.pow((realYP- realY[i]), 2);
        }

        double minDistance = -1;
        int neighbor = 0;
        for (int i = 0; i < rooms.length; i += 1) {
            if (distancesPow[i] == 0) {
                continue;
            }
            if (minDistance < 0 || distancesPow[i] < minDistance) {
                minDistance = distancesPow[i];
                neighbor = i;
            }
        }

        return neighbor;
    }

    private Room copyRoom() {
        if (this.p == null) {
            return null;
        }
        Room cR = new Room(this.width, this.height, this.p);
        return cR;
    }
    private Room[] copyRoom(Room[] rooms) {
        if (rooms == null) {
            return null;
        }
        Room[] cRoom = new Room[rooms.length];
        for (int i = 0; i < rooms.length; i += 1) {
            if (rooms[i] == null) {
                cRoom = null;
            } else {
                cRoom[i] = rooms[i].copyRoom();}
        }
        return cRoom;
    }


    /** Build rooms by floor. */
    public void floorRoom(TETile[][] world) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[p.x+x][p.y+y] = Tileset.FLOOR;
            }
        }
    }
}
