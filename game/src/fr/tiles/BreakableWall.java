package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import fr.application.Application;
import fr.scale.Scale;
/**
 * Mur cassable
 */
public class BreakableWall extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = Color.red;

	private static final boolean walkable = false;
	private static final boolean destroyable = true;
	/**
	 * constructeur BreakableWall
	 * @param x : abscisse du point ou est placé le mur cassable
	 * @param y : ordonnée du point ou est placé le mur cassable
	 */
	public BreakableWall(int x, int y) {
		super(x, y);
	}
	/**
	 * affiche le mur cassable
	 * @param g : permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		AffineTransform oldTransform = g.getTransform();
		AffineTransform af = new AffineTransform();
		af.scale(Scale.getScale(), Scale.getScale());
		af.translate(pos.getIX(), pos.getIY());
		g.setTransform(af);
		g.setColor(c);
		g.fillRect(0, 0, DEFAULT_SIZE, DEFAULT_SIZE);
		g.setTransform(oldTransform);

	}
	/**
	 * permet d'interagir avec le mur cassable
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
