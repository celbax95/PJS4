package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;
/**
 * Zone d'apparition des joueurs
 */
public class SpawnPoint extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = new Color(0, 255, 0);

	private static final boolean destroyable = false;
	private static final boolean walkable = true;

	/**
	 * constructeur SpawnPoint
	 * @param x : abscisse du point ou est plac� l'element
	 * @param y : ordonn�e du point ou est plac� l'element
	 */
	public SpawnPoint(int x, int y) {
		super(x, y);
	}
	/**
	 * affiche la zone d'apparition
	 * @param g : permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), DEFAULT_SIZE, DEFAULT_SIZE);
	}
	/**
	 * permet d'interagir avec la zone d'apparition
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