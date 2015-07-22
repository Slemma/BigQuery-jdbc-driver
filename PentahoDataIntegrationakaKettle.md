# Introduction #

You can obtain Kettle from:

http://kettle.pentaho.com/


# How to use the driver #
  1. Copy the JDBC drivers **-standalone.jar** file to **data-integration\libext\JDBC**
  1. Copy the content of **dependencies for kettle.zip** under
    * data-integration\libswt\win32
    * data-integration\libswt\win64
  1. You can set up a connection like in pentaho report:
    * Select **Generic database** as **Connection Type**
      * Fill the required informations.
        * **Connection name**: User given
        * For the others: [JDBC URL](JDBCURL.md)
      * Click on _Test_ to  make sure you did everything good. Then click _OK_
![http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_2.png](http://getcloudkeeper.com/jdbc/images/pentaho_report_setup_2.png)