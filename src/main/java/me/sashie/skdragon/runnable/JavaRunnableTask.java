package me.sashie.skdragon.runnable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public abstract class JavaRunnableTask implements Runnable, RunnableTask<TimerTask> {

    private Timer timer = new Timer();
    private TimerTask task;

	private static TimerTask wrap(Runnable r) {
		return new TimerTask() {

			@Override
			public void run() {
				r.run();
			}
		};
	}

	@Override
	public int getTaskId() {
		return 0;
	}

    @Override
    public synchronized TimerTask runTask() throws IllegalArgumentException, IllegalStateException {
        //timer.scheduleAtFixedRate(this, 0, 10 * 1000);

    	task = wrap(() -> {
    		run();
    	});
        timer.schedule(task, 0);
        return task;
    }

	//private static final ExecutorService THREADS = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public synchronized TimerTask runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException {
    	//ExecutorService executor = Executors.newSingleThreadExecutor();
/*
		CompletableFuture<Void> run = CompletableFuture.runAsync(new Runnable() {
			public void run() {
					
			}
		}, THREADS);
*/
    	task = wrap(() -> {
    		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
    		    run();
    		}/*, THREADS*/);
    	});

        timer.schedule(task, 0);

        return task;
    }

    @Override
    public synchronized TimerTask runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException {
    	task = wrap(() -> {
    		run();
    	});
        timer.schedule(task, delay);
        return task;
    }

    @Override
    public synchronized TimerTask runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException {
    	task = wrap(() -> {
    		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
    		    run();
    		}/*, THREADS*/);
    	});
        timer.schedule(task, delay);
        return task;
    }

    @Override
    public synchronized TimerTask runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException {
    	task = wrap(() -> {
    		run();
    	});
    	timer.schedule(task, delay, period);
        return task;
    }

    @Override
    public synchronized TimerTask runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException {
    	task = wrap(() -> {
    		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
    		    run();
    		}/*, THREADS*/);
    	});
    	timer.schedule(task, delay, period);
        return task;
    }

    @Override
	public void cancel() {
		timer.cancel();
		timer.purge();
	}
}
