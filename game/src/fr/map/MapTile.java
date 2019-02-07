package fr.map;

import java.io.Serializable;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.util.point.Point;

public interface MapTile extends Drawable, Serializable {

	public Point getPos();

	public int getSize();

	public Point getTile();

	public void interact(Application m);

	public boolean isWalkable();
}
