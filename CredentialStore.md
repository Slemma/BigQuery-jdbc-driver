# What is a CredentialStore #

  * A credentialstore is used for storing login information so the client don't have to authorize itself over and over again
  * Our driver stores the client credentials for comprehensive use in an xml file **encoded**. The default location of this file is:
(user home)/.bqjdbc/credentials.xml
  * This location is used if there is no properties file, see "**Changing the location of the CredentialStore**"

# Changing the location of the CredentialStore #

  * Our driver uses a properties file to change the location where the xml file with the encoded credentials will be.
  * The driver searches for this file under (user home)/.bjdbc/xmllocation.properties


# Editing the file #

The file must contain only the following row:
```
xmlpath=${user.home}/.bqjdbc/credentials.xml
```
where xmlpath is the location where the credentials will be stored. It can be changed.