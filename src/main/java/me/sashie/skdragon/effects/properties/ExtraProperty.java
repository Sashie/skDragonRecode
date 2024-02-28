package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.SkDragonRecode;

public class ExtraProperty {

	private float[] extra;
	
	public float[] getArray() {
		return extra;
	}

	public Float getValue(int index) {
		if (index > this.extra.length) {
			SkDragonRecode.error("This effect only uses " + this.extra.length + " extra values not " + index);
			return 1.0f;
		}
		return extra[index - 1];
	}

	public void setValue(int index, float extra) {
		if (index > this.extra.length) {
			SkDragonRecode.error("This effect only uses " + this.extra.length + " extra values not " + index);
			return;
		}
		this.extra[index - 1] = extra;
	}

	public void initValue(float... extra) {
		this.extra = extra;
	}
	
}
