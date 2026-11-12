/*
 *    Copyright 2018-2025 virustotalop
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
package com.clubobsidian.dynamicgui.parser.test.gui;

import com.clubobsidian.dynamicgui.api.parser.gui.GuiToken;
import com.clubobsidian.dynamicgui.parser.gui.SimpleGuiToken;
import com.clubobsidian.dynamicgui.parser.test.FactoryTest;
import com.clubobsidian.wrappy.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PacketGuiTokenTest extends FactoryTest {

    private GuiToken load(String name) {
        File folder = new File("test", "gui");
        return new SimpleGuiToken(Configuration.load(new File(folder, name)));
    }

    @Test
    public void testNotPacketByDefault() {
        GuiToken token = this.load("no-mode.yml");
        assertFalse(token.isPacket());
        assertTrue(token.isValid());
    }

    @Test
    public void testPacketChestExtendsIntoPlayerInventory() {
        GuiToken token = this.load("packet-chest.yml");
        assertTrue(token.isPacket());
        assertTrue(token.isValid());
        assertTrue(token.getSlots().containsKey(0));
        assertTrue(token.getSlots().containsKey(89));
        assertFalse(token.getSlots().containsKey(90));
    }

    @Test
    public void testOversizedChestIsInvalid() {
        assertFalse(this.load("oversized-chest.yml").isValid());
    }

    @Test
    public void testPacketHopperExtendsIntoPlayerInventory() {
        GuiToken token = this.load("packet-hopper.yml");
        assertTrue(token.isValid());
        assertTrue(token.getSlots().containsKey(0));
        assertTrue(token.getSlots().containsKey(40));
        assertFalse(token.getSlots().containsKey(41));
    }

    @Test
    public void testOversizedPacketHopperIsInvalid() {
        assertFalse(this.load("packet-hopper-oversized.yml").isValid());
    }

    @Test
    public void testPacketPlayerIsInvalid() {
        assertFalse(this.load("packet-player.yml").isValid());
    }

    @Test
    public void testHopperUsesTypeSlots() {
        GuiToken token = this.load("hopper-no-rows.yml");
        assertTrue(token.isValid());
        assertTrue(token.getSlots().containsKey(4));
        assertFalse(token.getSlots().containsKey(5));
    }
}
