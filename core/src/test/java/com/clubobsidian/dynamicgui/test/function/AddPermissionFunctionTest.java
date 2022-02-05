package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.AddPermissionFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddPermissionFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testNullPermission() {
        Function function = new AddPermissionFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void testPermissionAdded() {
        String permission = "test";
        Function function = new AddPermissionFunction();
        function.setData(permission);
        PlayerWrapper<?> player = this.factory.createPlayer();
        assertTrue(function.function(player));
    }

    @Test
    public void testPermissionNotAdded() {
        String permission = "test";
        Function function = new AddPermissionFunction();
        function.setData(permission);
        PlayerWrapper<?> player = this.factory.createPlayer();
        player.addPermission(permission);
        assertFalse(function.function(player));
    }
}