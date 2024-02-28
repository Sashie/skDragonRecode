package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SimpleRadiusEffect;
import me.sashie.skdragon.effects.properties.IStyle;
import me.sashie.skdragon.effects.properties.StyleProperty;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.Utils;

public class Sphere extends SimpleRadiusEffect implements IStyle {

	private StyleProperty styleProperty;

	public Sphere() {
		styleProperty = new StyleProperty();
		this.getRadiusProperty().initRadius(1.5f);
		this.getDensityProperty().initDensity(60);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		if (this.getStyleProperty().getStyle() == 1) {
			v = RandomUtils.getRandomVector(v).multiply(this.getRadiusProperty().getRadius(1));
		} else if (this.getStyleProperty().getStyle() >= 2) {
			double t = step / this.getDensityProperty().getDensity(1);
			double angle1 = Math.acos(1 - 2 * t);
			double angle2 = Utils.GOLDEN_ANGLE_INCREMENT * step;

			double x = this.getRadiusProperty().getRadius(1) * Math.sin(angle1) * Math.cos(angle2);
			double y = this.getRadiusProperty().getRadius(1) * Math.sin(angle1) * Math.sin(angle2);
			double z = this.getRadiusProperty().getRadius(1) * Math.cos(angle1);
			v.setX(x).setY(y).setZ(z);
		}
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
