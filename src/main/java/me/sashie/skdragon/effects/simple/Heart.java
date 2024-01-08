package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.SimpleRadiusValueEffect;
import me.sashie.skdragon.math.vector.Vector2d;
import me.sashie.skdragon.util.Utils;

/**
 * Created by Sashie on 8/9/2021.
 */
public class Heart extends SimpleRadiusValueEffect {

	public Heart() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
		this.getExtraProperty().initValue(2f, 0.8f, 1f);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		double alpha = (( Math.PI / this.getExtraProperty().getValue(1)) / this.getDensityProperty().getDensity(1)) * step;
		double phi = Math.pow(Math.abs(Math.sin(2 * this.getExtraProperty().getValue(1) * alpha)) + this.getExtraProperty().getValue(2) * Math.abs(Math.sin(this.getExtraProperty().getValue(1) * alpha)), this.getExtraProperty().getValue(3));

		v.setX(phi * (Math.sin(alpha) + Math.cos(alpha)) * this.getRadiusProperty().getRadius(2));
		v.setZ(phi * (Math.cos(alpha) - Math.sin(alpha)) * this.getRadiusProperty().getRadius(1));
	}

	public static Vector2d heart(float step, int density, float value1, float value2, float value3, float radiusX, float radiusY) {
		double alpha = (( Math.PI / value1) / density) * step;
		double phi = Math.pow(Math.abs(Math.sin(2 * value1 * alpha)) + value2 * Math.abs(Math.sin(value1 * alpha)), value3);

		return new Vector2d(phi * (Math.sin(alpha) + Math.cos(alpha)) * radiusX, phi * (Math.cos(alpha) - Math.sin(alpha)) * radiusY);
	}

}
