/*
 *    Copyright 2022 virustotalop and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.dynamicgui.core.test.mock.scheduler;

import com.clubobsidian.dynamicgui.core.scheduler.Scheduler;

public class MockScheduler implements Scheduler {

    private final MockThreadRunnable synchronous = new MockThreadRunnable();
    private final MockThreadRunnable async = new MockThreadRunnable();

    private final Thread mainThread = new Thread(this.synchronous);
    private final Thread asyncThread = new Thread(this.async);

    public MockScheduler() {
        this.mainThread.start();
        this.asyncThread.start();
    }

    @Override
    public void runSyncDelayedTask(Runnable runnable, long delay) {
        this.synchronous.addTask(new MockTask(runnable, delay));
    }

    @Override
    public void runAsynchronousDelayedTask(Runnable runnable, long delay) {
        this.async.addTask(new MockTask(runnable, delay));
    }

    @Override
    public void scheduleSyncRepeatingTask(Runnable runnable, long delayInitial, long delayRepeating) {
        this.synchronous.addTask(new MockTask(runnable, delayInitial, delayRepeating));
    }

    @Override
    public void scheduleAsyncRepeatingTask(Runnable runnable, long delayInitial, long delayRepeating) {
        this.async.addTask(new MockTask(runnable, delayInitial, delayRepeating));
    }

    public boolean isOnMainThread() {
        return Thread.currentThread().equals(this.mainThread);
    }

    public void stop() {
        this.synchronous.stop();
        this.async.stop();
    }
}
