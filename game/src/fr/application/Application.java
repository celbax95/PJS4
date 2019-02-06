package fr.application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.drawables.Character;
import fr.drawables.Drawable;
import fr.util.time.Timer;

public class Application implements Runnable {
	private final static int spawnPlaces[][] = { { 100, 100 }, { 500, 500 }, { 100, 500 }, { 500, 100 },
			{ 250, 250 }, };

	private Map<Integer, Character> players;

	private List<Drawable> drawables;

	private Timer timerApploop;

	private Thread myThread;

	public Application(int width, int height) {
		players = new HashMap<>();
		drawables = new ArrayList<>();
		timerApploop = new Timer();
		myThread = new Thread(this);
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

	public List<Drawable> getDrawables() {
		return drawables;
	}

	public void managePlayer(int id, List<Integer> cliKeys) {
		players.get(id).setMove(cliKeys);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timerApploop.tick();
			for (Character b : players.values()) {
				b.move(timerApploop.lastTickS());
			}
		}
	}

	public void start() {
		myThread.start();
	}
}
