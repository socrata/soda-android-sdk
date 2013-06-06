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

package com.socrata.android.soql.utils;

import android.text.TextUtils;
import com.socrata.android.soql.clauses.BuildCapable;

import static com.socrata.android.soql.clauses.Expression.simpleExpression;

/**
 * Build utils to build expressions and {@link BuildCapable} instances
 */
public class BuildUtils {

    /**
     * Prevents instantiation
     */
    private BuildUtils() {
    }

    /**
     * Joins an array of objects with a separator into a String
     *
     * @param separator the separator
     * @param args      the args
     * @return the joined string
     */
    public static String join(CharSequence separator, Object... args) {
        return TextUtils.join(separator, args);
    }

    /**
     * Wraps a string into a simple expression
     *
     * @param expression the string expression
     * @return the build capable simple expression
     */
    public static BuildCapable asExpression(String expression) {
        return simpleExpression(expression);
    }

    /**
     * Converts an array of string expressions into simple build capable expressions
     *
     * @param expressions the expressions as strings
     * @return the build capable array representation
     */
    public static BuildCapable[] asExpressions(String... expressions) {
        BuildCapable[] simpleExpressions = new BuildCapable[expressions.length];
        for (int i = 0; i < expressions.length; i++) {
            String expression = expressions[i];
            simpleExpressions[i] = asExpression(expression);
        }
        return simpleExpressions;
    }

    /**
     * Builds {@link BuildCapable} objects into their string representation
     *
     * @param buildCapable the build capable objects
     * @return the String array of built expressions
     */
    public static String[] buildAll(BuildCapable... buildCapable) {
        String[] built = new String[buildCapable.length];
        for (int i = 0; i < buildCapable.length; i++) {
            BuildCapable capable = buildCapable[i];
            built[i] = capable.build();
        }
        return built;
    }

}
