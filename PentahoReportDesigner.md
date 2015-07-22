# Introduction #

You can obtain the complete suite from:

http://www.pentaho.com/download/
http://www.pentaho.com/

# How to use the driver #
  1. Copy the JDBC drivers .jar file to the required location.
    * You can get help from [Pentaho InfoCenter](http://infocenter.pentaho.com/help/index.jsp?topic=%2Fadmin_guide%2Fconcept_adding_a_jdbc_driver.html)
    * Or use the batch file we've made for it: [pentaho\_copier.bat](http://code.google.com/p/starschema-bigquery-jdbc/downloads/detail?name=pentaho_copier.bat&can=2&q=)
  1. Start the program, then select the **Report wizard** either from the welcome screen, or from **File** > **Report wizard**
    * Select a template you like, then click _Next_
> > > ![http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_1.png](http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_1.png)
    * Select the default **JDBC** connection, and delete it by clicking the **[X](X.md)** icon on the top right.
    * Click on the **[+]**, select the **JDBC**. Then either select the previously configured, or create a new one, by clicking on the **(+)**
      * Select **Generic database** as **Connection Type**
      * Fill the required informations.
        * **Connection name**: User given
        * For the others: [JDBC URL](JDBCURL.md)
      * Click on _Test_ to  make sure you did everything good. Then click _OK_
> > > > ![http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_2.png](http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_2.png)
    * Select or Create a new Query at **Available Queries**
      * Type in your query
      * You can preview the result by clicking on the Preview
    * Hit _OK_

> > > ![http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_3.png](http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_3.png)
  1. Select the items you want to display and the groups

> > ![http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_4.png](http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_4.png)
    * With the **Preview** you can check the output of the report.
> > > ![http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_5.png](http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_5.png)