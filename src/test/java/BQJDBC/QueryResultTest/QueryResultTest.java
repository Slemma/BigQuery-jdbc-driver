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
 * @author Gunics Balazs
 */
package BQJDBC.QueryResultTest;

import org.junit.Assert;
import net.starschema.clouddb.jdbc.BQConnection;
import net.starschema.clouddb.jdbc.BQSupportFuncts;
import net.starschema.clouddb.jdbc.BQSupportMethods;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
//import net.starschema.clouddb.bqjdbc.logging.Logger;

/**
 * This Junit tests if the query results return as expected
 * 
 * @author Horv�th Attila
 * @author Gunics Balazs
 */
public class QueryResultTest {
    
    private static java.sql.Connection con = null;
    //Logger logger = new Logger(QueryResultTest.class.getName());
    Logger logger = Logger.getLogger(QueryResultTest.class.getName());
    
    /**
     * Compares two String[][]
     * 
     * @param expected
     * @param reality
     * @return true if they are equal false if not
     */
    private boolean comparer(String[][] expected, String[][] reality) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                if (expected[i][j].toString().equals(reality[i][j]) == false) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Makes a new Bigquery Connection to Hardcoded URL and gives back the
     * Connection to static con member.
     */
    @Before
    public void NewConnection() {
        try {
            if (QueryResultTest.con == null || !QueryResultTest.con.isValid(0)) {
                
                this.logger.info("Testing the JDBC driver");
                try {
                    Class.forName("net.starschema.clouddb.jdbc.BQDriver");

                    Properties properties = BQSupportFuncts.readFromPropFile("installedaccount.properties");
                    properties.setProperty("transformQuery","true");

                    QueryResultTest.con = DriverManager
                            .getConnection(
                                    BQSupportFuncts
                                            .constructUrlFromPropertiesFile(properties),
                                    properties);
                }
                catch (Exception e) {
                    this.logger.error("Error in connection" + e.toString());
                    Assert.fail("General Exception:" + e.toString());
                }
                this.logger.info(((BQConnection) QueryResultTest.con)
                        .getURLPART());
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
          
//    @Test
//    public void QueryResultTest01() {
//        final String sql = "SELECT TOP(word, 10), COUNT(*) FROM publicdata:samples.shakespeare";
//        final String description = "The top 10 word from shakespeare #TOP #COUNT";
//        String[][] expectation = new String[][] {
////                {"you", "yet", "would", "world", "without", "with", "your", "young",
////                    "words", "word"},
////                { "42", "42", "42", "42", "42", "42", "41", "41", "41", "41" } };
//                { "you", "yet", "would", "world", "without", "with", "will",
//                        "why", "whose", "whom" },
//                { "42", "42", "42", "42", "42", "42", "42", "42", "42", "42" } };
//
//        this.logger.info("Test number: 01");
//        this.logger.info("Running query:" + sql);
//
//        java.sql.ResultSet Result = null;
//        try {
//            Result = QueryResultTest.con.createStatement().executeQuery(sql);
//        }
//        catch (SQLException e) {
//            this.logger.error("SQLexception" + e.toString());
//            Assert.fail("SQLException" + e.toString());
//        }
//        Assert.assertNotNull(Result);
//
//        this.logger.debug(description);
//        HelperFunctions.printer(expectation);
//        try {
//            Assert.assertTrue(
//                    "Comparing failed in the String[][] array",
//                    this.comparer(expectation,
//                            BQSupportMethods.GetQueryResult(Result)));
//        }
//        catch (SQLException e) {
//            this.logger.error("SQLexception" + e.toString());
//            Assert.fail(e.toString());
//        }
//    }
    
    @Test
    public void QueryResultTest02() {
        final String sql = "SELECT corpus FROM publicdata:samples.shakespeare GROUP BY corpus ORDER BY corpus LIMIT 5";
        final String description = "The book names of shakespeare #GROUP_BY #ORDER_BY";
        String[][] expectation = new String[][] { { "1kinghenryiv",
                "1kinghenryvi", "2kinghenryiv", "2kinghenryvi", "3kinghenryvi" } };
        this.logger.info("Test number: 02");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
            HelperFunctions.printer(expectation);
        try {
            Assert.assertTrue(
                    "Comparing failed in the String[][] array",
                    this.comparer(expectation,
                            BQSupportMethods.GetQueryResult(Result)));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }

    //TODO: FIX TEST
//    @Test
//    public void QueryResultTest03() {
//        final String sql = "SELECT COUNT(DISTINCT web100_log_entry.connection_spec.remote_ip) AS num_clients FROM [guid754187384106:m_lab.2010_01] "
//                + "WHERE IS_EXPLICITLY_DEFINED(web100_log_entry.connection_spec.remote_ip) AND IS_EXPLICITLY_DEFINED(web100_log_entry.log_time) "
//                + "AND web100_log_entry.log_time > 1262304000 AND web100_log_entry.log_time < 1262476800";
//        final String description = "A sample query from google, but we don't have Access for the query table #ERROR #accessDenied #403";
//
//        this.logger.info("Test number: 03");
//        this.logger.info("Running query:" + sql);
//        this.logger.debug(description);
//        java.sql.ResultSet result = null;
//        try {
//            Statement stmt = con.createStatement();
//            //stmt.setQueryTimeout(60);
//            result = stmt.executeQuery(sql);
//        }
//        catch (SQLException e) {
//            this.logger.debug("SQLexception" + e.toString());
//            //fail("SQLException" + e.toString());
//            Assert.assertTrue(e
//                    .getCause()
//                    .toString()
//                    .contains(
//                            "Access Denied: Table measurement-lab:m_lab.2010_01: QUERY_TABLE"));
//        }
//        logger.info("QueryResult03 result is" + result.toString());
//    }
    
    @Test
    public void QueryResultTest04() {
        final String sql = "SELECT corpus FROM publicdata:samples.shakespeare WHERE LOWER(word)=\"lord\" GROUP BY corpus ORDER BY corpus DESC LIMIT 5;";
        final String description = "A query which gets 5 of Shakespeare were the word lord is present";
        String[][] expectation = new String[][] { { "winterstale", "various",
                "twogentlemenofverona", "twelfthnight", "troilusandcressida" } };
        
        this.logger.info("Test number: 04");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
            HelperFunctions.printer(expectation);
        try {
            Assert.assertTrue(
                    "Comparing failed in the String[][] array",
                    this.comparer(expectation,
                            BQSupportMethods.GetQueryResult(Result)));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void QueryResultTest05() {
        final String sql = "SELECT word FROM publicdata:samples.shakespeare WHERE word='huzzah' ;";
        final String description = "The word \"huzzah\" NOTE: It doesn't appear in any any book, so it returns with a null #WHERE";
        
        this.logger.info("Test number: 05");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
            this.logger.debug(Result.getMetaData().getColumnCount());
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
        try {
            if(Result.getType() != ResultSet.TYPE_FORWARD_ONLY) 
                Assert.assertFalse(Result.first());
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void QueryResultTest06() {
        final String sql = "SELECT corpus_date,SUM(word_count) FROM publicdata:samples.shakespeare GROUP BY corpus_date ORDER BY corpus_date DESC LIMIT 5;";
        final String description = "A query which gets how many words Shapespeare wrote in a year (5 years displayed descending)";
        String[][] expectation = new String[][] {
                { "1612", "1611", "1610", "1609", "1608" },
                { "26265", "17593", "26181", "57073", "19846" } };
        
        this.logger.info("Test number: 06");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
            HelperFunctions.printer(expectation);
        try {
            Assert.assertTrue(
                    "Comparing failed in the String[][] array",
                    this.comparer(expectation,
                            BQSupportMethods.GetQueryResult(Result)));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void QueryResultTest07() {
        final String sql = "SELECT corpus, SUM(word_count) as w_c FROM publicdata:samples.shakespeare GROUP BY corpus HAVING w_c > 20000 ORDER BY w_c ASC LIMIT 5;";
        final String description = "A query which gets Shakespeare were there are more words then 20000 (only 5 is displayed ascending)";
        String[][] expectation = new String[][] {
                { "juliuscaesar", "twelfthnight", "titusandronicus",
                        "kingjohn", "tamingoftheshrew" },
                { "21052", "21633", "21911", "21983", "22358" } };
        
        this.logger.info("Test number: 07");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
        
            HelperFunctions.printer(expectation);
        
        try {
            Assert.assertTrue(
                    "Comparing failed in the String[][] array",
                    this.comparer(expectation,
                            BQSupportMethods.GetQueryResult(Result)));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void QueryResultTest08() {
        final String sql = "SELECT corpus, MAX(word_count) as m, word FROM publicdata:samples.shakespeare GROUP BY corpus,word ORDER BY m DESC LIMIT 5;";
        final String description = "A query which gets those Shakespeare with the most common word ordered by count descending (only 5 is displayed)";
        String[][] expectation = new String[][] {
                { "hamlet", "coriolanus", "kinghenryv", "2kinghenryiv",
                        "kingrichardiii" },
                { "995", "942", "937", "894", "848" },
                { "the", "the", "the", "the", "the" } };
        
        this.logger.info("Test number: 08");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
       
            HelperFunctions.printer(expectation);
        
        try {
            Assert.assertTrue(
                    "Comparing failed in the String[][] array",
                    this.comparer(expectation,
                            BQSupportMethods.GetQueryResult(Result)));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void QueryResultTest09() {
        final String sql = "SELECT corpus, corpus_date FROM publicdata:samples.shakespeare GROUP BY corpus, corpus_date ORDER BY corpus_date DESC LIMIT 3;";
        final String description = "Shakespeare's 3 latest";
        String[][] expectation = new String[][] {
                { "kinghenryviii", "tempest", "winterstale" },
                { "1612", "1611", "1610" } };
        
        this.logger.info("Test number: 09");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Result = QueryResultTest.con.createStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        
        this.logger.debug(description);
      
            HelperFunctions.printer(expectation);
        
        try {
            Assert.assertTrue(
                    "Comparing failed in the String[][] array",
                    this.comparer(expectation,
                            BQSupportMethods.GetQueryResult(Result)));
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void QueryResultTest10() {
        final String sql = "SELECT corpus_date,SUM(word_count) FROM publicdata:samples.shakespeare GROUP BY corpus_date ORDER BY corpus_date DESC LIMIT 5;";
        // final String description =
        // "A query which gets how many words Shapespeare wrote in a year (5 years displayed descending)";
        /*
         * String[][] expectation = new String[][] {
         * {"1612","1611","1610","1609","1608"},
         * {"26265","17593","26181","57073","19846"} };
         */
        
        this.logger.info("Test number: 10");
        this.logger.info("Running query:" + sql);
        
        try {
            Statement stmt = QueryResultTest.con.createStatement();
            stmt.setQueryTimeout(1);
            stmt.executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.info("SQLexception" + e.toString());
            Assert.assertTrue(true);
        }
        
    }
    
    @Test
    public void QueryResultTest11() {
        
        this.logger.info("Test number: 10");
        this.logger.info("Testing databesmetadata ... getSchemas() ");
        
        try {
            QueryResultTest.con.getMetaData().getSchemas();
        }
        catch (SQLException e) {
            this.logger.warn("SQLexception" + e.toString());
            Assert.fail("schema problem");
        }
        
    }
    
    @Test
    public void QueryResultTest12() {
        int limitNum = 40000;
        final String sql = "SELECT weight_pounds  FROM publicdata:samples.natality LIMIT "  + limitNum;
       
        this.logger.info("Test number: 12");
        this.logger.info("Running query:" + sql);
        
        java.sql.ResultSet Result = null;
        try {
            Statement stm = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stm.setFetchSize(1000);
            Result = stm.executeQuery(sql);
        }
        catch (SQLException e) {
            this.logger.error("SQLexception" + e.toString());
            Assert.fail("SQLException" + e.toString());
        }
        Assert.assertNotNull(Result);
        try {/*
            int j = 0;
            for (int i = 0; i < limitNum-1; i++) {
                if(i%1000 == 0) {
                    logger.debug("fetched 1k for the " + ++j + ". time");
                }
                Assert.assertTrue(Result.next());  
            }*/
            Result.absolute(limitNum);
            Assert.assertTrue(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
