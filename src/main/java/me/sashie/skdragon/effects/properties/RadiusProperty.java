package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.debug.ParticleEffectException;

public class RadiusProperty {

	private float[] radius;
	
	public float[] getArray() {
		return radius;
	}

	public Float getRadius(int index) throws ParticleEffectException {
		if (index > radius.length) {
			throw new ParticleEffectException("This effect only uses " + this.radius.length + " radius values not " + index);
		}
		return radius[index - 1];
	}

	public void setRadius(int index, float radius) throws ParticleEffectException {
		if (index > this.radius.length) {
			throw new ParticleEffectException("This effect only uses " + this.radius.length + " radius values not " + index);
		}
		this.radius[index - 1] = radius;
	}

	public void initRadius(float... radius) {
		this.radius = radius;
	}
}
