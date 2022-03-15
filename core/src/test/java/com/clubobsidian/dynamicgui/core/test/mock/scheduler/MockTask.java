package com.clubobsidian.dynamicgui.core.test.mock.scheduler;

public class MockTask implements Runnable {

    private final Runnable runnable;
    private final long delayInitial;
    private long delay;
    private final long delayRepeating;

    public MockTask(Runnable runnable, long delayInitial) {
        this(runnable, delayInitial, -1);
    }

    public MockTask(Runnable runnable, long delayInitial, long delayRepeating) {
        this.runnable = runnable;
        this.delayInitial = delayInitial;
        this.delay = delayInitial;
        this.delayRepeating = delayRepeating;
    }

    public long decrementDelay() {
        this.delay -= 1;
        return this.delay;
    }

    public long resetDelay() {
        this.delay = this.delayRepeating;
        return this.delay;
    }

    public boolean isRepeating() {
        return this.delayRepeating != -1;
    }

    @Override
    public void run() {
        this.runnable.run();
    }
}
