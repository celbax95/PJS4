package fr.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;

public class Ball implements Drawable, Serializable {
	private static final long serialVersionUID = 1L;
	private int r, spd;
	private double x, y;
	private int mx, my;
	private Color c;

	public Ball(Ball b) {
		this(b.x, b.y, b.r, b.spd, b.c);
	}

	public Ball(double x, double y, int r, int spd, Color c) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = c;
		this.spd = spd;
		this.mx = 0;
		this.my = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillOval((int) Math.round(x), (int) Math.round(y), r, r);
	}

	public void move(double t) {
		x = x + (mx * spd * t);
		y = y + (my * spd * t);
		mx = 0;
		my = 0;
	}

	public void setMove(List<Integer> keys) {
		if (keys.contains(KeyEvent.VK_Z))
			this.my = (this.my > 0 ? 0 : -1);
		if (keys.contains(KeyEvent.VK_S))
			this.my = (this.my < 0 ? 0 : 1);
		if (keys.contains(KeyEvent.VK_Q))
			this.mx = (this.mx > 0 ? 0 : -1);
		if (keys.contains(KeyEvent.VK_D))
			this.mx = (this.mx < 0 ? 0 : 1);
	}

	@Override
	public String toString() {
		return x + " " + y + " " + r;
	}

}
