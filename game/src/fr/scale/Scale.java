package fr.scale;

public class Scale {

	private static double scale = 1;

	private Scale() {
	}

	public static double getScale() {
		return scale;
	}

	public static void setScale(double s) {
		scale = s;
	}
}
