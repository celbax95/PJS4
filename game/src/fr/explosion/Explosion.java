package fr.explosion;

import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public abstract class Explosion implements IExplosion {

	private static final long serialVersionUID = 1L;

	protected static final int TILE_SIZE = (int) (120 * Scale.getScale());

	protected int type;

	protected Point tile, pos;

	protected double damage;

	protected int tileSize;

	protected Cooldown cd;

	public Explosion() {
		tileSize = TILE_SIZE;
		type = 0;
		tile = new Point(0, 0);
		this.pos = getPosfromTile(tile);
		this.damage = 0;
		this.cd = new Cooldown(0);
	}

	private Point getPosfromTile(Point tile) {
		return new Point(tile.x * tileSize, tile.y * tileSize);
	}

	@Override
	public void setCooldown(int cd) {
		this.cd = new Cooldown(cd);
	}

	@Override
	public void setDamage(double damage) {
		this.damage = damage;
	}

	@Override
	public void setTile(Point tile) {
		this.tile = tile;
		this.pos = getPosfromTile(tile);
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}
}
