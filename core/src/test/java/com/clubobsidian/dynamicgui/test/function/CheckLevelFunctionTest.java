package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.CheckLevelFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.MockPlayer;
import com.clubobsidian.dynamicgui.test.mock.MockPlayerWrapper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckLevelFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testNull() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        Function function = new CheckLevelFunction();
        assertFalse(function.function(wrapper));
    }

    @Test
    public void testLevelGreater() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(10);
        Function function = new CheckLevelFunction();
        function.setData("1");
        assertTrue(function.function(wrapper));
    }

    @Test
    public void testLevelEqual() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(10);
        Function function = new CheckLevelFunction();
        function.setData("10");
        assertTrue(function.function(wrapper));
    }

    @Test
    public void testLevelLessThan() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(1);
        Function function = new CheckLevelFunction();
        function.setData("10");
        assertFalse(function.function(wrapper));
    }

    @Test
    public void testInvalidFormat() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(1);
        Function function = new CheckLevelFunction();
        function.setData("a");
        assertFalse(function.function(wrapper));
    }
}