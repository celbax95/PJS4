package fr.scale;

/**
 * Singleton Echelle, permet de redimentionner l'affichage (fonctionnalite de
 * zoom)
 */
public class Scale {

	private static double step = 0.01;

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
	 * diminue le scale
	 */
	public void decreaseScale() {
		scale -= step;
	}

	/**
	 * @return l'echelle
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * @return le pas de changement du scale
	 */
	public double getStep() {
		return scale;
	}

	/**
	 * augmente le scale
	 */
	public void increaseScale() {
		scale += step;
	}

	/**
	 * @param s : l'echelle
	 */
	public void setScale(double s) {
		scale = s;
	}

	public static Scale getInstance() {
		return instance;
	}
}
