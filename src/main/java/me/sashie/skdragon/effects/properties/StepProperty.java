package me.sashie.skdragon.effects.properties;

public class StepProperty {

	private float step;
	private int stepAmount = 1;

	public float getStep() {
		return step;
	}

	public void update() {
		step++;
		if (step >= 719999) {
			step = 0;
		}
	}
}
