# How to use the jdbc driver with iSQL #
You can obtain iSQL from: http://isql.sourceforge.net/

To connect to Google BigQuery, first create a new entry in _Database Service_ .

  1. Go to _File_ -> _New_ -> _Database Service..._
    * Check **Create a new JDBC connection** When Completed hit _Next_
> > ![http://getcloudkeeper.com/jdbc/images/isql_public01.png](http://getcloudkeeper.com/jdbc/images/isql_public01.png)
  1. Fill in Database Connect information as following:
    * **Service name** - Name of the connection (user supplied)
    * **JDBC Driver** - _net.starschema.clouddb.jdbc.BQDriver_
    * **JDBC URL** - [Construction of the JDBC URL scheme is described here](JDBCURL.md)
    * **Username** and **Password** depends from the [JDBC URL](JDBCURL.md)
> > ![http://getcloudkeeper.com/jdbc/images/isql_public02.png](http://getcloudkeeper.com/jdbc/images/isql_public02.png)
    * Confirm settings by clicking _Next_
  1. Configure the classpath
    * Hit the button at the top-left of the wizard, and select the jar file.
> > ![http://getcloudkeeper.com/jdbc/images/isql_public03.png](http://getcloudkeeper.com/jdbc/images/isql_public03.png)
    * After that hit _Next_
  1. On the next page everything should be empty except: **Encrypt Cached Credentials**
> > ![http://getcloudkeeper.com/jdbc/images/isql_public04.png](http://getcloudkeeper.com/jdbc/images/isql_public04.png)
      * You could enable **Stack traces** to catch nasty glitches in our driver. However, there is a built in logger facility for debugging purpose.
      1. Hit _Next_
      1. Select no options, hit _Next_
  1. Check **Version 3.0+** and click _Finish_
> > ![http://getcloudkeeper.com/jdbc/images/isql_public05.png](http://getcloudkeeper.com/jdbc/images/isql_public05.png)

From here you can connect to BigQuery with _Services_  -> _Your Connection name_

You can run a query by pressing **CTRL+ENTER**

![http://getcloudkeeper.com/jdbc/images/isql_public06.png](http://getcloudkeeper.com/jdbc/images/isql_public06.png)