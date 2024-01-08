package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.SimpleRadiusEffect;
import me.sashie.skdragon.math.vector.Vector2d;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.Utils;

/**
 * Created by Sashie on 8/9/2021.
 */
public class Circle extends SimpleRadiusEffect {

	public Circle() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
	}

	@Override
	public void math(float step) {
		double angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
		v.setX(Math.cos(angle) * this.getRadiusProperty().getRadius(1));
		v.setZ(Math.sin(angle) * this.getRadiusProperty().getRadius(2));
	}

	public static Vector2d circle(float step, int density, float radiusX, float radiusY) {
		double angle = step * MathUtils.PI2 / density;
		return new Vector2d( Math.cos(angle) * radiusX, Math.sin(angle) * radiusY);
	}

}
