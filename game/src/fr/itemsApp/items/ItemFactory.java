package fr.itemsApp.items;

public class ItemFactory {

	private static ItemFactory instance;

	static {
		instance = new ItemFactory();
	}

	private ItemFactory() {
	}

	public ItemFactory getInstance() {
		return instance;
	}

}
