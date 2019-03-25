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

	/**
	 * Creation d'un bombe
	 *
	 * @param type        : type de la bombe a creer
	 * @param application : Application
	 * @param creator     : Character qui a place la bombe
	 * @return Bombe creee
	 */
	public IBomb create(String type, Application application, Character creator) {

		GameMap map = application.getMap();

		// Recuperation de la tile sur laquelle le joueur est
		Point tile = creator.getCenter();
		tile = map.getTileFor(tile.x, tile.y);

		// Creation de la bombe
		switch (type.trim().toLowerCase()) {
		case "std":
			return new BombStd(tile, map.getTileSize(), 1000, 1);
		}
		return null;
	}

}
