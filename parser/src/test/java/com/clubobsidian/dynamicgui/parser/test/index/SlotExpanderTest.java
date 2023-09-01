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