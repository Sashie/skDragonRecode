package me.sashie.skdragon.api.runnable;

import me.sashie.skdragon.SkDragonRecode;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class BukkitRunnableTask implements Runnable, RunnableTask<BukkitTask> {

	private BukkitRunnable run = null;

	public BukkitRunnableTask() {

	}

	private static BukkitRunnable wrap(Runnable r) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				r.run();
			}
		};

	}

	@Override
	public synchronized void runTask() throws IllegalArgumentException, IllegalStateException {
		run = wrap(this);
		run.runTask(SkDragonRecode.getInstance());
	}

	@Override
	public synchronized void runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException {
		run = wrap(this);
		run.runTaskAsynchronously(SkDragonRecode.getInstance());
	}

	@Override
	public synchronized void runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException {
		run = wrap(this);
		run.runTaskLater(SkDragonRecode.getInstance(), delay);
	}

	@Override
	public synchronized void runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException {
		run = wrap(this);
		run.runTaskLaterAsynchronously(SkDragonRecode.getInstance(), delay);
	}

	@Override
	public synchronized void runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException {
		run = wrap(this);
		run.runTaskTimer(SkDragonRecode.getInstance(), delay, period);
	}

	@Override
	public synchronized void runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException {
		run = wrap(this);
		run.runTaskTimerAsynchronously(SkDragonRecode.getInstance(), delay, period);
	}

	@Override
	public int getTaskId() {
		return run.getTaskId();
	}

	@Override
	public void cancel() {
		run.cancel();
	}
}
