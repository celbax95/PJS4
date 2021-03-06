package fr.itemsApp.bomb;

import fr.application.Application;
import fr.explosion.ExplosionCreator;
import fr.util.point.Point;
import fr.util.time.Cooldown;

/**
 * Superclass pour les bombes
 */
public abstract class Bomb implements IBomb {

	private static final long serialVersionUID = 1L;

	protected static final int TILE_SIZE = 120;

	protected static final int DEFAULT_COOLDOWN = 4000;
	protected static final int DEFAULT_BOMB_SIZE = 80;

	protected Point pos, tile;

	protected Cooldown cooldown;

	protected int explosionSize;

	public Bomb(Point pos, Point tile, int cooldown, int explosionSize) {
		this.pos = pos;
		this.cooldown = new Cooldown(cooldown);
		this.tile = tile;
		this.explosionSize = explosionSize;
	}

	@Override
	public void explode(Application application) {
		ExplosionCreator ec = new ExplosionCreator();

		ec.create(application, this);
		IBomb.removeFromLists(application, this);
	}

	@Override
	public Point getCenter() {
		return new Point(pos.x + DEFAULT_BOMB_SIZE / 2, pos.y + DEFAULT_BOMB_SIZE / 2);
	}

	@Override
	public int getExplosionSize() {
		return explosionSize;
	}

	@Override
	public Point getPos() {
		return pos;
	}

	@Override
	public Point getSize() {
		return new Point(DEFAULT_BOMB_SIZE, DEFAULT_BOMB_SIZE);
	}

	@Override
	public Point getTile() {
		return tile;
	}

	@Override
	public abstract void manage(Application a, double timeSinceLastCall);

	@Override
	public void start() {
		cooldown.start();
	}
}
