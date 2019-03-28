package fr.itemsApp.bomb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.explosion.StandardExplosion;
import fr.scale.Scale;
import fr.util.point.Point;

/**
 * Bombe standard
 */
public class BombStd extends Bomb {

	private static final long serialVersionUID = 1L;

	private static Color c = Color.black;

	private static final int TIME = 2200;
	private static final int EXPLOSION_TIME = 1200;
	private static final int DAMAGE = 100;

	public BombStd(Point tile, int explosionSize) {
		// Calcul de la position de la bombe
		super(new Point((tile.getIX() * TILE_SIZE) + (TILE_SIZE - DEFAULT_BOMB_SIZE) / 2,
				(tile.getIY() * TILE_SIZE) + (TILE_SIZE - DEFAULT_BOMB_SIZE) / 2), tile, TIME, explosionSize);
	}

	@Override
	public void draw(Graphics2D g) {
		Scale scale = Scale.getInstance();
		AffineTransform old = g.getTransform();
		g.scale(scale.getScale(), scale.getScale());
		g.translate(pos.getIX(), pos.getIY());
		g.setColor(c);
		g.fillRect(0, 0, DEFAULT_BOMB_SIZE, DEFAULT_BOMB_SIZE);
		g.setTransform(old);
	}

	@Override
	public void explode(Application a) {
		super.explode(a);
	}

	@Override
	public IExplosion getExplosion() {
		IExplosion ex = new StandardExplosion();
		ex.setDamage(DAMAGE);
		ex.setCooldown(EXPLOSION_TIME);
		return ex;
	}

	@Override
	public void manage(Application a, double t) {
		if (cooldown.isDone()) {
			explode(a);
		}
	}

	/**
	 * @return Taille de la bombe
	 */
	public static int getSIZE() {
		return DEFAULT_BOMB_SIZE;
	}
}
