
//package Controller;
//
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import javafx.concurrent.Task;
//
//@Deprecated
//public class SaveAndLoadData {
//
//
//    private final String DIRECTORY_NAME = "Game Save Data"+ File.separator;
//
//    /**
//     * Directory where the game files are located!
//     */
//    private final String PATH_ROOT = "." + File.separator + DIRECTORY_NAME ;
//
//
//    private final String SAVE_FILE = "gameData.saveSlot";
//
//    private final String SAVE_FILE_PATH = PATH_ROOT + SAVE_FILE;
//
//    private SaveData data;
//
//
//    /**
//     * Can be called in order to create a save game
//     */
//
////    public void createSaveSlot() {
////        SaveData data = new SaveData(AbstractGame.players, AbstractGame.bank, AbstractGame.board, AbstractGame.spaces, AbstractGame.properties, AbstractGame.currPlayer, AbstractGame.dice, AbstractGame.decks, AbstractGame.diceHistory, AbstractGame.possibleTokens, AbstractGame.numRollsInJail);
////        saveGame(data);
////
////    }
//
//    /**
//     * Method which when called will attempt to save a SaveSlot
//     * to a specified directory.
//     * @param data
//     */
//    private void saveGame(SaveData data) {
//        Task<Void> task = new Task<Void>() {
//            @Override
//            public Void call() throws Exception {
//
//                File root = new File(PATH_ROOT);
//                File file = new File(SAVE_FILE_PATH);
//
//                file.delete();
//                logState("Saving file...");
//                try {
//                    root.mkdirs();
//
//                    FileOutputStream fileOut = new FileOutputStream(SAVE_FILE_PATH);
//                    BufferedOutputStream bufferedStream = new BufferedOutputStream(fileOut);
//                    ObjectOutputStream outputStream = new ObjectOutputStream(bufferedStream);
//
//                    outputStream.writeObject(data);
//                    outputStream.close();
//                    fileOut.close();
//
//                    logState("File saved.");
//
//                } catch (IOException e) {
//                    logState("Failed to save, I/O exception");
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
//    }
//    /**
//     * Method which when called attempts to retrieve the saved data
//     * from a specified directory
//     */
//
//    public void loadGame() {
//        Task<Void> task = new Task<Void>() {
//            @Override
//            public Void call() throws Exception {
//
//                File root = new File(PATH_ROOT);
//                File file = new File(SAVE_FILE_PATH);
//
//                if (root.exists() && file.exists()) {
//                    try {
//                        logState("Loading file");
//
//                        FileInputStream fileIn = new FileInputStream(SAVE_FILE_PATH);
//                        ObjectInputStream inputStream = new ObjectInputStream(fileIn);
//
//                        data = (SaveData) inputStream.readObject();
//                        processSavedData(data);
//                        inputStream.close();
//                        fileIn.close();
//
//                        logState("File loaded");
//
//                    } catch (IOException | ClassNotFoundException e) {
//                        logState("Failed to load! " + e.getLocalizedMessage());
//                    }
//                } else {
//                    logState("Nothing to load.");
//                }
//                return null;
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
//
//    }
////    private void processSavedData(SaveData data){
////        AbstractGame.players = data.getPlayers();
////        AbstractGame.bank = data.getBank();
////        AbstractGame.board = data.getBoard();
////        AbstractGame.bank = data.getBank();
////        AbstractGame.spaces = data.getSpaces();
////        AbstractGame.properties = data.getProperties();
////        AbstractGame.currPlayer = data.getCurrPlayer();
////        AbstractGame.decks = data.getDecks();
////        AbstractGame.diceHistory = data.getDiceHistory();
////        AbstractGame.possibleTokens = data.getPossibleTokens();
////        AbstractGame.numRollsInJail = data.getNumRollsInJail();
////    }
//
//    private String logState(String log){
//        System.out.println("Game Saver: " + log);
//        return log;
//    }
//}
//

