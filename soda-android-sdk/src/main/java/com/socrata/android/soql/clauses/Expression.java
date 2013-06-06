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

package com.socrata.android.soql.clauses;


import com.socrata.android.soql.datatypes.GeoBox;

import static com.socrata.android.soql.utils.BuildUtils.*;

/**
 * A query expression that may appear on query clauses, encapsulates the most common
 * query operators, functions,...
 */
public class Expression implements BuildCapable {

    /**
     * The query expression value as a string
     */
    private String value;

    /**
     * If this query expression needs to be wrapped in parentheses
     */
    private boolean requiresWrap;

    /**
     * Constructs a query expression
     *
     * @param value
     */
    public Expression(String value) {
        this.value = value;
    }

    /**
     * Constructs a query expression optionally stating that it needs to be wrapped in parentheses
     *
     * @param value        The query value
     * @param requiresWrap If this query expression needs to be wrapped in parentheses
     */
    public Expression(String value, boolean requiresWrap) {
        this.value = value;
        this.requiresWrap = requiresWrap;
    }

    /**
     * @return The query expression value as a string
     */
    public String getValue() {
        return value;
    }

    /**
     * @return If this query expression needs to be wrapped in parentheses
     */
    public boolean isRequiresWrap() {
        return requiresWrap;
    }

    /**
     * Constructs a simple expression
     */
    public static Expression simpleExpression(String value) {
        return new Expression(value);
    }

    /**
     * Constructs a function expression such as 'not a'
     */
    public static Expression infixedFunction(String function, BuildCapable arg) {
        return new Expression(String.format("%s %s", function, arg.build()));
    }

    /**
     * Constructs a function expression such as 'a is not null'
     */
    public static Expression suffixedFunction(String function, BuildCapable arg) {
        return new Expression(String.format("%s %s", arg.build(), function));
    }

    /**
     * Joins a left and right expression with an operator e.g. 'a + b'
     */
    public static Expression applyOperator(String operator, BuildCapable... expressions) {
        return new Expression(join(String.format(" %s ", operator), buildAll(expressions)));
    }

    /**
     * Constructs a function with a list of arguments e.g. function(a,b,c)
     */
    public static Expression function(String function, BuildCapable... args) {
        return new Expression(String.format("%s(%s)", function, join(", ", buildAll(args))));
    }

    /**
     * is not null expression. e.g. 'a is not null'
     */
    public static Expression isNotNull(BuildCapable expression) {
        return suffixedFunction("is not null", expression);
    }

    /**
     * is not null expression. e.g. 'a is not null'
     */
    public static Expression isNotNull(String expression) {
        return isNotNull(asExpression(expression));
    }

    /**
     * is null expression. e.g. 'a is null'
     */
    public static Expression isNull(BuildCapable expression) {
        return suffixedFunction("is null", expression);
    }

    /**
     * is null expression. e.g. 'a is null'
     */
    public static Expression isNull(String expression) {
        return isNull(asExpression(expression));
    }

    /**
     * not expression. e.g. 'not a'
     */
    public static Expression not(BuildCapable expression) {
        return infixedFunction("not", expression);
    }

    /**
     * not expression. e.g. 'not a'
     */
    public static Expression not(String expression) {
        return not(asExpression(expression));
    }

    /**
     * Joins an array of expression with 'and' e.g. (a and b and c)
     */
    public static Expression and(BuildCapable... expressions) {
        return applyOperator("and", expressions);
    }

    /**
     * Joins an array of expression with 'and' e.g. (a and b and c)
     */
    public static Expression and(String... expressions) {
        return and(asExpressions(expressions));
    }

    /**
     * Joins an array of expression with 'or' e.g. (a or b or c)
     */
    public static Expression or(BuildCapable... expressions) {
        return applyOperator("or", expressions);
    }

    /**
     * Joins an array of expression with 'or' e.g. (a or b or c)
     */
    public static Expression or(String... expressions) {
        return or(asExpressions(expressions));
    }

    /**
     * Joins a left and right expression with an less than comparison operator e.g. 'a < b'
     */
    public static Expression lt(BuildCapable left, BuildCapable right) {
        return applyOperator("<", left, right);
    }

    /**
     * Joins a left and right expression with an less than comparison operator e.g. 'a < b'
     */
    public static Expression lt(String left, String right) {
        return lt(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with an less than or equals comparison operator e.g. 'a <= b'
     */
    public static Expression lte(BuildCapable left, BuildCapable right) {
        return applyOperator("<=", left, right);
    }

    /**
     * Joins a left and right expression with an less than or equals comparison operator e.g. 'a <= b'
     */
    public static Expression lte(String left, String right) {
        return lte(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with an equals comparison operator e.g. 'a = b'
     */
    public static Expression eq(BuildCapable left, BuildCapable right) {
        return applyOperator("=", left, right);
    }

    /**
     * Joins a left and right expression with an equals comparison operator e.g. 'a = b'
     */
    public static Expression eq(String left, String right) {
        return eq(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with a not equals comparison operator e.g. 'a != b'
     */
    public static Expression neq(BuildCapable left, BuildCapable right) {
        return applyOperator("!=", left, right);
    }

    /**
     * Joins a left and right expression with a not equals comparison operator e.g. 'a != b'
     */
    public static Expression neq(String left, String right) {
        return neq(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with a greater than comparison operator e.g. 'a > b'
     */
    public static Expression gt(BuildCapable left, BuildCapable right) {
        return applyOperator(">", left, right);
    }

    /**
     * Joins a left and right expression with a greater than comparison operator e.g. 'a > b'
     */
    public static Expression gt(String left, String right) {
        return gt(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with a greater than or equals comparison operator e.g. 'a >= b'
     */
    public static Expression gte(BuildCapable left, BuildCapable right) {
        return applyOperator(">=", left, right);
    }

    /**
     * Joins a left and right expression with a greater than or equals comparison operator e.g. 'a >= b'
     */
    public static Expression gte(String left, String right) {
        return gte(asExpression(left), asExpression(right));
    }

    /**
     * Wraps an expression with an upper function that would evaluate as uppercase in the server e.g. 'upper(a)'
     */
    public static Expression upper(BuildCapable expression) {
        return function("upper", expression);
    }

    /**
     * Wraps an expression with an upper function that would evaluate as uppercase in the server e.g. 'upper(a)'
     */
    public static Expression upper(String expression) {
        return upper(asExpression(expression));
    }

    /**
     * Wraps an expression with a lower function that would evaluate as lowercase in the server e.g. 'lower(a)'
     */
    public static Expression lower(BuildCapable expression) {
        return function("lower", expression);
    }

    /**
     * Wraps an expression with a lower function that would evaluate as lowercase in the server e.g. 'lower(a)'
     */
    public static Expression lower(String expression) {
        return lower(asExpression(expression));
    }

    /**
     * Wraps a left and right expression with a startsWith function e.g. 'starts_with(a, b)'
     */
    public static Expression startsWith(BuildCapable left, BuildCapable right) {
        return function("starts_with", left, right);
    }

    /**
     * Wraps a left and right expression with a startsWith function e.g. 'starts_with(a, b)'
     */
    public static Expression startsWith(String left, String right) {
        return startsWith(asExpression(left), asExpression(right));
    }

    /**
     * Wraps a left and right expression with a contains function e.g. 'contains(a, b)'
     */
    public static Expression contains(BuildCapable left, BuildCapable right) {
        return function("contains", left, right);
    }

    /**
     * Wraps a left and right expression with a contains function e.g. 'contains(a, b)'
     */
    public static Expression contains(String left, String right) {
        return contains(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with a multiply operator e.g. 'a * b'
     */
    public static Expression multipliedBy(BuildCapable left, BuildCapable right) {
        return applyOperator("*", left, right);
    }

    /**
     * Joins a left and right expression with a multiply operator e.g. 'a * b'
     */
    public static Expression multipliedBy(String left, String right) {
        return multipliedBy(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with a divide operator e.g. 'a / b'
     */
    public static Expression dividedBy(BuildCapable left, BuildCapable right) {
        return applyOperator("/", left, right);
    }

    /**
     * Joins a left and right expression with a divide operator e.g. 'a / b'
     */
    public static Expression dividedBy(String left, String right) {
        return dividedBy(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with an add operator e.g. 'a + b'
     */
    public static Expression add(BuildCapable left, BuildCapable right) {
        return applyOperator("+", left, right);
    }

    /**
     * Joins a left and right expression with an add operator e.g. 'a + b'
     */
    public static Expression add(String left, String right) {
        return add(asExpression(left), asExpression(right));
    }

    /**
     * Joins a left and right expression with an subtract operator e.g. 'a - b'
     */
    public static Expression subtract(BuildCapable left, BuildCapable right) {
        return applyOperator("-", left, right);
    }

    /**
     * Joins a left and right expression with an subtract operator e.g. 'a - b'
     */
    public static Expression subtract(String left, String right) {
        return subtract(asExpression(left), asExpression(right));
    }

    /**
     * Wraps an expression with an to_string function that would evaluate as a string cast in the server e.g. 'to_string(a)'
     */
    public static Expression castToString(BuildCapable expression) {
        return function("to_string", expression);
    }

    /**
     * Wraps an expression with an to_string function that would evaluate as a string cast in the server e.g. 'to_string(a)'
     */
    public static Expression castToString(String expression) {
        return castToString(asExpression(expression));
    }

    /**
     * Wraps an expression with an to_number function that would evaluate as a number cast in the server e.g. 'to_number(a)'
     */
    public static Expression toNumber(BuildCapable expression) {
        return function("to_number", expression);
    }

    /**
     * Wraps an expression with an to_number function that would evaluate as a number cast in the server e.g. 'to_number(a)'
     */
    public static Expression toNumber(String expression) {
        return toNumber(asExpression(expression));
    }

    /**
     * Wraps a left and right expression with a to_location function e.g. 'to_location(lat, lng)'
     */
    public static Expression toLocation(BuildCapable lat, BuildCapable lng) {
        return function("to_location", lat, lng);
    }

    /**
     * Wraps a left and right expression with a to_location function e.g. 'to_location(lat, lng)'
     */
    public static Expression toLocation(String lat, String lng) {
        return toLocation(asExpression(lat), asExpression(lng));
    }

    /**
     * Wraps an expression with an to_boolean function that would evaluate as a number cast in the server e.g. 'to_boolean(a)'
     */
    public static Expression toBoolean(BuildCapable expression) {
        return function("to_boolean", expression);
    }

    /**
     * Wraps an expression with an to_boolean function that would evaluate as a number cast in the server e.g. 'to_boolean(a)'
     */
    public static Expression toBoolean(String expression) {
        return toBoolean(asExpression(expression));
    }


    /**
     * Wraps an expression with an to_fixed_timestamp function that would evaluate as a to fixed timestamp conversion in the server e.g. 'to_fixed_timestamp(a)'
     */
    public static Expression toFixedTimestamp(BuildCapable expression) {
        return function("to_fixed_timestamp", expression);
    }

    /**
     * Wraps an expression with an to_fixed_timestamp function that would evaluate as a to fixed timestamp conversion in the server e.g. 'to_fixed_timestamp(a)'
     */
    public static Expression toFixedTimestamp(String expression) {
        return toFixedTimestamp(asExpression(expression));
    }

    /**
     * Wraps an expression with an to_floating_timestamp function that would evaluate as a to floating timestamp conversion in the server e.g. 'to_floating_timestamp(a)'
     */
    public static Expression toFloatingTimestamp(BuildCapable expression) {
        return function("to_floating_timestamp", expression);
    }

    /**
     * Wraps an expression with an to_floating_timestamp function that would evaluate as a to floating timestamp conversion in the server e.g. 'to_floating_timestamp(a)'
     */
    public static Expression toFloatingTimestamp(String expression) {
        return toFloatingTimestamp(asExpression(expression));
    }

    /**
     * Wraps an expression with a sum function that would evaluate as and aggregated sum in the server e.g. 'sum(a)'
     */
    public static Expression sum(BuildCapable expression) {
        return function("sum", expression);
    }

    /**
     * Wraps an expression with a sum function that would evaluate as and aggregated sum in the server e.g. 'sum(a)'
     */
    public static Expression sum(String expression) {
        return sum(asExpression(expression));
    }

    /**
     * Wraps an expression with a count function that would evaluate as and aggregated count in the server e.g. 'count(a)'
     */
    public static Expression count(BuildCapable expression) {
        return function("count", expression);
    }

    /**
     * Wraps an expression with a count function that would evaluate as and aggregated count in the server e.g. 'count(a)'
     */
    public static Expression count(String expression) {
        return count(asExpression(expression));
    }

    /**
     * Wraps an expression with a avg function that would evaluate as and aggregated avg in the server e.g. 'avg(a)'
     */
    public static Expression avg(BuildCapable expression) {
        return function("avg", expression);
    }

    /**
     * Wraps an expression with a avg function that would evaluate as and aggregated avg in the server e.g. 'avg(a)'
     */
    public static Expression avg(String expression) {
        return avg(asExpression(expression));
    }

    /**
     * Wraps an expression with a min function that would evaluate as a min value for an expression in the server e.g. 'min(a)'
     */
    public static Expression min(BuildCapable expression) {
        return function("min", expression);
    }

    /**
     * Wraps an expression with a min function that would evaluate as a min value for an expression in the server e.g. 'min(a)'
     */
    public static Expression min(String expression) {
        return min(asExpression(expression));
    }

    /**
     * Wraps an expression with a max function that would evaluate as a max value for an expression in the server e.g. 'max(a)'
     */
    public static Expression max(BuildCapable expression) {
        return function("max", expression);
    }

    /**
     * Wraps an expression with a max function that would evaluate as a max value for an expression in the server e.g. 'max(a)'
     */
    public static Expression max(String expression) {
        return max(asExpression(expression));
    }

    /**
     * Wraps an expression as an alias that can be further used in the query for other things such as aggregated calculation e.g. 'a as aliasOfA'
     */
    public static Expression as(BuildCapable expression, String alias) {
        return applyOperator("as", expression, simpleExpression(alias));
    }

    /**
     * Wraps an expression as an alias that can be further used in the query for other things such as aggregated calculation e.g. 'a as aliasOfA'
     */
    public static Expression as(String expression, String alias) {
        return as(asExpression(expression), alias);
    }

    /**
     * Represents a column in a clause e.g. 'a'
     */
    public static Expression column(String name) {
        return simpleExpression(name);
    }

    /**
     * Single quotes an expression for literal comparison eg. " a = 'something'  "
     */
    public static Expression quoted(BuildCapable expression) {
        return simpleExpression(String.format("'%s'", expression.build()));
    }

    /**
     * Single quotes an expression for literal comparison eg. " a = 'something'  "
     */
    public static Expression quoted(String expression) {
        return quoted(asExpression(expression));
    }

    /**
     * Adds order direction to an aexpression e.g. 'a desc'
     */
    public static Expression order(BuildCapable expression, OrderDirection direction) {
        return simpleExpression(String.format("%s %s", expression.build(), direction.name().toLowerCase()));
    }

    /**
     * Adds order direction to an aexpression e.g. 'a desc'
     */
    public static Expression order(String expression, OrderDirection direction) {
        return order(asExpression(expression), direction);
    }

    /**
     * Wraps an array of expressions in parentheses e.g. '(a)'
     */
    public static Expression parentheses(BuildCapable... expressions) {
        return simpleExpression(String.format("(%s)", buildAll(expressions)));
    }

    /**
     * Wraps an array of expressions in parentheses e.g. '(a)'
     */
    public static Expression parentheses(String... expressions) {
        return parentheses(asExpressions(expressions));
    }

    /**
     * Wraps an expression with a within_box function to look up withinBox based properties in bounding box represented by ne and sw coordinates
     */
    public static Expression withinBox(BuildCapable location, GeoBox geoBox) {
        return function("within_box", location, simpleExpression(geoBox.build()));
    }

    /**
     * Wraps an expression with a within_box function to look up withinBox based properties in bounding box represented by ne and sw coordinates
     */
    public static Expression withinBox(String location, GeoBox geoBox) {
        return withinBox(asExpression(location), geoBox);
    }

    /**
     * @see com.socrata.android.soql.clauses.BuildCapable#build()
     */
    @Override
    public String build() {
        return isRequiresWrap() && value != null ? parentheses(simpleExpression(value)).build() : value;
    }

    @Override
    public String toString() {
        return String.format("Expression: %s", build());
    }
}