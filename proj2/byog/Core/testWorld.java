package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;
import org.junit.Test;
import static org.junit.Assert.*;

public class testWorld {
    int WIDTH = 50;
    int HEIGHT = 30;
    TETile[][] world = new TETile[WIDTH][HEIGHT];
    Wall w = new Wall(world, WIDTH, HEIGHT);

    @Test
    public void testCheckToBuildWall() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position grass = new Position(25, 15);
        Position wall = new Position(10, 10);
        world[25][15] = Tileset.GRASS;
        boolean actual1 = w.checkToBuildWall(grass);
        boolean actual2 = w.checkToBuildWall(wall);
        assertTrue(actual1);
        assertFalse(actual2);
    }

    /*
    @Test
    public void testIfSave() {
        Game game = new Game();
        String input1 = "123:q";
        String input2 = " ";
        boolean actual1 = game.ifSave(input1);
        boolean actual2 = game.ifSave(input2);
        assertTrue(actual1);
        assertFalse(actual2);
    }

    @Test
    public void testFindSeed() {
        Game game = new Game();
        String input1 = "";
        String input2 = "qe";
        String input3 = "wr123";
        int actual1 = game.findSeed(input1);
        int actual2 = game.findSeed(input2);
        int actual3 = game.findSeed(input3);
        int expected = 0;
        int expected3 = 123;
        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
        assertEquals(expected3, actual3);
    }
    */
}
