package fr.hud;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import fr.itemsApp.Drawable;
import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public class HUD implements Drawable {

	private static HUD instance;

	protected static final double scale = 0.4;

	private static Image image = new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif")).getImage();

	private static final Point barSize = new Point(412, 52);

	private static final Point[] barPos = { new Point(465, 42), new Point(466, 149) };

	private static final Point itemPos = new Point(401, 260);

	private static final Point itemSize = new Point(160, 160);

	static {
		instance = new HUD(new Point(0, 0));
	}

	private HUDElement[] elements;

	private Item item;

	private Point pos;

	private HUD(Point pos) {
		this.pos = pos;

		this.elements = new HUDElement[3];

		// LifeBar
		this.elements[0] = LifeBar.getInstance();
		this.elements[0].setPos(barPos[0]);
		this.elements[0].setSize(barSize);

		// CooldownBar
		this.elements[1] = CooldownBar.getInstance();
		this.elements[1].setPos(barPos[1]);
		this.elements[1].setSize(barSize);

		this.elements[2] = Item.getInstance();
		this.elements[2].setPos(itemPos);
		this.elements[2].setSize(itemSize);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform oldTransform = g.getTransform();
		g.scale(scale, scale);
		g.drawImage(image, (int) (this.pos.getIX() * scale), (int) (this.pos.getIY() * scale), null);
		for (HUDElement bar : this.elements)
			bar.draw(g);
		g.setTransform(oldTransform);
	}

	/**
	 * Change le ICharacter pour les IBar
	 *
	 * @param player : nouveau ICharacter
	 */
	public void setCharacter(ICharacter player) {
		for (HUDElement b : this.elements)
			b.setCharacter(player);
	}

	/**
	 * Change la pos
	 *
	 * @param player : nouvelle pos
	 */
	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * @return l'instance unique de HUD
	 */
	public static HUD getInstance() {
		return instance;
	}
}
