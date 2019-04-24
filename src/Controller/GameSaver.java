package Controller;

import Model.AbstractPlayer;
import Model.ClassicPlayer;

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

    public void savePlayer(AbstractPlayer player, File file) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(player);
            os.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ClassicGame game = new ClassicGame("Normal_Config_Rework.xml");
        GameSaver<ClassicGame> gs = new GameSaver<ClassicGame>();
        AbstractPlayer player = new ClassicPlayer();
        game.addPlayer(player);
        File file = new File("/Users/SGGS1234/Desktop/player.ser");
        gs.saveGame(game, file);
    }
}
