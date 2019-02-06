package fr.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;

public class Ball implements Drawable, Serializable {
	private static final long serialVersionUID = 1L;
	private int radius, speed;
	private double x, y;
	private byte mouvXP, mouvXN, mouvYP, mouvYN;
	private Color color;

	public Ball(Ball b) {
		this(b.x, b.y, b.radius, b.speed, b.color);
	}

	public Ball(double x, double y, int radius, int speed, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		this.speed = speed;
		this.mouvXP = 0;
		this.mouvXN = 0;
		this.mouvYP = 0;
		this.mouvYN = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillOval((int) Math.round(x), (int) Math.round(y), radius, radius);
	}

	public void move(double t) {
		x = x + ((mouvXN + mouvXP) * speed * t);
		y = y + ((mouvYN + mouvYP) * speed * t);
	}

	public void setMove(List<Integer> keys) {
		mouvYN = (byte) ((keys.contains(KeyEvent.VK_Z)) ? -1 : 0);
		mouvYP = (byte) ((keys.contains(KeyEvent.VK_S)) ? 1 : 0);
		mouvXN = (byte) ((keys.contains(KeyEvent.VK_Q)) ? -1 : 0);
		mouvXP = (byte) ((keys.contains(KeyEvent.VK_D)) ? 1 : 0);
	}

	@Override
	public String toString() {
		return x + " " + y + " " + radius;
	}
}
