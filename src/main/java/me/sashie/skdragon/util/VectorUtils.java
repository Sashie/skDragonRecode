package me.sashie.skdragon.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class VectorUtils {

	public static final Vector ONE = new Vector(1, 1, 1);

	public static Vector add(Vector vector, double x, double y, double z) {
		return vector.setX(vector.getX() + x).setY(vector.getY() + y).setZ(vector.getZ() + z);
	}

	public static Vector add(Vector vector, Location add) {
		return vector.setX(vector.getX() + add.getX()).setY(vector.getY() + add.getY()).setZ(vector.getZ() + add.getZ());
	}

	public static Vector subtract(Vector vector, Location subtract) {
		return vector.setX(vector.getX() - subtract.getX()).setY(vector.getY() - subtract.getY()).setZ(vector.getZ() - subtract.getZ());
	}

	/**
	 * Rotate around an axis
	 */
	public static Vector rot(Vector vector, Vector axis, double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		Vector a = axis.clone().normalize();
		double ax = a.getX();
		double ay = a.getY();
		double az = a.getZ();
		Vector rotx = new Vector(cos+ax*ax*(1-cos), ax*ay*(1-cos)-az*sin, ax*az*(1-cos)+ay*sin);
		Vector roty = new Vector(ay*ax*(1-cos)+az*sin, cos+ay*ay*(1-cos), ay*az*(1-cos)-ax*sin);
		Vector rotz = new Vector(az*ax*(1-cos)-ay*sin, az*ay*(1-cos)+ax*sin, cos+az*az*(1-cos));
		double x = rotx.dot(vector);
		double y = roty.dot(vector);
		double z = rotz.dot(vector);
		vector.setX(x).setY(y).setZ(z);
		return vector;
	}

	public static double degreesToRadians(double angle) {
		return angle * (Math.PI / 180);
	}

	public static final Vector rotateAroundAxisX(Vector v, double angle) {
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		//return new Vector(v.getX(), y, z);//v.setY(y).setZ(z);
		return v.setY(y).setZ(z);
		//return v.clone();
	}

	public static final Vector rotateAroundAxisY(Vector v, double angle) {
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		//return new Vector(x, v.getY(), z);//v.setX(x).setZ(z);
		return v.setX(x).setZ(z);
		//return v.clone();
	}
	
	public static final Vector rotateAroundAxisY(Vector v, float angle) {
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		//return new Vector(x, v.getY(), z);//v.setX(x).setZ(z);
		return v.setX(x).setZ(z);
		//return v.clone();
	}

	public static final Vector rotateAroundAxisZ(Vector v, double angle) {
		double x, y, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos - v.getY() * sin;
		y = v.getX() * sin + v.getY() * cos;
		//return new Vector(x, y, v.getZ());//v.setX(x).setY(y);
		return v.setX(x).setY(y);
		//return v.clone();
	}

	public static final Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
		// double x = v.getX(), y = v.getY(), z = v.getZ();
		// double cosX = Math.cos(angleX), sinX = Math.sin(angleX), cosY =
		// Math.cos(angleY), sinY = Math.sin(angleY), cosZ = Math.cos(angleZ),
		// sinZ = Math.sin(angleZ);
		// double nx, ny, nz;
		// nx = (x * cosY + z * sinY) * (x * cosZ - y * sinZ);
		// ny = (y * cosX - z * sinX) * (x * sinZ + y * cosZ);
		// nz = (y * sinX + z * cosX) * (-x * sinY + z * cosY);
		// return v.setX(nx).setY(ny).setZ(nz);
		// Having some strange behavior up there.. Have to look in it later. TODO
		rotateAroundAxisX(v, angleX);
		rotateAroundAxisY(v, angleY);
		rotateAroundAxisZ(v, angleZ);
		return v;
	}

	/**
	 * Rotate a vector about a location using that location's direction
	 *
	 * @param v
	 * @param location
	 * @return
	 */
	public static final Vector rotateVector(Vector v, Location location) {
		return rotateVectorYZ(v, location.getYaw(), location.getPitch());
	}

	/**
	 * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
	 * ZY rotation
	 * Thanks to SexyToad!
	 *
	 * @param v
	 * @param yawDegrees
	 * @param pitchDegrees
	 * @return
	 */
	public static final Vector rotateVectorYZ(Vector v, float yawDegrees, float pitchDegrees) {
		//double yaw = Math.toRadians(-1 * (yawDegrees + 90));
		//double pitch = Math.toRadians(-pitchDegrees);
		double yaw = -1 * (yawDegrees + 90);
		double pitch = -pitchDegrees;

		double cosYaw = Math.cos(yaw);
		double cosPitch = Math.cos(pitch);
		double sinYaw = Math.sin(yaw);
		double sinPitch = Math.sin(pitch);

		double initialX, initialY, initialZ;
		double x, y, z;

		// Z_Axis rotation (Pitch)
		initialX = v.getX();
		initialY = v.getY();
		x = initialX * cosPitch - initialY * sinPitch;
		y = initialX * sinPitch + initialY * cosPitch;

		// Y_Axis rotation (Yaw)
		initialZ = v.getZ();
		initialX = x;
		z = initialZ * cosYaw - initialX * sinYaw;
		x = initialZ * sinYaw + initialX * cosYaw;

		return new Vector(x, y, z);
	}
	
	/**
	 * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
	 * XY rotation
	 * Thanks to Sashie!
	 *
	 * @param v
	 * @param yawDegrees
	 * @param pitchDegrees
	 * @return
	 */
	public static final Vector rotateVectorYX(Vector v, float yawDegrees, float pitchDegrees) {
		//double yaw = Math.toRadians(-1 * yawDegrees);
		//double pitch = Math.toRadians(-pitchDegrees);
		
		double yaw = -1 * yawDegrees;
		double pitch = -pitchDegrees;
		
		//double pitch = pitchDegrees;

		double cosYaw = Math.cos(yaw);
		double cosPitch = Math.cos(pitch);
		double sinYaw = Math.sin(yaw);
		double sinPitch = Math.sin(pitch);

		double initialX, initialY, initialZ;
		double x, y, z;

		// X_Axis rotation (Pitch)
		initialY = v.getY();
		initialZ = v.getZ();
		z = initialY * sinPitch - initialZ * cosPitch;
		y = initialY * cosPitch + initialZ * sinPitch;
		
		// Y_Axis rotation (Yaw)
		initialZ = z;
		initialX = v.getX();
		z = initialZ * cosYaw - initialX * sinYaw;
		x = initialZ * sinYaw + initialX * cosYaw;
		
		return new Vector(x, y, z);
	}
   
	public static final Vector rotateVectorXYZ(Vector v, float xRadians, float yRadians, float zRadians) {
		//double yaw = Math.toRadians(-1 * yawDegrees);
		//double pitch = Math.toRadians(-pitchDegrees);
		
		double inputY = -1 * yRadians;
		double inputX = -xRadians;
		double inputZ = -zRadians;
		
		//double pitch = pitchDegrees;

		double cosY = Math.cos(inputY);
		double cosX = Math.cos(inputX);
		double cosZ = Math.cos(inputZ);
		
		double sinY = Math.sin(inputY);
		double sinX = Math.sin(inputX);
		double sinZ = Math.sin(inputZ);
		

		double initialX, initialY, initialZ;
		double x, y, z;

		// X_Axis rotation (Pitch)
		initialY = v.getY();
		initialZ = v.getZ();
		z = initialY * sinX - initialZ * cosX;
		y = initialY * cosX + initialZ * sinX;
		
		// Y_Axis rotation (Yaw)
		initialZ = z;
		initialX = v.getX();
		z = initialZ * cosY - initialX * sinY;
		x = initialZ * sinY + initialX * cosY;
		
		// Z_Axis rotation (Pitch)	//TODO buggy stuff, the thing stretches around all weird
		initialX = v.getX();
		initialY = v.getY();
		x = initialX * cosZ - initialY * sinZ;
		y = initialX * sinZ + initialY * cosZ;
		
		return new Vector(x, y, z);
	}
	
	public static float angleXZBetweenPoints(Location center, Location facing) {
		double dx = facing.getX() - center.getX();
		double dz = -(facing.getZ() - center.getZ());

		double angle = Math.atan2(dz, dx);
		if (angle < 0)
			angle = Math.abs(angle);
		else
			angle = 2 * Math.PI - angle;

		return (float) angle;
	}

	public static float angleXYBetweenPoints(Location center, Location facing) {
		double dx = facing.getX() - center.getX();
		double dy = -(facing.getY() - center.getY());

		double angle = Math.atan2(dy, dx);
		if (angle < 0)
			angle = Math.abs(angle);
		else
			angle = 2 * Math.PI - angle;

		return (float) angle;
	}

	public static final double angleToXAxis(Vector vector) {
		return Math.atan2(vector.getX(), vector.getY());
	}
	
	public static Vector getBackVector(final Location location) {
		final float newZ = (float)(location.getZ() + 1.0 * Math.sin(Math.toRadians(location.getYaw() + 90.0f)));
		final float newX = (float)(location.getX() + 1.0 * Math.cos(Math.toRadians(location.getYaw() + 90.0f)));
		return new Vector(newX - location.getX(), 0.0, newZ - location.getZ());
	}
   
	
	public static double offset(final Entity a, final Entity b) {
		return offset(a.getLocation().toVector(), b.getLocation().toVector());
	}
	
	public static double offset(final Location a, final Location b) {
		return offset(a.toVector(), b.toVector());
	}
	
	public static double offset(final Vector a, final Vector b) {
		return a.subtract(b).length();
	}

	public static double getSize(Vector v) {
		return Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
	}
	
	public static Vector calculateBezierPoint(float t, Vector p0, Vector p1, Vector p2, Vector p3) {
		float u = 1 - t;
		float tt = t * t;
		float uu = u * u;
		float uuu = uu * u;
		float ttt = tt * t;
 
		//Vector p = uuu * p0; //first term
		Vector p = p0.multiply(uuu);
		//p += 3 * uu * t * p1; //second term
		p.add(p1.multiply(3 * uu * t));
		//p += 3 * u * tt * p2; //third term
		p.add(p2.multiply(3 * u * tt));
		//p += ttt * p3; //fourth term
		p.add(p3.multiply(ttt));
		return p;
	}
	
	public static Vector calculateBezierPoint(float t, Location p0, Location p1, Location p2, Location p3) {
		float u = 1 - t;
		float tt = t * t;
		float uu = u * u;
		float uuu = uu * u;
		float ttt = tt * t;

		Vector p = p0.toVector().multiply(uuu);
		p.add(p1.toVector().multiply(3 * uu * t));
		p.add(p2.toVector().multiply(3 * u * tt));
		p.add(p3.toVector().multiply(ttt));
		return p;
	}

	public static Vector lerp(Vector vector, final Vector target, float alpha) {
		vector.setX(vector.getX() + alpha * (target.getX() - vector.getX()));
		vector.setY(vector.getY() + alpha * (target.getY() - vector.getY()));
		vector.setZ(vector.getZ() + alpha * (target.getZ() - vector.getZ()));
		return vector;
	}
}
