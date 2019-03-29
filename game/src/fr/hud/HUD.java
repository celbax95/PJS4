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

	private static Image image = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif"))).getImage();

	private static final Point barSize = new Point(412, 52);

	private static final Point[] barPos = { new Point(465, 42), new Point(466, 149) };

	static {
		instance = new HUD(new Point(0, 0));
	}

	private IBar[] bars;

	private Point pos;

	private HUD(Point pos) {
		this.pos = pos;
		bars = new IBar[2];
		bars[0] = new LifeBar(barPos[0], barSize);
		bars[1] = new CooldownBar(barPos[1], barSize);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform oldTransform = g.getTransform();
		g.scale(scale, scale);
		g.drawImage(image, (int) (pos.getIX() * scale), (int) (pos.getIY() * scale), null);
		for (int i = 0; i < bars.length; i++) {
			bars[i].draw(g);
		}
		g.setTransform(oldTransform);
	}

	public void setCharacter(ICharacter player) {
		for (IBar b : bars) {
			b.setCharacter(player);
		}
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public static HUD getInstance() {
		return instance;
	}
}
