package me.sashie.skdragon.util.pool;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorPool {

	private final ObjectPool<Vector> pool = new ObjectPool<>(Vector::new);

	public Vector acquire() {
		return pool.acquire();
	}

	public Vector acquire(Vector vector) {
		Vector v = pool.acquire();
		v.setX(vector.getX()).setY(vector.getY()).setZ(vector.getZ());
		return v;
	}

	public Vector acquire(Location location) {
		Vector v = pool.acquire();
		v.setX(location.getX()).setY(location.getY()).setZ(location.getZ());
		return v;
	}

	public void release(Vector vector) {
		if (vector == null) return;
		vector.setY(0).setY(0).setZ(0);
		pool.release(vector);
	}
}
