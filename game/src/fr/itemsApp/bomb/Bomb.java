package fr.itemsApp.bomb;

import fr.application.Application;
import fr.explosion.ExplosionCreator;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public abstract class Bomb implements IBomb {

	private static final long serialVersionUID = 1L;

	protected static final int DEFAULT_COOLDOWN = 4000;
	protected static final int DEFAULT_BOMB_SIZE = 80;

	protected Point pos, tile;

	protected Cooldown cooldown;
	protected int explosionSize;
	protected int tileSize;

	@Override
	public void explode(Application a) {
		ExplosionCreator ec = new ExplosionCreator();

		ec.create(a, this);
		a.removeDrawable(this);
		a.removeManageable(this);
	}

	@Override
	public int getExplosionSize() {
		return explosionSize;
	}

	@Override
	public Point getTile() {
		return tile;
	}

	@Override
	public void manage(Application a, double t) {
		tileSize = a.getMap().getTileSize();
	}

	@Override
	public void start() {
		cooldown.start();
	}
}
