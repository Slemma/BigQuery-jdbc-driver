# How to set up the jdbc driver in SAP Crystal Reports 2011 #
You can obtain SAP Crystal Reports from: [www.sap.com](http://www.sap.com/solutions/sme/business-intelligence-crystal-solutions/sap-crystal-reports/index.epx)

To connect to Google BigQuery, first add the driver to the SAP Crystals path:
  * Search for _CRCONFIG.xml_ You can find it under:
    * On 64bit: **_C:\Program Files (x86)\SAP_BusinessObjects\SAP BusinessObjects Enterprise XI 4.0\java\CRConfig.xml**_* On 32bit:__C:\Program Files\SAP **BusinessObjects\SAP BusinessObjects Enterprise XI 4.0\java\CRConfig.xml**_
  * Add the drivers location to the 

&lt;Classpath&gt;

_> > ![http://getcloudkeeper.com/jdbc/images/sap_crystal_install_1.png](http://getcloudkeeper.com/jdbc/images/sap_crystal_install_1.png)
If you want to use the Query Parser also increase the java heap size which can be done by modifying the
      * JVMMaxHeap>64000000

Unknown end tag for &lt;/JVMMaxHeap&gt;

_The default 64mb isn't enough, use at least 200mb (200000000) or just add a 0 to give a maximum of 640mb_

  * Start the Program go to _File_ -> _Log On or Off Server..._
> > ![http://getcloudkeeper.com/jdbc/images/sap_crystal_install_2.png](http://getcloudkeeper.com/jdbc/images/sap_crystal_install_2.png)
    * Select _Create New Connection_ -> _JDBC (JNDI) ->_Make New Connection**> > > ![http://getcloudkeeper.com/jdbc/images/sap_crystal_install_3.png](http://getcloudkeeper.com/jdbc/images/sap_crystal_install_3.png)
        * Check**JDBC connection*****Connection URL:**- [Construction of the JDBC URL scheme is described here](JDBCURL.md)
        ***Database Classname:_-_net.starschema.clouddb.jdbc.BQDriver*****Connection Name (Optional)_-  Name for the connection (user supplied)
        * Hit_Next >**> > > ![http://getcloudkeeper.com/jdbc/images/sap_crystal_install_4.png](http://getcloudkeeper.com/jdbc/images/sap_crystal_install_4.png)**

  * **Database** - The name of the database (user supplied)
  * **User ID** and **Password** depends from the [JDBC URL](JDBCURL.md)

> Confirm settings by clicking_Finish