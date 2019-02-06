package fr.util.time;

import java.io.Serializable;

public class Cooldown implements Serializable {

	private static final long serialVersionUID = 1L;
	private long freq;
	private long last;

	private Cooldown(Cooldown c) {
		this(c.freq,c.last);
	}

	public Cooldown(long freq) {
		this(freq,System.currentTimeMillis());
	}

	private Cooldown(long freq, long last) {
		assert (freq >= 0);
		this.freq = freq;
		this.last = last;
	}

	@Override
	public Cooldown clone() {
		return new Cooldown(this);
	}

	public long getFreq() {return freq;}
	public long getLast() {return last;}

	public boolean isDone() {
		return (last+freq <= System.currentTimeMillis());
	}

	public void reset() {
		last = System.currentTimeMillis();
	}

	public boolean resetOnDone() {
		if (last+freq <= System.currentTimeMillis()) {
			last = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	public void setFreq(long freq) {
		assert(freq >= 0);
		this.freq = freq;
	}

	public void start() {
		last = System.currentTimeMillis();
	}

	public long timeBefore() {
		long tmp = System.currentTimeMillis();
		if (last+freq > tmp)
			return last+freq-tmp;
		else
			return 0;
	}

}
