package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SimpleEffect;
import me.sashie.skdragon.effects.properties.IStyle;
import me.sashie.skdragon.effects.properties.StyleProperty;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.Utils;

public class Sphere extends SimpleEffect implements IStyle {

	private StyleProperty styleProperty;
	private double x, y, z;

	public Sphere() {
		styleProperty = new StyleProperty();
		this.getRadiusProperty().initRadius(1.5f, 1.5f, 1.5f);
		this.getDensityProperty().initDensity(60);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		if (this.getStyleProperty().getStyle() == 1) {
			v = RandomUtils.getRandomVector(v);
			x = this.getRadiusProperty().getRadius(1) * v.getX();
			y = this.getRadiusProperty().getRadius(2) * v.getY();
			z = this.getRadiusProperty().getRadius(3) * v.getZ();
		} else if (this.getStyleProperty().getStyle() == 2) {
			double t = step / this.getDensityProperty().getDensity(1);
			float angle1 = (float) Math.acos(1 - 2 * t);
			float angle2 = (float) (Utils.GOLDEN_ANGLE_INCREMENT * step);

			x = this.getRadiusProperty().getRadius(1) * MathUtils.sin(angle1) * MathUtils.cos(angle2);
			y = this.getRadiusProperty().getRadius(2) * MathUtils.cos(angle1);
			z = this.getRadiusProperty().getRadius(3) * MathUtils.sin(angle1) * MathUtils.sin(angle2);
		} else if (this.getStyleProperty().getStyle() == 3) {
			float angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
			// double angle = (step % this.getDensityProperty().getDensity(1)) * Utils.PI / this.getDensityProperty().getDensity(1);

			x = this.getRadiusProperty().getRadius(1) * MathUtils.cos(angle) * MathUtils.sin(angle);
			y = this.getRadiusProperty().getRadius(2) * MathUtils.cos(angle);
			z = this.getRadiusProperty().getRadius(3) * MathUtils.sin(angle) * MathUtils.sin(angle);
		} else if (this.getStyleProperty().getStyle() >= 4) {
			float t = (step % this.getDensityProperty().getDensity(1)) * 2.0f / this.getDensityProperty().getDensity(1) - 1.0f;
			float theta = MathUtils.PI2 * (t % 1.0f);

			x = this.getRadiusProperty().getRadius(1) * t;
			y = this.getRadiusProperty().getRadius(2) * MathUtils.cos(theta);
			z = this.getRadiusProperty().getRadius(3) * MathUtils.sin(theta);
		}
		v.setX(x).setY(y).setZ(z);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.STYLE);
	}

	@Override
	public StyleProperty getStyleProperty() {
		return styleProperty;
	}
}
