package fr.itemsApp.items;

public class ItemFactory {

	private static ItemFactory instance;

	static {
		instance = new ItemFactory();
	}

	private ItemFactory() {
	}

	public PlaceableItem create(String type) {
		switch (type.toLowerCase()) {
		case "":

			return null;

		default:
			return null;
		}
	}

	public ItemFactory getInstance() {
		return instance;
	}

}
