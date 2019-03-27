package fr.explosion;

import fr.application.Application;
import fr.util.point.Point;
import fr.util.time.Cooldown;

/**
 *
 * Classe abstraite Explosion
 *
 */
public abstract class Explosion implements IExplosion {

	private static final long serialVersionUID = 1L;

	protected static final int TILE_SIZE = 120;

	protected int type;

	protected Point tile, pos;

	protected int damage;

	protected int tileSize;

	protected Cooldown explosionTime;

	/**
	 * constructeur Explosion
	 */
	public Explosion() {
		tileSize = TILE_SIZE;
		type = 0;
		tile = new Point(0, 0);
		this.pos = getPosfromTile(tile);
		this.damage = 0;
		this.explosionTime = new Cooldown(0);
	}

	/**
	 * Supprime une explosion de l'application
	 *
	 * @param application : application
	 */
	protected void delete(Application application) {
		application.removeExplosion(this);
		application.removeDrawable(this);
		application.removeManageable(this);
	}

	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * @param tile : élément du jeu possedant une largeur et une hauteur
	 * @return la position de la tile
	 */
	private Point getPosfromTile(Point tile) {
		return new Point(tile.x * tileSize, tile.y * tileSize);
	}

	@Override
	public Point getTile() {
		return tile;
	}

	/**
	 * @param explosionTime : temps mis par l'explosion avant de disparaitre du
	 *                      terrain
	 */
	@Override
	public void setCooldown(int explosionTime) {
		this.explosionTime = new Cooldown(explosionTime);
	}

	/**
	 *
	 * @param damage : dommage provoqués par une explosion
	 */
	@Override
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 *
	 * @param tile : élément du jeu possedant une largeur et une hauteur
	 */
	@Override
	public void setTile(Point tile) {
		this.tile = tile;
		this.pos = getPosfromTile(tile);
	}

	/**
	 *
	 * @param type : type de l'explosion (1 direction droite ou gauche) (2 direction
	 *             haut ou bas)
	 */
	@Override
	public void setType(int type) {
		this.type = type;
	}
}
