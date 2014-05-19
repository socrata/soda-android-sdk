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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Abstract super class for all immutable clauses that can append expressions
 * to themselves returning a new clause with the appended expressions
 */
public abstract class ImmutableClause implements BuildCapable {

    /**
     * The list of expressions contained in this clause
     */
    protected List<BuildCapable> expressions = Collections.emptyList();

    /**
     * An array with the expressions contained in this class
     */
    public BuildCapable[] getExpressions() {
        return expressions.toArray(new BuildCapable[expressions.size()]);
    }

    /**
     * Immutable builder that returns a new clause of the same type with the appended expressions
     *
     * @param expressions the expressions
     * @param <T>         a subclass of ImmutableClass
     * @return a copy of this class with the appended expressions
     */
    @SuppressWarnings("unchecked")
    public <T extends ImmutableClause> T append(final BuildCapable... expressions) {
        try {
            List<BuildCapable> appendedList = new ArrayList<BuildCapable>(
                Arrays.asList(this.getExpressions())) {{
                addAll(Arrays.asList(expressions));
            }};
            
            T clause = (T) getClass().newInstance();
            clause.expressions = Collections.unmodifiableList(appendedList);
            return clause;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
