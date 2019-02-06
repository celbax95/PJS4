package fr.drawables;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

import fr.util.point.Point;

public class Character implements Drawable, Serializable {
	private static final long serialVersionUID = 1L;
	private static Image imgS = (new ImageIcon(Character.class.getResource("/images/s.png"))).getImage()
			.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
	private static Image[] imgD = {
			(new ImageIcon(Character.class.getResource("/images/d1.png"))).getImage().getScaledInstance(200, 200,
					Image.SCALE_DEFAULT),
			(new ImageIcon(Character.class.getResource("/images/d2.png"))).getImage().getScaledInstance(200, 200,
					Image.SCALE_DEFAULT), };
	private static int freq = 60;

	private int speed;

	private double angle;
	private int step;

	private Point pos;
	private Point mouv;

	public Character(Character b) {
		this(b.pos.x, b.pos.y, b.speed);
	}

	public Character(double x, double y, int speed) {
		pos = new Point(x, y);
		this.speed = speed;
		mouv = new Point(0, 0);
		angle = 0;
		step = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform af = new AffineTransform();
		af.translate(pos.x, pos.y);
		af.rotate(angle, imgS.getHeight(null) / 2, imgS.getHeight(null) / 2);
		if (mouv.x == 0 && mouv.y == 0)
			g.drawImage(imgS, af, null);
		else {
			g.drawImage(imgD[(step < freq / 2) ? 0 : 1], af, null);
		}
	}

	public void move(double t) {
		pos.translate(mouv.x * speed * t, mouv.y * speed * t);
		step = (step + 1) % freq;
	}

	public void setMove(List<Integer> keys) {
		mouv.setLocation(0, 0);
		if (keys.contains(KeyEvent.VK_Z))
			mouv.y--;
		if (keys.contains(KeyEvent.VK_S))
			mouv.y++;
		if (keys.contains(KeyEvent.VK_Q))
			mouv.x--;
		if (keys.contains(KeyEvent.VK_D))
			mouv.x++;
		mouv.normalize();
		if (!(mouv.x == 0 && mouv.y == 0)) {
			angle = mouv.getAngle();
		}
	}
}
