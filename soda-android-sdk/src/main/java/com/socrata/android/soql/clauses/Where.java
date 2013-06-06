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

import com.socrata.android.soql.utils.BuildUtils;

import static com.socrata.android.soql.clauses.Expression.and;
import static com.socrata.android.soql.utils.BuildUtils.buildAll;

/**
 * A where clause e.g. (expression and expression).
 */
public class Where extends ImmutableClause {

    /**
     * @see com.socrata.android.soql.clauses.BuildCapable#build()
     */
    @Override
    public String build() {
        BuildCapable[] expressions = getExpressions();
        return expressions.length == 0 ? "" : String.format("where %s", BuildUtils.join(" ", buildAll(and(expressions))));
    }
}
