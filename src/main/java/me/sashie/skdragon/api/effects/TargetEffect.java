package me.sashie.skdragon.api.effects;

import me.sashie.skdragon.api.util.DynamicLocation;
import me.sashie.skdragon.api.util.EffectUtils;
import org.bukkit.util.Vector;


/**
 * Effects using this type require a target location 'setTarget(DynamicLocation)' and optionally a target displacement 'setTargetDisplacement(Vector)'
 * - this should be set before starting these effects
 */
public abstract class TargetEffect extends EffectData {

	private DynamicLocation[] targets;
	private Vector targetDisplacement;

	/**
	 * Should this effect stop playing if the target entity becomes null?
	 */
	//public boolean disappearWithTargetEntity = false;

	private boolean init;

	public TargetEffect() {

	}

	public void init(DynamicLocation location) {
	}

	;

	public abstract void update(DynamicLocation location, DynamicLocation target, float step);

	@Override
	public void update(float step) {
		if (targets == null) {
			this.triggerStop(true);
			return;
		}

		for (int j = 0; j < targets.length; j++) {
			if (!init) {
				if (targetDisplacement != null)
					targets[j].add(targetDisplacement);
			} else {
				targets[j].update();
				if (targets[j].isDynamic() && targetDisplacement != null)
					targets[j].add(targetDisplacement);
			}

			for (int i = 0; i < this.getLocations().length; i++) {
				if (!this.init) {
					if (!this.getLocations()[i].isDynamic())
						this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
					init(this.getLocations()[i]);

					if (i == this.getLocations().length - 1) {
						this.init = true;
					}
				} else {
					this.getLocations()[i].update();
					if (this.getLocations()[i].isDynamic())
						this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
					update(this.getLocations()[i], targets[j], step);
				}
			}
		}


	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return EffectUtils.array(EffectProperty.DISPLACEMENT);
	}

	public void setTargets(DynamicLocation[] targets) {
		this.targets = targets;
	}

	public DynamicLocation[] getTargets() {
		return targets;
	}

	public void setTargetDisplacement(Vector targetDisplacement) {
		this.targetDisplacement = targetDisplacement;
	}

	public Vector getTargetDisplacement() {
		return targetDisplacement;
	}
}