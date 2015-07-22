# How to set up the jdbc driver in SQuirreL #

You can obtain SQuirreL SQL from: http://www.squirrelsql.org/#installation

To connect to Google BigQuery, first add the jdbc driver to Squirrel SQL.

  * For this, select the _Drivers_ tab on the left side then click on the **+** icon.
> > ![http://getcloudkeeper.com/jdbc/images/squirrel_install_1.png](http://getcloudkeeper.com/jdbc/images/squirrel_install_1.png)
  * Then fill in the needed information.
    * **Name** - Name of the connection (user supplied)
    * **Example URL** - [Construction of the JDBC URL scheme is described here](JDBCURL.md)
    * **Website URL** - http://code.google.com/p/starschema-bigquery-jdbc/
    * Switch to **Extra Class Path** tab, then click _**Add**_, select the downloaded driver.
    * Click **List Drivers** it should find the driver, and fill the **Class Name:** with: _net.starschema.clouddb.jdbc.BQDriver_
    * After these hit **OK**
> > ![http://getcloudkeeper.com/jdbc/images/squirrel_install_2.png](http://getcloudkeeper.com/jdbc/images/squirrel_install_2.png)
  * Go to the _Aliases_ tab, and hit the **+** icon.
    * **Name** - Name of the connection (user supplied)
    * **Driver** - Select the driver you just added.
    * **JDBC URL** - [Construction of the JDBC URL scheme is described here](JDBCURL.md)
    * **User Name** and **Password** depends from the [JDBC URL](JDBCURL.md)
    * Other settings like **Auto logon** and **Connect at Startup** are up to you.
> > Confirm settings by clicking **OK**
> > It will connect automatically or by pressing the _Connect_ left to the icon the **+** icon.
> > ![http://getcloudkeeper.com/jdbc/images/squirrel_install_3.png](http://getcloudkeeper.com/jdbc/images/squirrel_install_3.png)

  * Switch to _SQL_ tab,
> > Write your Query then Click the icon with the running man on it. Or by pressing _CTRL + ENTER_
> > ![http://getcloudkeeper.com/jdbc/images/squirrel_install_4_v1.png](http://getcloudkeeper.com/jdbc/images/squirrel_install_4_v1.png)