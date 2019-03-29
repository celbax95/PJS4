package fr.hud;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.itemsApp.Drawable;
import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public class HUD implements Drawable {

	private static final double rescaleRatio = 0.7;

	private static Image image = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif"))).getImage();

	private static final Point barSize = new Point(412, 52);

	private static final Point[] barPos = { new Point(465, 42), new Point(466, 149) };

	private IBar[] bars;

	private Point pos;

	public HUD(Point pos, ICharacter c) {
		this.pos = pos;
		bars = new IBar[2];
		bars[0] = new LifeBar(c, barPos[0], barSize);
		bars[1] = new CooldownBar(c, barPos[1], barSize);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, pos.getIX(), pos.getIY(), null);
		for (int i = 0; i < bars.length; i++) {
			bars[i].draw(g);
		}
	}

	public void setCharacter(ICharacter player) {
		for (IBar b : bars) {
			b.setCharacter(player);
		}
	}

}
