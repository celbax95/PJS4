package fr.hud;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public class Item implements HUDElement {

	private static Item instance;

	private static Image image = new ImageIcon(HUD.class.getResource("/images/HUD/grid.gif")).getImage();

	static {
		instance = new Item();
	}

	private Point pos, size;

	private ICharacter c;

	public Item() {
		this.pos = new Point(0, 0);
		this.size = new Point(0, 0);
	}

	@Override
	public void draw(Graphics2D g) {
		if (this.c.getItem() != null)
			g.drawImage(this.c.getItem().getIcon(), this.pos.getIX(), this.pos.getIY(), null);

		g.drawImage(image, this.pos.getIX(), this.pos.getIY(), null);
	}

	@Override
	public void setCharacter(ICharacter player) {
		this.c = player;
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos;
	}

	@Override
	public void setSize(Point size) {
		this.size = size;
	}

	/**
	 * @return l'instance unique de Item
	 */
	public static Item getInstance() {
		return instance;
	}
}
