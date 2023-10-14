package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.condition.*;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionFunctionTest extends FactoryTest {
    ExpressionConfiguration config = ExpressionConfiguration.defaultConfiguration().withAdditionalFunctions(
            Map.entry("STREQUAL", new EqualLazyFunction()),
            Map.entry("STREQUALIGNORECASE", new IgnoreCaseEqualLazyFunction()),
            Map.entry("STRCONTAINS", new ContainsLazyFunction()),
            Map.entry("STRENDSWITH", new EndsWithLazyFunction()),
            Map.entry("STRSTARTSWITH", new StartsWithLazyFunction()),
            Map.entry("AFTERDATE", new AfterDateLazyFunction()),
            Map.entry("BEFOREDATE", new BeforeDateLazyFunction())
    );

    @Test
    public void testStringEquals() {
        Expression equals = new Expression("STREQUAL(\"test\", \"test\")", config);
        try {
            assertEquals(BigDecimal.ONE, equals.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notEquals = new Expression("STREQUAL(\"test\", \"test1\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notEquals.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression empty = new Expression("STREQUAL(\"test\", \"\")", config);
        try {
            assertEquals(BigDecimal.ZERO, empty.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringEqualsIgnoreCase(){
        Expression equals = new Expression("STREQUALIGNORECASE(\"tEST\", \"test\")", config);
        try {
            assertEquals(BigDecimal.ONE, equals.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notEquals = new Expression("STREQUALIGNORECASE(\"Test\", \"test1\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notEquals.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression empty = new Expression("STREQUALIGNORECASE(\"test\", \"\")", config);
        try {
            assertEquals(BigDecimal.ZERO, empty.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringStartsWith(){
   Expression startsWith = new Expression("STRSTARTSWITH(\"test\", \"te\")", config);
        try {
            assertEquals(BigDecimal.ONE, startsWith.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notStartsWith = new Expression("STRSTARTSWITH(\"end\", \"s\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notStartsWith.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringEndsWith(){
        Expression endsWith = new Expression("STRENDSWITH(\"testend\", \"end\")", config);
        try {
            assertEquals(BigDecimal.ONE, endsWith.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notEndsWith = new Expression("STRENDSWITH(\"test\", \"end\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notEndsWith.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringContains(){
        Expression contains = new Expression("STRCONTAINS(\"test\", \"es\")", config);
        try {
            assertEquals(BigDecimal.ONE, contains.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notContains = new Expression("STRCONTAINS(\"test\", \"es1\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notContains.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAfterDate(){
        Expression afterDate = new Expression("AFTERDATE(\"01 01, 2023 00:00:00\")", config);
        try {
            assertEquals(BigDecimal.ONE, afterDate.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notAfterDate = new Expression("AFTERDATE(\"01 01, 2100 00:00:00\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notAfterDate.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBeforeDate(){
        Expression beforeDate = new Expression("BEFOREDATE(\"01 01, 2100 00:00:00\")", config);
        try {
            assertEquals(BigDecimal.ONE, beforeDate.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
        Expression notBeforeDate = new Expression("BEFOREDATE(\"01 01, 2023 00:00:00\")", config);
        try {
            assertEquals(BigDecimal.ZERO, notBeforeDate.evaluate().getNumberValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckTickFunction(){
        PlayerWrapper<?> playerWrapper = this.getFactory().createPlayer();
        CheckTickFunction checkTickFunction = new CheckTickFunction();
        assertFalse(checkTickFunction.function(playerWrapper));
    }

    @Test
    public void testConditionFunction(){
        PlayerWrapper<?> playerWrapper = this.getFactory().createPlayer();
        ConditionFunction conditionFunction = new ConditionFunction();
        conditionFunction.setData("STREQUAL(\"test\", \"test\")");
        assertTrue(conditionFunction.function(playerWrapper));

        conditionFunction.setData("STREQUALIGNORECASE(\"tEST\", \"test\")");
        assertTrue(conditionFunction.function(playerWrapper));

        conditionFunction.setData("STRSTARTSWITH(\"test\", \"te\")");
        assertTrue(conditionFunction.function(playerWrapper));

        conditionFunction.setData("STRENDSWITH(\"testend\", \"end\")");
        assertTrue(conditionFunction.function(playerWrapper));

        conditionFunction.setData("STRCONTAINS(\"test\", \"es\")");
        assertTrue(conditionFunction.function(playerWrapper));

        conditionFunction.setData("AFTERDATE(\"01 01, 2023 00:00:00\")");
        assertTrue(conditionFunction.function(playerWrapper));

        conditionFunction.setData("BEFOREDATE(\"01 01, 2100 00:00:00\")");
        assertTrue(conditionFunction.function(playerWrapper));
    }

}
