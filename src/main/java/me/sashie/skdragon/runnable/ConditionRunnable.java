package me.sashie.skdragon.runnable;

/*
 * Usage:
 *
 * ConditionRunnable test = new ConditionRunnable() {
 *
 * 	  @Override
 *    public boolean ifTrue() {
 * 		return false; //<--if this return value is true the runnable will stop
 *    }
 *
 * };
 *
 */
/* usage

 */
public abstract class ConditionRunnable extends BukkitRunnableTask {

    public abstract boolean stopIfTrue();

    @Override
    public void run() {
        if (stopIfTrue()) {
            cancel();
        }
    }

}
