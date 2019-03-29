package fr.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public class CooldownBar implements IBar {

	private static Image image = (new ImageIcon(HUD.class.getResource("/images/HUD/cooldownBar.gif"))).getImage();

	private ICharacter c;

	private Point pos, size;

	public CooldownBar(Point pos, Point size) {
		this.pos = pos;
		this.size = size;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, pos.getIX(), pos.getIY(), null);
		g.setColor(Color.black);
		g.fill(getRect());
	}

	private Rectangle getRect() {
		int less = (int) ((double) (c.maxTimeBeforeBomb() - c.timeBeforeBomb()) * size.getIX() / c.maxTimeBeforeBomb());

		int rectX = pos.getIX() + less;
		int rectWidth = size.getIX() - less;

		return new Rectangle(rectX, pos.getIY(), rectWidth, size.getIY());
	}

	@Override
	public void setCharacter(ICharacter player) {
		this.c = player;
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos;
	}
}