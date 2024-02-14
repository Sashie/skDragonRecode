package me.sashie.skdragon.project.effects.properties;

import me.sashie.skdragon.project.debug.ParticleEffectException;

public class ExtraProperty {

	private float[] extra;

	public float[] getArray() {
		return extra;
	}

	public Float getValue(int index) throws ParticleEffectException {
		if (index > this.extra.length) {
			throw new ParticleEffectException("This effect only uses " + this.extra.length + " extra values not " + index);
		}
		return extra[index - 1];
	}

	public void setValue(int index, float extra) throws ParticleEffectException {
		if (index > this.extra.length) {
			throw new ParticleEffectException("This effect only uses " + this.extra.length + " extra values not " + index);
		}
		this.extra[index - 1] = extra;
	}

	public void initValue(float... extra) {
		this.extra = extra;
	}

}
