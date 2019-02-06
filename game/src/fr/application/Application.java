package fr.application;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import fr.drawables.Character;
import fr.drawables.Drawable;
import fr.map.GameMap;
import fr.util.time.Timer;

public class Application implements Runnable {
	private final static int spawnPlaces[][] = { { 120, 120 } };

	private static final int TILE_SIZE = 120;

	private Map<Integer, Character> players;

	private List<Drawable> drawables;

	private GameMap map;

	private Timer timerApploop;

	private Thread myThread;

	public Application(int width, int height) {
		players = new HashMap<>();
		drawables = new Vector<>();
		timerApploop = new Timer();
		myThread = new Thread(this);
		map = new GameMap("/maps/1.bmap", TILE_SIZE);
	}

	public int addPlayer(Color c) {
		int id = Math.abs((new Random()).nextInt());
		while (players.containsKey(id))
			id++;

		int spd = 500;

		int sp[] = spawnPlaces[(new Random().nextInt(spawnPlaces.length))];
		if (players.size() == 0)
			players.put(id, new Character(sp[0], sp[1], spd));
		else
			players.put(id, new Character(sp[0], sp[1], spd));
		drawables.add(players.get(id));
		return id;
	}

	public void deletePlayer(int id) {
		Character c = players.get(id);
		players.remove(id);
		drawables.remove(c);
	}

	public List<Drawable> getDrawables() {
		return drawables;
	}

	public GameMap getMap() {
		return map;
	}

	public void managePlayer(int id, List<Integer> cliKeys) {
		players.get(id).setMove(cliKeys);
	}

	@Override
	public void run() {
		timerApploop.tick();
		while (!Thread.currentThread().isInterrupted()) {

			for (Character b : players.values()) {
				b.manage(this, timerApploop.lastTickS());
			}

			// wait
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			timerApploop.tick();
		}
	}

	public void start() {
		myThread.start();
	}
}
