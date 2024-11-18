package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.SkDragonRecode;

public class RadiusProperty {

	private float[] radius;
	private float[] startRadius, endRadius;
	private boolean[] oscillate, repeat;
	private float[] stepAmount;

	public float[] getArray() {
		return radius;
	}

	public boolean isOscillating(int index) {
		return oscillate[index - 1];
	}

	public void setOscillation(int index, boolean oscillate) {
		if (oscillate && this.repeat[index - 1]) this.repeat[index - 1] = false;
		this.oscillate[index - 1] = oscillate;
	}

	public boolean isRepeating(int index) {
		return repeat[index - 1];
	}

	public void setRepeating(int index, boolean repeat) {
		if (repeat && this.oscillate[index - 1]) this.oscillate[index - 1] = false;
		this.repeat[index - 1] = repeat;
	}

	public float getStepAmount(int index) {
		return this.stepAmount[index - 1];
	}

	public void setStepAmount(int index, float stepAmount) {
		if (index > this.stepAmount.length) {
			SkDragonRecode.error("This effect only uses " + this.endRadius.length + " end radius values not " + index);
			return;
		}
		if (Math.signum(this.stepAmount[index - 1]) == -1 && Math.signum(stepAmount) != -1) {
			this.stepAmount[index - 1] =  -stepAmount;
		} else {
			this.stepAmount[index - 1] =  stepAmount;
		}
	}

	public float getRadius(int index) {
		if (index > radius.length) {
			SkDragonRecode.error("This effect only uses " + this.radius.length + " radius values not " + index);
			return 1.0f;
		}
		return radius[index - 1];
	}

	/**
	 * Sets the radius of an effect
	 */
	public void setRadius(int index, float radius) {
		if (index > this.radius.length) {
			SkDragonRecode.error("This effect only uses " + this.radius.length + " radius values not " + index);
			return;
		}
		this.radius[index - 1] = radius;
	}

	public float getStartRadius(int index) {
		if (this.startRadius == null) {
			SkDragonRecode.error("A start radius is not set for this effect yet");
			return 1.0f;
		}
		if (index > this.startRadius.length) {
			SkDragonRecode.error("This effect only uses " + this.startRadius.length + " end radius values not " + index);
			return 1.0f;
		}
		return this.startRadius[index - 1];
	}

	public void setStartRadius(int index, float radius) {
		if (this.startRadius == null) {
			this.startRadius = new float[this.radius.length];
		}
		if (index > this.startRadius.length) {
			SkDragonRecode.error("This effect only uses " + this.startRadius.length + " end radius values not " + index);
			return;
		}
		this.startRadius[index - 1] = radius;
	}

	public float getEndRadius(int index) {
		if (this.endRadius == null) {
			SkDragonRecode.error("An end radius is not set for this effect yet");
			return 1.0f;
		}
		if (index > this.endRadius.length) {
			SkDragonRecode.error("This effect only uses " + this.endRadius.length + " end radius values not " + index);
			return 1.0f;
		}
		return this.endRadius[index - 1];
	}

	public void setEndRadius(int index, float radius) {
		if (this.endRadius == null) {
			this.endRadius = new float[this.radius.length];
		}
		if (index > this.endRadius.length) {
			SkDragonRecode.error("This effect only uses " + this.endRadius.length + " end radius values not " + index);
			return;
		}
		this.endRadius[index - 1] = radius;
	}

	public void updateRadius() {
		if (startRadius == null || endRadius == null) return;

		for (int i = 0; i < radius.length; i++) {
			float start = startRadius[i];
			float end = endRadius[i];

			if (start == end)
				break;

			float direction = Math.signum(end - start);

			if (oscillate[i]) {
				if ((direction == 1 && radius[i] >= end) || (direction == -1 && radius[i] <= end))
					stepAmount[i] = -stepAmount[i];
			} else if (repeat[i]) {
				if ((direction == 1 && radius[i] >= end) || (direction == -1 && radius[i] <= end))
					radius[i] = start;
				else if ((direction == 1 && radius[i] <= start) || (direction == -1 && radius[i] >= start))
					radius[i] = end;
			}

			radius[i] += stepAmount[i];
		}
	}

	public void initRadius(float... radius) {
		this.radius = radius;
		this.oscillate = new boolean[radius.length];
		this.repeat = new boolean[radius.length];
		this.stepAmount = new float[radius.length];
		for (int i = 0; i < radius.length; i++) {
			this.stepAmount[i] = 0.1f;
			this.repeat[i] = true;
		}
	}
}
