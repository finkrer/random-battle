package data.missions.scripts;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.mission.MissionDefinitionAPI;

public class RandomShipGenerator {
    private final List ships = new ArrayList();
    private final MissionDefinitionAPI api;

    public RandomShipGenerator(MissionDefinitionAPI api) {
        this.api = api;
        populateShipList();
    }

    private void add(String name, String... variants) {
        int weight = (int) Math.ceil(180.0 / api.getFleetPointCost(name + "_" + variants[0]) / variants.length);
        for (int i = 0; i < variants.length; i++) {
            for (int j = 0; j < weight; j++) {
                ships.add(name + "_" + variants[i]);
            }
        }
	}

    public String getNext() {
        int index = (int)(Math.random() * ships.size());
        String id = (String) ships.get(index);
        return id;
    }

    private void populateShipList() {
        add("afflictor", "Strike", "d_pirates_Strike");
        add("apogee", "Balanced");
        add("astral", "Attack", "Elite", "Strike");
        add("atlas2", "Standard");
        add("aurora", "Assault_Support", "Assault", "Balanced", "Escort", "FS", "Strike");
        add("brawler", "Assault", "Elite", "pather_Raider", "tritachyon_Standard");
        add("buffalo2", "Fighter_Support", "FS");
        add("centurion", "Assault");
        add("cerberus", "d_pirates_Shielded", "d_pirates_Standard", "d_Standard", "Hardened", "luddic_path_Attack",
        "Overdriven", "Shielded", "Standard");
        add("colossus2", "Pather");
        add("colossus3", "Pirate");
        add("condor", "Attack", "Strike", "Support");
        add("conquest", "Elite", "Standard");
        add("dominator", "AntiCV", "Assault", "d_Assault", "Fighter_Support", "Outdated", "Support", "XIV_Elite");
        add("doom", "Strike", "Support");
        add("drover", "Strike");
        add("eagle", "Assault", "Balanced", "d_Assault", "xiv_Elite");
        add("enforcer", "Assault", "Balanced", "CS", "d_pirates_Strike", "d_Strike", "Elite", "Escort",
        "Fighter_Support", "Outdated", "Overdriven", "XIV_Elite");
        add("falcon", "Attack", "CS", "d_CS", "p_Strike", "xiv_Elite", "xiv_Escort");
        add("gemini", "Standard");
        add("gremlin", "d_pirates_Strike", "luddic_path_Strike", "Strike");        
        add("gryphon", "FS", "Standard");
        add("hammerhead", "Balanced", "d_CS", "Elite", "Overdriven", "Support");
        add("harbinger", "Strike");
        add("heron", "Attack", "Strike");
        add("hound", "d_pirates_Shielded", "d_pirates_Standard", "d_Standard", "hegemony_Standard",
        "luddic_church_Standard", "luddic_path_Attack", "d_pirates_Overdriven", "Standard");
        add("hyperion", "Attack", "Strike");
        add("kite", "hegemony_Interceptor", "luddic_path_Raider", "luddic_path_Strike", "pirates_Raider",
        "Standard", "Support");
        add("lasher", "Assault", "CS", "d_CS", "luddic_church_Standard", "luddic_path_Raider",
        "Overdriven", "PD", "Standard", "Strike");
        add("legion", "Assault", "Escort", "FS", "Strike", "xiv_Elite");
        add("mora", "Assault", "Strike", "Support");
        add("mule", "d_pirates_Smuggler", "d_pirates_Standard", "d_Standard", "Fighter_Support", "Standard");
        add("medusa", "Attack", "CS", "PD");
        add("monitor", "Escort");
        add("mudskipper2", "CS", "Hellbore");
        add("odyssey", "Balanced");
        add("omen", "PD");
        add("onslaught", "Elite", "Outdated", "Standard", "xiv_Elite");
        add("paragon", "Elite", "Escort", "Raider");
        add("prometheus2", "Standard");
        add("scarab", "Experimental");
        add("shade", "Assault", "d_pirates_Assault");
        add("shrike", "Attack", "p_Attack", "Support");
        add("sunder", "Assault", "CS", "d_Assault", "Overdriven");
        add("tempest", "Attack");
        add("venture", "Balanced", "Outdated");
        add("vigilance", "AP", "FS", "Standard", "Strike");
        add("wayfarer", "Standard");
        add("wolf", "Assault", "CS", "d_Attack", "d_pirates_Attack", "hegemony_Assault", "hegemony_CS",
        "hegemony_PD", "Overdriven", "PD", "Strike");
    }
}