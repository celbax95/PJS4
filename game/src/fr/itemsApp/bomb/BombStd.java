package fr.itemsApp.bomb;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.explosion.StandardExplosion;
import fr.util.point.Point;

/**
 * Bombe standard
 */
public class BombStd extends Bomb {

	private static final long serialVersionUID = 1L;
	private static Image bomb = new ImageIcon(BombStd.class.getResource("/images/bombs/standard/dropped/4.png"))
			.getImage();

	private static final int TIME = 2200;
	private static final int EXPLOSION_TIME = 1200;
	private static final int DAMAGE = 230;

	public BombStd(Point tile, int explosionSize) {
		// Calcul de la position de la bombe
		super(new Point(tile.getIX() * TILE_SIZE + (TILE_SIZE - DEFAULT_BOMB_SIZE) / 2,
				tile.getIY() * TILE_SIZE + (TILE_SIZE - DEFAULT_BOMB_SIZE) / 2), tile, TIME, explosionSize);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(bomb, this.pos.getIX(), this.pos.getIY(), DEFAULT_BOMB_SIZE, DEFAULT_BOMB_SIZE, null);
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
		if (this.cooldown.isDone())
			this.explode(a);
	}

	@Override
	public void SonExplosion() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Taille de la bombe
	 */
	public static int getSIZE() {
		return DEFAULT_BOMB_SIZE;
	}
}
