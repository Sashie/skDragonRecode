package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.SimpleValueEffect;
import me.sashie.skdragon.util.MathUtils;

public class Heart extends SimpleValueEffect {

	public Heart() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
		this.getExtraProperty().initValue(2f, 0.8f, 1f);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		float alpha = (( MathUtils.PI / this.getExtraProperty().getValue(1)) / this.getDensityProperty().getDensity(1)) * step;
		double phi = Math.pow(Math.abs(MathUtils.sin(2 * this.getExtraProperty().getValue(1) * alpha)) + this.getExtraProperty().getValue(2) * Math.abs(MathUtils.sin(this.getExtraProperty().getValue(1) * alpha)), this.getExtraProperty().getValue(3));

		v.setX(phi * (MathUtils.sin(alpha) + MathUtils.cos(alpha)) * this.getRadiusProperty().getRadius(2));
		v.setZ(phi * (MathUtils.cos(alpha) - MathUtils.sin(alpha)) * this.getRadiusProperty().getRadius(1));
	}
}
