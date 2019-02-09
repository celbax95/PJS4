package fr.itemsApp.bomb;

import java.awt.Graphics2D;
import java.io.Serializable;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.itemsApp.Manageable;
import fr.util.point.Point;

public interface IBomb extends Serializable, Manageable, fr.itemsApp.Drawable {

	@Override
	void draw(Graphics2D g);

	void explode(Application a);

	IExplosion getExplosion();

	int getExplosionSize();

	Point getTile();

	@Override
	void manage(Application a, double t);

	void start();
}