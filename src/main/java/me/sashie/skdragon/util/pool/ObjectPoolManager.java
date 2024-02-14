package me.sashie.skdragon.util.pool;

public class ObjectPoolManager {
	private static final DynamicLocationPool dynamicLocationPool = new DynamicLocationPool();
	
	private static final VectorPool vectorPool = new VectorPool();

	public static DynamicLocationPool getDynamicLocationPool() {
		return dynamicLocationPool;
	}

	public static VectorPool getVectorPool() {
		return vectorPool;
	}

}