package fr.application;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.itemsApp.character.Character;
import fr.map.GameMap;
import fr.scale.Scale;
import fr.util.time.Timer;

public class Application implements Runnable {
	private final static int spawnPlaces[][] = { { (int) (120 * Scale.getScale()), (int) (120 * Scale.getScale()) } };

	private Map<Integer, Character> players;

	private List<Drawable> drawables;
	private List<Manageable> manageables;

	private GameMap map;

	private Timer timerApploop;

	private Thread myThread;

	public Application(int width, int height) {
		players = new HashMap<>();
		drawables = new Vector<>();
		manageables = new Vector<>();
		timerApploop = new Timer();
		myThread = new Thread(this);
		map = new GameMap("/maps/1.bmap");
	}

	public void addDrawable (Drawable d) {
		drawables.add(d);
	}

	public void addManageable(Manageable m) {
		manageables.add(m);
	}

	public int addPlayer(Color c) {
		int id = Math.abs((new Random()).nextInt());
		while (players.containsKey(id))
			id++;

		int spd = 500;

		int sp[] = spawnPlaces[(new Random().nextInt(spawnPlaces.length))];
		if (players.size() == 0)
			players.put(id, new Character(sp[0], sp[1], 900, spd));
		else
			players.put(id, new Character(sp[0], sp[1], 900, spd));
		drawables.add(players.get(id));
		manageables.add(players.get(id));
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

	public List<Manageable> getManageables() {
		return manageables;
	}

	public GameMap getMap() {
		return map;
	}

	public void managePlayer(int id, List<Integer> cliKeys) {
		players.get(id).actions(this, cliKeys);
	}

	public void removeDrawable(Drawable d) {
		drawables.remove(d);
	}

	public void removeManageable(Manageable m) {
		manageables.remove(m);
	}

	@Override
	public void run() {
		timerApploop.tick();
		while (!Thread.currentThread().isInterrupted()) {
			for (int i = 0; i < manageables.size(); i++) {
				manageables.get(i).manage(this, timerApploop.lastTickS());
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
