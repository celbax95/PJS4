package fr.explosion;

import java.io.Serializable;

import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.util.point.Point;

/**
 * Interface Explosion, permet de créer un comportement generique pour les
 * explosions
 */
public interface IExplosion extends Drawable, Manageable, Serializable {

	/**
	 * @param damage : dommage provoqués par une explosion
	 */
	public int getDamage();

	/**
	 * @return La tile de l'explosion
	 */
	public Point getTile();

	/**
	 * @param explosionTime : temps mis par l'explosion avant de disparaitre du
	 *                      terrain
	 */
	public void setCooldown(int explosionTime);

	/**
	 * @param damage : dommage provoqués par l'explosion
	 */
	public void setDamage(int damage);

	/**
	 * @param tile : élément du jeu possedant une largeur et une hauteur
	 */
	public void setTile(Point tile);

	/**
	 * @param type : type de l'explosion (1 direction droite ou gauche) (2 direction
	 *             haut ou bas)
	 */
	public void setType(int type);
}
