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

package com.clubobsidian.dynamicgui.parser.test;

import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionTypeTest {

    @Test
    public void clickIsClickTest() {
        Assertions.assertTrue(FunctionType.CLICK.isClick());
    }

    @Test
    public void leftIsClickTest() {
        Assertions.assertTrue(FunctionType.LEFT.isClick());
    }

    @Test
    public void rightIsClickTest() {
        Assertions.assertTrue(FunctionType.RIGHT.isClick());
    }

    @Test
    public void middleIsClickTest() {
        Assertions.assertTrue(FunctionType.MIDDLE.isClick());
    }

    @Test
    public void shiftClickIsClickTest() {
        Assertions.assertTrue(FunctionType.SHIFT_CLICK.isClick());
    }

    @Test
    public void shiftLeftIsClickTest() {
        Assertions.assertTrue(FunctionType.SHIFT_LEFT.isClick());
    }

    @Test
    public void shiftRightIsClickTest() {
        Assertions.assertTrue(FunctionType.SHIFT_RIGHT.isClick());
    }

    @Test
    public void loadIsNotClickTest() {
        Assertions.assertFalse(FunctionType.LOAD.isClick());
    }

    @Test
    public void failIsNotClickTest() {
        Assertions.assertFalse(FunctionType.FAIL.isClick());
    }

    @Test
    public void switchMenuNotClickTest() {
        Assertions.assertFalse(FunctionType.SWITCH_MENU.isClick());
    }

    @Test
    public void exitMenuNotClickTest() {
        Assertions.assertFalse(FunctionType.EXIT_MENU.isClick());
    }
}