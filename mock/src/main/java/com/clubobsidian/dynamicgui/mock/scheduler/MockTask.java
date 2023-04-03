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
