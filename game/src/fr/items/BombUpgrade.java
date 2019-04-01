package fr.items;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;
import fr.itemsApp.items.Item;

public class BombUpgrade extends Item {

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillRect(this.pos.getIX(), this.pos.getIY(), this.SIZE, this.SIZE);
	}

	@Override
	public void use(Application application) {
	}

}
