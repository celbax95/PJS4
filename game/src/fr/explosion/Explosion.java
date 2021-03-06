package fr.explosion;

import fr.application.Application;
import fr.son.AudioPlayer;
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
		this.tileSize = TILE_SIZE;
		this.type = 0;
		this.tile = new Point(0, 0);
		this.pos = this.getPosfromTile(this.tile);
		this.damage = 0;
		this.explosionTime = new Cooldown(0);
		try {
			String cheminBombe = "game\\BanqueSon\\bombe.wav";
			AudioPlayer audioPlayerBombe = new AudioPlayer(cheminBombe);
			audioPlayerBombe.play();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();

		}
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
		return this.damage;
	}

	/**
	 * @param tile : �l�ment du jeu possedant une largeur et une hauteur
	 * @return la position de la tile
	 */
	private Point getPosfromTile(Point tile) {
		return new Point(tile.x * this.tileSize, tile.y * this.tileSize);
	}

	@Override
	public Point getTile() {
		return this.tile;
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
	 * @param damage : dommage provoqu�s par une explosion
	 */
	@Override
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 *
	 * @param tile : �l�ment du jeu possedant une largeur et une hauteur
	 */
	@Override
	public void setTile(Point tile) {
		this.tile = tile;
		this.pos = this.getPosfromTile(tile);
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
