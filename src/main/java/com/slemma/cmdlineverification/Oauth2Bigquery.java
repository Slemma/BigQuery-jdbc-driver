/**
 * Starschema Big Query JDBC Driver
 * Copyright (C) 2012, Starschema Ltd.
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * This class implements functions to Authorize bigquery client
 */

package com.slemma.cmdlineverification;

import java.io.*;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Builder;
import com.google.api.services.bigquery.BigqueryScopes;

public class Oauth2Bigquery {

    private static String servicepath = null;

    /**
     * Log4j logger, for debugging.
     */
    // static Logger logger = new Logger(Oauth2Bigquery.class.getName());
    static Logger logger = Logger.getLogger(Oauth2Bigquery.class.getName());
    /**
     * Browsers to try:
     */
    static final String[] browsers = {"google-chrome", "firefox", "opera",
            "epiphany", "konqueror", "conkeror", "midori", "kazehakase",
            "mozilla"};
    /**
     * Google client secrets or {@code null} before initialized in
     * {@link #authorize}.
     */
    private static GoogleClientSecrets clientSecrets = null;

    /**
     * Reference to the GoogleAuthorizationCodeFlow used in this installed
     * application authorization sequence
     */
    public static GoogleAuthorizationCodeFlow codeflow = null;

    /**
     * The default path to the properties file that stores the xml file location
     * where client credentials are saved
     */
    private static String PathForXmlStore = System.getProperty("user.home")
            + File.separator + ".bqjdbc" + File.separator
            + "xmllocation.properties";

    /**
     * Authorizes the installed application to access user's protected data. if
     * possible, gets the credential from xml file at PathForXmlStore
     *
     * @param transport   HTTP transport
     * @param jsonFactory JSON factory
     * @param scopes      OAuth 2.0 scopes
     */
    public static Credential authorize(HttpTransport transport,
                                       JsonFactory jsonFactory,
                                       List<String> scopes, String clientid, String clientsecret,
                                       String refreshToken)
            throws Exception {

        GoogleClientSecrets.Details details = new Details();
        details.setClientId(clientid);
        details.setClientSecret(clientsecret);
        details.set("Factory", CmdlineUtils.getJsonFactory());
        details.setAuthUri("https://accounts.google.com/o/oauth2/auth");
        details.setTokenUri("https://accounts.google.com/o/oauth2/token");
        GoogleClientSecrets secr = new GoogleClientSecrets()
                .setInstalled(details);
        GoogleCredential CredentialForReturn = new GoogleCredential.Builder()
                .setJsonFactory(CmdlineUtils.getJsonFactory())
                .setTransport(CmdlineUtils.getHttpTransport())
                .setClientSecrets(secr).build();

        CredentialForReturn.setExpiresInSeconds(null);
        CredentialForReturn.setExpirationTimeMilliseconds(null);
        CredentialForReturn.setRefreshToken(refreshToken);
        CredentialForReturn.setAccessToken(null);
        return CredentialForReturn;
    }

    /**
     * Authorizes a bigquery Connection with the given "Installed Application"
     * Clientid and Clientsecret
     *
     * @param clientid Client ID
     * @param clientsecret Client Secret
     * @return Authorized bigquery Connection
     * @throws SQLException
     */
    public static Bigquery authorizeviainstalled(String clientid,
                                                 String clientsecret,
                                                 String refreshToken) throws SQLException, IOException {
        List<String> Scopes = new ArrayList<String>();
        Scopes.add(BigqueryScopes.BIGQUERY);
        Credential credential = null;
        try {
            logger.debug("Authorizing as installed app.");
            credential = Oauth2Bigquery.authorize(
                    CmdlineUtils.getHttpTransport(),
                    CmdlineUtils.getJsonFactory(), Scopes, clientid,
                    clientsecret, refreshToken);
        } catch (Exception e) {
            throw new SQLException(e);
        }
        logger.debug("Creating a new bigquery client.");
        Bigquery bigquery = new Builder(CmdlineUtils.getHttpTransport(),
                CmdlineUtils.getJsonFactory(), credential).build();
        Oauth2Bigquery.servicepath = bigquery.getServicePath();
        return bigquery;
    }

    /**
     * This function gives back an Authorized Bigquery Client It uses a service
     * account, which doesn't need user interaction for connect
     *
     * @param serviceaccountemail
     * @param keypath
     * @return Authorized Bigquery Client via serviceaccount e-mail and keypath
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static Bigquery authorizeviaservice(String serviceaccountemail,
                                               String keypath) throws GeneralSecurityException, IOException {
        List<String> scopes = new ArrayList<String>();
        scopes.add(BigqueryScopes.BIGQUERY);
        logger.debug("Authorizing with service account.");
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(CmdlineUtils.getHttpTransport())
                .setJsonFactory(CmdlineUtils.getJsonFactory())
                .setServiceAccountId(serviceaccountemail)
                        // e-mail ADDRESS!!!!
                .setServiceAccountScopes(scopes)
                        // Currently we only want to access bigquery, but it's possible
                        // to name more than one service too
                .setServiceAccountPrivateKeyFromP12File(new File(keypath))
                .build();

        Bigquery bigquery = new Builder(CmdlineUtils.getHttpTransport(),
                CmdlineUtils.getJsonFactory(), credential).build();
        Oauth2Bigquery.servicepath = bigquery.getServicePath();
        return bigquery;
    }

    /**
     * Returns the Google client secrets or {@code null} before initialized in
     * {@link #authorize}.
     */
    public static GoogleClientSecrets getClientSecrets() {
        return Oauth2Bigquery.clientSecrets;
    }

    public static String getservicepath() {
        return Oauth2Bigquery.servicepath;
    }

    /**
     * Creates GoogleClientsecrets "installed application" instance based on
     * given Clientid, and Clientsecret
     *
     * @param jsonFactory
     * @param clientid
     * @param clientsecret
     * @return GoogleClientsecrets of "installed application"
     * @throws IOException
     */
    private static GoogleClientSecrets loadClientSecrets(
            JsonFactory jsonFactory, String clientid, String clientsecret)
            throws IOException {
        if (Oauth2Bigquery.clientSecrets == null) {
            String clientsecrets = "{\n"
                    + "\"installed\": {\n"
                    + "\"client_id\": \""
                    + clientid
                    + "\",\n"
                    + "\"client_secret\":\""
                    + clientsecret
                    + "\",\n"
                    + "\"redirect_uris\": [\"http://localhost\", \"urn:ietf:oauth:2.0:oob\"],\n"
                    + "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n"
                    + "\"token_uri\": \"https://accounts.google.com/o/oauth2/token\"\n"
                    + "}\n" + "}";
            StringReader stringReader = new StringReader(clientsecrets);
            Oauth2Bigquery.clientSecrets = GoogleClientSecrets.load(
                    jsonFactory, stringReader);
        }
        return Oauth2Bigquery.clientSecrets;
    }
}
