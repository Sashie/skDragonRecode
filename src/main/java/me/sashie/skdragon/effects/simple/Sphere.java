package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.SimpleRadiusEffect;
import me.sashie.skdragon.util.RandomUtils;


public class Sphere extends SimpleRadiusEffect {

	public Sphere() {
		this.getRadiusProperty().initRadius(1.5f);
		this.getDensityProperty().initDensity(50);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		v = RandomUtils.getRandomVector(v).multiply(this.getRadiusProperty().getRadius(1));
	}

}
