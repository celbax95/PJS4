package fr.itemsApp.items;

import java.util.Random;

import fr.items.BombUpgrade;

public class ItemFactory {

	private static ItemFactory instance;

	private static int NB_ITEM = 1;

	static {
		instance = new ItemFactory();
	}

	private ItemFactory() {
	}

	public PlaceableItem create(int type) {

		switch (type % NB_ITEM) {
		case 0:
			return new BombUpgrade();

		default:
			return null;
		}
	}

	public PlaceableItem create(String type) {

		switch (type.toLowerCase()) {
		case "bombupgrade":
			return this.create(0);

		default:
			return null;
		}
	}

	public PlaceableItem createRandom() {
		return this.create(new Random().nextInt(NB_ITEM));
	}

	public static ItemFactory getInstance() {
		return instance;
	}

}
