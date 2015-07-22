# Introduction #

BiRT is an eclipse based program, you can obtain one from:
  * http://www.eclipse.org/birt/phoenix/
  * Video tutorial for basic report making:
> > http://download.eclipse.org/birt/downloads/examples/reports/2.1/tutorial/tutorial.html

# How to use #
  1. Create a new Project from **File**>**New**>**Project...**
  1. Add a new Report under it.
    * Right click on the created project **New**>**Report**
    * Choose a template etc etc.
# Update / Replace the JDBC driver #

If you want to update or replace the current version of the installed driver make sure to update the jar file in the following directory:
`BiRT\eclipse\plugins\org.eclipse.birt.report.data.oda.jdbc_4.2.0.v20120611\drivers`

If the file is deleted from here, BiRT will copy it from the defined source place.

More info can be found in [here](http://www.birt-exchange.org/org/devshare/deploying-birt-reports/169-placing-jdbc-drivers-for-birt-and-e-spreadsheet-reports/)

# Adding the JDBC Driver #
How to add the JDBC driver as a **Data Source**

  1. Select the Data Explorer View
    * it can be found under: **Window** > **Show View** > **Data Explorer**
> > > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_1.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_1.png)
  1. Right click on the **Data Sources** and select **New data source**
    * Select the **JDBC Data Source** and hit _Next >_
> > > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_2.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_2.png)
    * In order to be able to select the driver from the **Driver Class:** list the driver must be added.
> > > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_3.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_3.png)
    * Click on the _Manage Drivers..._ button
      * Click on _Add._ locate the downloaded driver.
> > > > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_3a.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_3a.png)
      * Check the **Drivers** tab, look for **net.starschema.clouddb.jdbc.BQDriver** it should appear there.
      * You can set a Display name and the URL template for it, more help for the [JDBC URL can be obtained here](JDBCURL.md)
> > > > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_3b.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_3b.png)
      * Hit _OK_ if you finished.
    * The connection should appear in the **Data Sources**
  1. Right Click on Data Sets and add

  * Select the Data Source, and **Data Set Type**, because of limitation at BigQuery, stored procedures aren't supported. So **Data Set Type** must be _**SQL Select Query**_
  * **Data Set Name:** User chosen.
  * Hit _Next >_ when done.

> > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4a.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4a.png)
  * Make the Query you want to run. Then click _Finish_
> > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4b.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4b.png)
  * Name the columns
> > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4c.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4c.png)
  * You can check your query at **Preview Results**
> > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4d.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_4d.png)
  * There are more options, but for a basic query these settings should be enough.
  * Hit _OK_ when done.
  1. Edit the design, and you're done.
> > ![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_5.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_5.png)
  1. Use the **Preview** to check the look of the result.
Here's the result of the sample query we've just run:
![http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_6.png](http://getcloudkeeper.com/jdbc/images/birt_datasource_setup_6.png)