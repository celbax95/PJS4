package fr.hud;

import fr.itemsApp.Drawable;
import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public interface IBar extends Drawable {

	void setCharacter(ICharacter player);

	void setPos(Point pos);

	void setSize(Point size);

}
