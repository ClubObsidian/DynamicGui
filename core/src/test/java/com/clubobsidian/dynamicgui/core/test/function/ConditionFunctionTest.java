package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.condition.*;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class ConditionFunctionTest extends FactoryTest {
    @Test
    public void testStringEquals() {
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STREQUAL(\"test\", \"test\")");
        assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringNotEquals(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STREQUAL(\"test\", \"test1\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }


    @Test
    public void testStringEqualsIgnoreCase(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STREQUALIGNORECASE(\"tEST\", \"test\")");
        assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringNotEqualsIgnoreCase(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STREQUALIGNORECASE(\"tEST\", \"test1\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringStartsWith(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STRSTARTSWITH(\"test\", \"te\")");
        assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringNotStartsWith(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STRSTARTSWITH(\"end\", \"s\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringEndsWith(){
       ConditionFunction condition = new ConditionFunction();
       condition.setData("STRENDSWITH(\"test\", \"st\")");
       assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringNotEndsWith(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STRENDSWITH(\"test\", \"end\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringContains(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STRCONTAINS(\"test\", \"es\")");
        assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testStringNotContains(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("STRCONTAINS(\"test\", \"end\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testAfterDate(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("AFTERDATE(\"01 01, 2023 00:00:00\")");
        assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testNotAfterDate(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("AFTERDATE(\"01 01, 2100 00:00:00\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testBeforeDate(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("BEFOREDATE(\"01 01, 2100 00:00:00\")");
        assertTrue(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testNotBeforeDate(){
        ConditionFunction condition = new ConditionFunction();
        condition.setData("BEFOREDATE(\"01 01, 2023 00:00:00\")");
        assertFalse(condition.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testCheckTickFunction(){
        PlayerWrapper<?> playerWrapper = this.getFactory().createPlayer();
        CheckTickFunction checkTickFunction = new CheckTickFunction();
        assertFalse(checkTickFunction.function(playerWrapper));
    }
}
