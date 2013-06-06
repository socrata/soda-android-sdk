/*
 * SODA Android SDK - Socrata, Inc
 *
 * Copyright (C) 2013 Socrata, Inc
 * All rights reserved.
 *
 * Developed for Socrata, Inc by:
 * 47 Degrees, LLC
 * http://47deg.com
 * hello@47deg.com
 */

package com.socrata.android.tests;

import com.socrata.android.soql.clauses.BuildCapable;
import junit.framework.TestCase;


import static com.socrata.android.soql.Query.*;
import static com.socrata.android.soql.clauses.Expression.*;
import static com.socrata.android.soql.clauses.OrderDirection.*;

/**
 * Query building test
 */
public final class SOSQLTest extends TestCase {

    private static void assertExpression(String expected, BuildCapable expression) {
        assertEquals("Malformed expression", expected, expression.build());
    }

    public void testSelect() {
        assertExpression("select a, b", select("a", "b"));
    }

    public void testWhere() {
        assertExpression("where a = b and c = d", where(and(eq("a", "b"), eq("c", "d"))));
        assertExpression("where a = b and b = c", where(eq("a", "b"), eq("b", "c")));
    }

    public void testOrderBy() {
        assertExpression("order a desc", orderBy(order("a", DESC)));
    }

    public void testGroupBy() {
        assertExpression("group a, b", groupBy("a", "b"));
    }

    public void testOffset() {
        assertExpression("offset 1", offset(1));
    }

    public void testLimit() {
        assertExpression("limit 1", limit(1));
    }


    public void testWrapped() {
        assertExpression("(a)", parentheses("a"));
    }

    public void testIsNotNull() {
        assertExpression("a is not null", isNotNull("a"));
    }

    public void testIsNull() {
        assertExpression("a is null", isNull("a"));
    }

    public void testAnd() {
        assertExpression("a and b and c and (c = d)", and("a", "b", "c", "(c = d)"));
        assertExpression("a = b and c = d", and(eq("a", "b"), eq("c", "d")));
    }

    public void testOr() {
        assertExpression("a or b or c or (c = d)", or("a", "b", "c", "(c = d)"));
    }

    public void testNot() {
        assertExpression("not a", not("a"));
    }

    public void testCol() {
        assertExpression("a", column("a"));
    }

    public void testAlias() {
        assertExpression("a as aliasA", as("a", "aliasA"));
    }

    public void testSum() {
        assertExpression("sum(a)", sum("a"));
    }

    public void testCount() {
        assertExpression("count(a)", count("a"));
    }

    public void testAvg() {
        assertExpression("avg(a)", avg("a"));
    }

    public void testMin() {
        assertExpression("min(a)", min("a"));
    }

    public void testMax() {
        assertExpression("max(a)", max("a"));
    }

    public void testLt() {
        assertExpression("a < b", lt("a", "b"));
    }

    public void testLte() {
        assertExpression("a <= b", lte("a", "b"));
    }

    public void testEq() {
        assertExpression("a = b", eq("a", "b"));
    }

    public void testNeq() {
        assertExpression("a != b", neq("a", "b"));
    }

    public void testGt() {
        assertExpression("a > b", gt("a", "b"));
    }

    public void testGte() {
        assertExpression("a >= b", gte("a", "b"));
    }

    public void testUpper() {
        assertExpression("upper(a)", upper("a"));
    }

    public void testLower() {
        assertExpression("lower(a)", lower("a"));
    }

    public void testStartsWith() {
        assertExpression("starts_with(a, b)", startsWith("a", "b"));
    }

    public void testContains() {
        assertExpression("contains(a, 'b')", contains("a", "'b'"));
    }

    public void testMultipliedBy() {
        assertExpression("a * b", multipliedBy("a", "b"));
    }

    public void testDividedBy() {
        assertExpression("a / b", dividedBy("a", "b"));
    }

    public void testAdd() {
        assertExpression("a + b", add("a", "b"));
    }

    public void testSubtract() {
        assertExpression("a - b", subtract("a", "b"));
    }

    public void testToString() {
        assertExpression("to_string(a)", castToString("a"));
    }

    public void testToNumber() {
        assertExpression("to_number(a)", toNumber("a"));
    }

    public void testToBoolean() {
        assertExpression("to_boolean(a)", toBoolean("a"));
    }

    public void testToLocation() {
        assertExpression("to_location(a, b)", toLocation("a", "b"));
    }

    public void testToFixedTimestamp() {
        assertExpression("to_fixed_timestamp(a)", toFixedTimestamp("a"));
    }

    public void testToFloatingTimestamp() {
        assertExpression("to_floating_timestamp(a)", toFloatingTimestamp("a"));
    }

    public void testLocationWithinGeoBox() {
        assertExpression("within_box(withinBox, 47.712585, -122.464676, 47.510759, -122.249756)", withinBox("withinBox", box(47.712585, -122.464676, 47.510759, -122.249756)));
    }

    public void testOrder() {
        assertExpression("a desc", order("a", DESC));
        assertExpression("a asc", order("a", ASC));
    }
    
    
}
