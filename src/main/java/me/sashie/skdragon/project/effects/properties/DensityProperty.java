package me.sashie.skdragon.project.effects.properties;

import me.sashie.skdragon.project.debug.ParticleEffectException;

public class DensityProperty {

	private int[] density;

	public int[] getArray() {
		return density;
	}

	public Integer getDensity(int index) throws ParticleEffectException {
		if (index > this.density.length) {
			throw new ParticleEffectException("This effect only uses " + this.density.length + " density values not " + index);
			//return null;
		}
		return density[index - 1];
	}

	public void setDensity(int index, int density) throws ParticleEffectException {
		if (index > this.density.length) {
			throw new ParticleEffectException("This effect only uses " + this.density.length + " density values not " + index);
		}
		this.density[index - 1] = density;
	}

	public void initDensity(int... density) {
		this.density = density;
	}

}
