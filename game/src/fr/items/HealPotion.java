package fr.items;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.itemsApp.items.Item;

public class HealPotion extends Item {

	private static final long serialVersionUID = 1L;

	private static Image img = new ImageIcon(HealPotion.class.getResource("/images/items/healPotion/healPotion.png"))
			.getImage().getScaledInstance(120, 120, Image.SCALE_FAST);

	private static boolean USE_NOW = false;

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, this.pos.getIX(), this.pos.getIY(), null);
	}

	@Override
	public void use(Application application) {
		this.holder.setHealth(this.holder.getHealth() + 60);
	}

	@Override
	public boolean useNow() {
		return USE_NOW;
	}
}
