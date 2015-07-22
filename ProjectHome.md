Starschema's high performance **Google BigQuery JDBC Driver** provides methods for querying and updating data in a Google BigQuery database.

## Features ##

  * The driver is written purely in java, thus it does not require any native libraries or programs. Runs on all platforms where java is available.
  * Support both server and OAuth2 authentication methods
  * Full support of metadata handling, e.g.: catalogs-projects, schema-dataset, tables and table definitions
  * Synchronous execution
  * [Query Transformation](QueryTransformationEngine.md) for compatibility between BigQuery and other tools

## Usage ##

  * ExampleCode: how to connect and execute queries against BigQuery
  * How to set [JDBC URL](JDBCURL.md) from applications
  * How to use from 3rd party software
    * [iSQL](iSQL.md)
    * [SquirreL SQL](SQuirreLSQL.md)
    * [iReport/Jasper Reports](JasperReports.md)
    * [Pentaho](PentahoReportDesigner.md)
    * [SAP Crystal Reports](SAPCrystalReports.md)
    * [BiRT](eclipseBiRT.md)
    * [RazorSQL](RazorSQL.md)
  * [Javadoc API documents](http://www.starschema.net/clouddb/javadoc/)
  * [Enable logging](Logger.md)

## Starschema's Google BigQuery JDBC driver in action ##

SQuirreL SQL viewer using BigQuery JDBC driver:

![http://www.starschema.net/images/clouddb/squirel.png](http://www.starschema.net/images/clouddb/squirel.png)

For any type of questions or enquiries please contact sales at starschema.net. If you would like to contribute, send your patches/pull requests to one of the project owners.