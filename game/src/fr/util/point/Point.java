package fr.util.point;

import java.io.Serializable;
/**
 * Un point de coordonnées x, y
 */
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;
	public double x;
	public double y;
	/**
	 * Constructeur Point vide
	 */
	public Point() {
		x = 0;
		y = 0;
	}
	/**
	 * Constructeur Point
	 * @param x : abscisse du point
	 * @param y : ordonnée du point
	 */
	public Point(double x, double y) {
		this();
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructeur Point
	 * @param p : un point
	 */
	public Point(Point p) {
		this(p.x, p.y);
	}
	/**
	 * return une nouvelle instance du point courant
	 */
	@Override
	public Point clone() {
		return new Point(this);
	}
	/**
	 * verifie si les 2 objects sont egaux
	 * @param obj : l'Objet avec lequel on compare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	/**
	 * Permet d'orienter le personnage lorsqu'il se déplace
	 * @return un angle entre x et y places dans un cercle trigonométrique 
	 */
	public double getAngle() {
		double a = Math.acos(x);
		if (y < 0)
			a *= -1;
		return (a + Math.PI * 2) % (Math.PI * 2);
	}
	/**
	 * @return x : abscisse du point caste en int
	 */
	public int getIX() {
		return (int) x;
	}
	/**
	 * @return y : ordonnée du point caste en int
	 */
	public int getIY() {
		return (int) y;
	}
	/**
	 * @return la position (x, y) courante
	 */
	public Point getLocation() {
		return this;
	}
	/**
	 * @return x : abscisse du point
	 */
	public double getX() {
		return x;
	}
	/**
	 * @return y : ordonnée du point
	 */
	public double getY() {
		return y;
	}
	/**
	 * Permet de stocker tous les points dans une hashmap
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/**
	 * Modifie les coordonnées du point 
	 * @param x : abscisse du point
	 * @param y : ordonnée du point
	 */
	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Permet de normaliser la vitesse, et la 
	 * ramener dans un cercle trigonometrique.
	 */
	public void normalize() {
		double tmp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (tmp != 0) {
			x /= tmp;
			y /= tmp;
		}
	}
	/**
	 * Modifie les coordonnées du point 
	 * @param x : abscisse du point
	 * @param y : ordonnée du point
	 */
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Modifie les coordonnées du point
	 * @param p : un point
	 */
	public void setLocation(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	/**
	 * methode toString
	 * @return : une chaine contenant les valeurs en x et en y d'un point
	 */
	@Override
	public String toString() {
		return "PointD [x=" + x + ", y=" + y + "]";
	}
	/**
	 * Incremente x et y d'une certaine valueur de déplacement
	 * @param dx : deplacement en x
	 * @param dy : deplacement en y
	 */
	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}
}
