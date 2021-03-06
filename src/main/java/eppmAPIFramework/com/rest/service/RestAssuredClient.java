package eppmAPIFramework.com.rest.service;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import eppmAPIFramework.beans.ApiResponseHolder;

/**
 * This class contains Generic HTTP Methods (GET, Post, PATCH, DELETE)
 * These methods will be used by different API's accordingly
 * 
 */
public class RestAssuredClient {

	private static Logger logger = Logger.getLogger("RestAssuredClient");

	/**
	 * This is a Generic HTTP POST Method that will be used to make any Post call by
	 * API
	 * 
	 */
	public static ApiResponseHolder doPost(String endPoint, String body, String csrfToken,
			Map<String, String> requestHeader) {
		RequestSpecification requestSpecification = RestAssured.given().urlEncodingEnabled(false).relaxedHTTPSValidation();
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.accept(ContentType.JSON);
		for (String key : requestHeader.keySet()) {
			Header header = new Header(key, requestHeader.get(key));
			requestSpecification.header(header);

		}
		if (csrfToken != null) {
			requestSpecification.header("X-CSRF-TOKEN", csrfToken);
		}
		requestSpecification.body(body);
		logger.info("end point url.." + endPoint);
		logger.info("Body.." + body);

		Response response = requestSpecification.post(endPoint);
		ApiResponseHolder apiresponseHolder = getApiResponseHolder(response);
		logger.info(apiresponseHolder.toString());
		return apiresponseHolder;
	}

	/**
	 * This is a Generic HTTP GET Method that will be used to make any GET call by
	 * API
	 * 
	 */
	public static ApiResponseHolder doGet(String endPoint, String csrfToken, Map<String, String> requestHeader) {
		RequestSpecification requestSpecification = RestAssured.given().urlEncodingEnabled(false).relaxedHTTPSValidation();
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.accept(ContentType.JSON);
		if (csrfToken != null) {
			requestSpecification.header("X-CSRF-TOKEN", csrfToken);
		}
		logger.info("end point url.." + endPoint);
		for (String key : requestHeader.keySet()) {
			Header header = new Header(key, requestHeader.get(key));
			requestSpecification.header(header);
		}
		Response response = requestSpecification.get(endPoint);
		ApiResponseHolder apiresponseHolder = getApiResponseHolder(response);
		logger.info(apiresponseHolder.toString());
		return apiresponseHolder;
	}

	/**
	 * This is a Generic API Response Holder Methods which will hold the Response
	 * from the API's
	 * 
	 */
	private static ApiResponseHolder getApiResponseHolder(Response response) {
		ApiResponseHolder apiresponseHolder = new ApiResponseHolder();
		apiresponseHolder.setResponse(response.getBody().asString());
		apiresponseHolder.setStatusCode(response.statusCode());
		Headers header = response.getHeaders();
		List<Header> responseHeaders = header.asList();
		Map<String, String> responseMap = new HashMap<String, String>();
		for (Header h : responseHeaders) {
			responseMap.put(h.getName(), h.getValue());
		}
		apiresponseHolder.setResponseHeaders(responseMap);
		return apiresponseHolder;
	}

	/**
	 * This is a Generic HTTP PATCH Method that will be used to make any PATCH call
	 * by API
	 * 
	 */
	public static ApiResponseHolder doPatch(String endPoint, String body, String csrfToken,
			Map<String, String> requestHeader) {
		RequestSpecification requestSpecification = RestAssured.given().urlEncodingEnabled(false).relaxedHTTPSValidation();
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.accept(ContentType.JSON);
		for (String key : requestHeader.keySet()) {
			Header header = new Header(key, requestHeader.get(key));
			requestSpecification.header(header);
		}
		if (csrfToken != null) {
			requestSpecification.header("X-CSRF-TOKEN", csrfToken);
		}
		requestSpecification.body(body);
		logger.info("End Point Url---->" + endPoint);
		logger.info("Body--->" + body);

		Response response = requestSpecification.patch(endPoint);
		ApiResponseHolder apiresponseHolder = getApiResponseHolder(response);
		logger.info(apiresponseHolder.toString());
		return apiresponseHolder;
	}

	/**
	 * This is a Generic HTTP DELETE Method that will be used to make any DELETE
	 * call by API
	 * 
	 */
	public static ApiResponseHolder doDelete(String endPoint, String csrfToken, Map<String, String> requestHeader) {
		RequestSpecification requestSpecification = RestAssured.given().urlEncodingEnabled(false).relaxedHTTPSValidation();
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.accept(ContentType.JSON);
		if (csrfToken != null) {
			requestSpecification.header("X-CSRF-TOKEN", csrfToken);
		}
		logger.info("End point url.." + endPoint);
		for (String key : requestHeader.keySet()) {
			Header header = new Header(key, requestHeader.get(key));
			requestSpecification.header(header);
		}
		Response response = requestSpecification.delete(endPoint);
		ApiResponseHolder apiresponseHolder = getApiResponseHolder(response);
		logger.info(apiresponseHolder.toString());
		return apiresponseHolder;
	}

}
