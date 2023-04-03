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

package com.clubobsidian.dynamicgui.mock.scheduler;

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
        while (this.running.get()) {
            try {
                Thread.sleep(50);
                Iterator<MockTask> it = this.tasks.iterator();
                while (it.hasNext()) {
                    MockTask task = it.next();
                    if (task.decrementDelay() <= 0) {
                        task.run();
                        if (task.isRepeating()) {
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
