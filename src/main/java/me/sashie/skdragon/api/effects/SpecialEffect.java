package me.sashie.skdragon.api.effects;

import me.sashie.skdragon.api.util.DynamicLocation;
import me.sashie.skdragon.api.util.EffectUtils;


public abstract class SpecialEffect extends EffectData {

	private boolean init;

	public SpecialEffect() {

	}

	public void init() {
	}

	;

	public void init(DynamicLocation location) {
	}

	;

	public abstract void update(DynamicLocation location, float step);

	@Override
	public void update(float step) {

		init();

		for (int i = 0; i < this.getLocations().length; i++) {
			DynamicLocation location = this.getLocations()[i];
			if (!init) {
				if (!location.isDynamic())
					location.add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				init(location);
				if (i == this.getLocations().length - 1) {
					init = true;
				}
			} else {
				location.update();
				if (location.isDynamic())
					location.add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				update(location, step);
			}
		}
	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return EffectUtils.array(EffectProperty.DISPLACEMENT);
	}
}