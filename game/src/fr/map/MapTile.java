package fr.map;

import java.io.Serializable;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.util.point.Point;
/**
 * Interface d'element constituant le terrain
 */
public interface MapTile extends Drawable, Serializable {
	/**
	 * @return la position (x, y) de l'element
	 */
	public Point getPos();
	/**
	 * @return la taille de l'element
	 */
	public int getSize();
	/**
	 * @return la case (x, y) de l'element
	 */
	public Point getTile();
	/**
	 * permet d'interagir avec un element
	 * @param m : l'application
	 */
	public void interact(Application m);
	/**
	 * 
	 * @return true si l'element est destructible et false sinon
	 */
	public boolean isDestroyable();
	/**
	 * 
	 * @return true si on peut marcher sur l'element et false sinon
	 */
	public boolean isWalkable();
}
