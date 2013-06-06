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

/**
 * A select clause e.g. select a, b, c.
 */
public class Select extends ImmutableClause {

    /**
     * @see com.socrata.android.soql.clauses.BuildCapable#build()
     */
    @Override
    public String build() {
        BuildCapable[] expressions = getExpressions();
        return expressions.length == 0 ? "select *" : String.format("select %s", BuildUtils.join(", ", BuildUtils.buildAll(expressions)));
    }
}
