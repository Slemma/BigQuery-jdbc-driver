# Introduction #

The following sample application illustrates how to use Starschema's BigQuery JDBC driver from a generic java application.

# Source code #

```

package bigquery.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import net.starschema.clouddb.jdbc.BigQueryApi;

/**
 * This is a sample use case for BigQuery jdbc driver
 * 
 * @author Horváth Attila (horvatha@starschema.net)
 * 
 */
public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con = null;

		// Sample Installed Application Properties file:
		// type=installed
		// user=sampleusername
		// password=samplepassword
		// projectid=sampleprojectid

		// Sample Service Application Properties file:
		// type=service
		// user=sampleusername
		// password=samplepathtop.12file
		// projectid=sampleprojectid

		try {
			Class.forName("net.starschema.clouddb.jdbc.BQDriver");
			Properties MyProperties = BigQueryApi
					.ReadFromPropFile("installedaccount.properties");
			String URL = BigQueryApi
					.ConstructUrlFromPropertiesFile(MyProperties);

			con = DriverManager.getConnection(URL, MyProperties);
		} catch (Exception e) {
			System.out.println("Failed to initialize Connection to BigQuery:");
			e.printStackTrace();
		}
		String samplesql = "SELECT corpus, corpus_date FROM publicdata:samples.shakespeare GROUP BY corpus, corpus_date ORDER BY corpus_date DESC LIMIT 10";
		ResultSet TheResult = null;

		try {
			TheResult = con.createStatement().executeQuery(samplesql);
		} catch (SQLException e) {
			System.out.println("Failed to run Query:");
			e.printStackTrace();
		}
		try {
			TheResult.first();
			ResultSetMetaData metadata = TheResult.getMetaData();
			int ColumnCount = metadata.getColumnCount();
			// Print Out Column Names
			String Line = "";
			for (int i = 0; i < ColumnCount; i++) {
				Line += String.format("%-32s", metadata.getColumnName(i + 1));
			}
			System.out.println(Line + "\n");

			// Print out Column Values
			while (!TheResult.isAfterLast()) {
				Line = "";
				for (int i = 0; i < ColumnCount; i++) {
					Line += String.format("%-32s", TheResult.getString(i + 1));
				}
				System.out.println(Line);
				TheResult.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
```