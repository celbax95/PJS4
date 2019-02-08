package fr.itemsApp.bomb;

import java.io.Serializable;

import fr.application.Application;
import fr.itemsApp.character.Character;
import fr.map.GameMap;
import fr.util.point.Point;

public class BombFactory implements Serializable {

	private static final long serialVersionUID = 1L;

	public BombFactory() {
	}

	public IBomb create(String type, Application a, Character c) {

		GameMap map = a.getMap();

		Point tile = c.getCenter();
		tile = map.getTileFor(tile.x, tile.y);

		switch (type.trim().toLowerCase()) {
		case "std":
			return new BombStd(tile, map.getTileSize(), 1000, 1);
		}
		return null;
	}

}
