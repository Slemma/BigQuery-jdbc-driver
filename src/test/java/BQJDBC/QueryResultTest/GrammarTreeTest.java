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
 * 
 * This class Tests the substitution of * in an sql select statement
 *    @author Horvath Attila
 */

package BQJDBC.QueryResultTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.slemma.jdbc.BQSQLException;
import com.slemma.jdbc.BQSupportFuncts;
import com.slemma.jdbc.list.CallContainer;
import com.slemma.jdbc.list.SQLCleaner;
import com.slemma.jdbc.list.SelectStatement;
import com.slemma.jdbc.list.TreeBuilder;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.BasicConfigurator;

public class GrammarTreeTest {
    
    //Not working for public datasets since bigquery api functions don't treat public datasets as the users own it doesn't list them with its getdatasets function
    //PUBLICDATARA NEM M�K�DIK, MERT A GETCOLUMNS FUNCI� NEM KERES A PUBLIC DATASETEKBEN, MERT AZ APIBAN L�V� H�V�SOK NEM VESZIK SAJ�T A PROJECTHEZ TARTOZ� T�BL�NAK
    static String string =
            //"select ARTICLE_LOOKUP.ARTICLE_CODE from ARTICLE_LOOKUP";
    		//SELECT ARTICLE_CODE,  `efashion`.ARTICLE_LOOKUP.ARTICLE_CODE,`efashion`.ARTICLE_LOOKUP.ARTICLE_LABEL, `efashion`.ARTICLE_LOOKUP_CRITERIA.ARTICLE_CODE, `efashion`.ARTICLE_LOOKUP_CRITERIA.ID FROM `efashion`.ARTICLE_LOOKUP_CRITERIA, `efashion`.ARTICLE_COLOR_LOOKUP JOIN `efashion`.ARTICLE_LOOKUP ON `efashion`.ARTICLE_COLOR_LOOKUP.ARTICLE_CODE = `efashion`.ARTICLE_LOOKUP.ARTICLE_CODE";
     "SELECT \"ARTICLE_LOOKUP\".\"ARTICLE_CODE\", \"ARTICLE_LOOKUP\".\"ARTICLE_LABEL\", \"ARTICLE_COLOR_LOOKUP\".\"COLOR_CODE\", \"ARTICLE_COLOR_LOOKUP\".\"SALE_PRICE\"\r\n" + 
            " FROM   (\"AGG_YR_QT_RN_ST_LN_CA_SR\" \"AGG_YR_QT_RN_ST_LN_CA_SR\" INNER JOIN \"ARTICLE_COLOR_LOOKUP\" \"ARTICLE_COLOR_LOOKUP\" ON \"AGG_YR_QT_RN_ST_LN_CA_SR\".\"CATEGORY\"=\"ARTICLE_COLOR_LOOKUP\".\"CATEGORY\") \r\n" + 
            "        INNER JOIN \"ARTICLE_LOOKUP\" \"ARTICLE_LOOKUP\" ON \"AGG_YR_QT_RN_ST_LN_CA_SR\".\"CATEGORY\"=\"ARTICLE_LOOKUP\".\"CATEGORY\"";        
    
    /**
     * @param args
     * @throws BQSQLException 
     * @throws RecognitionException 
     */   
    public static void main(String[] args) throws BQSQLException, RecognitionException {
        BasicConfigurator.configure();
        
        NewConnection();
        
        TreeBuilder builder = new TreeBuilder(string, con, new CallContainer());
        SelectStatement build = null;
        try {
            build = (SelectStatement)builder.build();
            SQLCleaner.Clean(build);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.err.println(build.toPrettyString(1));
    }
    
    /**
     * Static Connection holder
     */
    public static Connection con;
    
    /**
     * Creates a new Connection to bigquery with the jdbc driver
     */
    public static void NewConnection() {
        try {
            if (GrammarTreeTest.con == null || !GrammarTreeTest.con.isValid(0)) {
                
                try {
                    Class.forName("com.slemma.jdbc.BQDriver");
                    GrammarTreeTest.con = DriverManager
                            .getConnection(
                                    BQSupportFuncts
                                            .constructUrlFromPropertiesFile(BQSupportFuncts
                                                    .readFromPropFile("installedaccount.properties")),
                                    BQSupportFuncts
                                            .readFromPropFile("installedaccount.properties"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
