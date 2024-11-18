package me.sashie.skdragon.runnable;

import java.util.concurrent.*;

public abstract class JavaRunnableTask implements Runnable, RunnableTask<ScheduledFuture<?>> {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	private ScheduledFuture<?> scheduledTask;

	@Override
	public synchronized void runTask() throws IllegalArgumentException, IllegalStateException {
		//cancel();  // Cancel any existing task
		scheduledTask = scheduler.schedule(this, 0, TimeUnit.MILLISECONDS);
	}

	@Override
	public synchronized void runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException {
		//cancel();  // Cancel any existing task
		scheduledTask = scheduler.schedule(this, 0, TimeUnit.MILLISECONDS);
	}

	@Override
	public synchronized void runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException {
		//cancel();  // Cancel any existing task
		scheduledTask = scheduler.schedule(this, Math.max(delay, 0), TimeUnit.MILLISECONDS);
	}

	@Override
	public synchronized void runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException {
		//cancel();  // Cancel any existing task
		scheduledTask = scheduler.schedule(this, Math.max(delay, 0), TimeUnit.MILLISECONDS);
	}

	@Override
	public synchronized void runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException {
		//cancel();  // Cancel any existing task
		scheduledTask = scheduler.scheduleAtFixedRate(this, Math.max(delay, 0), Math.max(period, 1), TimeUnit.MILLISECONDS);
	}

	@Override
	public synchronized void runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException {
		//cancel();  // Cancel any existing task
		scheduledTask = scheduler.scheduleAtFixedRate(this, Math.max(delay, 0), Math.max(period, 1), TimeUnit.MILLISECONDS);
	}

	@Override
	public int getTaskId() {
		return (scheduledTask != null) ? scheduledTask.hashCode() : -1;
	}

	@Override
	public synchronized void cancel() {
		if (scheduledTask != null && !scheduledTask.isCancelled()) {
			scheduledTask.cancel(true);
			scheduledTask = null;
		}
	}

	// Ensure scheduler is properly shutdown when not needed
	public static void shutdownScheduler() {
		scheduler.shutdown();
		try {
			if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
				scheduler.shutdownNow();
			}
		} catch (InterruptedException ex) {
			scheduler.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
