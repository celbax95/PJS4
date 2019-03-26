package fr.itemsApp.bomb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.explosion.StandardExplosion;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

/**
 * Bombe standard
 */
public class BombStd extends Bomb {

	private static final long serialVersionUID = 1L;

	private static Color c = Color.black;

	private static final int TIME = 1400;
	private static final int DAMAGE = 333;

	public BombStd(Point tile, int tileSize, int cooldown, int explosionSize) {
		this.tile = tile;

		// Calcul de la position de la bombe
		this.pos = new Point((tile.getIX() * tileSize) + (tileSize - DEFAULT_BOMB_SIZE) / 2,
				(tile.getIY() * tileSize) + (tileSize - DEFAULT_BOMB_SIZE) / 2);
		this.cooldown = new Cooldown(cooldown);
		this.explosionSize = explosionSize;
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform af = new AffineTransform();
		af.scale(Scale.getScale(), Scale.getScale());
		af.translate(pos.getIX(), pos.getIY());
		g.setTransform(af);
		g.setColor(c);
		g.fillRect(0, 0, DEFAULT_BOMB_SIZE, DEFAULT_BOMB_SIZE);
	}

	@Override
	public void explode(Application a) {
		super.explode(a);
	}

	@Override
	public IExplosion getExplosion() {
		IExplosion ex = new StandardExplosion();
		ex.setDamage(DAMAGE);
		ex.setCooldown(TIME);
		return ex;
	}

	@Override
	public void manage(Application a, double t) {
		super.manage(a, t);
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
