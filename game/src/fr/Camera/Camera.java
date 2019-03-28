package fr.Camera;

import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.point.PointCalc;

public class Camera {

	private static final double changingStep = 10;

	private static final double step = 5;

	private Point ms;

	private Point ss;

	private Point a;

	private Point pos;

	public Camera(Point ss, Point ms, Point a) {
		this.ms = ms;
		this.ss = ss;
		this.a = a;
		this.pos = a.clone();
	}

	public Point getAimedPos() {
		double scale = Scale.getInstance().getScale();

		if (a == null)
			return new Point(-(ms.x * scale - ss.x) / 2, -(ms.y * scale - ss.y) / 2);
		else {
			double mx = -(a.x * scale - ss.x / 2), my = -(a.y * scale - ss.y / 2);
			if (ms.x * scale > ss.x) {
				if (mx >= 0)
					mx = 0;
				else if (mx < -(ms.x * scale - ss.x))
					mx = ss.x - ms.x * scale;
			}
			if (ms.y * scale > ss.y) {
				if (my >= 0)
					my = 0;
				else if (my < -(ms.y * scale - ss.y))
					my = ss.y - ms.y * scale;
			}
			return new Point(mx, my);
		}
	}

	public Point getPos() {
		return pos;
	}

	public void setA(Point a) {
		this.a = a;
	}

	public void update() {
		Point ap = getAimedPos();
		Point d = PointCalc.compare(pos, ap);
		if (d.x > changingStep) {
			pos.x += step * Math.signum(ap.x - pos.x);
		} else {
			pos.x = ap.x;
		}
		if (d.y > changingStep) {
			pos.y += step * Math.signum(ap.y - pos.y);
		} else {
			pos.y = ap.y;
		}
	}
}
