package me.sashie.skdragon.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.sashie.skdragon.SkDragonRecode;

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
    public synchronized BukkitTask runTask() throws IllegalArgumentException, IllegalStateException {
		run = wrap(this::run);
        return run.runTask(SkDragonRecode.getInstance());
    }

	@Override
    public synchronized BukkitTask runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException {
		run = wrap(() -> {
    		run();
    	});
        return run.runTaskAsynchronously(SkDragonRecode.getInstance());
    }

	@Override
    public synchronized BukkitTask runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException {
		run = wrap(() -> {
    		run();
    	});
        return run.runTaskLater(SkDragonRecode.getInstance(), delay);
    }

	@Override
    public synchronized BukkitTask runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException {
		run = wrap(() -> {
    		run();
    	});
		return run.runTaskLaterAsynchronously(SkDragonRecode.getInstance(), delay);
    }

	@Override
    public synchronized BukkitTask runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException {
		run = wrap(() -> {
    		run();
    	});
		return run.runTaskTimer(SkDragonRecode.getInstance(), delay, period);
    }

	@Override
    public synchronized BukkitTask runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException {
		run = wrap(() -> {
    		run();
    	});
		return run.runTaskTimerAsynchronously(SkDragonRecode.getInstance(), delay, period);
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
