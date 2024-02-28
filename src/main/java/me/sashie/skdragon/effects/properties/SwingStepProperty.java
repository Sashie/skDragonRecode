package me.sashie.skdragon.effects.properties;

public class SwingStepProperty {

	private float step;
	private int stepAmount = 1;
	private boolean oscillate, reverse;

	public boolean isOscillating() {
		return oscillate;
	}

	public void setOscillation(boolean oscillate) {
		this.oscillate = oscillate;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public float getStep() {
		return step;
	}

	public void update(int max) {
		if (oscillate) {
			if (step > max || step < 0)
				stepAmount = -stepAmount;
		} else {
			if (step > max)
				step = 0;
			else if (step < 0)
				step = max;
		}
		if (reverse)
			step -= stepAmount;
		else
			step += stepAmount;
	}

	public void update() {
		step++;
		if (step >= 719999) {
			step = 0;
		}
	}
}
