package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

public class GameSaver<T extends AbstractGame> {

    public void saveGame(T game, File file) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(game);
            os.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public T reload(File file) {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            T game = (T) is.readObject();
            return game;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
