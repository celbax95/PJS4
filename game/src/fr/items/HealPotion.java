package fr.items;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.itemsApp.items.Item;

public class HealPotion extends Item {

	private static final long serialVersionUID = 1L;

	private static Image img = new ImageIcon(
			HealPotion.class.getResource("/images/items/healPotion/placeableHealPotion.png")).getImage()
					.getScaledInstance(120, 120, Image.SCALE_FAST);

	private static Image icon = new ImageIcon(
			BombUpgrade.class.getResource("/images/items/healPotion/collectableHealPotion.png")).getImage()
					.getScaledInstance(160, 160, Image.SCALE_FAST);

	private static boolean USE_NOW = false;

	private static double HEAL_AMOUNT = 50;

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, this.pos.getIX(), this.pos.getIY(), null);
	}

	@Override
	public Image getIcon() {
		return icon;
	}

	@Override
	public void use(Application application) {
		this.holder.setHealth(this.holder.getHealth() + HEAL_AMOUNT);
	}

	@Override
	public boolean useNow() {
		return USE_NOW;
	}
}
