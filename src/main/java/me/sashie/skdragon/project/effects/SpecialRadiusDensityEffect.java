package me.sashie.skdragon.project.effects;

import me.sashie.skdragon.project.effects.properties.DensityProperty;
import me.sashie.skdragon.project.effects.properties.IDensity;
import me.sashie.skdragon.project.effects.properties.IRadius;
import me.sashie.skdragon.project.effects.properties.RadiusProperty;
import me.sashie.skdragon.project.util.EffectUtils;

public abstract class SpecialRadiusDensityEffect extends SpecialEffect implements IRadius, IDensity {

	private RadiusProperty radiusProperty;
	private DensityProperty densityProperty;

	public SpecialRadiusDensityEffect() {
		radiusProperty = new RadiusProperty();
		densityProperty = new DensityProperty();
//		this.initRadius(1.5f);
	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return EffectUtils.array(EffectProperty.RADIUS, EffectProperty.DENSITY, EffectProperty.DISPLACEMENT);
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

}
