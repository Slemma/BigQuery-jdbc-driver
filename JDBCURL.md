# Supported authentications #

In the current release two authentication types are supported: service account and web application OAuth2 authentication. While the first one relies on the private key file of the service account, the second uses generated client id/client secret pairs.

More information on BigQuery authentication can be obtained here: https://developers.google.com/bigquery/docs/authorization

More precisely information about how to create a service or web application Oauth2 id, can be found here:
https://developers.google.com/bigquery/authorization#clientsecrets

In order for the web application Oauth2 id to work, you must add Https://localhost/Callback to the allowed redirect urls after you create the id

# URL Syntax #

Based-on the preferred authentication method the URL strings can be the followings:

## In case of service account authentication: ##

```
jdbc:BQDriver:projectid(urlencoded)?withServiceAccount=true
```

And credential related connection properties are:

```
username: <account email>
password: <path to key file>
```

## In case of OAuth2 web appliaction: ##

```
jdbc:BQDriver:projectid(urlencoded)?withServiceAccount=false
```
Or
```
jdbc:BQDriver:projectid(urlencoded)
```
Optionally
```
?withServiceAccount=true //(by default it is set to false)
?transformQuery=true //(to enable the Query Transformation Engine)
```

And credential related connection properties are:

```
username: clientid
password: clientsecret
withServiceAccount: if we want to connect with service account
transformQuery: to enable the query parser
```

### From 1.3 you can set your connection like this ###
Sample from Squirrel SQL

http://www.starschema.net/images/clouddb/connection_settings.PNG
http://www.starschema.net/images/clouddb/connection_properties.PNG

## Without credential properties ##

In case when the application do not use credential properties, the driver can obtain username and password fields directly form the URL. Please note that all parameters must be URL encoded.

```
jdbc:BQDriver:projectid(urlencoded)?withServiceAccount=true&user=<serviceaccountemail(urlencoded)>&password=<Pathtokeyfile(urlencoded)>
```

Or

```
jdbc:BQDriver:projectid(urlencoded)?withServiceAccount=false&user=<clientid(urlencoded)>&password=<clientsecret(urlencoded)>
```

# Driver Classname #
```
net.starschema.clouddb.jdbc.BQDriver
```

# From Version 1.3 #
**withServiceAccount** and **transformQuery** can be set as Parameter.



## Enabling the Query Parser ##
**?transformQuery=true** enables the Query Parser which transforms SQL queries into BigQuery compatible queries.



# URL Encoding #

URL encoding for example replaces the **:** with **%3A** so **mycomp:project** will be transformed into **mycomp%3Aproject**
For more details please see:
http://en.wikipedia.org/wiki/Percent-encoding