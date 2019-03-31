package fr.itemsApp.character;

/**
 * Singleton factory pour les differents character
 */
public class CharacterFactory {

	private static CharacterFactory factory;

	static {
		factory = new CharacterFactory();
	}

	/**
	 * @return L'instance unique de CharacterFactory
	 */
	public static CharacterFactory getInstance() {
		return factory;
	}

	private CharacterFactory() {
	}

	/**
	 * Cree un character
	 *
	 * @param type
	 *            : type du character
	 * @return le character cree
	 */
	public ICharacter create(String type) {
		switch (type.toLowerCase()) {
		case "red":
			return new CharacterRed(0, 0, 100, 1400, 500);
		}
		return null;
	}

}
