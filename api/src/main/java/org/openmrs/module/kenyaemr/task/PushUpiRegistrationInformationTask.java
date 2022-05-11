/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.task;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.upiDataExchange.UpiDataExchange;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

public class PushUpiRegistrationInformationTask extends AbstractTask {

	private String url = "http://www.google.com:80/index.html";
	private static final Logger log = LoggerFactory.getLogger(PushUpiRegistrationInformationTask.class);
	/**
	 * @see AbstractTask#execute()
	 */
	public void execute() {
		Context.openSession();
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.connect();
			try {

				// connect to dhp server
				String authToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkU0MUU1QUM5RUIxNTlBMjc1NTY4NjM0MzIxMUJDQzAzMDMyMEUzMTZSUzI1NiIsIng1dCI6IjVCNWF5ZXNWbWlkVmFHTkRJUnZNQXdNZzR4WSIsInR5cCI6ImF0K2p3dCJ9.eyJpc3MiOiJodHRwczovL2RocGlkZW50aXR5c3RhZ2luZ2FwaS5oZWFsdGguZ28ua2UiLCJuYmYiOjE2NTIxODUyMzQsImlhdCI6MTY1MjE4NTIzNCwiZXhwIjoxNjUyMjcxNjM0LCJhdWQiOlsiREhQLkdhdGV3YXkiLCJESFAuVmlzaXRhdGlvbiJdLCJzY29wZSI6WyJESFAuR2F0ZXdheSIsIkRIUC5WaXNpdGF0aW9uIl0sImNsaWVudF9pZCI6InBhcnRuZXIudGVzdC5jbGllbnQiLCJqdGkiOiJENjUyOTUwNDQ1RDYyMjg2NDc1OTE3NjkxQzMwMzM4MyJ9.tey01umz34GOZv1ewpafpyiuj3Y0-lUO0ufww5nPEQ89Gl3QG73j6AjuU-mvnupCEt5hrPePuwTXt2gQ6CSgP9C82gVsdboF8pwbcr3eBZQ8Q9jNxPzKSOFoI6FuThnig_YDg6uHEcykgMnGBcM1OJIJnEnJcvc01mcfHi6J2IRlfI_wlG5__oeKKbvt2DjGygjuwBVUb4nGyEmqhjg8VRB0LZsD83h1bB2Z0FCU7IKyqUMC5dzZxGpWLYCtABdxG_YvPAP2tkzFD7SXdJKu7GT4UMJwh5CvNmQ4BVSWfcLOEk4d_8YblHjVXDy110Zk-qmPl5vv7NNRX1lv69N-gQ";
				String idType = "identification-number";
				String serverUrl = "https://dhpstagingapi.health.go.ke/visit/registry/search/" + idType + "/" +  "";

				String API_KEY = authToken;
				
				if (StringUtils.isBlank(serverUrl) || StringUtils.isBlank(API_KEY)) {
					System.out.println("Please set credentials for posting  enrollments to the nimeConfirm system");
					return;
				}
				
				// Get upi client registration details
				PatientService patientService = Context.getPatientService();
				List<Patient> allPatients = patientService.getAllPatients();

				for (Patient patient : allPatients) {

				UpiDataExchange e = new UpiDataExchange();
				ObjectNode tosend = e.generatePayloadForUpi(patient);
				String payload = tosend.toString();
					System.out.println("Payload ==>: "+ payload);

						String[] cipherSuites = new String[] { "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
						        "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA" };
						SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContexts.createDefault(),
						        new String[] { "TLSv1.2" }, cipherSuites,
						        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
						
						CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
							
							try {
								
								//Define a postRequest request
								HttpPost postRequest = new HttpPost(serverUrl);
								
								//Set the API media type in http content-type header
								postRequest.addHeader("content-type", "application/json");
								postRequest.addHeader("apikey", API_KEY);
								//Set the request post body
								StringEntity userEntity = new StringEntity(payload);
								postRequest.setEntity(userEntity);
								
								//Send the request; It will immediately return the response in HttpResponse object if any
								HttpResponse response = httpClient.execute(postRequest);
								//verify the valid error code first
								int statusCode = response.getStatusLine().getStatusCode();
								if (statusCode == 429) { // too many requests. just terminate
									System.out.println("Many requests please terminate");
									log.warn("Many requests please terminate");
									return;
								}
								
								if (statusCode == 200) {
									log.info("Successfully pushed enrollment info with id ");
								} else {
									
									JSONParser parser = new JSONParser();
									JSONObject responseObj = (JSONObject) parser.parse(EntityUtils.toString(response
									        .getEntity()));
									//JSONObject errorObj = (JSONObject) responseObj.get("error");
									System.out.println("Error while submitting enrollment sample.  " + "Error - "
									        + statusCode + ". Msg" + responseObj.get("message"));
									log.error("Error while submitting enrollment. " + "Error - " + statusCode + ". Msg"
									        + responseObj.get("message"));
									
								}
								Context.flushSession();
							}
							catch (Exception ee) {
								System.out.println("Could not push enrollments to the nimeConfirm system! " + ee.getCause());
								log.error("Could not push enrollments to the nimeConfirm system! " + ee.getCause());
								ee.printStackTrace();
							}
							finally {
								httpClient.close();
							}
						}
			}
			catch (Exception e) {
				throw new IllegalArgumentException("Unable to  push enrollments ", e);
			}
			finally {
				Context.closeSession();
				
			}
		}
		catch (IOException ioe) {
			
			try {
				String text = "At " + new Date()
				        + " there was an error reported connecting to the internet. Will not attempt pushing enrollments ";
				log.warn(text);
			}
			catch (Exception e) {
				log.error("Failed to check internet connectivity", e);
			}
		}
		
	}

	
}
