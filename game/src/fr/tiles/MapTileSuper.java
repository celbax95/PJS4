package fr.tiles;

import java.io.Serializable;

import fr.itemsApp.Drawable;
import fr.map.MapTile;
import fr.scale.Scale;
import fr.util.point.Point;

public abstract class MapTileSuper implements MapTile, Drawable, Serializable {
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_SIZE = 120;

	protected static int SIZE = (int) (DEFAULT_SIZE * Scale.getScale());

	protected Point tile;
	protected Point pos;

	public MapTileSuper(int x, int y) {
		tile = new Point(x, y);
		pos = new Point(x * SIZE, y * SIZE);
	}

	@Override
	public Point getPos() {
		return pos;
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	@Override
	public Point getTile() {
		return tile;
	}
}
