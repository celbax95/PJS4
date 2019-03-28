package fr.Camera;

import java.awt.geom.AffineTransform;

import fr.scale.Scale;
import fr.util.point.Point;

public class Camera {

	private Point ms;

	private Point ss;

	private Point a;

	public Camera(Point ss, Point ms, Point a) {
		this.ms = ms;
		this.ss = ss;
		this.a = a;
	}

	public AffineTransform getTransform() {
		double scale = Scale.getInstance().getScale();

		AffineTransform af = new AffineTransform();

		if (a == null)
			af.translate(-(ms.x * scale - ss.x) / 2, -(ms.y * scale - ss.y) / 2);
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
			System.out.println(ms.x + " " + ms.y);

			af.translate(mx, my);
		}
		return af;
	}

	public void setA(Point a) {
		this.a = a;
	}
}
