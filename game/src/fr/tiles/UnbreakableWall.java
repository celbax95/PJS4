package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;

/**
 * Mur Incassable
 */
public class UnbreakableWall extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = new Color(50, 50, 50);

	private static final boolean walkable = false;
	private static final boolean destroyable = false;

	/**
	 * Constructeur UnbreakableWall
	 *
	 * @param x : abscisse du point ou est placé l'element
	 * @param y : ordonnée du point ou est placé l'element
	 */
	public UnbreakableWall(int x, int y) {
		super(x, y);
	}

	/**
	 * affiche le mur incassable
	 *
	 * @param g : permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), DEFAULT_SIZE, DEFAULT_SIZE);
	}

	/**
	 * permet d'interagir avec le mur incassable
	 *
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
