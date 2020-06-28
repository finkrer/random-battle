package data.missions.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class RandomFleetGenerator {
    private static final Logger log = Global.getLogger(RandomFleetGenerator.class);
    private static final String CONFIG_PATH = "ships.json";
    private final List<String> shipList = new ArrayList<>();
    private final MissionDefinitionAPI api;

    public RandomFleetGenerator(MissionDefinitionAPI api) {
        this.api = api;
        try {
            JSONObject data = Global.getSettings().loadJSON(CONFIG_PATH);
            populateShipList(data);
        } catch (JSONException | IOException ex) {
            log.error(CONFIG_PATH+" not found or corrupt");
        }
    }

    public List<String> generateFleet(int maxFP) {
        int currFP = 0;
        LinkedList<String> ships = new LinkedList<>();

        do {
            String id = getNext();
            int cost = api.getFleetPointCost(id);
            currFP += cost;

            ListIterator<String> iterator = ships.listIterator();
            while (iterator.hasNext()) {
                String otherShip = iterator.next();
                int otherShipCost = api.getFleetPointCost(otherShip);
                if (otherShipCost < cost) {
                    iterator.previous();
                    break;
                }
            }
            iterator.add(id);

        } while (currFP <= maxFP);

        return ships;
    }

    private void populateShipList(JSONObject data) throws JSONException {
        for (Iterator mods = data.keys(); mods.hasNext();) {
            String modName = (String) mods.next();
            JSONObject mod = data.getJSONObject(modName);
            for (Iterator ships = mod.keys(); ships.hasNext();) {
                String ship = (String) ships.next();
                JSONArray variants = mod.getJSONArray(ship);
                String prefix = modName.equals("vanilla") ? "" : mod + "_";
                add(prefix+ship, variants);
            }
        }
    }

    private void add(String name, JSONArray variants) throws JSONException {
        int weight = (int) Math.ceil(180.0 / api.getFleetPointCost(name + "_" + variants.get(0)) / variants.length());
        for (int i = 0; i < variants.length(); i++) {
            for (int j = 0; j < weight; j++) {
                shipList.add(name + "_" + variants.get(i));
            }
        }
    }

    private String getNext() {
        int index = (int) (Math.random() * shipList.size());
        return shipList.get(index);
    }
}