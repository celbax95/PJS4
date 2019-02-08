package fr.explosion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.application.Application;
import fr.map.MapTile;
import fr.tiles.Floor;
import fr.util.point.Point;

public class ExplosionCreator implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<IExplosion> explosions;

	public void create(Application a, Point tile, int size, double damage, int time) {

		explosions = new ArrayList<>();

		MapTile[][] m = a.getMap().getMap();

		int tileSize = a.getMap().getTileSize();

		// horizontal

		boolean l = true, r = true, u = true, d = true;

		explosions.add(new StandardExplosion(0, tile.getIX(), tile.getIY(), tileSize, damage, time));

		for (int i = 1; i <= size; i++) {
			if (r && (r = setExplosion(m, 1, tile.getIX() + i, tile.getIY(), tileSize, damage, time)))
				;
			if (l && (l = setExplosion(m, 1, tile.getIX() - i, tile.getIY(), tileSize, damage, time)))
				;
			if (u && (u = setExplosion(m, 2, tile.getIX(), tile.getIY() - i, tileSize, damage, time)))
				;
			if (d && (d = setExplosion(m, 2, tile.getIX(), tile.getIY() + i, tileSize, damage, time)))
				;
		}
		for (IExplosion e : explosions) {
			a.addDrawable(e);
			a.addManageable(e);
		}
	}

	private boolean setExplosion(MapTile[][] m, int type, int x, int y, int tileSize, double damage, int time) {
		if (x < 0 || x > m.length || y < 0 || y > m[0].length)
			return false;

		if (m[x][y].isDestroyable() || m[x][y].isWalkable())
			explosions.add(new StandardExplosion(type, x, y, tileSize, damage, time));

		if (m[x][y].isDestroyable()) {
			m[x][y] = new Floor(m[x][y].getTile().getIX(), m[x][y].getTile().getIY());
		}

		return (!m[x][y].isDestroyable() || m[x][y].isWalkable());
	}
}
