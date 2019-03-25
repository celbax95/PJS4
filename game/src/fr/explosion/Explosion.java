package fr.explosion;

import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;
/**
 * 
 * Classe abstraite Explosion
 *
 */
public abstract class Explosion implements IExplosion {

	private static final long serialVersionUID = 1L;

	protected static final int TILE_SIZE = (int) (120 * Scale.getScale());

	protected int type;

	protected Point tile, pos;

	protected double damage;

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
	 * 
	 * @param tile : élément du jeu possedant une largeur et une hauteur 
	 * @return la position de la tile
	 */
	private Point getPosfromTile(Point tile) {
		return new Point(tile.x * tileSize, tile.y * tileSize);
	}
	
	/**
	 * @param explosionTime : temps mis par l'explosion avant de disparaitre du terrain
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
	public void setDamage(double damage) {
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
	 * @param type : type de l'explosion
	 */
	@Override
	public void setType(int type) {
		this.type = type;
	}
}
