package fr.util.time;

/**
 * Un minuteur
 */
public class Timer {

	private double lastTick;
	private long sec;

	public Timer() {
		lastTick = 0;
		sec = System.nanoTime();
	}

	/**
	 * @return Derniere fois que tick() a ete appele (milli-secondes)
	 */
	public double lastTickMS() {
		return lastTick;
	}

	/**
	 * @return Derniere fois que tick() a ete appele (nano-secondes)
	 */
	public double lastTickNS() {
		return lastTick * 1000000;
	}

	/**
	 * @return Derniere fois que tick() a ete appele (secondes)
	 */
	public double lastTickS() {
		return lastTick / 1000;
	}

	/**
	 * @return Derniere fois que tick() a ete appele (milli-secondes)
	 */
	public double tick() {
		long time = System.nanoTime();
		long tmp = System.nanoTime() - sec;
		sec = time;
		lastTick = tmp / 1000000;
		return lastTick;
	}
}
