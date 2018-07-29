package byog.lab6;
// Check the solution to get the answer.
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();

    }


    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Initialize random number generator
        rand = new Random(seed);
    }


    public String generateRandomString(int n) {
        //Generates random string of letters of length n
        String letters = "";
        for (int i = 0; i < n; i += 1) {
            int ith = rand.nextInt(26);
            letters += CHARACTERS[ith];
        }
        return letters;
    }

    public void drawFrame(String s) {
        //Take the string and display it in the center of the screen
        //If game is not over, display relevant game information at the top of the screen
        int len = s.length();

        StdDraw.clear(Color.BLACK);

        if (!gameOver) {
            Font fontInfo = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(fontInfo);
            String information = "Round: " + round;

            if (playerTurn) {
                information += ": Type";
            } else {
                information += ": Watch";
            }

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(width / 2, height - 1, information);
        }

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();

        StdDraw.clear(Color.BLACK);
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        int len = letters.length();

        for (int i = 0; i < len; i += 1) {
            String letter = String.valueOf(letters.charAt(i));
            drawFrame(letter);
            StdDraw.pause(750);
            drawFrame(" ");
            StdDraw.pause(750);
        }
        StdDraw.clear(Color.BLACK);
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String playerLetters = "";
        drawFrame(playerLetters);

        while (playerLetters.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char letter = StdDraw.nextKeyTyped();
                playerLetters += String.valueOf(letter);
                drawFrame(playerLetters);
            }
        }
        StdDraw.pause(500);
        return playerLetters;
    }


    public void startGame() {
        //TODO: Set any relevant variables before the game starts

        gameOver = false;
        playerTurn = false;
        round = 1;

        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round " + round + "! Good luck!");
            StdDraw.pause(1500);


            String roundString = generateRandomString(round);
            flashSequence(roundString);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);

            if (!userInput.equals(roundString)) {
                gameOver = true;
                drawFrame("Game Over! Final level: " + round);
            } else {
                drawFrame("Correct, well done!");
                StdDraw.pause(1500);
                round += 1;
            }
        }
    }

}
