
package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;

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
	public double vectorX(double angle) {
		return (this.getRadiusProperty().getRadius(1) - this.getRadiusProperty().getRadius(2)) * Math.cos(angle) + this.getRadiusProperty().getRadius(2) * Math.cos(((this.getRadiusProperty().getRadius(1) / this.getRadiusProperty().getRadius(2)) - 1) * angle);
	}

	@Override
	public double vectorZ(double angle) {
		return (this.getRadiusProperty().getRadius(1) - this.getRadiusProperty().getRadius(2)) * Math.sin(angle) - this.getRadiusProperty().getRadius(2) * Math.sin(((this.getRadiusProperty().getRadius(1) / this.getRadiusProperty().getRadius(2)) - 1) * angle);
	}

}
