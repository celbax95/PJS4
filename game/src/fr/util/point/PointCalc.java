package fr.util.point;

public class PointCalc {
	private PointCalc() {
	};

	/**
	 * Addition entre un point et un nombre
	 *
	 * @param p1 : le point
	 * @param p2 : le nombre
	 * @return le resultat de l'addition
	 */
	public static Point add(Point p1, double p2) {
		return new Point(p1.x + p2, p1.y + p2);
	}

	/**
	 * Addition entre deux points
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return le resultat de l'addition
	 */
	public static Point add(Point p1, Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}

	/**
	 * Calcul de la difference entre deux points
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return la difference entre les coordonnees x et y respectivement de p1 et p2
	 */
	public static Point compare(Point p1, Point p2) {
		return new Point(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
	}

	/**
	 * Distance entre deux points
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return la distance entre les deux points
	 */
	public static double distance(Point p1, Point p2) {
		Point o = compare(p1, p2);
		return Math.sqrt(Math.pow(o.x, 2) + Math.pow(o.y, 2));
	}

	/**
	 * Division d'un point par un nombre
	 *
	 * @param p1 : le point
	 * @param p2 : le nombre
	 * @return le resultat de la division du point par le nombre
	 */
	public static Point div(Point p1, double p2) {
		return new Point(p1.x / p2, p1.y / p2);
	}

	/**
	 * Division de deux points
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return le resultat de la division des deux points
	 */
	public static Point div(Point p1, Point p2) {
		return new Point(p1.x / p2.x, p1.y / p2.y);
	}

	/**
	 * Calcul de l'angle d'inclinaison du point par rapport au referentiel (0,0)
	 *
	 * @param p : le point
	 * @return l'angle entre le segment p et (0,0)
	 */
	public static double getAngle(Point p) {
		Point o = p.clone();
		o.normalize();

		double a = Math.acos(o.x);
		if (o.y < 0)
			a *= -1;
		return (a + Math.PI * 2) % (Math.PI * 2);
	}

	/**
	 * Normalise un point pour qu'il soit compris dans le cercle trigonometrique
	 *
	 * @param p : le point
	 * @return le point normalise
	 */
	public static Point getNormalized(Point p) {
		double tmp = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		return new Point(p.x / tmp, p.y / tmp);
	}

	/**
	 * Modularise un point par un nombre
	 *
	 * @param p1 : le point
	 * @param p2 : le nombre
	 * @return le point modulo le nombre
	 */
	public static Point mod(Point p1, double p2) {
		return new Point(p1.x % p2, p1.y % p2);
	}

	/**
	 * Modularise le point 1 par rapport au point 2
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return point 1 modulo point 2
	 */
	public static Point mod(Point p1, Point p2) {
		return new Point(p1.x % p2.x, p1.y % p2.y);
	}

	/**
	 * Multipli un point par un nombre
	 *
	 * @param p1 : le point
	 * @param p2 : le nombre
	 * @return le point multiplie par le nombre
	 */
	public static Point mult(Point p1, double p2) {
		return new Point(p1.x * p2, p1.y * p2);
	}

	/**
	 * Multiplie deux point entre eux
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return les deux point multiplies entre eux
	 */
	public static Point mult(Point p1, Point p2) {
		return new Point(p1.x * p2.x, p1.y * p2.y);
	}

	/**
	 * Applique une rotation a un point par rapport au referentiel (0,0)
	 *
	 * @param p     : le point
	 * @param angle : l'angle de rotation appliquer
	 * @return le point apres rotation
	 */
	public static Point rot(Point p, double angle) {
		angle = (angle + Math.PI * 2) % (Math.PI * 2);
		return rot(p, Math.cos(angle), Math.sin(angle));
	}

	/**
	 * Applique une rotation a un point par rapport au referentiel (0,0)
	 *
	 * @param p   : le point
	 * @param cos : cosinus de l'angle a appliquer
	 * @param sin : sinus de l'angle a appliquer
	 * @return le point apres rotation
	 */
	public static Point rot(Point p, double cos, double sin) {
		return new Point(p.x * cos + p.y * -sin, p.x * sin + p.y * cos);
	}

	/**
	 * Calcule le produit scalaire entre deux points
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return Le produit scalaire des deux nombres
	 */
	public static double scalar(Point p1, Point p2) {
		return p1.x * p2.x + p1.y * p2.y;
	}

	/**
	 * Soustrait un nombre a un point
	 *
	 * @param p1 : le point
	 * @param p2 : le nombre
	 * @return le resultat de la soustraction
	 */
	public static Point sub(Point p1, double p2) {
		return new Point(p1.x - p2, p1.y - p2);
	}

	/**
	 * Soustrait le point 2 au point 1
	 *
	 * @param p1 : point 1
	 * @param p2 : point 2
	 * @return le resultat de la soustraction
	 */
	public static Point sub(Point p1, Point p2) {
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}
}
