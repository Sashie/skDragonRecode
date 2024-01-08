package me.sashie.skdragon.runnable;

public interface RunnableTask<R> {

	public int getTaskId();

	public void cancel();
	
    public R runTask() throws IllegalArgumentException, IllegalStateException;

    public R runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException;

    public R runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException;

    public R runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException;

    public R runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException;

    public R runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException;
}
