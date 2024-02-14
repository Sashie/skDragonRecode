package me.sashie.skdragon.project.effects;

import me.sashie.skdragon.project.effects.properties.IRadius;
import me.sashie.skdragon.project.effects.properties.RadiusProperty;
import me.sashie.skdragon.project.util.EffectUtils;

public abstract class SimpleRadiusEffect extends SimpleEffect implements IRadius {

	private RadiusProperty radiusProperty;

	public SimpleRadiusEffect() {
		radiusProperty = new RadiusProperty();
//		this.initRadius(1.5f);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.RADIUS);
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

}
