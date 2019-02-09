package fr.explosion;

import java.io.Serializable;

import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.util.point.Point;

public interface IExplosion extends Drawable, Manageable, Serializable {
	public void setCooldown(int cd);

	public void setDamage(double damage);

	public void setTile(Point tile);

	public void setType(int type);
}
