package me.sashie.skdragon.project.effects;

import me.sashie.skdragon.project.effects.properties.ExtraProperty;
import me.sashie.skdragon.project.effects.properties.IExtra;
import me.sashie.skdragon.project.effects.properties.IRadius;
import me.sashie.skdragon.project.effects.properties.RadiusProperty;
import me.sashie.skdragon.project.util.EffectUtils;

public abstract class SimpleRadiusValueEffect extends SimpleEffect implements IRadius, IExtra {

	private RadiusProperty radiusProperty;
	private ExtraProperty extraProperty;

	public SimpleRadiusValueEffect() {
		radiusProperty = new RadiusProperty();
		extraProperty = new ExtraProperty();
//		this.initRadius(1.5f);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.RADIUS, EffectProperty.EXTRA);
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}
}
