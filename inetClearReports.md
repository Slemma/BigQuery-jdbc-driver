# How to use the jdbc driver with i-net Clear  Reports #
You can obtain iSQL from: http://www.inetsoftware.de/products/clear-reports

To connect to Google BigQuery, first create a new _Datasource_

  1. Go to _File_ -> _New_ -> then select the second icon, for _datasources_ -> Click on the Datasource Manager
> > ![http://getcloudkeeper.com/jdbc/images/inet_install_1.png](http://getcloudkeeper.com/jdbc/images/inet_install_1.png)
  1. Click on _Add_ then select the user defined driver, and click _OK_:
> > ![http://getcloudkeeper.com/jdbc/images/inet_install_2.png](http://getcloudkeeper.com/jdbc/images/inet_install_2.png)
  1. Fill in Database Connect information as following:
    * **Data Source Name** - Name of the connection (user supplied)
    * **JDBC Driver** - _net.starschema.clouddb.jdbc.BQDriver_
    * **Database Class** - stays empty
    * **Library** - the path to the driver
    * **JDBC Driver URL** - [Construction of the JDBC URL scheme is described here](JDBCURL.md)
    * **Username** and **Password** depends from the [JDBC URL](JDBCURL.md)
    * **supportsParenthesesForJoin** should be false
> > ![http://getcloudkeeper.com/jdbc/images/inet_install_3.png](http://getcloudkeeper.com/jdbc/images/inet_install_3.png)
      * Confirm settings by clicking _Ok_
  1. Connection configuration is done.

> ![http://getcloudkeeper.com/jdbc/images/inet_install_4.png](http://getcloudkeeper.com/jdbc/images/inet_install_4.png)