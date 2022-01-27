/*
 *    Copyright 2021 Club Obsidian and contributors.
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

package com.clubobsidian.dynamicgui.parser.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.dynamicgui.parser.function.FunctionType;

public class FunctionTypeTest {

    @Test
    public void clickIsClickTest() {
        assertTrue(FunctionType.CLICK.isClick());
    }

    @Test
    public void leftIsClickTest() {
        assertTrue(FunctionType.LEFT.isClick());
    }

    @Test
    public void rightIsClickTest() {
        assertTrue(FunctionType.RIGHT.isClick());
    }

    @Test
    public void middleIsClickTest() {
        assertTrue(FunctionType.MIDDLE.isClick());
    }

    @Test
    public void shiftClickIsClickTest() {
        assertTrue(FunctionType.SHIFT_CLICK.isClick());
    }

    @Test
    public void shiftLeftIsClickTest() {
        assertTrue(FunctionType.SHIFT_LEFT.isClick());
    }

    @Test
    public void shiftRightIsClickTest() {
        assertTrue(FunctionType.SHIFT_RIGHT.isClick());
    }

    @Test
    public void loadIsNotClickTest() {
        assertFalse(FunctionType.LOAD.isClick());
    }

    @Test
    public void failIsNotClickTest() {
        assertFalse(FunctionType.FAIL.isClick());
    }

    @Test
    public void switchMenuNotClickTest() {
        assertFalse(FunctionType.SWITCH_MENU.isClick());
    }

    @Test
    public void exitMenuNotClickTest() {
        assertFalse(FunctionType.EXIT_MENU.isClick());
    }
}