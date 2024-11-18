
package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;
import me.sashie.skdragon.util.MathUtils;

/**
 * Created by Sashie on 8/11/2017.
 * 
 * 		x = (a - b) cos(t) + b cos((a/b - 1)t)
 * 		z = (a - b) sin(t) - b sin((a/b - 1)t)
 * 
 */

public class Hypocycloid2D extends Parametric2DEffect {

	public Hypocycloid2D() {
		this.getRadiusProperty().initRadius(-0.6f, 0.3f);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public float vectorX(float angle) {
		return (this.getRadiusProperty().getRadius(1) - this.getRadiusProperty().getRadius(2)) * MathUtils.cos(angle) + this.getRadiusProperty().getRadius(2) * MathUtils.cos(((this.getRadiusProperty().getRadius(1) / this.getRadiusProperty().getRadius(2)) - 1) * angle);
	}

	@Override
	public float vectorZ(float angle) {
		return (this.getRadiusProperty().getRadius(1) - this.getRadiusProperty().getRadius(2)) * MathUtils.sin(angle) - this.getRadiusProperty().getRadius(2) * MathUtils.sin(((this.getRadiusProperty().getRadius(1) / this.getRadiusProperty().getRadius(2)) - 1) * angle);
	}

}
