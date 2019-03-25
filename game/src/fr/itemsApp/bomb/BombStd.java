package fr.itemsApp.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.explosion.StandardExplosion;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public class BombStd extends Bomb {

	private static final long serialVersionUID = 1L;

	private static int SIZE = (int) (DEFAULT_BOMB_SIZE * Scale.getScale());

	private static Color c = Color.black;

	private static final int TIME = 1400;
	private static final int DAMAGE = 333;

	public BombStd(Point tile, int tileSize, int cooldown, int explosionSize) {
		this.tile = tile;

		// Calcul de la position de la bombe
		this.pos = new Point((tile.getIX() * tileSize) + (tileSize - SIZE) / 2,
				(tile.getIY() * tileSize) + (tileSize - SIZE) / 2);
		this.cooldown = new Cooldown(cooldown);
		this.explosionSize = explosionSize;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), SIZE, SIZE);
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
		return SIZE;
	}
}
