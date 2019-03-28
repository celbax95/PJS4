package fr.tiles;

import java.io.Serializable;

import fr.itemsApp.Drawable;
import fr.map.MapTile;
import fr.util.point.Point;

/**
 * Classe abstraite d'element constituant le terrain
 */
public abstract class MapTileSuper implements MapTile, Drawable, Serializable {
	private static final long serialVersionUID = 1L;

	protected static final int DEFAULT_SIZE = 120;

	protected Point tile;
	protected Point pos;

	/**
	 * Constructeur MapTileSuper
	 * 
	 * @param x
	 *            : abscisse du point ou est placé l'element
	 * @param y
	 *            : ordonnée du point ou est placé l'element
	 */
	public MapTileSuper(int x, int y) {
		tile = new Point(x, y);
		pos = new Point(x * DEFAULT_SIZE, y * DEFAULT_SIZE);
	}

	/**
	 * @return pos : la position (x, y) de l'element
	 */
	@Override
	public Point getPos() {
		return pos;
	}

	/**
	 * @return SIZE : la taille de l'element
	 */
	@Override
	public int getSize() {
		return DEFAULT_SIZE;
	}

	/**
	 * @return tile : case du terrain
	 */
	@Override
	public Point getTile() {
		return tile;
	}
}