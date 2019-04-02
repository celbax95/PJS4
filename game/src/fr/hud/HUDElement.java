package fr.hud;

import fr.itemsApp.Drawable;
import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public interface HUDElement extends Drawable {

	/**
	 * Change le ICharacter
	 * 
	 * @param player : nouveau ICharacter
	 */
	void setCharacter(ICharacter player);

	/**
	 * Change la pos
	 * 
	 * @param player : nouvelle pos
	 */
	void setPos(Point pos);

	/**
	 * Change la size
	 * 
	 * @param player : nouvelle size
	 */
	void setSize(Point size);

}
