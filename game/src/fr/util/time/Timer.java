package fr.util.time;

public class Timer {

	private double lastTick;
	private long sec;

	public Timer() {
		lastTick = 0;
		sec = System.nanoTime();
	}

	public double lastTickMS() {
		return lastTick;
	}

	public double lastTickNS() {
		return lastTick * 1000000;
	}

	public double lastTickS() {
		return lastTick / 1000;
	}

	public double tick() {
		long time = System.nanoTime();
		long tmp = System.nanoTime() - sec;
		sec = time;
		lastTick = tmp / 1000000;
		return lastTick;
	}
}
