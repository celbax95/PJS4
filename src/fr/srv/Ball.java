package fr.srv;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

import fr.screen.Drawable;

public class Ball implements Drawable, Serializable {
	private static final long serialVersionUID = 1L;
	private int x, y, r;
	private Color c;

	public Ball(int x, int y, int r, Color c) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = c;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillOval(x, y, r, r);
	}

	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + r;
	}

}
