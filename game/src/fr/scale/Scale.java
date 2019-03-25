package fr.scale;
/**
 * Echelle, permet de redimentionner l'affichage (fonctionnalite de zoom)
 * Utilise un ratio par lequel on multiplie la taille des elements a afficher
 */
public class Scale {

	private static double scale = 1;
	/**
	 * constructeur Scale vide
	 */
	private Scale() {
	}
	/**
	 * @return l'echelle
	 */
	public static double getScale() {
		return scale;
	}
	/**
	 * 
	 * @param s : l'echelle
	 */
	public static void setScale(double s) {
		scale = s;
	}
}
