package fr.scale;

/**
 * Singleton Echelle, permet de redimentionner l'affichage (fonctionnalite de
 * zoom)
 */
public class Scale {

	private static Scale instance;

	static {
		instance = new Scale(1);
	}

	private double scale;

	/**
	 * constructeur Scale vide
	 */
	private Scale(double scale) {
		this.scale = scale;
	}

	/**
	 * @return l'echelle
	 */
	public double getScale() {
		return scale;
	}
	/**
	 *
	 * @param s : l'echelle
	 */
	public void setScale(double s) {
		scale = s;
	}
	public static Scale getInstance() {
		return instance;
	}
}
