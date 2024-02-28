package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.SkDragonRecode;

public class RadiusProperty {

	private float[] radius;
	
	public float[] getArray() {
		return radius;
	}

	public Float getRadius(int index) {
		if (index > radius.length) {
			SkDragonRecode.error("This effect only uses " + this.radius.length + " radius values not " + index);
			return 1.0f;
		}
		return radius[index - 1];
	}

	public void setRadius(int index, float radius) {
		if (index > this.radius.length) {
			SkDragonRecode.error("This effect only uses " + this.radius.length + " radius values not " + index);
			return;
		}
		this.radius[index - 1] = radius;
	}

	public void initRadius(float... radius) {
		this.radius = radius;
	}
}
