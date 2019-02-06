package fr.util.point;

public class PointCalc {
	private PointCalc() {
	};

	public double getAngle(Point p) {
		double a = Math.acos(p.x);
		if (p.y < 0)
			a *= -1;
		return (a + Math.PI * 2) % (Math.PI * 2);
	}

	public static Point add(Point p1, double p2) {
		return new Point(p1.x + p2, p1.y + p2);
	}

	public static Point add(Point p1, Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}

	public static Point compare(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}

	public static Point dip(Point p1, double p2) {
		return new Point(p1.x / p2, p1.y / p2);
	}

	public static double distance(Point p1, Point p2) {
		Point o = compare(p1, p2);
		return Math.sqrt(Math.pow(o.x, 2) + Math.pow(o.y, 2));
	}

	public static Point div(Point p1, Point p2) {
		return new Point(p1.x / p2.x, p1.y / p2.y);
	}

	public static Point getNormalized(Point p) {
		double tmp = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		return new Point(p.x / tmp, p.y / tmp);
	}

	public static Point mod(Point p1, double p2) {
		return new Point(p1.x % p2, p1.y % p2);
	}

	public static Point mod(Point p1, Point p2) {
		return new Point(p1.x % p2.x, p1.y % p2.y);
	}

	public static Point mult(Point p1, double p2) {
		return new Point(p1.x * p2, p1.y * p2);
	}

	public static Point mult(Point p1, Point p2) {
		return new Point(p1.x * p2.x, p1.y * p2.y);
	}

	public static Point rot(Point p, double angle) {
		angle = (angle + Math.PI * 2) % (Math.PI * 2);
		return rot(p, Math.cos(angle), Math.sin(angle));
	}

	public static Point rot(Point p, double cos, double sin) {
		return new Point(p.x * cos + p.y * -sin, p.x * sin + p.y * cos);
	}

	public static double scalar(Point p1, Point p2) {
		return p1.x * p2.x + p1.y * p2.y;
	}

	public static Point sub(Point p1, double p2) {
		return new Point(p1.x - p2, p1.y - p2);
	}

	public static Point sub(Point p1, Point p2) {
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}
}
