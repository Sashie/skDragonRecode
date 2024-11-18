package me.sashie.skdragon.util.pool;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorPool {

	private final ObjectPool<Vector> pool = new ObjectPool<>(Vector::new);

	public Vector acquire() {
		return pool.acquire();
	}

	public Vector acquire(Vector vector) {
		return acquire(vector.getX(), vector.getY(), vector.getZ());
	}

	public Vector acquire(Location location) {
		return acquire(location.getX(), location.getY(), location.getZ());
	}

	public Vector acquire(double x, double y, double z) {
		Vector v = pool.acquire();
		v.setX(x).setY(y).setZ(z);
		return v;
	}

	public void release(Vector vector) {
		if (vector == null) return;
		vector.setY(0).setY(0).setZ(0);
		pool.release(vector);
	}
}
