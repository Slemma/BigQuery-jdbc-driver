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
package BQJDBC.QueryResultTest;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;
import com.slemma.jdbc.BQConnection;
import com.slemma.jdbc.BQSupportFuncts;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * This Junit test tests functions in BQResultset
 * 
 * @author Horv�th Attila
 * @author Gunics Balazs
 */
public class BQForwardOnlyResultSetFunctionTest {
    
    private static java.sql.Connection con = null;
    private static java.sql.ResultSet Result = null;
    
    Logger logger = Logger.getLogger(BQForwardOnlyResultSetFunctionTest.class.getName());
    
    @Test
    public void ChainedCursorFunctionTest() {
        this.logger.info("ChainedFunctionTest");
        try {
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("you",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        try {
            BQForwardOnlyResultSetFunctionTest.Result.absolute(10);
        }
        catch (SQLException e) {
            Assert.assertTrue(true);
        }
        
        try {
            for (int i = 0; i < 9; i++) {
                Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            }
            Assert.assertFalse(BQForwardOnlyResultSetFunctionTest.Result.next());
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        
        try {
            Assert.assertEquals("", BQForwardOnlyResultSetFunctionTest.Result.getString(1));
        }
        catch (SQLException e) {
                Assert.assertTrue(true);
        }
        
        QueryLoad();
        try {
            Result.next();
            Assert.assertEquals("you",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        this.logger.info("chainedfunctiontest end");
    }

        
    /**
     * For testing isValid() , Close() , isClosed()
     */
    @Test
    public void isClosedValidtest() {
        try {
            Assert.assertEquals(true, BQForwardOnlyResultSetFunctionTest.con.isValid(0));
        }
        catch (SQLException e) {
            Assert.fail("Got an exception" + e.toString());
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(true, BQForwardOnlyResultSetFunctionTest.con.isValid(10));
        }
        catch (SQLException e) {
            Assert.fail("Got an exception" + e.toString());
            e.printStackTrace();
        }
        try {
            BQForwardOnlyResultSetFunctionTest.con.isValid(-10);
        }
        catch (SQLException e) {
            Assert.assertTrue(true);
            // e.printStackTrace();
        }
        
        try {
            BQForwardOnlyResultSetFunctionTest.con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.con.isClosed());
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        
        try {
            BQForwardOnlyResultSetFunctionTest.con.isValid(0);
        }
        catch (SQLException e) {
            Assert.assertTrue(true);
            e.printStackTrace();
        }
        
    }
    
    /**
     * Makes a new Bigquery Connection to URL in file and gives back the
     * Connection to static con member.
     */
    @Before
    public void NewConnection() {
        
        try {
            if (BQForwardOnlyResultSetFunctionTest.con == null
                    || !BQForwardOnlyResultSetFunctionTest.con.isValid(0)) {
                this.logger.info("Testing the JDBC driver");
                try {
                    Class.forName("com.slemma.jdbc.BQDriver");

                    Properties properties = BQSupportFuncts.readFromPropFile("installedaccount.properties");
                    properties.setProperty("transformQuery","false");

                    BQForwardOnlyResultSetFunctionTest.con = DriverManager.getConnection(
                            BQSupportFuncts.constructUrlFromPropertiesFile(properties),
                            properties);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    this.logger.error("Error in connection" + e.toString());
                    Assert.fail("General Exception:" + e.toString());
                }
                this.logger.info(((BQConnection) BQForwardOnlyResultSetFunctionTest.con)
                        .getURLPART());
            }
        }
        catch (SQLException e) {
            logger.debug("Oops something went wrong",e);
        }
        this.QueryLoad();
    }
    
    // Comprehensive Tests:
      
    
    public void QueryLoad() {
        final String sql = "SELECT * FROM\n" +
                "(SELECT 'you' as word, 42 as count, 1 as ord)\n" +
                ",(SELECT 'yet' as word, 42 as count, 2 as ord)\n" +
                ",(SELECT 'would' as word, 42 as count, 3 as ord)\n" +
                ",(SELECT 'world' as word, 42 as count, 4 as ord)\n" +
                ",(SELECT 'without' as word, 42 as count, 5 as ord)\n" +
                ",(SELECT 'with' as word, 42 as count, 6 as ord)\n" +
                ",(SELECT 'your' as word, 41 as count, 7 as ord)\n" +
                ",(SELECT 'young' as word, 41 as count, 8 as ord)\n" +
                ",(SELECT 'words' as word, 41 as count, 9 as ord)\n" +
                ",(SELECT 'word' as word, 41 as count, 10 as ord)" +
                "ORDER BY ord";
        this.logger.info("Test number: 01");
        this.logger.info("Running query:" + sql);
        
        try {
            //Statement stmt = BQResultSetFunctionTest.con.createStatement();
            Statement stmt = BQForwardOnlyResultSetFunctionTest.con
                    .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setQueryTimeout(500);
            BQForwardOnlyResultSetFunctionTest.Result = stmt.executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(BQForwardOnlyResultSetFunctionTest.Result);
    }
    
    @Test
    public void ResultSetMetadata() {
        try {
            this.logger.debug(BQForwardOnlyResultSetFunctionTest.Result.getMetaData()
                    .getSchemaName(1));
            this.logger.debug(BQForwardOnlyResultSetFunctionTest.Result.getMetaData()
                    .getScale(1));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
        }
        Assert.assertTrue(true);
    }
    
    @Test
    public void TestResultIndexOutofBound() {
        try {
            this.logger.debug(BQForwardOnlyResultSetFunctionTest.Result.getBoolean(99));
        }
        catch (SQLException e) {
            Assert.assertTrue(true);
            this.logger.error("SQLexception" + e.toString());
        }
    }
       
        
    @Test
    public void TestResultSetFirst() {
        try {
//            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.first());
            Result.next();
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.isFirst());
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
    }
    
    @Test
    public void TestResultSetgetBoolean() {
        try {
            Assert.assertTrue(Result.next());
            Assert.assertEquals(Boolean.parseBoolean("42"),
                    BQForwardOnlyResultSetFunctionTest.Result.getBoolean(2));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
    }
    
    @Test
    public void TestResultSetgetFloat() {
        try {
            Assert.assertTrue(Result.next());
            Assert.assertEquals((float)42, BQForwardOnlyResultSetFunctionTest.Result.getFloat(2),0.001);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
    }
    
    @Test
    public void TestResultSetgetInteger() {
        try {
            Assert.assertTrue(Result.next());
            Assert.assertEquals(42, BQForwardOnlyResultSetFunctionTest.Result.getInt(2));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
    }
    
    @Test
    public void TestResultSetgetRow() {
        
        try {
            Assert.assertTrue(Result.next());
            BQForwardOnlyResultSetFunctionTest.Result.getRow();
        }
        catch (SQLException e) {
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void TestResultSetgetString() {
        try {
            Assert.assertTrue(Result.next());
            Assert.assertEquals("you",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
    }
    
    @Test
    public void TestResultSetNext() {
        try {
//            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.first());
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("yet",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("would",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("world",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("without",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("with",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("your",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("young",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("words",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertTrue(BQForwardOnlyResultSetFunctionTest.Result.next());
            Assert.assertEquals("word",
                    BQForwardOnlyResultSetFunctionTest.Result.getString(1));
            Assert.assertFalse(BQForwardOnlyResultSetFunctionTest.Result.next());
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        
        try {
            Assert.assertEquals("", BQForwardOnlyResultSetFunctionTest.Result.getString(1));
        }
        catch (SQLException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void selectNestedDataset() {

        String input = "SELECT [repository.name], [repository.url],SUM( [repository.size] ) " +
                "FROM [publicdata.samples.github_nested] " +
                "GROUP BY [repository.name], [repository.url] limit 10 ";
        logger.info("Running test: selectNestedDataset \r\n" + input );

        ResultSet queryResult = null;
        try {
            queryResult = con.createStatement().executeQuery(input);
            String firstColumnName  = queryResult.getMetaData().getColumnName(1);
            Assert.assertEquals("repository.name", firstColumnName);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(queryResult);
        HelperFunctions.printer(queryResult);
    }

    @Test
    public void selectNestedDataSetAliased1() {

        String input = "SELECT [repository.name] as repository_name, [repository.url],SUM( [repository.size] ) " +
                "FROM [publicdata.samples.github_nested] " +
                "GROUP BY repository_name, [repository.url] limit 10 ";
        logger.info("Running test: selectNestedDataset \r\n" + input );

        ResultSet queryResult = null;
        try {
            queryResult = con.createStatement().executeQuery(input);
            String firstColumnName  = queryResult.getMetaData().getColumnName(1);
            Assert.assertEquals("repository_name", firstColumnName);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(queryResult);
        HelperFunctions.printer(queryResult);
    }

    @Test
    public void selectNestedDataSetAliased2() {

        String input = "SELECT [repository.name] repository_name, [repository.url],SUM( [repository.size] ) " +
                "FROM [publicdata.samples.github_nested] " +
                "GROUP BY repository_name, [repository.url] limit 10 ";
        logger.info("Running test: selectNestedDataset \r\n" + input );

        ResultSet queryResult = null;
        try {
            queryResult = con.createStatement().executeQuery(input);
            String firstColumnName  = queryResult.getMetaData().getColumnName(1);
            Assert.assertEquals("repository_name", firstColumnName);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(queryResult);
        HelperFunctions.printer(queryResult);
    }

    @Test
    public void selectDateDateTimeTimestamp() {

        String input = "SELECT \n" +
                "CAST(DATE(time_ts) as DATE) as D_FIELD, CAST(DATE(time_ts) as DATETIME) as DT_FIELD, time_ts as TS_FIELD \n" +
                "FROM [bigquery-public-data:hacker_news.comments]\n" +
                "where \n" +
                "time_ts is not null \n" +
                "and time_ts > cast ('2014-01-01 00:00:00' as TIMESTAMP )\n" +
                "order by time_ts\n" +
                "LIMIT 10";
        logger.info("Running test: selectDateDateTimeTimestamp \r\n" + input );

        ResultSet queryResult = null;
        try {
            queryResult = con.createStatement().executeQuery(input);
            Assert.assertEquals("DATE", queryResult.getMetaData().getColumnTypeName(1));
            Assert.assertEquals("DATETIME", queryResult.getMetaData().getColumnTypeName(2));
            Assert.assertEquals("TIMESTAMP", queryResult.getMetaData().getColumnTypeName(3));

            queryResult.next();

            Assert.assertEquals("2014-01-01", queryResult.getString(1));
            Assert.assertEquals("2014-01-01T00:00:00", queryResult.getString(2));
            Assert.assertEquals("2014-01-01 00:00:05.000+0000", queryResult.getString(3));
            queryResult.close();

            ((BQConnection)con).setTimeZone("CET");
            queryResult = con.createStatement().executeQuery(input);
            queryResult.next();

            Assert.assertEquals("2014-01-01", queryResult.getString(1));
            Assert.assertEquals("2014-01-01T00:00:00", queryResult.getString(2));
            Assert.assertEquals("2014-01-01 01:00:05.000+0100", queryResult.getString(3));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(queryResult);
    }

    @Test
    public void selectTimestamp() {

        String input = "SELECT \n" +
                "CAST(DATE(time_ts) as DATE) as D_FIELD, CAST(DATE(time_ts) as DATETIME) as DT_FIELD, time_ts as TS_FIELD \n" +
                "FROM [bigquery-public-data:hacker_news.comments]\n" +
                "where \n" +
                "time_ts is not null \n" +
                "and time_ts > cast ('2014-01-01 00:00:00' as TIMESTAMP )\n" +
                "order by time_ts\n" +
                "LIMIT 10";
        logger.info("Running test: selectDateDateTimeTimestamp \r\n" + input );

        ResultSet queryResult = null;
        try {
            queryResult = con.createStatement().executeQuery(input);
            queryResult.next();

            Assert.assertEquals(queryResult.getObject(1).getClass(), java.sql.Date.class);
            Assert.assertEquals(1388512800000L, queryResult.getTimestamp(1).getTime());
            Assert.assertEquals(queryResult.getObject(2).getClass(), java.sql.Timestamp.class);
            Assert.assertEquals(1388512800000L, queryResult.getTimestamp(2).getTime());
            Assert.assertEquals(queryResult.getObject(3).getClass(), java.sql.Timestamp.class);
            Assert.assertEquals(1388534405000L, queryResult.getTimestamp(3).getTime());
            queryResult.close();

            ((BQConnection)con).setTimeZone("CET");
            queryResult = con.createStatement().executeQuery(input);
            queryResult.next();

            Assert.assertEquals(1388512800000L, queryResult.getTimestamp(1).getTime());
            Assert.assertEquals(1388512800000L, queryResult.getTimestamp(2).getTime());
            Assert.assertEquals(1388534405000L, queryResult.getTimestamp(3).getTime());
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(queryResult);
    }
}
