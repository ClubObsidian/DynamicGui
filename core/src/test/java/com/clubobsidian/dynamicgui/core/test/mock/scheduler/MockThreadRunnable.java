package com.clubobsidian.dynamicgui.core.test.mock.scheduler;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MockThreadRunnable implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Collection<MockTask> tasks = new ConcurrentLinkedQueue<>();

    public boolean addTask(MockTask task) {
        return this.tasks.add(task);
    }

    @Override
    public void run() {
        while(this.running.get()) {
            try {
                Thread.sleep(50);
                Iterator<MockTask> it = this.tasks.iterator();
                while(it.hasNext()) {
                    MockTask task = it.next();
                    if(task.decrementDelay() <= 0) {
                        task.run();
                        if(task.isRepeating()) {
                            task.resetDelay();
                        } else {
                            it.remove();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.running.set(false);
    }
}
