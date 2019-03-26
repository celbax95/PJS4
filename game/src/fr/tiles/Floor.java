package fr.tiles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.scale.Scale;
/**
 * Sol
 */
public class Floor extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Image img = (new ImageIcon(Floor.class.getResource("/images/map/floor/floor.png"))).getImage()
			.getScaledInstance(DEFAULT_SIZE, DEFAULT_SIZE, Image.SCALE_DEFAULT);

	private static final boolean walkable = true;

	private static final boolean destroyable = false;
	/**
	 * constructeur Floor
	 * @param x : abscisse du point ou est placé le sol
	 * @param y : ordonnée du point ou est placé le sol
	 */
	public Floor(int x, int y) {
		super(x, y);
	}
	/**
	 * affiche le sol
	 * @param g : permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		Scale scale = Scale.getInstance();
		AffineTransform af = new AffineTransform();
		af.scale(scale.getScale(), scale.getScale());
		af.translate(pos.getIX(), pos.getIY());
		g.drawImage(img, af, null);
	}
	/**
	 * permet d'interagir avec un element
	 * @param m : l'application
	 */
	@Override
	public void interact(Application m) {
		// TODO Auto-generated method stub

	}
	/**
	 * @return destroyable : true si l'element est destructible et false sinon
	 */
	@Override
	public boolean isDestroyable() {
		return destroyable;
	}
	/**
	 * @return walkable : true si on peut marcher sur l'element et false sinon
	 */
	@Override
	public boolean isWalkable() {
		return walkable;
	}
}
