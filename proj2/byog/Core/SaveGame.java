package byog.Core;

import java.io.*;


public class SaveGame {
    String file = "saveWorld.txt";

    public SaveGame() {
    }


    public void writeWorld(String input) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(input);
            out.close();

        } catch (IOException e) {
        }
    }

    public String readWorld() {
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = read.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        return s;
    }

}
