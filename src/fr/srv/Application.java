package fr.srv;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.screen.Drawable;

public class Application {
	private final static int spawnPlaces[][] = { { 100, 100 }, { 500, 500 }, { 100, 500 }, { 500, 100 },
			{ 250, 250 }, };

	private Map<Integer, Ball> players;

	private List<Drawable> listD;

	public Application(int width, int height) {
		players = new HashMap<>();
		listD = new ArrayList<>();
	}

	public int addPlayer(Color c) {
		int id = Math.abs((new Random()).nextInt());
		while (players.containsKey(id))
			id++;

		int sp[] = spawnPlaces[(new Random().nextInt(spawnPlaces.length))];
		players.put(id, new Ball(sp[0], sp[1], 50, 10, c));
		listD.add(players.get(id));
		return id;
	}

	public List<Drawable> getDrawables() {
		return listD;
	}

	public void managePlayer(int id, List<Integer> keys) {
		players.get(id).move(keys);
	}
}
