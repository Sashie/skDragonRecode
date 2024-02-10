package me.sashie.skdragon.effects;

import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.util.DynamicLocation;

public abstract class WingsEffect extends SpecialEffect implements IExtra {

	private ExtraProperty extraProperty;

	float wingAngle = 0;
	float flap = 0;
	float flapStep = 0.3f;
	float flapRange = 20;

	public WingsEffect() {
		extraProperty = new ExtraProperty();
	}

	public abstract void updateWings(DynamicLocation location, float flap);
	
	@Override
	public void update(DynamicLocation location, float step) {
		updateWings(location, flap);
		if (isFlapping()){
			if (flap > flapRange || flap < 0.0f) flapStep = -flapStep;
			flap += flapStep;
		}
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	public boolean isFlapping() {
		return flapStep != 0.0f;
	}
	
	public float getFlapStep() {
		return flapStep;
	}
	
	public void setFlapStep(float flapStep) {
		if (flapStep <= -5.0f) {
			flapStep = -5.0f;
		} else if (flapStep >= 5.0f) {
			flapStep = 5.0f;
		}
		this.flapStep = flapStep;
	}
	
	public float getFlapRange() {
		return flapRange;
	}
	
	public void setFlapRange(float flapRange) {
		this.flapRange = flapRange;
	}
	
	public float getWingAngle() {
		return wingAngle;
	}
	
	public void setWingAngle(float wingAngle) {
		this.wingAngle = wingAngle;
	}
}