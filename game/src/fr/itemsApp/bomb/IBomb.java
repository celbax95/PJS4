package fr.itemsApp.bomb;

import java.awt.Graphics2D;
import java.io.Serializable;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.itemsApp.Manageable;
import fr.util.point.Point;

/**
 * Interface pour une bombe
 */
public interface IBomb extends Serializable, Manageable, fr.itemsApp.Drawable {

	@Override
	void draw(Graphics2D g);

	/**
	 * Fait exploser la bombe @see Explosion
	 *
	 * @param application : application
	 */
	void explode(Application application);

	/**
	 * @return Type de l'explosion a creer
	 */
	IExplosion getExplosion();

	/**
	 * @return Taille de l'explosion de la bombe
	 */
	int getExplosionSize();

	/**
	 * @return Emplacement de la bombe sur la map
	 */
	Point getTile();

	@Override
	void manage(Application a, double t);

	/**
	 * Lancement du cooldown de la bombe
	 */
	void start();
}