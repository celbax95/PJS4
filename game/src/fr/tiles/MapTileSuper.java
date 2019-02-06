package fr.tiles;

import java.io.Serializable;

import fr.drawables.Drawable;
import fr.map.MapTile;
import fr.util.point.Point;

public abstract class MapTileSuper implements MapTile, Drawable, Serializable {
	private static final long serialVersionUID = 1L;
	protected int size;
	protected Point tile;
	protected Point pos;

	public MapTileSuper(int x, int y, int size) {
		this.size = size;
		tile = new Point(x, y);
		pos = new Point(x * size, y * size);
	}

	public Point getPos() {
		return pos;
	}

	public int getSize() {
		return size;
	}

	@Override
	public Point getTile() {
		return tile;
	}
}
