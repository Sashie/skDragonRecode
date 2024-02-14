package me.sashie.skdragon.api.util.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class ObjectPool<T> {

	private final Queue<T> pool;
	private final Supplier<T> constructor;

	public ObjectPool(Supplier<T> constructor) {
		this.pool = new ConcurrentLinkedQueue<>();
		this.constructor = constructor;
	}

	public T acquire() {
		T item = pool.poll();
		if (item == null) {
			item = constructor.get();
		}
		return item;
	}

	public void release(T item) {
		pool.offer(item);
	}
}
