package fr.explosion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.application.Application;
import fr.itemsApp.bomb.IBomb;
import fr.map.MapTile;
import fr.tiles.Floor;
import fr.util.point.Point;

public class ExplosionCreator implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<IExplosion> explosions;

	public void create(Application a, IBomb b) {

		explosions = new ArrayList<>();

		MapTile[][] m = a.getMap().getMap();

		int tileSize = a.getMap().getTileSize();

		// horizontal

		Point tile = b.getTile();

		int size = b.getExplosionSize();

		boolean l = true, r = true, u = true, d = true;

		IExplosion ex = b.getExplosion();
		ex.setTile(tile.clone());
		ex.setType(0);

		explosions.add(ex);

		for (int i = 1; i <= size; i++) {
			if (r && (r = setExplosion(m, b, 1, tile.getIX() + i, tile.getIY())))
				;
			if (l && (l = setExplosion(m, b, 1, tile.getIX() - i, tile.getIY())))
				;
			if (u && (u = setExplosion(m, b, 2, tile.getIX(), tile.getIY() - i)))
				;
			if (d && (d = setExplosion(m, b, 2, tile.getIX(), tile.getIY() + i)))
				;
		}
		for (IExplosion e : explosions) {
			a.addDrawable(e);
			a.addManageable(e);
		}
	}

	private boolean setExplosion(MapTile[][] m, IBomb b, int type, int x, int y) {
		if (x < 0 || x > m.length || y < 0 || y > m[0].length)
			return false;

		if (m[x][y].isDestroyable() || m[x][y].isWalkable()) {
			IExplosion ex = b.getExplosion();
			ex.setTile(new Point(x, y));
			ex.setType(type);
			explosions.add(ex);
		}

		if (m[x][y].isDestroyable()) {
			m[x][y] = new Floor(m[x][y].getTile().getIX(), m[x][y].getTile().getIY());
		}

		return (!m[x][y].isDestroyable() || m[x][y].isWalkable());
	}
}
