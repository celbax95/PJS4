package fr.itemsApp.items;

import java.util.Random;

import fr.items.BombUpgrade;
import fr.items.HealPotion;

public class ItemFactory {

	private static ItemFactory instance;

	private static double FREQUENCE = 0.4;

	static {
		instance = new ItemFactory();
	}

	private ItemFactory() {
	}

	public PlaceableItem create(double freq) {
		if (freq < 0.7)
			return new BombUpgrade();
		else
			return new HealPotion();
	}

	public PlaceableItem create(String type) {

		switch (type.toLowerCase()) {
		case "bombupgrade":
			return this.create(0);
		case "healpotion":
			return this.create(1);

		default:
			return null;
		}
	}

	public PlaceableItem createRandom() {
		return this.create(new Random().nextDouble());
	}

	public double getFrequence() {
		return FREQUENCE;
	}

	public static ItemFactory getInstance() {
		return instance;
	}

}
