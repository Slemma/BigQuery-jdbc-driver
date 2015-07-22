# Introduction #

RazorSQL is an SQL query tool, database browser, SQL editor, and database administration tool for Windows, Mac OS X, Linux, and Solaris.

RazorSQL has been tested on over 30 databases, can connect to databases via either JDBC or ODBC.

http://www.razorsql.com/

# Connection steps #

  1. Click on _Connections_, then click on _Add_ _Connection_ _Profile_
  1. Select the Database type for _**Other**_, then click _Continue_
![http://getcloudkeeper.com/jdbc/images/razor1_public.png](http://getcloudkeeper.com/jdbc/images/razor1_public.png)
  1. In the new window, select the **JDBC** for _CONNECTION\_TYPE_ on the left side
  1. Then fill the appropriate fields:
    * **Profile Name**
    * **Driver Location:** The place where you put the jar file. Click Browse to find it
    * **Driver Class:** _net.starschema.clouddb.jdbc.BQDriver_
    * **Login:** Your Client ID
    * **Password:** Your Client secret
    * **JDBC URL:** [Construction of the JDBC URL scheme is described here](JDBCURL.md)
  1. Click _CONNECT_ to continue

![http://getcloudkeeper.com/jdbc/images/razor2_public.png](http://getcloudkeeper.com/jdbc/images/razor2_public.png)

  1. In the new window you can type your queries in the top section, or browse your datasets and tables by clicking on the + sign next to your Project Domain on the left side

![http://getcloudkeeper.com/jdbc/images/razor3_public_v1.png](http://getcloudkeeper.com/jdbc/images/razor3_public_v1.png)