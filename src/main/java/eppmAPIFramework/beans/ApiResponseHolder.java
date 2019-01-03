package eppmAPIFramework.beans;

import java.util.Map;

/**
 * This class holds the variables which would always
 * be available in every response
 * 
 */
public class ApiResponseHolder {

	private String response;
	private Integer statusCode;
	private Map<String, String> responseHeaders;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	@Override
	public String toString() {
		return "ApiResponseHolder [response=" + response + ", statusCode=" + statusCode + ", responseHeaders="
				+ responseHeaders + "]";
	}

}
