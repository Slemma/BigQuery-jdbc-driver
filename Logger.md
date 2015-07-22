# Logger #

Our JDBC driver uses a log4j logger which is disabled by default.

If you want to enable logging follow these steps below:

  * Old Version (0.0.9):
    1. Download the jar files from the [Downloads](http://code.google.com/p/starschema-bigquery-jdbc/downloads/list) section (0.0.9)
    1. Uncompress it with any archiver tool supporting zip format
    1. Edit the log4j.properties file (details below)
    1. Compress back all the files and directories in the same format as it was before
    1. Use the driver and watch for logs in the **.bqjdc** directory in your Home folder
  * New Version (after 0.0.9):
    * Edit Log4j.properties file located in user home/.bqjdbc if the file does not exist, create it:
```
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yy-MM-dd HH:MM:ss,SSS} BQJDBC [%t] %-5p %c %x - %m%n

log4j.appender.ConsoleFileAppender=org.apache.log4j.RollingFileAppender
log4j.appender.ConsoleFileAppender.File=${user.home}/.bqjdbc/bqjdbconsole.log
log4j.appender.ConsoleFileAppender.layout=org.apache.log4j.PatternLayout
log4j.appender.ConsoleFileAppender.layout.ConversionPattern=%d{yy-MM-dd HH:MM:ss,SSS} BQJDBC [%t] %-5p %c %x - %m%n
log4j.appender.ConsoleFileAppender.MaxFileSize=10MB

log4j.appender.FileAppender=org.apache.log4j.RollingFileAppender
log4j.appender.FileAppender.File=${user.home}/.bqjdbc/bqjdbc.log
log4j.appender.FileAppender.layout=org.apache.log4j.PatternLayout
log4j.appender.FileAppender.layout.ConversionPattern=%d{yy-MM-dd HH:MM:ss,SSS} BQJDBC [%t] %-5p %c %x - %m%n
log4j.appender.FileAppender.MaxFileSize=10MB

#Uncomment the next lines to enable logging
log4j.logger.net.starschema.clouddb.jdbc=,FileAppender
log4j.logger.net.starschema.clouddb.commandlineverification=,FileAppender
#log4j.rootLogger=,ConsoleFileAppender, stdout
```
    * If it already exists, see Editing log4j.properties for more detail


## Editing log4j.properties file ##

Uncomment the following lines by removing # from the beggining of the lines.
```
#log4j.logger.net.starschema.clouddb.jdbc=,FileAppender
#log4j.logger.net.starschema.clouddb.commandlineverification=,FileAppender
```

The first line is responsible for the log messages of the driver, the second line is for log messages regarding the authentication part.


Note:

If you uncomment:
```
#log4j.rootLogger=,ConsoleFileAppender, stdout
```
you will see the applications log (the application which uses log4j and our driver) at location specified by:
```
log4j.appender.ConsoleFileAppender.File=${user.home}/.bqjdbc/bqjdbconsole.log
```
so in this case at ${user.home}/.bqjdbc/bqjdbconsole.log

### Log severity ###

It's possible to set the log level through a parameter in the following line:
```
#log4j.logger.net.starschema.clouddb.jdbc=<LOG LEVEL>,FileAppender
```

Here's a list about the possible <LOG LEVEL>s:
  * TRACE
  * DEBUG
  * INFO
  * WARN
  * ERROR
  * FATAL

### File location ###

You can change the location and file name by changing the following line the properties file:
```
log4j.appender.FileAppender.File=${user.home}/.bqjdbc/bqjdbc.log
```