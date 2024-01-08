package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.SimpleRadiusEffect;
import me.sashie.skdragon.math.vector.Vector2d;
import me.sashie.skdragon.math.vector.Vector3d;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.Utils;
import org.bukkit.util.Vector;

/**
 * Created by Sashie on 8/9/2021.
 */
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
