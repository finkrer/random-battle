package data.missions.randombattle1;

import java.util.LinkedList;
import java.util.ListIterator;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;
import data.missions.scripts.RandomShipGenerator;

public class MissionDefinition implements MissionDefinitionPlugin {
	private void generateFleet(int maxFP, FleetSide side, MissionDefinitionAPI api) {
		int currFP = 0;

		RandomShipGenerator generator = new RandomShipGenerator(api);
		LinkedList ships = new LinkedList();
		
		while (true) {
			String id = generator.getNext();
			int cost = api.getFleetPointCost(id);
			currFP += cost;

			ListIterator iterator = ships.listIterator();
			while (iterator.hasNext()) {
				String otherShip = (String) iterator.next();
				int otherShipCost = api.getFleetPointCost(otherShip);
				if (otherShipCost < cost) {
					iterator.previous();
					break;
				}
			}
			iterator.add(id);

			if (currFP > maxFP)
				break;
		}

		ListIterator iterator = ships.listIterator();
		String flagship = (String) iterator.next();
		api.addToFleet(side, flagship, FleetMemberType.SHIP, true);
		while (iterator.hasNext()) {
			String ship = (String) iterator.next();
			api.addToFleet(side, ship, FleetMemberType.SHIP, false);
		}
	}
	
	public void defineMission(MissionDefinitionAPI api) {
		// Set up the fleets so we can add ships and fighter wings to them.
		// In this scenario, the fleets are attacking each other, but
		// in other scenarios, a fleet may be defending or trying to escape
		api.initFleet(FleetSide.PLAYER, "ISS", FleetGoal.ATTACK, false, 5);
		api.initFleet(FleetSide.ENEMY, "ISS", FleetGoal.ATTACK, true, 5);

		// Set a small blurb for each fleet that shows up on the mission detail and
		// mission results screens to identify each side.
		api.setFleetTagline(FleetSide.PLAYER, "Your forces");
		api.setFleetTagline(FleetSide.ENEMY, "Enemy forces");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Defeat all enemy forces");
		
		// Set up the fleets
		generateFleet(100 + (int)((float) Math.random() * 50), FleetSide.PLAYER, api);
		generateFleet(100 + (int)((float) Math.random() * 50), FleetSide.ENEMY, api);
		
		// Set up the map.
		float width = 24000f;
		float height = 18000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		float minX = -width/2;
		float minY = -height/2;
		
		
		for (int i = 0; i < 50; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 400f; 
			api.addNebula(x, y, radius);
		}
		
		// Add objectives
		api.addObjective(minX + width * 0.25f + 2000, minY + height * 0.25f + 2000, "nav_buoy");
		api.addObjective(minX + width * 0.75f - 2000, minY + height * 0.25f + 2000, "comm_relay");
		api.addObjective(minX + width * 0.75f - 2000, minY + height * 0.75f - 2000, "nav_buoy");
		api.addObjective(minX + width * 0.25f + 2000, minY + height * 0.75f - 2000, "comm_relay");
		api.addObjective(minX + width * 0.5f, minY + height * 0.5f, "sensor_array");
		
		String [] planets = {"barren", "terran", "gas_giant", "ice_giant", "cryovolcanic", "frozen", "jungle", "desert", "arid"};
		String planet = planets[(int) (Math.random() * (double) planets.length)];
		float radius = 100f + (float) Math.random() * 150f;
		api.addPlanet(0, 0, radius, planet, 200f, true);
	}

}





