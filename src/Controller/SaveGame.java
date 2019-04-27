package Controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.properties.Property;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SaveGame {

    //call property to setIsOwned
    //save is property is mortgaged

    public void save(AbstractGame game, String file) throws Exception {
        JSONObject data = new JSONObject();
        JSONArray players = new JSONArray();
        JSONArray properties = new JSONArray();

        for(int i = 0; i < game.getPlayers().size(); i++) {
            AbstractPlayer p = game.getPlayers().get(i);
            JSONObject play_o = new JSONObject();
            play_o.put("name", p.getName().toLowerCase());
            play_o.put("funds", p.getFunds());
            play_o.put("location", p.getCurrentLocation());
            play_o.put("token", p.getImage());
            players.add(play_o);
        }

        for(Property p : game.getBank().getOwnedPropsMap().keySet()) {
            JSONObject prop_o = new JSONObject();
            prop_o.put("name", p.getName().toLowerCase());
            prop_o.put("owner", game.getBank().getOwnedPropsMap().get(p).getName().toLowerCase());
            prop_o.put("buildings", p.getBuildingMap());
            prop_o.put("owned", p.getIsOwned());
            prop_o.put("mortgaged", p.getIsMortgaged());
            properties.add(prop_o);
        }

        for(Property p : game.getBank().getUnOwnedProps()) {
            JSONObject prop_o = new JSONObject();
            prop_o.put("name", p.getName().toLowerCase());
            prop_o.put("owner", null);
            prop_o.put("buildings", null);
            prop_o.put("owned", false);
            prop_o.put("mortgaged", false);
            properties.add(prop_o);
        }

        data.put("players", players);
        data.put("properties", properties);

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(data.toJSONString());
            fw.flush();
        }
    }

    public ClassicGame load(String file, String configFile) throws XmlReaderException {
        ClassicGame game = new ClassicGame(configFile);
        JSONParser parser = new JSONParser();
        try {
            JSONObject a = (JSONObject) parser.parse(new FileReader(file));
            List<AbstractPlayer> players = new ArrayList<>();

            JSONArray playerArray = (JSONArray) a.get("players");
            for(Object o : playerArray) {
                JSONObject person = (JSONObject) o;
                AbstractPlayer p = new ClassicPlayer();
                p.setName((String) person.get("name"));
                p.setFunds((Double) person.get("funds"));
                p.setCurrentLocation(((Long) person.get("location")).intValue());
                p.setImage((String) person.get("token"));
                players.add(p);
            }

            game.setPlayers(players);

            JSONArray propertyArray = (JSONArray) a.get("properties");
            for(Object o : propertyArray) {
                JSONObject prop = (JSONObject) o;
                for(Property p : game.getProperties()) {
                    if(p.getName().equals(prop.get("name"))) {
                        p.setIsOwned((boolean) prop.get("owned"));
                        p.setIsMortgaged((boolean) prop.get("mortgaged"));
                        if(prop.get("owner") != null) {
                            for(AbstractPlayer player : game.getPlayers()) {
                                if(player.getName().equals(prop.get("owner"))) {
                                    game.getBank().setPropertyOwner(p, player);
                                    player.addProperty(p);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return game;
    }

    public static void main(String[] args) throws Exception {
        SaveGame sg = new SaveGame();
        String configFile = "Normal_Config_Rework.xml";
        AbstractGame testGame = new ClassicGame(configFile);
        AbstractPlayer p1 = new ClassicPlayer("Dylan", "blah.img");
        AbstractPlayer p2 = new ClassicPlayer("Sahil", "testimage.img");
        List<AbstractPlayer> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        testGame.setPlayers(players);
        String file = "/Users/SGGS1234/Desktop/JSON";
        sg.save(testGame, file);
        sg.load(file, configFile);
    }
}
