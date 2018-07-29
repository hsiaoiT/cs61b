package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    //public static final int WIDTH = 80;
    //public static final int HEIGHT = 30;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StartGame page = new StartGame();
        page.interact();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        int len = input.length();
        int SEED;
        SaveGame file = new SaveGame();
        if (len < 1) {
            Random random = new Random();
            SEED = random.nextInt();
        } else if (ifSave(input)) {
            SEED = findSeed(input);
            file.writeWorld(input);
        } else if (input.equals("l")) {
            input = file.readWorld();
            SEED = findSeed(input);
        } else {
            SEED = findSeed(input);
        }
        BuildWorld buildWorld = new BuildWorld(SEED);
        TETile[][] finalWorldFrame = buildWorld.built();
        ter.initialize(buildWorld.WIDTH, buildWorld.HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

        private boolean ifSave(String input) {

            int len = input.length();
            if (len < 2) {
                return false;
            }
            char lastChar = input.charAt(len-1);
            if (lastChar == 'q') {
                if (input.charAt(len-2) == ':') {
                    return true;
                }
            }
            return false;
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
}

