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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 *
 *
 */
public class BQForbiddenException extends SQLException {

    private static final long serialVersionUID = -3669744441475950504L;

    Logger logger = Logger.getLogger(BQSQLException.class.getName());

    /**
     * <p>
     * Constructs a SQLException object.
     * </p>
     * The reason, SQLState are initialized to null and the vendor code is
     * initialized to 0. The cause is not initialized, and may subsequently be
     * initialized by a call to the Throwable.initCause(java.lang.Throwable)
     * method.
     *
     */
    public BQForbiddenException() {
        super();
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        this.logger.debug("SQLexception " + stacktrace);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given reason.
     * </p>
     * The SQLState is initialized to null and the vender code is initialized to
     * 0. The cause is not initialized, and may subsequently be initialized by a
     * call to the Throwable.initCause(java.lang.Throwable) method.
     *
     * @param reason
     *            - a description of the exception
     */
    public BQForbiddenException(String reason) {
        super(reason);
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        this.logger.debug(reason + stacktrace);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given reason and SQLState.
     * </p>
     * The cause is not initialized, and may subsequently be initialized by a
     * call to the Throwable.initCause(java.lang.Throwable) method. The vendor
     * code is initialized to 0.
     *
     * @param reason
     *            - a description of the exception
     * @param sqlState
     *            - an XOPEN or SQL:2003 code identifying the exception
     */
    public BQForbiddenException(String reason, String sqlState) {
        super(reason, sqlState);
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        this.logger.debug("SQLexception " + reason + " ;; " + sqlState + " ;; "
                + stacktrace);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given reason, SQLState and
     * vendorCode.
     * </p>
     * The cause is not initialized, and may subsequently be initialized by a
     * call to the Throwable.initCause(java.lang.Throwable) method.
     *
     * @param reason
     *            - a description of the exception
     * @param sqlState
     *            - an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode
     *            - a database vendor-specific exception code
     */
    public BQForbiddenException(String reason, String sqlState, int vendorCode) {
        super(reason, sqlState, vendorCode);
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        this.logger.debug("SQLexception " + reason + " " + sqlState + " "
                + String.valueOf(vendorCode) + stacktrace);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given reason, SQLState,
     * vendorCode and cause.
     * </p>
     *
     * @param reason
     *            - a description of the exception
     * @param sqlState
     *            - an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode
     *            - a database vendor-specific exception code
     * @param cause
     *            - the underlying reason for this SQLException
     */
    public BQForbiddenException(String reason, String sqlState, int vendorCode,
                          Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
        this.logger.debug("SQLexception " + reason + " " + sqlState + " "
                + String.valueOf(vendorCode), cause);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given reason, SQLState and cause.
     * </p>
     * The vendor code is initialized to 0.
     *
     * @param reason
     *            - a description of the exception
     * @param sqlState
     *            - an XOPEN or SQL:2003 code identifying the exception
     * @param cause
     *            - the underlying reason for this SQLException
     */
    public BQForbiddenException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
        this.logger.debug("SQLexception " + reason + " " + sqlState, cause);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given reason and cause.
     * </p>
     * The SQLState is initialized to null and the vendor code is initialized to
     * 0.
     *
     * @param reason
     *            - a description of the exception
     * @param cause
     *            - the underlying reason for this SQLException
     */
    public BQForbiddenException(String reason, Throwable cause) {
        super(reason, cause);
        this.logger.debug("SQLexception " + reason, cause);
    }

    /**
     * <p>
     * Constructs a SQLException object with a given cause.
     * </p>
     * The SQLState is initialized to null and the vendor code is initialized to
     * 0. The reason is initialized to null if cause==null or to
     * cause.toString() if cause!=null.
     *
     * @param cause
     *            - the underlying reason for this SQLException
     */
    public BQForbiddenException(Throwable cause) {
        super(cause);
        this.logger.debug("SQLexception ", cause);
    }

}
