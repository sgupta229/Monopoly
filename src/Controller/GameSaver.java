package Controller;
import java.io.*;

public class GameSaver {

    public void save(AbstractGame game) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Game.ser"));
            os.writeObject(game);
            os.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ClassicGame reload(File file) {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            ClassicGame game = (ClassicGame) is.readObject();
            return game;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        GameSaver g = new GameSaver();
        File f = new File("bob.txt");
        g.reload(f);
    }
}
