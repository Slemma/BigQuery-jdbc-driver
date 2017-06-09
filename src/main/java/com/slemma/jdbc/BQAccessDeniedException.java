/**
 * Starschema Big Query JDBC Driver
 * Copyright (C) 2012, Starschema Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.slemma.jdbc;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 *
 *
 */
public class BQAccessDeniedException extends SQLException {

    Logger logger = Logger.getLogger(BQSQLException.class.getName());

    /**
     * <p>
     * Constructs a BQAccessDeniedException object.
     * </p>
     */
    public BQAccessDeniedException() {
        super();
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        this.logger.debug("BQAccessDeniedException " + stacktrace);
    }

    /**
     * <p>
     * Constructs a BQAccessDeniedException object with a given reason.
     * </p>
     * The SQLState is initialized to null and the vender code is initialized to
     * 0. The cause is not initialized, and may subsequently be initialized by a
     * call to the Throwable.initCause(java.lang.Throwable) method.
     *
     * @param reason
     *            - a description of the exception
     */
    public BQAccessDeniedException(String reason) {
        super(reason);
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        this.logger.debug(reason + stacktrace);
    }

    /**
     * <p>
     * Constructs a BQAccessDeniedException object with a given reason and cause.
     * </p>
     *
     * @param reason
     *            - a description of the exception
     * @param cause
     *            - the underlying reason for this BQAccessDeniedException
     */
    public BQAccessDeniedException(String reason, Throwable cause) {
        super(reason, cause);
        this.logger.debug("BQAccessDeniedException " + reason, cause);
    }

    /**
     * <p>
     * Constructs a BQAccessDeniedException object with a given cause.
     * </p>
     *
     * @param cause
     *            - the underlying reason for this BQAccessDeniedException
     */
    public BQAccessDeniedException(Throwable cause) {
        super(cause);
        this.logger.debug("BQAccessDeniedException ", cause);
    }

}