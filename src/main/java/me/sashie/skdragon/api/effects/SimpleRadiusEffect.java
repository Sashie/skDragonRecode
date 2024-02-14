package me.sashie.skdragon.api.effects;

import me.sashie.skdragon.api.effects.properties.IRadius;
import me.sashie.skdragon.api.effects.properties.RadiusProperty;
import me.sashie.skdragon.api.util.EffectUtils;

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
