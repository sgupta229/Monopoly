package Controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import Model.AbstractPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SaveGame {

    public void save(AbstractGame game, String file) throws Exception {
        JSONArray players = new JSONArray();

        for(int i = 0; i < 4; i++) {
            JSONObject o = new JSONObject();
            o.put("Name", "Sahil");
            o.put("Property", "hi");
            players.add(o);
        }

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(players.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + players);
            fw.flush();
        }
    }

    public void load(String file) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(file));
            for(Object o : a) {
                System.out.println(o);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        SaveGame sg = new SaveGame();
        AbstractGame test = new ClassicGame("Normal_Config_Rework.xml");
        String file = "/Users/SGGS1234/Desktop/JSON";
        sg.save(test, file);
        sg.load(file);
    }

}
