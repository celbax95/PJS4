package fr.explosion;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.scale.Scale;
/**
 * Explosion standard
 */
public class StandardExplosion extends Explosion {

	private static final long serialVersionUID = 1L;

	private static Image[] img = {
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/0.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/1.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/2.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
	};
	/**
	 * constructeur StandardExplosion
	 */
	public StandardExplosion() {
		super();
	}

	/**
	 * Dessine l'explosion sur le terrain
	 * @param g : permet l'affichage d'�l�ments (images, text, ...)
	 */
	@Override
	public void draw(Graphics2D g) {
		Scale scale = Scale.getInstance();
		AffineTransform af = new AffineTransform();
		af.scale(scale.getScale(), scale.getScale());
		af.translate(pos.getIX(), pos.getIY());
		g.drawImage(img[type], af, null);
	}
	/**
	 * Gestion de l'element
	 *
	 * @param app               : Application
	 * @param timeSinceLastCall : temps depuis le dernier appel, pour la
	 *                          synchro @see Application.run
	 */
	@Override
	public void manage(Application a, double timeSinceLastCall) {
		tileSize = a.getMap().getTileSize();
		if (explosionTime.isDone()) {
			a.removeDrawable(this);
			a.removeManageable(this);
		}
	}
}
