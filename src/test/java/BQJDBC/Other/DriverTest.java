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
 * This Junit test runs queries throught the jdbc driver and checks their
 * results
 * 
 * @author Horv�th Attila
 */
package BQJDBC.Other;

import BQJDBC.QueryResultTest.HelperFunctions;
import com.slemma.jdbc.BQConnection;
import com.slemma.jdbc.BQDriver;
import com.slemma.jdbc.BQSupportFuncts;
import com.slemma.jdbc.BQSupportMethods;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DriverTest
{

    private static java.sql.Connection con = null;
    Logger logger = Logger.getLogger(DriverTest.class.getName());

    /**
     * Load Driver
     */
    @Before
    public void NewConnection() {
        try {
            Class.forName("com.slemma.jdbc.BQDriver");
        }
        catch (ClassNotFoundException e)
        {
            this.logger.error("Error:" + e.toString());
            Assert.fail("General Exception:" + e.toString());
        }
        try {
            this.logger.info("thread will sleep for 1 minute");
            Thread.sleep(1000 * 1); // 1000milisec = 1 sec * 60 = 1 minute

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseUrl1() {
        String url = "jdbc:BQDriver://test.com:test_project";
        try {
            BQDriver drv = (BQDriver)DriverManager.getDriver(url);
            Properties parsedProperties = drv.parseURL(url, new Properties());
            if (parsedProperties.containsKey("projectid")) {
                String projectId = (String) parsedProperties.get("projectid");
                Assert.assertEquals("test.com:test_project", projectId);
            } else {
                Assert.fail("Key 'projectId' not found");
            }

        }
        catch (SQLException e) {
            Assert.fail("Fail/ Exception message: " + e.getMessage());
        }
    }

    @Test
    public void parseUrl2() {
        String url = "jdbc:BQDriver://test.com:test_project?transformQuery=true&qqq=rrr";
        try {
            BQDriver drv = (BQDriver)DriverManager.getDriver(url);
            Properties parsedProperties = drv.parseURL(url, new Properties());
            if (parsedProperties.containsKey("projectid")) {
                String projectId = (String) parsedProperties.get("projectid");
                Assert.assertEquals("test.com:test_project", projectId);
            } else {
                Assert.fail("Key 'projectId' not found");
            }

        }
        catch (SQLException e) {
            Assert.fail("Fail/ Exception message: " + e.getMessage());
        }
    }

}
