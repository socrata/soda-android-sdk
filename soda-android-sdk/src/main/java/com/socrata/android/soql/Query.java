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

package com.socrata.android.soql;

import com.socrata.android.soql.clauses.*;
import com.socrata.android.soql.datatypes.GeoBox;
import com.socrata.android.soql.utils.BuildUtils;

import static com.socrata.android.soql.clauses.Expression.*;
import static com.socrata.android.soql.utils.BuildUtils.asExpressions;
import static com.socrata.android.soql.utils.BuildUtils.buildAll;

/**
 * Main interface to build structured queries
 */
public class Query implements BuildCapable {

    /**
     * The select clause
     *
     * @see Select
     */
    private Select select = new Select();

    /**
     * The where clause
     *
     * @see Where
     */
    private Where where = new Where();

    /**
     * The group by clause
     *
     * @see GroupBy
     */
    private GroupBy groupBy = new GroupBy();

    /**
     * The order by clause
     *
     * @see OrderBy
     */
    private OrderBy orderBy = new OrderBy();

    /**
     * The offset clause
     *
     * @see Offset
     */
    private Integer offset = 0;

    /**
     * The limit clause
     *
     * @see Limit
     */
    private Integer limit = 25;

    /**
     * The dataset this query is referring to
     */
    private String dataset;

    /**
     * The object type resulting objects from this query will be mapped to.
     */
    private Class<?> mapping;

    /**
     * Constructs a SODA query
     *
     * @param dataset Dataset this query is referring to
     * @param mapping The object type resulting objects from this query will be mapped to.
     */
    public Query(String dataset, Class<?> mapping) {
        this.dataset = dataset;
        this.mapping = mapping;
    }

    /**
     * @see Select
     */
    public Select getSelect() {
        return select;
    }

    /**
     * @see Where
     */
    public Where getWhere() {
        return where;
    }

    /**
     * @see GroupBy
     */
    public GroupBy getGroupBy() {
        return groupBy;
    }

    /**
     * @see OrderBy
     */
    public OrderBy getOrderBy() {
        return orderBy;
    }

    /**
     * @see Offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @see Limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @return dataset this query is referring to
     */
    public String getDataset() {
        return dataset;
    }

    /**
     * @return The object type resulting objects from this query will be mapped to.
     */
    public Class<?> getMapping() {
        return mapping;
    }

    /**
     * Adds expressions to the clause replacing the clause with a new clause including the
     * appended expressions
     *
     * @param expressions the expressions to be appended
     */
    public void addSelect(BuildCapable... expressions) {
        this.select = this.select.append(expressions);
    }

    /**
     * Adds expressions to the clause replacing the clause with a new clause including the
     * appended expressions
     *
     * @param expressions the expressions to be appended
     */
    public void addWhere(BuildCapable... expressions) {
        this.where = this.where.append(expressions);
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#isNotNull
     */
    public void whereIsNotNull(BuildCapable expression) {
        addWhere(isNotNull(expression));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#isNull
     */
    public void whereIsNull(BuildCapable expression) {
        addWhere(isNull(expression));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#gt
     */
    public void whereGreaterThan(BuildCapable left, BuildCapable right) {
        addWhere(gt(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#get
     */
    public void whereGreaterThanOrEquals(BuildCapable left, BuildCapable right) {
        addWhere(gte(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#lt
     */
    public void whereLessThan(BuildCapable left, BuildCapable right) {
        addWhere(lt(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#lte
     */
    public void whereLessThanOrEquals(BuildCapable left, BuildCapable right) {
        addWhere(lte(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#eq
     */
    public void whereEquals(BuildCapable left, BuildCapable right) {
        addWhere(eq(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#neq
     */
    public void whereNotEquals(BuildCapable left, BuildCapable right) {
        addWhere(neq(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#startsWith
     */
    public void whereStartsWith(BuildCapable left, BuildCapable right) {
        addWhere(startsWith(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#contains
     */
    public void whereContains(BuildCapable left, BuildCapable right) {
        addWhere(contains(left, right));
    }

    /**
     * Syntactic sugar to add a where expression of type Expression#withinBox
     */
    public void whereWithinBox(BuildCapable location, GeoBox geoBox) {
        addWhere(withinBox(location, geoBox));
    }

    /**
     * Adds expressions to the clause replacing the clause with a new clause including the
     * appended expressions
     *
     * @param expressions the expressions to be appended
     */
    public void addGroup(BuildCapable... expressions) {
        this.groupBy = this.groupBy.append(expressions);
    }

    /**
     * Adds expressions to the clause replacing the clause with a new clause including the
     * appended expressions
     *
     * @param expressions the expressions to be appended
     */
    public void addOrder(BuildCapable... expressions) {
        this.orderBy = this.orderBy.append(expressions);
    }

    /**
     * Sets the query offset
     *
     * @param offset the query offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * Sets the query limit
     *
     * @param limit the query limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @see com.socrata.android.soql.clauses.BuildCapable#build()
     */
    @Override
    public String build() {
        BuildCapable[] parts = new BuildCapable[]{getSelect(), getWhere(), getGroupBy(), getOrderBy(), new Offset(getOffset()), new Limit(getLimit())};
        return BuildUtils.join(" ", buildAll(parts)).replaceAll("  ", " ").trim();
    }

    /**
     * Commodity method to construct a Select initialized with the provided expressions
     */
    public static Select select(String... expressions) {
        return select(asExpressions(expressions));
    }

    /**
     * Commodity method to construct a Select initialized with the provided expressions
     */
    public static Select select(BuildCapable... expressions) {
        return new Select().append(expressions);
    }

    /**
     * Commodity method to construct a Where initialized with the provided expressions
     */
    public static Where where(String... expressions) {
        return where(asExpressions(expressions));
    }

    /**
     * Commodity method to construct a Where initialized with the provided expressions
     */
    public static Where where(BuildCapable... expressions) {
        return new Where().append(expressions);
    }

    /**
     * Commodity method to construct a OrderBy initialized with the provided expressions
     */
    public static OrderBy orderBy(String... expressions) {
        return orderBy(asExpressions(expressions));
    }

    /**
     * Commodity method to construct a OrderBy initialized with the provided expressions
     */
    public static OrderBy orderBy(BuildCapable... expressions) {
        return new OrderBy().append(expressions);
    }

    /**
     * Commodity method to construct a GroupBy initialized with the provided expressions
     */
    public static GroupBy groupBy(String... expressions) {
        return groupBy(asExpressions(expressions));
    }

    /**
     * Commodity method to construct a GroupBy initialized with the provided expressions
     */
    public static GroupBy groupBy(BuildCapable... expressions) {
        return new GroupBy().append(expressions);
    }

    /**
     * Commodity method to construct an Offset initialized with the provided value
     */
    public static Offset offset(Integer offset) {
        return new Offset(offset);
    }

    /**
     * Commodity method to construct a Limit initialized with the provided value
     */
    public static Limit limit(Integer limit) {
        return new Limit(limit);
    }

    /**
     * Commodity method to construct a GeoBox initialized with bounding box
     * constrained by north, east, south and west values
     */
    public static GeoBox box(double north, double east, double south, double west) {
        return new GeoBox(north, east, south, west);
    }

}
