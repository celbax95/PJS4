package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;
/**
 * Mur
 */
public class SideWall extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = new Color(0, 0, 0);

	private static final boolean walkable = false;

	private static final boolean destroyable = false;
	/**
	 * Constructeur SideWall
	 * @param x : abscisse du point ou est placé l'element
	 * @param y : ordonnée du point ou est placé l'element
	 */
	public SideWall(int x, int y) {
		super(x, y);
	}
	/**
	 * affiche le mur
	 * @param g : permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), SIZE, SIZE);
	}
	
	/**
	 * permet d'interagir avec le mur
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
