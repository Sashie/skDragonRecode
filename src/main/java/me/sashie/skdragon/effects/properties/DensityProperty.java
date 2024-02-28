package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.SkDragonRecode;

public class DensityProperty {

	private int[] density;
	
	public int[] getArray() {
		return density;
	}

	public Integer getDensity(int index) {
		if (index > this.density.length) {
			SkDragonRecode.error("This effect only uses " + this.density.length + " density values not " + index);
			return 1;
		}
		return density[index - 1];
	}

	public void setDensity(int index, int density) {
		if (index > this.density.length) {
			SkDragonRecode.error("This effect only uses " + this.density.length + " density values not " + index);
			return;
		}
		this.density[index - 1] = density;
	}

	public void initDensity(int... density) {
		this.density = density;
	}
	
}
