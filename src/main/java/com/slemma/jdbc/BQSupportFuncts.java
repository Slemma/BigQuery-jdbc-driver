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
 * <p>
 * This class implements functions such as connecting to Bigquery, Checking out
 * the results and displaying them on console
 * </p>
 */

package com.slemma.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import com.google.api.services.bigquery.model.*;
import org.apache.log4j.Logger;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.model.DatasetList.Datasets;
import com.google.api.services.bigquery.model.ProjectList.Projects;
import com.google.api.services.bigquery.model.TableList.Tables;

/**
 * This class contains static methods for interacting with BigQuery
 * 
 * @author Gunics Bal�zs, Horv�th Attila
 * 
 */
public class BQSupportFuncts {
    /** Google public data project ID */
    static final String PUBLIC_PROJECT_ID = "publicdata";
    static final String DOT_SUBSTITUTE = "_";
    static final String COLON_SUBSTITUTE = "__";

    /** log4j.Logger instance */
    // static Logger logger = new Logger(BQSupportFuncts.class.getName());
    static Logger logger = Logger.getLogger(BQSupportFuncts.class.getName());

    public static String EncodeProjectId(String projectId) {
        return projectId.replace(".", DOT_SUBSTITUTE).replace(":", COLON_SUBSTITUTE);
    }

    public static String DecodeProjectId(String projectId) {
        return projectId.replace(COLON_SUBSTITUTE, ":").replace(DOT_SUBSTITUTE, ".");
    }

    /**
     * Constructs a valid BigQuery JDBC driver URL from the specified properties
     * File
     * 
     * @param properties
     * @return a valid BigQuery JDBC driver URL or null if it fails to load
     * @throws UnsupportedEncodingException
     */
    public static String constructUrlFromPropertiesFile(Properties properties)
            throws UnsupportedEncodingException {
        String ProjectId = properties.getProperty("projectid");
        logger.debug("projectId is: " + ProjectId);
        String User = properties.getProperty("user");
        String Password = properties.getProperty("password");
        String transformQuery = null;      
            
        transformQuery = properties.getProperty("transformquery");
        
        String forreturn = "";
        
        if (properties.getProperty("type").equals("installed")) {
            if (User != null && Password != null && ProjectId != null) {
                forreturn = BQDriver.getURLPrefix()
                        + URLEncoder.encode(ProjectId, "UTF-8");
            }
            else {
                return null;
            }
        }
        else
            if (properties.getProperty("type").equals("service")) {
                if (User != null && Password != null && ProjectId != null) {
                    forreturn = BQDriver.getURLPrefix()
                            + URLEncoder.encode(ProjectId, "UTF-8")
                            + "?withServiceAccount=true";
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        
        if(transformQuery != null){
            return forreturn + "?transformQuery="+transformQuery;
        }
        else return forreturn;
    }
    
    /**
     * Displays results of a Query on the console
     * 
     * @param bigquery
     *            A valid authorized Bigquery instance
     * @param projectId
     *            The exact Id of the Project associated with the completed
     *            BigQuery job
     * @param completedJob
     *            The Job reference of the completed job
     * @throws IOException
     *             <p>
     *             If the request fails to get the QueryResult of the specified
     *             job in the given ProjectId
     *             </p>
     */
    public static void displayQueryResults(Bigquery bigquery, String projectId,
            Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery
                .jobs()
                .getQueryResults(projectId,
                        completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();
        System.out.print("\nQuery Results:\n------------\n");
        for (TableRow row : rows) {
            for (TableCell field : row.getF()) {
                System.out.printf("%-20s", field.getV());
            }
            System.out.println();
        }
    }
    
    /**
     * Return a list of Projects which contains the String catalogname
     * 
     * @param catalogName
     *            The String which the id of the result Projects must contain
     * @param Connection
     *            A valid BQConnection instance
     * @return a list of Projects which contains the String catalogname
     * @throws IOException
     *             <p>
     *             if the initialization of requesting the list of all Projects
     *             (to be sorted from) fails
     *             </p>
     */
    private static List<Projects> getCatalogs(String catalogName,
            BQConnection Connection) throws IOException {
        logger.debug("Function call getCatalogs catalogName: " + 
            (catalogName != null ? catalogName : "null") );
        List<Projects> projects = Connection.getBigquery().projects().list()
                .execute().getProjects();        
        
        if (projects != null && projects.size() != 0) {             //we got projects!
            for (Projects projects2 : projects) {                
                projects2.setId(projects2.getId());
                //updating the reference too
                ProjectReference projRef = projects2.getProjectReference();
                projRef.setProjectId(projRef.getProjectId());
                projects2.setProjectReference(projRef);
            }
            if (catalogName != null) {
                List<Projects> ProjectsSearch = new ArrayList<Projects>();
                for (Projects project : projects) {
                    if (project.getId().contains(catalogName)) {
                        ProjectsSearch.add(project);
                    }
                }
                if (ProjectsSearch.size() == 0) {
                    return null;
                }
                else {
                    return ProjectsSearch;
                }
            }
            else {
                return projects;
            }
        }
        else {
            return new ArrayList<Projects>();
        }
    }
    
    /**
     * Parses a (instance of dataset).getId() and gives back the id only for the
     * dataset
     * 
     * @param getId
     *            (instance of dataset).getId()
     * @return the id only for the dataset
     */
    public static String getDatasetIdFromDatasetDotGetId(String getId) {
        /*logger.debug("Function call getDatasetIdFromDatasetDotGetId" + getId +
                "return is: " + getId.substring(getId.lastIndexOf(":") + 1));*/
        return getId.substring(getId.lastIndexOf(":") + 1);
    }
    
    /**
     * Parses a (instance of table).getid() and gives back the id only for the
     * dataset
     * 
     * @param getId
     *            (instance of table).getid()
     * @return the id only for the dataset
     */
    public static String getDatasetIdFromTableDotGetId(String getId) {
        /*logger.debug("Function call getDatasetIdFromTableDotGetId" + getId +
                "return is: " + getId.substring(getId.lastIndexOf(":") + 1, getId.lastIndexOf(".")));*/
        return getId.substring(getId.lastIndexOf(":") + 1, getId.lastIndexOf("."));
    }
    
    /**
     * Returns a list of Datasets, which are associated with the Project which's
     * id is exactly ProjectId, and their name matches datasetnamepattern
     * 
     * @param datasetname
     *            The String the dataset's id must contain
     * @param projectId
     *            The Id of the Project the dataset is preferably contained in
     * @param connection
     *            A valid BQConnection Instance
     * @return a list of Datasets, which are associated with the Project which's
     *         id is exactly ProjectId, and their name contains datasetname
     * @throws IOException
     *             <p>
     *             if the request to get Projects that match the given ProjectId
     *             fails
     *             </p>
     */
    private static List<Datasets> getDatasets(String datasetname,
            String projectId, BQConnection connection) throws IOException {
        logger.debug("function call getDatasets, " + 
            "datasetName: " + (datasetname != null ? datasetname : "null") +
            ", projectId: " + (projectId != null ? projectId : "null"));
        List<Datasets> datasetcontainer = connection.getBigquery().datasets()
                .list(projectId).execute().getDatasets();
        
        if (datasetcontainer != null && datasetcontainer.size() != 0) {
            if (datasetname != null) {
                List<Datasets> datasetsSearch = new ArrayList<Datasets>();
                for (Datasets in : datasetcontainer) {
                    if (matchPattern(getDatasetIdFromDatasetDotGetId(in.getId()),
                            datasetname)) {
                        datasetsSearch.add(in);
                    }
                }
                if (datasetsSearch.size() == 0) {
                    return new ArrayList<Datasets>();
                }
                else {
                    return datasetsSearch;
                }
            }
            else {
                return datasetcontainer;
            }
        }
        else {
            return new ArrayList<Datasets>();
        }
    }
    
    /**
     * Parses a (instance of dataset)/(instance of table).getid() and gives back
     * the id only for the Project
     * 
     * @param getId
     *            (instance of dataset)/(instance of table).getid()
     * @returnthe the id only for the Project
     */
    public static String getProjectIdFromAnyGetId(String getId) {
        int pos = getId.indexOf(":");        // The first appearance of ":"
		if (getId.indexOf(":", pos + 1) != -1) {
         // If there's a second ":" we'll use it 
         // (there must be a second ":" !!)
            pos = getId.indexOf(":", pos + 1);  
        }
        String ret = getId.substring(0, pos); // Cutting out the project id
        return ret;
    }
    
    /**
     * Returns the result of a completed query
     * 
     * @param bigquery
     *            Instance of authorized Bigquery client
     * @param projectId
     *            The id of the Project the completed job was run in
     * @param completedJob
     *            The Job instance of the completed job
     * @return the result of a completed query specified by projectId and
     *         completedJob
     * @throws IOException
     *             <p>
     *             if the request to get QueryResults specified by the given
     *             ProjectId and Job id fails
     *             </p>
     */
    public static GetQueryResultsResponse getQueryResults(Bigquery bigquery,
            String projectId, Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId,
                        completedJob.getJobReference().getJobId()).execute();
        long totalRows = queryResult.getTotalRows().longValue();
        if(totalRows == 0){
            return queryResult;
        }
        while( totalRows  > (long)queryResult.getRows().size() ) {
            queryResult.getRows().addAll(
                bigquery.jobs()
                    .getQueryResults(projectId,
                            completedJob.getJobReference().getJobId())
                    .setStartIndex(BigInteger.valueOf((long)queryResult.getRows().size()) )
                    .execute()
                    .getRows());           
        }
        return queryResult;
    }
    
    /**
     * Returns the result of a completed query
     * 
     * @param bigquery
     *            Instance of authorized Bigquery client
     * @param projectId
     *            The id of the Project the completed job was run in
     * @param completedJob
     *            The Job instance of the completed job
     * @return the result of a completed query specified by projectId and
     *         completedJob
     * @throws IOException
     *             <p>
     *             if the request to get QueryResults specified by the given
     *             ProjectId and Job id fails
     *             </p>
     */
    public static GetQueryResultsResponse getQueryResultsDivided(Bigquery bigquery,
            String projectId, Job completedJob, BigInteger startAtRow, int fetchCount) throws IOException {
        GetQueryResultsResponse queryResult;
        queryResult = bigquery.jobs()
                .getQueryResults(projectId,
                        completedJob.getJobReference().getJobId())
                        .setStartIndex(startAtRow)
                        .setMaxResults((long) fetchCount).execute();
        return queryResult;
    }
    /**
     * Returns the status of a job
     * 
     * @param myjob
     *            Instance of Job
     * @param bigquery
     *            Instance of authorized Bigquery client
     * @param projectId
     *            The id of the Project the job is contained in
     * @return the status of the job
     * @throws IOException
     *             <p>
     *             if the request to get the job specified by myjob and
     *             projectId fails
     *             </p>
     */
    public static String getQueryState(Job myjob, Bigquery bigquery,
            String projectId) throws IOException {
        Job pollJob = bigquery.jobs()
                .get(projectId, myjob.getJobReference().getJobId()).execute();
        BQSupportFuncts.logger.info("Job status: "
                + pollJob.getStatus().getState()
                + " ; "
                + pollJob.getJobReference().getJobId()
                + " ; "
                + (System.currentTimeMillis() - pollJob.getStatistics()
                        .getStartTime()));
        return pollJob.getStatus().getState();
    }
    
    /**
     * Parses a (instance of table).getid() and gives back the id only for the
     * table
     * 
     * @param getId
     *            (instance of table).getid()
     * @return the id only for the table
     */
    public static String getTableIdFromTableDotGetId(String getId) {
        return getId.substring(getId.lastIndexOf(".") + 1);
    }
    
    /**
     * Returns a list of Tables which's id matches TablenamePattern and are
     * exactly in the given Project and Dataset
     * 
     * @param tableNamePattern
     *            String that the tableid must contain
     * @param projectId
     *            The exact Id of the Project that the tables must be in
     * @param datasetId
     *            The exact Id of the Dataset that the tables must be in
     * @param connection
     *            Instance of a valid BQConnection
     * @return a list of Tables which's id contains Tablename and are exactly in
     *         the given Project and Dataset
     * @throws IOException
     *             <p>
     *             if the request to get all tables (to sort from) specified by
     *             ProjectId, DatasetId fails
     *             </p>
     */
    public static List<Tables> getTables(String tableNamePattern,
            String projectId, String datasetId, BQConnection connection)
            throws IOException {        
        logger.debug("Function call getTables : " + 
            "tableNamePattern: " + (tableNamePattern != null ? tableNamePattern : "null") +
            ", projectId: " + (projectId != null ? projectId : "null") + 
            ", datasetID:" + (datasetId != null ? datasetId : "null") +
            "connection");
        List<Tables> tables = connection.getBigquery().tables()
                .list(projectId, datasetId).execute().getTables();
        if (tables != null && tables.size() != 0) {
            if (tableNamePattern != null) {
                List<Tables> tablesSearch = new ArrayList<Tables>();
                for (Tables in : tables) {
                    if (matchPattern(getTableIdFromTableDotGetId(in.getId()),
                            tableNamePattern)) {
                        tablesSearch.add(in);
                    }
                }
                if (tablesSearch.size() == 0) {
                    logger.debug("returning null");
                    return new ArrayList<Tables>();
                }
                else {
                    return tablesSearch;
                }
            }
            else {
                return tables;
            }
        }
        else {
            logger.debug("returning null");
            return new ArrayList<Tables>();
        }
    }

    /**
     * Gets Tables information from specific projects matching catalog,
     * tablenamepattern and datasetidpatterns
     * 
     * @param connection
     *            Valid instance of BQConnection
     * @return List of Table
     * @throws IOException
     *             <p>
     *             if the initialization of requesting the list of all Projects
     *             (to be sorted from) fails<br>
     *             if the request to get Projects that match the given ProjectId
     *             fails<br>
     *             if the request to get all tables (to sort from) specified by
     *             ProjectId, DatasetId fails<br>
     *             if the request to get table information based on ProjectId
     *             DatasetId TableId fails
     *             <p>
     */
    public static List<Table> getTables(BQConnection connection,
            String catalog, String schema, String tablename) throws IOException {
        List<Table> RET = new ArrayList<Table>();
        logger.debug("Function call getTables : " + 
                "catalog: " + (catalog != null ? catalog : "null") +
                ", schema: " + (schema != null ? schema : "null") + 
                ", tablename:" + (tablename != null ? tablename : "null") +
                "connection");

        List<String> projectIds = new ArrayList<String>();

        if (catalog == null) {
            //getting the projects for this connection
            List<Projects> projects = BQSupportFuncts.getCatalogs(catalog,
                    connection);
            for (Projects proj : projects) {
                projectIds.add(proj.getId());
//                if (proj.getId().contains("."))
//                    projectIds.add(proj.getNumericId().toString());
//                else
//                    projectIds.add(proj.getId());
            }
        } else {
            projectIds.add(catalog);
        }


        if (projectIds.size() != 0) {
            for (String projId : projectIds) {
                List<Datasets> datasetlist = null;
                datasetlist = BQSupportFuncts.getDatasets(schema, projId,
                        connection);
                if (datasetlist != null && datasetlist.size() != 0) {
                    for (Datasets dataset : datasetlist) {
                        List<Tables> tables = null;
                        
                        tables = BQSupportFuncts.getTables(tablename, projId,
                                BQSupportFuncts
                                        .getDatasetIdFromDatasetDotGetId(dataset
                                                .getId()), connection);
                        
                        if (tables != null && tables.size() != 0) {
                            for (Tables table : tables) {
                                //TODO replace projID __ -> : _ -> .
                                String datasetString = BQSupportFuncts
                                        .getDatasetIdFromDatasetDotGetId(dataset.getId());
                                String tableString = BQSupportFuncts
                                        .getTableIdFromTableDotGetId(table.getId());
                                logger.debug("Calling connection.getBigquery().tables() " +
                                        "dataset is: " + datasetString +
                                        ", table is: " + tableString +
                                        ", project is: " + projId);
                                Table tbl = connection.getBigquery().tables()
                                        .get(projId, datasetString, tableString)
                                        .execute();
                                if (tbl != null) {
                                    RET.add(tbl);
                                }
                            }
                        }
                    }
                }
            }
            if (RET.size() == 0) {
                return new ArrayList<Table>();
            }
            else {
                return RET;
            }
        }
        else {
            return new ArrayList<Table>();
        }
    }
    
    /**
     * <br>
     * Some DatabaseMetaData methods take arguments that are String patterns.
     * These arguments all have names such as fooPattern. Within a pattern
     * String, "%" means match any substring of 0 or more characters, and "_"
     * means match any one character. Only metadata entries matching the search
     * pattern are returned. If a search pattern argument is set to null, that
     * argument's criterion will be dropped from the search.
     * 
     * Checks if the input String matches the pattern string which may contain
     * %, which means it can be any character
     * 
     * @param input
     * @param pattern
     * @return true if matches, false if not
     */
    public static boolean matchPattern(String input, String pattern) {
        if (pattern == null) {
            return true;
        }
        
        boolean regexexpression = false;
        String regexexpressionstring = null;
        
        if (pattern.contains("%")) {
            regexexpression = true;
            
            if (regexexpressionstring == null) {
                regexexpressionstring = pattern.replace("%", ".*");
            }
        }
        if (pattern.contains("_")) {
            regexexpression = true;
            
            if (regexexpressionstring == null) {
                regexexpressionstring = pattern.replace("_", ".");
            }
            else {
                regexexpressionstring = regexexpressionstring.replace("_", ".");
            }
        }
        if (regexexpression) {
            return input.matches(regexexpressionstring);
        }
        else {
            //return input.contains(pattern);
            return input.equals(pattern);
        }
    }
    
    /**
     * Convert Bigquery field type to java.sql.Types
     * 
     * @param columntype
     *            String of the Column type
     * @return java.sql.Types value of the given Columtype
     */
    public static int parseToSqlFieldType(String columntype) {
        if (columntype.equals("FLOAT")) {
            return java.sql.Types.FLOAT;
        }
        else
            if (columntype.equals("BOOLEAN")) {
                return java.sql.Types.BOOLEAN;
            }
            else
                if (columntype.equals("INTEGER")) {
                    return java.sql.Types.INTEGER;
                }
                else
                    if (columntype.equals("STRING")) {
                        return java.sql.Types.VARCHAR;
                    }
                    else
                        if (columntype.equals("BIGINT")){
                            return java.sql.Types.BIGINT;
                        }
                        else {
                            return 0;
                        }        
    }
    
    /**
     * Reads Properties File from location
     * 
     * @param filePath
     *            Location of the Properties File
     * @return Properties The Properties object made from the Properties File
     * @throws IOException
     *             if the load from file fails
     */
    public static Properties readFromPropFile(String filePath)
            throws IOException {
        // Read properties file.
        Properties properties = new Properties();
        properties.load(new FileInputStream(filePath));
        
        return properties;
    }
    
    /**
     * Starts a new query in async mode.
     * 
     * @param bigquery
     *            The bigquery instance, which is authorized
     * @param projectId
     *            The project's ID
     * @param querySql
     *            The sql query which we want to run
     * @return A JobReference which we'll use to poll the bigquery, for its
     *         state, then for its mined data.
     * @throws IOException
     *             <p>
     *             if the request for initializing or executing job fails
     *             </p>
     */
    public static Job startQuery(Bigquery bigquery, String projectId,
            String querySql) throws IOException {
        BQSupportFuncts.logger.info("Inserting Query Job: " + querySql.replace("\t","").replace("\n", " ").replace("\r", ""));
        Job job = new Job();
        JobConfiguration config = new JobConfiguration();
        JobConfigurationQuery queryConfig = new JobConfigurationQuery();
        config.setQuery(queryConfig);
        
        job.setConfiguration(config);
        queryConfig.setQuery(querySql);
        
        Insert insert = bigquery.jobs().insert(querySql, job);
        insert.setProjectId(projectId);
        return insert.execute();
    }

    public static int getJdbcTypeByTypeName(String typeName)
    {
        if (typeName.equals("FLOAT"))
        {
            return java.sql.Types.FLOAT;
        }
        else if (typeName.equals("BOOLEAN"))
        {
            return java.sql.Types.BOOLEAN;
        }
        else if (typeName.equals("INTEGER"))
        {
            return java.sql.Types.INTEGER;
        }
        else if (typeName.equals("STRING"))
        {
            return java.sql.Types.VARCHAR;
        }
        else if (typeName.equals("TIMESTAMP"))
        {
            return java.sql.Types.TIMESTAMP;
        }
        else if (typeName.equals("DATE"))
        {
            return java.sql.Types.DATE;
        }
        else if (typeName.equals("TIME"))
        {
            return java.sql.Types.TIME;
        }
        else if (typeName.equals("DATETIME"))
        {
            return java.sql.Types.TIMESTAMP;
        }
        else
        {
            return java.sql.Types.NULL;
        }
    }

    public static final SimpleDateFormat TIMESTAMP_TZ_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    public static final String DATETIME_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    public static final SimpleDateFormat DATETIME_Format = new SimpleDateFormat(DATETIME_FORMAT_STRING);
    public static final SimpleDateFormat DATE_Format = new SimpleDateFormat(DATE_FORMAT_STRING);

    public static String timestampToString(Timestamp value, String timeZone)
    {
        TIMESTAMP_TZ_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
        return TIMESTAMP_TZ_FORMAT.format(value);
    }

}
