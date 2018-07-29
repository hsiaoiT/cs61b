package byog.lab5;
import javafx.geometry.Pos;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    /** Draws one HexWorld.
     *  @param s the size of Hexagon.
     *  @param t the tile to draw.
     */
    public void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexangon must be at least size 2.");
        }

        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;
            int thisRowX;
            if (yi < s) {
                thisRowX = p.x - yi;
            } else {
                thisRowX = p.x + yi - 2 * s + 1;
            }
            Position thisRowP = new Position(thisRowX, thisRowY);
            int nIthRow = nHexagonRow(yi, s);
            addHexagonRow(world, thisRowP, nIthRow, t);
        }
    }

    /** The nunber of tiles for each row of the hexagon.
     * @param ith the ith row of the hexagon.
     * @param s the size of the hexagon.
     * @return n the number of the tile in the ith row. */
    private int nHexagonRow(int ith, int s) {
        int n;
        if (ith < s) {
            n = s + 2 * ith;
        } else {
            n = 5 * s - 2 * ith - 2;
        }
        return n;
    }

    private static void addHexagonRow(TETile[][] world, Position p, int nIthRow, TETile t) {
        for (int xi = 0; xi < nIthRow; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;

            Random RANDOM = new Random();
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    public static void main(String[] args) {
        int WIDTH = 28;
        int HEIGHT = 30;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        HexWorld hexWorld = new HexWorld();

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        /** SIZE 3 */
        int S = 3;

        Position pMontains1 = new Position(12, 0);
        hexWorld.addHexagon(world, pMontains1, S, Tileset.MOUNTAIN);
        Position pMontains2 = new Position(12, 6);
        hexWorld.addHexagon(world, pMontains2, S, Tileset.MOUNTAIN);
        Position pMontains3 = new Position(12, 12);
        hexWorld.addHexagon(world, pMontains3, S, Tileset.MOUNTAIN);
        Position pMontains4 = new Position(12, 18);
        hexWorld.addHexagon(world, pMontains4, S, Tileset.MOUNTAIN);
        Position pTree1 = new Position(12, 24);
        hexWorld.addHexagon(world, pTree1, S, Tileset.TREE);

        Position pFlowers1 = new Position(7, 3);
        hexWorld.addHexagon(world, pFlowers1, S, Tileset.FLOWER);
        Position pMontains5 = new Position(7, 9);
        hexWorld.addHexagon(world, pMontains5, S, Tileset.MOUNTAIN);
        Position pMontains6 = new Position(7, 15);
        hexWorld.addHexagon(world, pMontains6, S, Tileset.MOUNTAIN);
        Position pGrass1 = new Position(7, 21);
        hexWorld.addHexagon(world, pGrass1, S, Tileset.GRASS);

        Position pGrass2 = new Position(2, 6);
        hexWorld.addHexagon(world, pGrass2, S, Tileset.GRASS);
        Position pGrass3 = new Position(2, 12);
        hexWorld.addHexagon(world, pGrass3, S, Tileset.GRASS);
        Position pMontains7 = new Position(2, 18);
        hexWorld.addHexagon(world, pMontains7, S, Tileset.MOUNTAIN);

        Position pMontains8 = new Position(17, 3);
        hexWorld.addHexagon(world, pMontains8, S, Tileset.MOUNTAIN);
        Position pTree2 = new Position(17, 9);
        hexWorld.addHexagon(world, pTree2, S, Tileset.TREE);
        Position pDesert1 = new Position(17, 15);
        hexWorld.addHexagon(world, pDesert1, S, Tileset.SAND);
        Position pFlowers2 = new Position(17, 21);
        hexWorld.addHexagon(world, pFlowers2, S, Tileset.FLOWER);

        Position pDesert2 = new Position(22, 6);
        hexWorld.addHexagon(world, pDesert2, S, Tileset.SAND);
        Position pTree3 = new Position(22, 12);
        hexWorld.addHexagon(world, pTree3, S, Tileset.TREE);
        Position pFlower3 = new Position(22, 18);
        hexWorld.addHexagon(world, pFlower3, S, Tileset.FLOWER);

        // draws the world to the screen
        ter.renderFrame(world);
    }

}
