package me.sashie.skdragon.effects.properties;

public class StepProperty {

	private float step;
	private float stepAmount = 0.1f;

	public float getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void update() {
		step++;
		if (step >= 719999) {
			step = 0;
		}
	}
}
