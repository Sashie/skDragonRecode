package me.sashie.skdragon.api.util.pool;

public class ObjectPoolManager {
	private static final DynamicLocationPool dynamicLocationPool = new DynamicLocationPool();
	private static final VectorPool vectorPool = new VectorPool();
	//private static final PhysicsParticlePool physicsPool = new PhysicsParticlePool();

	public static DynamicLocationPool getDynamicLocationPool() {
		return dynamicLocationPool;
	}

	public static VectorPool getVectorPool() {
		return vectorPool;
	}

	//public static PhysicsParticlePool getPhysicsParticlePool() {
	//	return physicsPool;
	//}

}