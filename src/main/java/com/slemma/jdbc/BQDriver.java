/**
 * Starschema Big Query JDBC Driver
 * Copyright (C) 2012, Starschema Ltd.
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * BQDriver - This class implements the java.sql.Driver interface
 * <p/>
 * The driver URL is:
 * <p/>
 * If Service account: jdbc:BQDriver:projectid(urlencoded)?ServiceAccount=true
 * Properties File: username: account email (NOT URLENCODED) password: path to
 * key file (NOT URLENCODED)
 * <p/>
 * If Installed account:
 * jdbc:BQDriver:projectid(urlencoded)?ServiceAccount=false or
 * jdbc:BQDriver:projectid(urlencoded) Properties File: username: accountid (NOT
 * URLENCODED) password: clientsecret (NOT URLENCODED)
 * <p/>
 * You can also specify the login username and password in the url directly:
 * <p/>
 * If Service account:
 * jdbc:BQDriver:projectid(urlencoded)?ServiceAccount=true&user
 * =accountemail(urlencoded)&password=keypath(urlencoded)
 * <p/>
 * If Installed account:
 * jdbc:BQDriver:projectid(urlencoded)?ServiceAccount=false
 * &user=accountid(urlencoded)&password=clientsecret(urlencoded) or
 * jdbc:BQDriver
 * :projectid(urlencoded)&user=accountid(urlencoded)&password=clientsecret
 * (urlencoded)
 * <p/>
 * Any Java program can use this driver for JDBC purpose by specifying this URL
 * format.
 * </p>
 */

package com.slemma.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This Class implements the java.sql.Driver interface
 *
 * @author Horv�th Attila
 */
public class BQDriver implements java.sql.Driver {

    /** Instance log4j.Logger */
    // static Logger logg = new Logger(BQDriver.class.getName());
    static Logger logg = Logger.getLogger(BQDriver.class.getName());
    /** Url_Prefix for using this driver */
    private static final String URL_PREFIX = "jdbc:BQDriver:";
    private static final String SUB_PROTOCOL = "bqdriver";
    /** MAJOR Version of the driver */
    private static final int MAJOR_VERSION = 1;
    /** Minor Version of the driver */
    private static final int MINOR_VERSION = 3;
    /** Properties **/
    private Properties _Props = null;

    /** Registers the driver with the drivermanager */
    static {
        try {

            BQDriver driverInst = new BQDriver();
            DriverManager.registerDriver(driverInst);

            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(System
                        .getProperty("user.home")
                        + File.separator
                        + ".bqjdbc"
                        + File.separator + "log4j.properties"));
                PropertyConfigurator.configure(properties);
            } catch (IOException e) {
                BasicConfigurator.configure();
            }

            logg = Logger.getLogger(driverInst.getClass());
            logg.debug("Registered the driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets Major Version of the Driver as static
     *
     * @return Major Version of the Driver as static
     */
    public static int getMajorVersionAsStatic() {
        return BQDriver.MAJOR_VERSION;
    }

    /**
     * Gets Minor Version of the Driver as static
     *
     * @return Minor Version of the Driver as static
     */
    public static int getMinorVersionAsStatic() {
        return BQDriver.MINOR_VERSION;
    }

    /** It returns the URL prefix for using BQDriver */
    public static String getURLPrefix() {
        return BQDriver.URL_PREFIX;
    }

    /** {@inheritDoc} */
    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (parseURL(url, null) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * <p>
     * <h1>Implementation Details:</h1><br>
     * This method create a new BQconnection and then returns it
     * </p>
     */
    @Override
    public Connection connect(String url, Properties info)
            throws SQLException {

        BQConnection localConInstance = null;

        logg.debug("Creating Connection With url: " + url);

//        if (this.acceptsURL(url)) {
//            localConInstance = new BQConnection(url, info);
//        }
//
//        return localConInstance;
        if ((_Props = parseURL(url, info)) == null) {
            return null;
        } else {
            return new BQConnection(url, _Props);
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getMajorVersion() {
        return BQDriver.MAJOR_VERSION;
    }

    /** {@inheritDoc} */
    @Override
    public int getMinorVersion() {
        return BQDriver.MINOR_VERSION;
    }

    /**
     * <p>
     * <h1>Implementation Details:</h1><br>
     * Gets information about the possible properties for this driver.
     * </p>
     *
     * @return a default DriverPropertyInfo
     */
    @Override
    public java.sql.DriverPropertyInfo[] getPropertyInfo(String url,
                                                         Properties info) throws SQLException {

        if (_Props == null) {
            _Props = new Properties();
        }

        DriverPropertyInfo projectID =
                new DriverPropertyInfo("projectid", _Props.getProperty("projectid"));
        projectID.required = true;

        projectID.description = "Project ID";

        DriverPropertyInfo type =
                new DriverPropertyInfo("type", _Props.getProperty("type", "installed"));
        type.required = true;
        type.choices = new String[]{"installed", "service"};
        type.description = "Authorization type";

        DriverPropertyInfo user =
                new DriverPropertyInfo("user", info.getProperty("user"));
        user.required = true;
        user.description = "Username to authenticate as";

        DriverPropertyInfo password =
                new DriverPropertyInfo("password", info.getProperty("password"));
        password.required = true;
        password.description = "Password to use for authentication";

        DriverPropertyInfo refreshToken =
                new DriverPropertyInfo("refreshToken", info.getProperty("refreshToken"));
        refreshToken.required = false;
        refreshToken.description = "Refresh token to use for authorization";

        DriverPropertyInfo transformQuery =
                new DriverPropertyInfo("transformQuery", info.getProperty("transformQuery", "true"));
        transformQuery.required = false;
        transformQuery.choices = new String[]{"true", "false"};
        transformQuery.description = "Should the driver try to convert SQL?";

        DriverPropertyInfo largeJoinsEnabled =
                new DriverPropertyInfo("largeJoinsEnabled", info.getProperty("largeJoinsEnabled", "true"));
        largeJoinsEnabled.required = false;
        largeJoinsEnabled.choices = new String[]{"true", "false"};
        largeJoinsEnabled.description = "Should the driver use EACH operator in JOINs";

        DriverPropertyInfo publicDataEnabled =
                new DriverPropertyInfo("publicDataEnabled", info.getProperty("publicDataEnabled", "false"));
        publicDataEnabled.required = false;
        publicDataEnabled.choices = new String[]{"true", "false"};
        publicDataEnabled.description = "If true then metadata do not retrieved for publicdata";


        DriverPropertyInfo[] Dpi = {
                projectID,
                type,
                user,
                password,
                refreshToken,
                transformQuery,
                largeJoinsEnabled,
                publicDataEnabled
        };
        return Dpi;
    }

    /**
     * <p>
     * <h1>Implementation Details:</h1><br>
     * Always returns false, since the driver is not jdbcCompliant
     * </p>
     */
    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    /**
     * Constructs a new DriverURL, splitting the specified URL into its
     * component parts
     * @param url JDBC URL to parse
     * @param defaults Default properties
     * @return Properties with elements added from the url
     * @exception java.sql.SQLException
     */


    Properties parseURL(String url, Properties defaults) throws java.sql.SQLException {
        Properties urlProps = new Properties(defaults);



	/*
     * Parse parameters after the ? in the URL and remove
	 * them from the original URL.
	 */

        int index = url.indexOf("?");

        if (index != -1) {
            String ParamString = url.substring(index + 1, url.length());
            url = url.substring(0, index);

            StringTokenizer queryParams = new StringTokenizer(ParamString, "&");

            while (queryParams.hasMoreTokens()) {

                StringTokenizer vp = new StringTokenizer(queryParams.nextToken(), "=");

                String param = "";

                if (vp.hasMoreTokens()) {
                    param = vp.nextToken();
                }

                String value = "";

                if (vp.hasMoreTokens()) {
                    value = vp.nextToken();
                }

                if (value.length() > 0 && param.length() > 0) {
                    urlProps.put(param, value);
                }
            }
        }

        StringTokenizer st = new StringTokenizer(url, ":/", true);

        if (st.hasMoreTokens()) {
            String protocol = st.nextToken();
            if (protocol != null) {
                if (!protocol.toLowerCase().equals("jdbc"))
                    return null;
            } else {
                return null;
            }
        } else {
            return null;
        }

        // Look for the colon following 'jdbc'

        if (st.hasMoreTokens()) {
            String Colon = st.nextToken();
            if (Colon != null) {
                if (!Colon.equals(":"))
                    return null;
            } else {
                return null;
            }
        } else {
            return null;
        }

        // Look for sub-protocol to be mysql

        if (st.hasMoreTokens()) {
            String subProto = st.nextToken();
            if (subProto != null) {
                if (!subProto.toLowerCase().equals(BQDriver.SUB_PROTOCOL))
                    return null; // We only handle BQDriver sub-protocol
            } else {
                return null;
            }
        } else {
            return null;
        }

        // Look for the colon following 'BQDriver'

        if (st.hasMoreTokens()) {
            String colon = st.nextToken();
            if (colon != null) {
                if (!colon.equals(":"))
                    return null;
            } else {
                return null;
            }
        } else {
            return null;
        }

        // Look for the "project id" of the URL

        if (st.hasMoreTokens()) {
            String project_id = st.nextToken();
            if (project_id != null) {
                urlProps.put("projectid", project_id);

                // We're done
                return urlProps;
            } else {
                return null;
            }

        } else {
            return null;
        }


    }

    /**
     * Return the hostname property
     * @return
     */
    public String ProjectId() {
        return _Props.getProperty("project_id");
    }


    /**
     * return the value of any property this driver knows about
     * @param Name
     * @return
     */

    public String property(String Name) {
        return _Props.getProperty(Name);
    }

    //------------------------- for Jdk1.7 -----------------------------------

    /** {@inheritDoc} */
    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
