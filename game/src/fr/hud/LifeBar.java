package fr.hud;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public class LifeBar implements IBar {

	private static Image image = (new ImageIcon(HUD.class.getResource("/images/HUD/lifeBar.gif"))).getImage();

	private ICharacter c;

	private Point pos;

	public LifeBar(ICharacter c, Point pos) {
		this.c = c;
		this.pos = pos;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, pos.getIX(), pos.getIY(), null);
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos;
	}

}
