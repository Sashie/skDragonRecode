package me.sashie.skdragon.effects;

import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;

public abstract class SpecialEffect extends EffectData {

	private boolean init = true;
	
	public SpecialEffect() {

	}

	public void init() {};

	public void init(DynamicLocation location) {};

	public abstract void update(DynamicLocation location);
	
	@Override
	public void update() {

		init();
		
		for (int i = 0; i < this.getLocations().length; i++) {
			DynamicLocation location = this.getLocations()[i];
			if (init) {
				if (!location.isDynamic())
					location.add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				init(location);
				if (i == this.getLocations().length - 1) {
					init = false;
				}
			} else {
				location.update();
				if (location.isDynamic())
					location.add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				update(location);
			}
		}
	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return EffectUtils.array(EffectProperty.DISPLACEMENT);
	}
}