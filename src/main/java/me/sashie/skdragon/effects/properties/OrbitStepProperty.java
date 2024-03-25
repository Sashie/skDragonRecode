package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.particles.Value3d;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.Utils;
import me.sashie.skdragon.util.VectorUtils;
import org.bukkit.util.Vector;

public class OrbitStepProperty {

	Vector v = new Vector();
	private Value3d axis = new Value3d(0, 1, 0);
	private float step;
	private float radius = 0.0f;
	private int density = 30;

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	/**
	 * Should not be used in effects
	 *
	 * @return Vector with values in degrees
	 */
	public Vector getAxisAsVector() {
		return new Vector(Math.toDegrees(axis.getX()), Math.toDegrees(axis.getY()), Math.toDegrees(axis.getZ()));
	}

	/**
	 *
	 * @return Values in radians
	 */
	public Value3d getAxis() {
		return axis;
	}

	/**
	 * Input values in degrees
	 */
	public void setAxis(Value3d axis) {
		setAxis(axis.getX(), axis.getY(), axis.getZ());
	}

	/**
	 * Input values in degrees
	 */
	public void setAxis(Vector axis) {
		setAxis(axis.getX(), axis.getY(), axis.getZ());
	}

	/**
	 * Input values in degrees
	 */
	public void setAxis(double x, double y, double z) {
		this.axis.setX(Math.toRadians(x));
		this.axis.setY(Math.toRadians(y));
		this.axis.setZ(Math.toRadians(z));
		this.axis.normalize();
	}

	public float getStep() {
		return step;
	}

	public void updateOrbit(DynamicLocation location) {
		if (radius != 0.0f) {
			double increment = Utils.PI2 / density;	//orbital density
			double angle = step * increment;
			v.setX(Math.cos(angle) * radius);	//orbital radius
			v.setZ(Math.sin(angle) * radius);
			VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
			location.add(v);
			if (step > density)
				step = 0;
			step++;
		}
	}
}
