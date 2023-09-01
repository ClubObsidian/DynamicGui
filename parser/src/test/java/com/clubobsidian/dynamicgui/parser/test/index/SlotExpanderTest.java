/*
 *    Copyright 2018-2023 virustotalop
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

package com.clubobsidian.dynamicgui.parser.test.index;

import com.clubobsidian.dynamicgui.parser.index.SlotExpander;
import com.clubobsidian.dynamicgui.parser.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlotExpanderTest extends FactoryTest {

    @Test
    public void testInteger() {
        List<Integer> slots = SlotExpander.expand(1);
        assertEquals(1, slots.size());
        assertEquals(1, slots.get(0));
    }

    @Test
    public void testStringNoSpecialChars() {
        List<Integer> slots = SlotExpander.expand("1");
        assertEquals(1, slots.size());
        assertEquals(1, slots.get(0));
    }

    @Test
    public void testComma() {
        List<Integer> slots = SlotExpander.expand("1,2");
        assertEquals(2, slots.size());
        assertEquals(1, slots.get(0));
        assertEquals(2, slots.get(1));
    }

    @Test
    public void testDash() {
        List<Integer> slots = SlotExpander.expand("0-1");
        assertEquals(2, slots.size());
        assertEquals(0, slots.get(0));
        assertEquals(1, slots.get(1));
    }

    @Test
    public void testCommaAndDash() {
        List<Integer> slots = SlotExpander.expand("0-1,2-3");
        assertEquals(4, slots.size());
        assertEquals(0, slots.get(0));
        assertEquals(1, slots.get(1));
        assertEquals(2, slots.get(2));
        assertEquals(3, slots.get(3));
    }
}