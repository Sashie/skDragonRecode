package me.sashie.skdragon.api.effects.simple.parametric;

import me.sashie.skdragon.api.effects.EffectProperty;
import me.sashie.skdragon.api.effects.Parametric3DEffect;
import me.sashie.skdragon.api.effects.properties.IRadius;
import me.sashie.skdragon.api.effects.properties.IStyle;
import me.sashie.skdragon.api.effects.properties.RadiusProperty;
import me.sashie.skdragon.api.effects.properties.StyleProperty;
import me.sashie.skdragon.api.util.EffectUtils;

/**
 * x = r * cos(t)
 * z = r * sin(t)
 * y = c * t
 */
public class Spiral3D extends Parametric3DEffect implements IRadius, IStyle {

	private RadiusProperty radiusProperty;
	private StyleProperty styleProperty;

	public Spiral3D() {
		radiusProperty = new RadiusProperty();
		styleProperty = new StyleProperty();
		this.getExtraProperty().initValue(1.5f);
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle, double angle2) {
		if (this.getStyleProperty().getStyle() == 1)
			return this.getRadiusProperty().getRadius(1) * Math.cos(angle);
		else
			return this.getRadiusProperty().getRadius(1) * Math.sin(angle);
	}

	@Override
	public double vectorY(double angle, double angle2) {
		return this.getExtraProperty().getValue(1) * angle2;
	}

	@Override
	public double vectorZ(double angle, double angle2) {
		if (this.getStyleProperty().getStyle() == 1)
			return this.getRadiusProperty().getRadius(2) * Math.sin(angle);
		else
			return this.getRadiusProperty().getRadius(2) * Math.cos(angle);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.STYLE, EffectProperty.RADIUS, EffectProperty.EXTRA);
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

	@Override
	public StyleProperty getStyleProperty() {
		return styleProperty;
	}

}
