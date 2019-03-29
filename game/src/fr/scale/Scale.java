package fr.scale;

/**
 * Singleton Echelle, permet de redimentionner l'affichage (fonctionnalite de
 * zoom)
 */
public class Scale implements Runnable {

	private static double maxScale = 2, minScale = 0.6;

	private static double step = 0.005;

	private static double changingStep = 0.07;

	private static double animationSpeedFactor = 10;

	private static Scale instance;

	static {
		instance = new Scale(1);
	}

	private static long holdDelay = 50;

	private double tmpScale;

	private double scale;

	private double aimedScale;

	private Thread myThread;

	private Object lock;

	/**
	 * constructeur Scale vide
	 */
	private Scale(double scale) {
		this.scale = scale;
		tmpScale = scale;
		aimedScale = scale;
		lock = new Object();
		(myThread = new Thread(this)).start();
	}

	/**
	 * diminue le scale
	 */
	public void decrease() {
		aimedScale -= changingStep;
		if (aimedScale < minScale)
			aimedScale = minScale;
		unLock();
		try {
			Thread.sleep(holdDelay);
		} catch (InterruptedException e) {
		}
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
		return step;
	}

	/**
	 * augmente le scale
	 */
	public void increase() {
		aimedScale += changingStep;
		if (aimedScale > maxScale)
			aimedScale = maxScale;
		unLock();
		try {
			Thread.sleep(holdDelay);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Animation du changement de scale
	 */
	@Override
	public void run() {
		try {
			double difference = 0.;

			while (!Thread.currentThread().isInterrupted()) {
				while ((difference = Math.abs(tmpScale - aimedScale)) > 0.02) {

					if (scale < aimedScale)
						tmpScale += step * difference * animationSpeedFactor;
					else
						tmpScale -= step * difference * animationSpeedFactor;

					Thread.sleep(16);
				}
				synchronized (lock) {
					lock.wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param s : l'echelle
	 */
	public void setScale(double s) {
		scale = s;
		unLock();
	}

	/**
	 * Declanche l'animation
	 */
	private void unLock() {
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	/**
	 * met a jour la valeur reelle de scale par rapport a l'animation
	 */
	public double update() {
		double tmp = tmpScale - scale;
		scale = tmpScale;
		return tmp;
	}

	/**
	 * @return l'instance unique de scale
	 */
	public static Scale getInstance() {
		return instance;
	}
}
