package me.sashie.skdragon.effects;

import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.util.DynamicLocation;

/**
 * Created by Sashie on 12/12/2016.
 */

public abstract class WingsEffect extends SpecialEffect implements IExtra {

	private ExtraProperty extraProperty;

	protected int wingStyle;
	boolean flapMode;
	float flapStep;
	float flapRange;
	float wingAngle;
	float flap = 0;
	
	public WingsEffect() {
		extraProperty = new ExtraProperty();
	}

	public abstract void updateWings(DynamicLocation location, float flap);
	
	@Override
	public void update(DynamicLocation location, float step) {

		updateWings(location, flap);
        
    	if (flapMode){
			if (flap > flapRange || flap < 0) flapStep = -flapStep;
			flap += flapStep;
		}
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	public boolean getFlapMode() {
		return flapMode;
	}
	
	public void setFlapMode(boolean flapMode) {
		this.flapMode = flapMode;
	}
	
	public float getFlapStep() {
		return flapStep;
	}
	
	public void setFlapStep(float flapStep) {
		if (flapStep <= -5.0f) {
			flapStep = -5.0f;
		} else if (flapStep == 0.0f) {
			flapMode = false;
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
	
	public int getStyle() {
		return wingStyle;
	}
	
	protected abstract void setStyle();
	
	public void setStyle(int style) {
		this.wingStyle = style;
		setStyle();
	}
}