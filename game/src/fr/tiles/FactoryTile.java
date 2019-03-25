package fr.tiles;

import fr.map.IFactoryTile;
import fr.map.MapTile;
/**
 * Fabrique d'elements de terrain
 */
public class FactoryTile implements IFactoryTile {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creer un element de terrain
	 * @param type : type de l'element
	 * @param x : abscisse du point ou est placé l'element
	 * @param y : ordonnée du point ou est placé l'element
	 * @return l'element de terrain
	 */
	@Override
	public MapTile create(char type, int x, int y) {
		switch (type) {
		case '_':
			return new Floor(x, y);
		case 'W':
			return new SideWall(x, y);
		case 'X':
			return new UnbreakableWall(x, y);
		case '+':
			return new BreakableWall(x, y);
		case 'G':
			return new Spawner(x, y);
		case 'S':
			return new SpawnPoint(x, y);
		default:
			return null;
		}

	}

}
