package me.sashie.skdragon.api.effects.simple.parametric;

import me.sashie.skdragon.api.effects.Parametric2DEffect;

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
		this.getExtraProperty().initValue(30f, -10f, 26f);
		this.getDensityProperty().initDensity(100);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle) {
		return (this.getExtraProperty().getValue(1) + this.getExtraProperty().getValue(2)) * Math.cos(angle) - (this.getExtraProperty().getValue(2) + this.getExtraProperty().getValue(3)) * Math.cos(((this.getExtraProperty().getValue(1) + this.getExtraProperty().getValue(2)) / this.getExtraProperty().getValue(2)) * angle);
	}

	@Override
	public double vectorZ(double angle) {
		return (this.getExtraProperty().getValue(1) + this.getExtraProperty().getValue(2)) * Math.sin(angle) - (this.getExtraProperty().getValue(2) + this.getExtraProperty().getValue(3)) * Math.sin(((this.getExtraProperty().getValue(1) + this.getExtraProperty().getValue(2)) / this.getExtraProperty().getValue(2)) * angle);
	}

}
