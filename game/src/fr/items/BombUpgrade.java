package fr.items;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;
import fr.itemsApp.items.Item;

public class BombUpgrade extends Item {

	private static final long serialVersionUID = 1L;

	private static boolean USE_NOW = true;

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillRect(this.pos.getIX(), this.pos.getIY(), this.SIZE, this.SIZE);
	}

	@Override
	public void use(Application application) {
		this.holder.setExplosionSize(this.holder.getExplosionSize() + 1);
	}

	@Override
	public boolean useNow() {
		return USE_NOW;
	}
}
