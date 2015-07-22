# Introduction #

With some applications there might be issues getting authentication working properly, so in order to make the driver working we need to generate the credential file.

This test interface creates the credential file after initiating a test connection or running a query.

# Details #

![http://getcloudkeeper.com/jdbc/images/jdbc_initializer.png](http://getcloudkeeper.com/jdbc/images/jdbc_initializer.png)

The use of the GUI is pretty straight-forward.
You need to fill out the following fields:
  * Client ID
  * Client secret
  * Project ID

Then you can generate the JDBCURL by pressing the _Get JDBC url_ button, or you can run the following built-in query on the sample dataset:


```
SELECT 
   corpus, corpus_date 
FROM 
   publicdata:samples.shakespeare 
GROUP BY 
   corpus, corpus_date 
ORDER BY 
   corpus_date DESC 
LIMIT 1
```

In the text box you can type your own queries and run them.

To generate the JDBCURL with username and password included, use the _Include username and password_ checkbox.

# Running the application #

Since this test interface is integrated into the driver, you only need the jar file from the Downloads section and run the following way:

```
java -classpath bqjdbc-1.3.2.jar net.starschema.clouddb.initializer.GUI
```

Make sure to have java in your PATH environment variable.

# Source code #
https://code.google.com/p/starschema-bigquery-jdbc/source/browse/src/main/java/net/starschema/clouddb/initializer/GUI.java