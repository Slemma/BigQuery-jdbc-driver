# Introduction #

iReport is the free, open source report designer for JasperReports. Create very sophisticated layouts containing charts, images, subreports, crosstabs and much more directly from your BigQuery data. Access data through this JDBC driver and publish reports as PDF, RTF, XML, XLS, CSV, HTML, XHTML, text, DOCX, or OpenOffice.

http://jasperforge.org/projects/ireport
http://jasperforge.org/

# Connection steps for executing queries #
  1. Click on the _Databases_, then right click on _Drivers_, select **New Driver...**
    * Click on _Add..._ then select the downloaded driver
    * Click on _Find_ it will search for the Drivers in the package. If everything went fine, **net.starschema.clouddb.jdbc.BQDriver** is displayed.
    * Name your connection
    * Hit _OK_
  1. Right Click on the Driver we just made then select _Connect Using.._
    * **Username** and **Password** depends from the [JDBC URL](JDBCURL.md) Confirm settings by clicking _Next_
    * **JDBC URL** - [Construction of the JDBC URL scheme is described here](JDBCURL.md)
  1. Right click on the connection that been made, and select _Execute Command..._
  1. Write your Query then run it with the button or **Ctrl+Shift+E**

The following figures illustrate how to setup and configure iReport with Google BigQuery thru JDBC.

![http://getcloudkeeper.com/jdbc/images/ireport_setup_v1.png](http://getcloudkeeper.com/jdbc/images/ireport_setup_v1.png)

# Connection steps for creating reports #
  1. Click on the **Tools/Options/Ireport/Classpath**, click on **add jar**, after select and add select BigQueryJDBC driver **jar file**, next click ok
  1. Click on the **Highlighted icon** on **picture 2** (named Report Datasources), click on new, after select Database JDBC Connection and click next
  1. Fill in the form as:
    * **JDBC Driver:** net.starschema.clouddb.jdbc.BQDriver
    * **Username** and **Password** depends from the [JDBC URL](JDBCURL.md) Confirm settings by clicking _Next_
    * **JDBC URL** - [Construction of the JDBC URL scheme is described here](JDBCURL.md)
  1. You can click **Test** to test the connection, after click save
  1. Click **File/New.../Report**, Select the **template** you wish to use then click **launch report wizard**
  1. Give a name to your report and select where to save it, click next
  1. Select the connection from **dropdown menu** which you have created before and **add your query** sql with Copy/Paste or Load Query then click next (Ireport will run the query to get the fields)
  1. Select the fields you wish to include in the report then move them to the right with the arrows, after click next
  1. Select from the dropdown menus that how the report will be **grouped by** then click next and finish report
  1. Costumize your report
  1. Click **Preview**

The following pictures illustrate how to create a Report:

![http://getcloudkeeper.com/jdbc/images/ireportreport01.png](http://getcloudkeeper.com/jdbc/images/ireportreport01.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport02.png](http://getcloudkeeper.com/jdbc/images/ireportreport02.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport03.png](http://getcloudkeeper.com/jdbc/images/ireportreport03.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport04.png](http://getcloudkeeper.com/jdbc/images/ireportreport04.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport05.png](http://getcloudkeeper.com/jdbc/images/ireportreport05.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport06.png](http://getcloudkeeper.com/jdbc/images/ireportreport06.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport07.png](http://getcloudkeeper.com/jdbc/images/ireportreport07.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport08.png](http://getcloudkeeper.com/jdbc/images/ireportreport08.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport09.png](http://getcloudkeeper.com/jdbc/images/ireportreport09.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport10.png](http://getcloudkeeper.com/jdbc/images/ireportreport10.png)
![http://getcloudkeeper.com/jdbc/images/ireportreport11.png](http://getcloudkeeper.com/jdbc/images/ireportreport11.png)