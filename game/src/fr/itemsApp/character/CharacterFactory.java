package fr.itemsApp.character;

/**
 * Singleton factory pour les differents character
 */
public class CharacterFactory {

	private static CharacterFactory factory;

	static {
		factory = new CharacterFactory();
	}

	private CharacterFactory() {
	}

	public ICharacter create(String type) {
		switch (type.toLowerCase()) {
		case "red":
			return new CharacterRed(0, 0, 900, 500);
		}
		return null;
	}

	/**
	 * @return L'instance unique de CharacterFactory
	 */
	public static CharacterFactory getInstance() {
		return factory;
	}

}
