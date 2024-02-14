package me.sashie.skdragon.api.runnable;

public enum RunnableType {

	/**
	 * Effect is played once instantly
	 */
	INSTANT,
	/**
	 * Effect is played several times instantly
	 */
	REPEATING,
	/**
	 * Effect is played once with delay
	 */
	DELAYED;
}
