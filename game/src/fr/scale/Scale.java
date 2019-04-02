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
		this.tmpScale = scale;
		this.aimedScale = scale;
		this.lock = new Object();
		(this.myThread = new Thread(this)).start();
	}

	/**
	 * diminue le scale
	 */
	public void decrease() {
		this.aimedScale -= changingStep;
		if (this.aimedScale < minScale)
			this.aimedScale = minScale;
		this.unLock();
		try {
			Thread.sleep(holdDelay);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * @return l'echelle
	 */
	public double getScale() {
		return this.scale;
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
		this.aimedScale += changingStep;
		if (this.aimedScale > maxScale)
			this.aimedScale = maxScale;
		this.unLock();
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
				while ((difference = Math.abs(this.tmpScale - this.aimedScale)) > 0.02) {

					if (this.scale < this.aimedScale)
						this.tmpScale += step * difference * animationSpeedFactor;
					else
						this.tmpScale -= step * difference * animationSpeedFactor;

					Thread.sleep(16);
				}
				synchronized (this.lock) {
					this.lock.wait();
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
		this.scale = s;
		this.unLock();
	}

	/**
	 * Declanche l'animation
	 */
	private void unLock() {
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * met a jour la valeur reelle de scale par rapport a l'animation
	 */
	public double update() {
		double tmp = this.tmpScale - this.scale;
		this.scale = this.tmpScale;
		return tmp;
	}

	/**
	 * @return l'instance unique de scale
	 */
	public static Scale getInstance() {
		return instance;
	}
}
