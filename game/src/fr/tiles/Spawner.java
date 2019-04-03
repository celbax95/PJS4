package fr.tiles;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;

/**
 * Zone d'apparition des objets
 */
public class Spawner extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Image spawner = new ImageIcon(SpawnPoint.class.getResource("/images/map/spawners/item/spawner.png"))
			.getImage();

	private static final boolean walkable = true;

	private static final boolean destroyable = false;

	/**
	 * constructeur Spawner
	 *
	 * @param x
	 *            : abscisse du point ou est placé l'element
	 * @param y
	 *            : ordonnée du point ou est placé l'element
	 */
	public Spawner(int x, int y) {
		super(x, y);
	}

	/**
	 * affiche la zone d'apparition
	 *
	 * @param g
	 *            : permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(spawner, this.pos.getIX(), this.pos.getIY(), DEFAULT_SIZE, DEFAULT_SIZE, null);
	}

	/**
	 * permet d'interagir avec la zone d'apparition
	 *
	 * @param m
	 *            : l'application
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
