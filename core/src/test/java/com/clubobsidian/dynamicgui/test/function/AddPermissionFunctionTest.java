package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.AddPermissionFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class AddPermissionFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testNullPermission() {
        Function function = new AddPermissionFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

}
