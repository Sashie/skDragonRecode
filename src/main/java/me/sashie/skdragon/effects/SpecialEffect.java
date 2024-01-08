package me.sashie.skdragon.effects;

import me.sashie.skdragon.util.DynamicLocation;

/**
 * Created by Sashie on 9/30/2017.
 */
public abstract class SpecialEffect extends EffectData {

	private boolean init;
	
	public SpecialEffect() {

	}

	public void init() {};

	public void init(DynamicLocation location) {};

	public abstract void update(DynamicLocation location, float step);
	
	@Override
	public void update(float step) {

		init();
		
		for (int i = 0; i < this.getLocations().length; i++) {
			if (!init) {
				if (!this.getLocations()[i].isDynamic())
					this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				init(this.getLocations()[i]);
				if (i == this.getLocations().length - 1) {
					init = true;
				}
	    	} else {
	    		this.getLocations()[i].update();
    			if (this.getLocations()[i].isDynamic())
    				this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				update(this.getLocations()[i], step);
	    	}
		}
	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return null;
	}
}