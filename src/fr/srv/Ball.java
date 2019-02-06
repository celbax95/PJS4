package fr.srv;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;

import fr.screen.Drawable;

public class Ball implements Drawable, Serializable {
	private static final long serialVersionUID = 1L;
	private int x, y, r, spd;
	private Color c;

	public Ball(Ball b) {
		this.x = b.x;
		this.y = b.y;
		this.r = b.r;
		this.c = b.c;
		this.spd = b.spd;
	}

	public Ball(int x, int y, int r, int spd, Color c) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = c;
		this.spd = spd;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillOval(x, y, r, r);
	}

	public void move(List<Integer> keys) {
		if (keys.contains(KeyEvent.VK_Z))
			this.y -= spd;
		if (keys.contains(KeyEvent.VK_S))
			this.y += spd;
		if (keys.contains(KeyEvent.VK_Q))
			this.x -= spd;
		if (keys.contains(KeyEvent.VK_D))
			this.x += spd;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + r;
	}

}
