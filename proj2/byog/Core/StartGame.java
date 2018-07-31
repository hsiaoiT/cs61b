package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class StartGame {
    private int WIDTH = 40;
    private int HEIGHT = 35;
    private int TILE_SIZE = 16;
    private static final char[] interact = {'N', 'L', 'Q'};
    int seed;
    private TETile[][] world;
    private PlayGameByKeyboard game;
    private static final char[] playChar = {'W', 'A', 'S', 'D'};
    TERenderer ter = new TERenderer();

    public StartGame() {

        StdDraw.setCanvasSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        /** CS61B: THE Game */
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH/2, HEIGHT/4 * 3, "CS61B: THE GAME");

        /** New Game (N)
         *  Load Game (L)
         *  Quit (Q) */
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH/2, HEIGHT/2, "New Game (N)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 4, "Quit (Q)");

        StdDraw.show();
    }

    public void interact() {
        while (true) {

            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            game = new PlayGameByKeyboard(key);

            if (key == interact[2]) { // Q
                drawFrame("Game over !");
                break;
            }

            if (key == interact[0]) { //'N'
                seed = enterSeed();
                world = game.inputSeed(seed);
                ter = game.ter;
                break;
            }
            if ( key == interact[1]) { //'L'
                StdDraw.pause(500);

                SaveGame file = new SaveGame();
                String seedS = file.readWorld();
                seed = findSeed(seedS);
                world = game.inputSeed(seed);
                ter = game.ter;
                break;
            }
        }
        Position playerStart = game.findPlayer();
        System.out.println("The player is at Position(" + playerStart.x + ", " + playerStart.y + ").");

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 'Q') {
                quit();
                break;
            }
            if (isMove(key)) {
                playerStart = game.play(world, playerStart, key);
                ter.renderFrame(world);

                //System.out.println("The player moves to (" + playerStart.x + ", " + playerStart.y + ").");
                if (world[playerStart.x][playerStart.y].equals(Tileset.UNLOCKED_DOOR)) {
                    StdDraw.pause(1000);
                    //System.out.println("Finished Game!");
                    succeed();
                    //drawFrame("SUCCEED!!");

                    break;
                }

            } else {
                continue;
            }

        }
    }
    private boolean isMove(char key) {
        for (char item : playChar) {
            if (key == item) {
                return true;
            }
        }
        return false;
    }

    private int enterSeed() {
        String input="";

        while (true) {
            drawFrame("Please enter the seed: " + input);
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 'Q') {
                drawFrame("Please enter the seed: " + input + "Q");
                break;
            }
            input += String.valueOf(key);
        }
        System.out.println(input);
        int num = Integer.parseInt(input);
        return num;
    }

    private void drawFrame(String s) {
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH/2, HEIGHT/2, s);
        StdDraw.show();
    }

    /** Finds the number in the input,
     * return random number if there is no number;
     * @param input . */
    private int findSeed(String input) {
        input = input.trim();
        String num = "";
        if( !input.equals("")){
            for(int i=0; i<input.length();i += 1){
                if(input.charAt(i)>=48 && input.charAt(i)<=57){
                    num = num + String.valueOf(input.charAt(i));
                }
            }
        }
        int n;
        if (num.equals("") || input.equals("")) {
            n = 0;
        } else {
            n = Integer.valueOf(num).intValue();
        }
        if (n == 0) {
            Random random = new Random();
            n = random.nextInt();
        }
        return n;
    }
    private void quit() {
        SaveGame quit = new SaveGame();
        quit.writeWorld(String.valueOf(String.valueOf(seed)));
        drawFrame("Game Over!");
    }

    private void succeed() {
        StdDraw.setCanvasSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        /** CS61B: THE Game */
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH/2, HEIGHT/2+1, "Congrats! You succeed!");
        StdDraw.show();
    }

}
