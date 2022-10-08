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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.core.function.DelayFunction;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DelayFunctionTest extends FactoryTest {

    @Test
    public void testNull() throws Exception {
        Function function = new DelayFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testNonNumber() throws Exception {
        Function function = new DelayFunction();
        function.setData("a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testInterrupt() {
        AtomicBoolean value = new AtomicBoolean(true);
        Thread thread = new Thread(() -> {
            Function function = new DelayFunction();
            function.setData("5000");
            try {
                value.set(function.function(this.getFactory().createPlayer()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
        int count = 0;
        while (thread.isAlive()) {
            try {
                Thread.sleep(1);
                count += 1;
                if (count > 1000) {
                    break; //Just so the test doesn't get stuck in an infinite loop
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(value.get());
    }

    @Test
    public void testValidData() throws Exception {
        Function function = new DelayFunction();
        function.setData("1");
        assertTrue(function.function(this.getFactory().createPlayer()));
    }
}