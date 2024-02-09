package me.sashie.skdragon.runnable;

public interface RunnableTask<R> {

    int getTaskId();

    void cancel();

    void runTask() throws IllegalArgumentException, IllegalStateException;

    void runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException;

    void runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException;

    void runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException;

    void runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException;

    void runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException;
}
