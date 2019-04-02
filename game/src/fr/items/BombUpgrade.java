package fr.items;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.itemsApp.items.Item;

public class BombUpgrade extends Item {

	private static final long serialVersionUID = 1L;

	private static Image img = new ImageIcon(
			BombUpgrade.class.getResource("/images/items/bombUpgrade/placeableBombUpgrade.png")).getImage()
					.getScaledInstance(120, 120, Image.SCALE_FAST);

	private static boolean USE_NOW = true;

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, this.pos.getIX(), this.pos.getIY(), null);
	}

	@Override
	public Image getIcon() {
		return null;
	}

	@Override
	public void use(Application application) {
		if (this.holder.getExplosionSize() < this.holder.getMaxExplosionSize())
			this.holder.setExplosionSize(this.holder.getExplosionSize() + 1);
	}

	@Override
	public boolean useNow() {
		return USE_NOW;
	}
}
