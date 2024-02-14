package me.sashie.skdragon.api.runnable;

public abstract class CounterRunnable extends BukkitRunnableTask {

	public abstract int runCounter(int iteration);
	
	private int counter;
	
	@Override
	public void run() {
		if (counter >= runCounter(counter)) {
			cancel();
		}
		counter++;
	}

}
