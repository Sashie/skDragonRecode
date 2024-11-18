package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;
import me.sashie.skdragon.util.MathUtils;

/**
 * x = (a + b) * cos(t) - (b + c) * cos(((a + b) / b) * t)
 * z = (a + b) * sin(t) - (b + c) * sin(((a + b) / b) * t)
 */
//examples

//r1=25, r2=-10, a=16
//r1=100, r2=40, a=16

//r1=30, r2=-10, a=26 //adobe

//r1=5, r2=-18, a=37 //fish

public class Spirograph2D extends Parametric2DEffect {

	public Spirograph2D() {
		this.getRadiusProperty().initRadius(30f, -10f, 26f);
		this.getDensityProperty().initDensity(100);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public float vectorX(float angle) {
		return (this.getRadiusProperty().getRadius(1) + this.getRadiusProperty().getRadius(2)) * MathUtils.cos(angle) - (this.getRadiusProperty().getRadius(2) + this.getRadiusProperty().getRadius(3)) * MathUtils.cos(((this.getRadiusProperty().getRadius(1) + this.getRadiusProperty().getRadius(2)) / this.getRadiusProperty().getRadius(2)) * angle);
	}

	@Override
	public float vectorZ(float angle) {
		return (this.getRadiusProperty().getRadius(1) + this.getRadiusProperty().getRadius(2)) * MathUtils.sin(angle) - (this.getRadiusProperty().getRadius(2) + this.getRadiusProperty().getRadius(3)) * MathUtils.sin(((this.getRadiusProperty().getRadius(1) + this.getRadiusProperty().getRadius(2)) / this.getRadiusProperty().getRadius(2)) * angle);
	}

}
