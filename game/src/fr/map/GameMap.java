package fr.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import fr.scale.Scale;
import fr.tiles.FactoryTile;
import fr.util.point.Point;

public class GameMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_TILE_SIZE = 120;

	private static int TILE_SIZE = (int) (DEFAULT_TILE_SIZE * Scale.getScale());

	private int width, height;

	private MapTile[][] map;

	private IFactoryTile factory;

	private ArrayList<Point> spawnPoints;

	public GameMap(String s) {
		factory = new FactoryTile();
		spawnPoints = new ArrayList<>();
		map = loadMap(s);
	}

	public int getHeight() {
		return height;
	}

	public MapTile[][] getMap() {
		return map;
	}

	public Point getTileFor(double x, double y) {
		return new Point(((int) x) / TILE_SIZE, ((int) y) / TILE_SIZE);
	}

	public int getTileSize() {
		return TILE_SIZE;
	}

	public int getWidth() {
		return width;
	}

	public MapTile[][] loadMap(String s) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(GameMap.class.getResourceAsStream(s));

		MapTile[][] map;

		width = sc.nextInt();
		height = sc.nextInt();
		sc.nextLine();

		map = new MapTile[width][height];

		int tmp = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < tmp; i++) {
			spawnPoints.add(new Point(sc.nextInt(), sc.nextInt()));
			sc.nextLine();
		}

		for (int y = 0; y < height; y++) {
			String str = sc.nextLine();
			for (int x = 0; x < width; x++) {
				map[x][y] = factory.create(str.charAt(x), x, y);
			}
		}
		return map;
	}
}
