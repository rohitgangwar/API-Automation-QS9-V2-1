package eppmAPIFramework.com.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.jayway.restassured.RestAssured;


import static eppmAPIFramework.com.rest.service.ServiceUrl.*;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import eppmAPIFramework.beans.ApiResponseHolder;
import eppmAPIFramework.com.rest.requestpojo.CreateBlockFunctionWBSRequest;
import eppmAPIFramework.com.rest.requestpojo.CreateBlockFunctionsPSTRequest;
import eppmAPIFramework.com.rest.requestpojo.CreateProjectRequest;
import eppmAPIFramework.com.rest.requestpojo.CreateWBSElementsRequest;
import eppmAPIFramework.com.rest.responsepojo.CreateBlockFunctionWBSResponse;
import eppmAPIFramework.com.rest.responsepojo.CreateBlockFunctionsPSTResponse;
import eppmAPIFramework.com.rest.responsepojo.CreateProjectResponse;
import eppmAPIFramework.com.rest.responsepojo.CreateWBSElementsResponse;

/**
 * This class holds all the API's Implementation Methods &
 * Methods to verify different API Results
 * 
 */
public class Service {
	private static Logger logger = Logger.getLogger("Service");

	private static String csrfToken = null;

// 	/**
// 	 * This method contains the AUTHORIZATION Key for CCF/715 SYSTEM Post call by API
// 	 * 
// 	 */
// 	static Map<String, String> requestHeader = new HashMap();
// 	{
// 		requestHeader.put("Authorization", "Basic Q0NmX0NPTU1fMDMwOTpXZWxjb21lMSE=");

// 	}
	
// 	/**
// 	 * This method contains the AUTHORIZATION Key for CC2/715 SYSTEM Post call by API
// 	 * 
// 	 */
// 	static Map<String, String> requestHeader = new HashMap();
// 	{
// 		requestHeader.put("Authorization", "Basic Q0MyX0NPTU1fMDMwOTpXZWxjb21lMSE=");

// 	}
	
	/**
	 * This method contains the AUTHORIZATION Key for QS9/711 SYSTEM Post call by API
	 * 
	 */
	static Map<String, String> requestHeader = new HashMap();
	{
		requestHeader.put("Authorization", "Basic UVM5X0NPTU1fMDMwNzpXZWxjb21lMSE=");

	}

	/**
	 * This method is used to Fetch the CSRF Token Value & Also updates the cookie
	 * 
	 */
	public static String getCsrfToken() {
		if (csrfToken == null) {
			int count = 0;
			while (count++ < 5) {
				ApiResponseHolder apiResponseHolder = RestAssuredClient.doGet(GET_SYSTEM_DETAILS, "FETCH",
						requestHeader);
				csrfToken = apiResponseHolder.getResponseHeaders().get("x-csrf-token");
				String cookie = apiResponseHolder.getResponseHeaders().get("set-cookie");
				if (cookie != null) {
					requestHeader.clear();
					requestHeader.put("Cookie", cookie);
				}
				logger.info("CSRF Value is " + csrfToken);
				if (csrfToken != null)
					break;
			}

		}
		return csrfToken;
	}

	/**
	 * This API will Create Project
	 * 
	 * @param project
	 * @param projectDescription
	 * @param projectStartDate
	 * @param projectEndDate
	 * @param profitCentre
	 * @param responsibleCostCenter
	 * @param projectProfileCode
	 * @return
	 */
	public ApiResponseHolder createProjectAPI(String project, String projectDescription, String projectStartDate,
			String projectEndDate, String profitCentre, String responsibleCostCenter, String projectProfileCode) {

		CreateProjectRequest createProjectRequestObj = new CreateProjectRequest();

		createProjectRequestObj.setProject(project);
		createProjectRequestObj.setProjectDescription(projectDescription);
		createProjectRequestObj.setProjectStartDate(projectStartDate);
		createProjectRequestObj.setProjectEndDate(projectEndDate);
		createProjectRequestObj.setProfitCenter(profitCentre);
		createProjectRequestObj.setResponsibleCostCenter(responsibleCostCenter);
		createProjectRequestObj.setProjectProfileCode(projectProfileCode);

		String body = new GsonBuilder().create().toJson(createProjectRequestObj);

		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPost(createProjectUrl, body, getCsrfToken(),
				requestHeader);
		return apiResponseHolder;

	}

	// Get the Project Details //

	public ApiResponseHolder getProjectDetails(String projectName) {
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doGet(GET_PROJECTDETAILS_URL.replace("{pName}", projectName), getCsrfToken(), null);
		return apiResponseHolder;

	}

	/**
	 * This methods will Remove the Undesired "D" tag from the Response
	 * 
	 */
	public static String sanitizeOutput(String body) {
		HashMap<String, Object> objectVal = new GsonBuilder().create().fromJson(body, HashMap.class);
		logger.info("Map has: " + objectVal.keySet());
		LinkedTreeMap<String, Object> map = (LinkedTreeMap) objectVal.get("d");
		return new GsonBuilder().create().toJson(map);
	}

	/**
	 * This API will CREATE WBS ELEMENT
	 * 
	 * @param projectElement
	 * @param projectElementDescription
	 * @param sortingNumber
	 * @param plannedStartDate
	 * @param plannedEndDate
	 * @param responsibleCostCenter
	 * @param profitCenter
	 * @param to_SubProjElement
	 * @param projectElementUUID
	 * @return
	 */
	public ApiResponseHolder createWBSElementsAPI(String projectElement, String projectElementDescription,
			String sortingNumber, String plannedStartDate, String plannedEndDate, String responsibleCostCenter,
			String profitCenter, List<CreateWBSElementsRequest> to_SubProjElement, String projectElementUUID) {

		CreateWBSElementsRequest createWBSElementsRequestObj = new CreateWBSElementsRequest(projectElement,
				projectElementDescription, sortingNumber, plannedStartDate, plannedEndDate, responsibleCostCenter,
				profitCenter);

		createWBSElementsRequestObj.setTo_SubProjElement(to_SubProjElement);

		String body = new GsonBuilder().create().toJson(createWBSElementsRequestObj);

		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPost(
				CREATE_WBS_ELEMENTS_URL.replace("{GUID}", projectElementUUID), body, getCsrfToken(), requestHeader);

		return apiResponseHolder;

	}

	/**
	 * This Method will verify whether the Project can be created or NOT
	 * for different Project Profiles
	 * 
	 */
	public boolean verifyCreateProject(String projectProfile) {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			System.out.println(createProjectResponse);
			System.out.println("Project ID is ---->" + createProjectResponse.getProject());
			System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());

			if (createProjectResponse.getProject().equalsIgnoreCase(projName)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * This is a UPDATE API used for Updating Attribute values
	 * 
	 */
	public ApiResponseHolder updateWBSElementAttributesAPI(CreateWBSElementsRequest request,
			String projectElementUUID) {
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPatch(
				UPDATE_WBS_ELEMENT_ATTRIBUTES_URL.replace("{GUID}", projectElementUUID), body, csrf, requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}

	/**
	 * This method verifies whether the API can update the Element Description Value
	 * 
	 */
	public boolean verifyUpdateWBSAttributeDescription() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projDesc = "ProjDesc" + time;
		CreateWBSElementsRequest request = new CreateWBSElementsRequest();
		request.setProjectElementDescription(projDesc);
		ApiResponseHolder apiResponseHolder = service.updateWBSElementAttributesAPI(request,
				"0894ef45-7d01-1eea-a3be-9ccffb1b0331");

		return apiResponseHolder.getStatusCode() == 204;

	}

	/**
	 * This is a Delete API
	 * 
	 */
	public ApiResponseHolder deleteProjectAPI(String projectUUID) {
		// String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doDelete(DELETE_PROJECT_URL.replace("{GUID}", projectUUID), csrf, requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}

	/**
	 * This method verifies if the Newly Created project Gets Deleted or not for Investment Profile
	 * 
	 */
	public boolean verifyDeleteInvestmentProject() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP02");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			String reqUUID = createProjectResponse.getProjectUUID();
			ApiResponseHolder apiResponseHoldeNew = service.deleteProjectAPI(reqUUID);
			if (apiResponseHoldeNew.getStatusCode() == 204) {
				return true;
			}
		}

		return false;

	}
	
	/**
	 * This method verifies if the Newly Created project Gets Deleted or not for Overhead Profile
	 * 
	 */
	public boolean verifyDeleteOverheadProject() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			String reqUUID = createProjectResponse.getProjectUUID();
			ApiResponseHolder apiResponseHoldeNew = service.deleteProjectAPI(reqUUID);
			if (apiResponseHoldeNew.getStatusCode() == 204) {
				return true;
			}
		}

		return false;

	}
	
	/**
	 * This method verifies if the Newly Created project Gets Deleted or not for Statistical Profile
	 * 
	 */
	public boolean verifyDeleteStatisticalProject() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP04");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			String reqUUID = createProjectResponse.getProjectUUID();
			ApiResponseHolder apiResponseHoldeNew = service.deleteProjectAPI(reqUUID);
			if (apiResponseHoldeNew.getStatusCode() == 204) {
				return true;
			}
		}

		return false;

	}
	
	/**
	 * This method verifies if the Newly Created project Gets Deleted or not for Revenue Profile
	 * 
	 */
	public boolean verifyDeleteRevenueProject() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP05");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			String reqUUID = createProjectResponse.getProjectUUID();
			ApiResponseHolder apiResponseHoldeNew = service.deleteProjectAPI(reqUUID);
			if (apiResponseHoldeNew.getStatusCode() == 204) {
				return true;
			}
		}

		return false;

	}

	/**
	 * This Method Verifies whether the WBS Hierarchy is Successfully Created
	 * 
	 */
	public boolean verifyCreateWBSHierarchy() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();

		String projName = "Proj_" + time;
		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";
		
		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);

			apiResponseHolder = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700", "2019-10-07T00:00:00",
					"2019-12-31T00:00:00", "10101501", "YB600", list, createProjectResponse.getProjectUUID());

			if (apiResponseHolder.getStatusCode() == 201) {
				String newResponse1 = sanitizeOutput(apiResponseHolder.getResponse());
				CreateWBSElementsResponse createWBSResponse = gson.fromJson(newResponse1,
						CreateWBSElementsResponse.class);

				System.out.println(createWBSResponse);
				System.out.println("Project UUID ---->" + createWBSResponse.getProjectUUID());
				
				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();
				
				System.out.println("result--->" + results);
				System.out.println(results.get(0).getProjectElement());
				System.out.println(results.get(1).getProjectElement());

				if (results.size() == 2) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * This method will Set the Project Status as Technically Completed based on given
	 * ProjectUUID
	 * 
	 */
	public ApiResponseHolder setProjectStatusTCHOAPI(CreateProjectRequest request, String projectUUID) {
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doPost(TCHO_PROJECT_URL.replace("{GUID}", projectUUID), body, getCsrfToken(), requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}
	
	/**
	 * This method will Set the Project Status as LOCKED based on given
	 * ProjectUUID
	 * 
	 */
	public ApiResponseHolder setProjectStatusLOCKEDAPI(CreateProjectRequest request, String projectUUID) {
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doPost(LOCKED_PROJECT_URL.replace("{GUID}", projectUUID), body, getCsrfToken(), requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}
	
	/**
	 * This method will Set the Project Status as CLOSED based on given
	 * ProjectUUID
	 * 
	 */
	public ApiResponseHolder setProjectStatusCLOSEDAPI(CreateProjectRequest request, String projectUUID) {
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doPost(CLOSED_PROJECT_URL.replace("{GUID}", projectUUID), body, getCsrfToken(), requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}
	
	/**
	 * This method will Set the Project Status as RELEASED based on given
	 * ProjectUUID
	 * 
	 */
	public ApiResponseHolder setProjectStatusReleasedAPI(CreateProjectRequest request, String projectUUID) {
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doPost(RELEASE_PROJECT_URL.replace("{GUID}", projectUUID), body, getCsrfToken(), requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}

	/**
	 * This methods verifies Creation of WBS Hierarchy for Released Project
	 * 
	 */
	public boolean verifyCreateHierarchyForRELProj() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";

		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());

			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			String reqUUID = createProjectResponse.getProjectUUID();

			CreateProjectRequest requestObj = new CreateProjectRequest();

			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);

			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);

			ApiResponseHolder apiResponseHolder5 = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700",
					"2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600", list,
					createProjectResponse.getProjectUUID());

			if (apiResponseHolder5.getStatusCode() == 201) {
				String newResponse1 = sanitizeOutput(apiResponseHolder5.getResponse());
				CreateWBSElementsResponse createWBSResponse = gson.fromJson(newResponse1,
						CreateWBSElementsResponse.class);

				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();

				if (results.size() == 2) {
					return true;
				}
			}

		}

		return false;

	}
	
	/**
	 * This methods verifies Creation of WBS Hierarchy for TCHO Project
	 * 
	 */
	public boolean verifyCreateHierarchyForTCHOProj() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";

		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);
			String reqUUID = createProjectResponse.getProjectUUID();
			CreateProjectRequest requestObj = new CreateProjectRequest();
			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);

			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeTchoAPI = service.setProjectStatusTCHOAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeTchoAPI.getStatusCode() == 200)) {
				return false;
			}

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);

			ApiResponseHolder apiResponseHolder5 = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700",
					"2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600", list,
					createProjectResponse.getProjectUUID());

			if (apiResponseHolder5.getStatusCode() == 201) {
				return false;
			}

			else {
				String errorText = getErrorText(apiResponseHolder5);
				if (errorText.toLowerCase().equals("project status prevents work packages from being created.")) {
					return true;
				}
			}
		}

		return false;

	}
	
	/**
	 * This methods verifies Creation of WBS Hierarchy for LOCKED Project
	 * 
	 */
	public boolean verifyCreateHierarchyForLOCKEDProj() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";

		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);
			String reqUUID = createProjectResponse.getProjectUUID();
			CreateProjectRequest requestObj = new CreateProjectRequest();
			
			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeTchoAPI = service.setProjectStatusLOCKEDAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeTchoAPI.getStatusCode() == 200)) {
				return false;
			}

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);

			ApiResponseHolder apiResponseHolder5 = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700",
					"2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600", list,
					createProjectResponse.getProjectUUID());

			if (apiResponseHolder5.getStatusCode() == 201) {
				return false;
			}

			else {
				String errorText = getErrorText(apiResponseHolder5);
				if (errorText.toLowerCase().equals("project status prevents work packages from being created.")) {
					return true;
				}
			}
		}

		return false;

	}
	
	/**
	 * This methods verifies Creation of WBS Hierarchy for CLOSED Project
	 * 
	 */
	public boolean verifyCreateHierarchyForCLOSEDProj() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";

		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);
			String reqUUID = createProjectResponse.getProjectUUID();
			CreateProjectRequest requestObj = new CreateProjectRequest();
			
			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeTchoAPI = service.setProjectStatusTCHOAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeTchoAPI.getStatusCode() == 200)) {
				return false;
			}
			
			ApiResponseHolder apiResponseHoldeClosedAPI = service.setProjectStatusCLOSEDAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeClosedAPI.getStatusCode() == 200)) {
				return false;
			}

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);

			ApiResponseHolder apiResponseHolder5 = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700",
					"2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600", list,
					createProjectResponse.getProjectUUID());

			if (apiResponseHolder5.getStatusCode() == 201) {
				return false;
			}

			else {
				String errorText = getErrorText(apiResponseHolder5);
				if (errorText.toLowerCase().equals("project status prevents work packages from being created.")) {
					return true;
				}
			}
		}

		return false;

	}
	
	/**
	 * This methods verifies whether the INVESTMENT PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateInvestmentProject() {
		String projectProfile = "YP02";
		boolean value = verifyCreateProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Overhead PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateOverheadProject() {
		String projectProfile = "YP03";
		boolean value = verifyCreateProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Statistical PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateStatisticalProject() {
		String projectProfile = "YP04";
		boolean value = verifyCreateProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Revenue PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateRevenueProject() {
		String projectProfile = "YP05";
		boolean value = verifyCreateProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Multiple INVESTMENT PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateMultipleInvestmentProject() {
		String projectProfile = "YP02";
		boolean value = verifyCreateMultipleINVProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Multiple Overhead PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateMultipleOverheadProject() {
		String projectProfile = "YP03";
		boolean value = verifyCreateMultipleOVHProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Multiple Statistical PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateMultipleStatisticalProject() {
		String projectProfile = "YP04";
		boolean value = verifyCreateMultipleStatProject(projectProfile);
		return value;
	}
	
	/**
	 * This methods verifies whether the Multiple Revenue PROJECT
	 * can be created or NOT
	 * 
	 */
	public boolean verifyCreateMultipleRevenueProject() {
		String projectProfile = "YP05";
		boolean value = verifyCreateMultipleRevProject(projectProfile);
		return value;
	}
	

	
	/**
	 * This Method will verify whether the Multiple INVESMENT Project can be created
	 * or NOT
	 * 
	 * 
	 */
	public boolean verifyCreateMultipleINVProject(String projectProfile) {
		Service service = new Service();

		for (int i = 0; i < 10; i++) {

			Date date = new Date();
			long time = date.getTime();
			String projName = "Proj_INV_" + time;

			ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
					"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

			if (apiResponseHolder.getStatusCode() == 201) {
				Gson gson = new Gson();
				String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
				CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

				System.out.println(createProjectResponse);
				System.out.println("Project ID for INVESTMENT Project is ---->" + createProjectResponse.getProject());
				System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());

				if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
					return false;
				}

			}

		}

		return true;
	}

	/**
	 * This Method will verify whether the Multiple Overhead Project can be created
	 * or NOT
	 * 
	 * 
	 */
	public boolean verifyCreateMultipleOVHProject(String projectProfile) {
		Service service = new Service();

		for (int i = 0; i < 10; i++) {

			Date date = new Date();
			long time = date.getTime();
			String projName = "Proj_OVH_" + time;

			ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
					"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

			if (apiResponseHolder.getStatusCode() == 201) {
				Gson gson = new Gson();
				String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
				CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

				System.out.println(createProjectResponse);
				System.out.println("Project ID for OVERHEAD Project is ---->" + createProjectResponse.getProject());
				System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());

				if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
					return false;
				}

			}

		}

		return true;
	}

	/**
	 * This Method will verify whether the Multiple Statistical Project can be
	 * created or NOT
	 * 
	 * 
	 */
	public boolean verifyCreateMultipleStatProject(String projectProfile) {
		Service service = new Service();

		for (int i = 0; i < 10; i++) {

			Date date = new Date();
			long time = date.getTime();
			String projName = "Proj_STAT_" + time;

			ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
					"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

			if (apiResponseHolder.getStatusCode() == 201) {
				Gson gson = new Gson();
				String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
				CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

				System.out.println(createProjectResponse);
				System.out.println("Project ID for STATISTICAL Project is ---->" + createProjectResponse.getProject());
				System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());

				if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
					return false;
				}

			}

		}

		return true;
	}

	/**
	 * This Method will verify whether the Multiple Revenue Project can be created
	 * or NOT
	 * 
	 * 
	 */
	public boolean verifyCreateMultipleRevProject(String projectProfile) {
		Service service = new Service();

		for (int i = 0; i < 10; i++) {

			Date date = new Date();
			long time = date.getTime();
			String projName = "Proj_REV_" + time;

			ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
					"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

			if (apiResponseHolder.getStatusCode() == 201) {
				Gson gson = new Gson();
				String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
				CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

				System.out.println(createProjectResponse);
				System.out.println("Project ID for Revenue Project is ---->" + createProjectResponse.getProject());
				System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());

				if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
					return false;
				}

			}

		}

		return true;
	}
	
//	/**
//	 * This methods verifies Update of WBS Hierarchy for Released Project
//	 * 
//	 */
//	public boolean verifyUpdateHierarchyForRELProj() {
//		Service service = new Service();
//		Date date = new Date();
//		long time = date.getTime();
//		String projName = "Proj_" + time;
//		
//		String newProjDesc = "ProjDesc" + time;
//
//		String parentWBSName = "Test_" + time + "_PJT1";
//		String firstChildWBSName = "Test_" + time + "_1";
//		String secondChildWBSWBSName = "Test_" + time + "_2";
//
//		System.out.println("Project name was --->" + projName);
//
//		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
//				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP03");
//
//		if (apiResponseHolder.getStatusCode() == 201) {
//			Gson gson = new Gson();
//			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
//
//			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);
//
//			String reqUUID = createProjectResponse.getProjectUUID();
//
//			CreateProjectRequest requestObj = new CreateProjectRequest();
//
//			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);
//
//			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
//				return false;
//			}
//
//			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
//			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
//					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
//			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
//					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
//			list.add(request1);
//			list.add(request2);
//
//			ApiResponseHolder apiResponseHolder5 = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700",
//					"2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600", list,
//					createProjectResponse.getProjectUUID());
//
//			if (apiResponseHolder5.getStatusCode() == 201) {
//				String newResponse1 = sanitizeOutput(apiResponseHolder5.getResponse());
//				CreateWBSElementsResponse createWBSResponse = gson.fromJson(newResponse1,
//						CreateWBSElementsResponse.class);
//
//				CreateWBSElementsRequest request = new CreateWBSElementsRequest();
//				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();
//				createWBSResponse.getTo_SubProjElement().getResults().get(0).setProjectElementDescription(newProjDesc);
//				ApiResponseHolder apiResponseHolder6 = service.updateWBSElementAttributesAPI(request,
//						"createWBSResponse.getTo_SubProjElement().getResults().get(0).getProjectElementUUID()");
//
//
//				if (apiResponseHolder6.getStatusCode() == 204) {
//					return true;
//				}
//			}
//
//		}
//
//		return false;
//
//	}
	
	/**
	 * This Method will verify whether the Released Project can be Deleted or NOT
	 * 
	 * 
	 */
	public boolean verifyDeleteRELProject() {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String projectProfile = "YP03";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			System.out.println(createProjectResponse);

			String reqUUID = createProjectResponse.getProjectUUID();

			if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
				return false;
			}

			CreateProjectRequest requestObj = new CreateProjectRequest();

			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);

			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeNew2 = service.deleteProjectAPI(reqUUID);

			if (!(apiResponseHoldeNew2.getStatusCode() == 400)) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * This Method will verify whether the TCHO Project can be Deleted or NOT
	 * 
	 * 
	 */
	public boolean verifyDeleteTCHOProject() {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String projectProfile = "YP03";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			System.out.println(createProjectResponse);

			String reqUUID = createProjectResponse.getProjectUUID();

			if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
				return false;
			}

			CreateProjectRequest requestObj = new CreateProjectRequest();

			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);

			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeTchoAPI = service.setProjectStatusTCHOAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeTchoAPI.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeNew2 = service.deleteProjectAPI(reqUUID);

			if (!(apiResponseHoldeNew2.getStatusCode() == 400)) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * This Method will verify whether the Locked Project can be Deleted or NOT
	 * 
	 * 
	 */
	public boolean verifyDeleteLockedProject() {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String projectProfile = "YP03";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			System.out.println(createProjectResponse);

			String reqUUID = createProjectResponse.getProjectUUID();

			if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
				return false;
			}

			CreateProjectRequest requestObj = new CreateProjectRequest();

			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);

			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeLockedAPI = service.setProjectStatusLOCKEDAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeLockedAPI.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeNew2 = service.deleteProjectAPI(reqUUID);

			if (!(apiResponseHoldeNew2.getStatusCode() == 400)) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * This Method will verify whether the CLOSED Project can be Deleted or NOT
	 * 
	 * 
	 */
	public boolean verifyDeleteClosedProject() {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String projectProfile = "YP03";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			System.out.println(createProjectResponse);

			String reqUUID = createProjectResponse.getProjectUUID();

			if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
				return false;
			}

			CreateProjectRequest requestObj = new CreateProjectRequest();

			ApiResponseHolder apiResponseHoldeNew = service.setProjectStatusReleasedAPI(requestObj, reqUUID);

			if (!(apiResponseHoldeNew.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeTchoAPI = service.setProjectStatusTCHOAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeTchoAPI.getStatusCode() == 200)) {
				return false;
			}
			
			ApiResponseHolder apiResponseHoldeClosedAPI = service.setProjectStatusCLOSEDAPI(requestObj, reqUUID);
			if (!(apiResponseHoldeClosedAPI.getStatusCode() == 200)) {
				return false;
			}

			ApiResponseHolder apiResponseHoldeNew2 = service.deleteProjectAPI(reqUUID);

			if (!(apiResponseHoldeNew2.getStatusCode() == 400)) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * This sample calls for Create Project API
	 * for Incorrect Project Profile Scenario
	 * 
	 */
	public boolean validateIncorrectProjProferror() {
		String projectProfile = "YP88";
		boolean value = verifyIncorrectProjProfError(projectProfile);
		return value;
	}
	
	/**
	 * This Method will verify if it gives the correct error on 
	 * creation of project for Invalid Project Profiles
	 * 
	 */
	public boolean verifyIncorrectProjProfError(String projectProfile) {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			return false;
		} else {
			Gson gson = new Gson();
			String errorText = getErrorText(apiResponseHolder);
			if (errorText.toLowerCase().equals("enter a valid value for the project profile.")) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * This Method returns the Error value Text 
	 * for Negative Scenarios
	 * 
	 */
	public String getErrorText(ApiResponseHolder responseHolder) {
		Gson gson = new Gson();
		Map<String, Object> data = gson.fromJson(responseHolder.getResponse(), Map.class);
		String errorData = gson.toJson(data.get("error"));
		Map<String, Object> data1 = gson.fromJson(errorData, Map.class);
		String message = gson.toJson(data1.get("message"));
		Map<String, Object> data2 = gson.fromJson(message, Map.class);
		String value = (String) data2.get("value");

		return value;

	}
	
	/**
	 * This sample calls for Create Project API
	 * for Invalid Profit Center Scenario
	 * 
	 */
	public boolean validateInvalidProfitCenterError() {
		String profitCenter = "YB6000";
		boolean value = verifyIncorrectProfitCenterError(profitCenter);
		return value;
	}
	
	/**
	 * This Method will verify if it gives the correct error on 
	 * creation of project for Invalid Profit Center
	 * 
	 */
	public boolean verifyIncorrectProfitCenterError(String profitCenter) {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", profitCenter, "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			return false;
		} else {
			Gson gson = new Gson();
			String errorText = getErrorText(apiResponseHolder);
			if (errorText.toLowerCase().equals("profit center yb6000 does not exist")) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * This sample calls for Create Project API
	 * for Invalid Project ID Scenario
	 * 
	 */
	public boolean validateInvalidProjectIDError() {
		String projName = "Proj_kkr_12222000000000000000000000000000000000000000";
		boolean value = verifyIncorrectProjectIDError(projName);
		return value;
	}
	
	/**
	 * This Method will verify if it gives the correct error on 
	 * creation of project for Invalid Project ID
	 * 
	 */
	public boolean verifyIncorrectProjectIDError(String projName) {
		Service service = new Service();

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			return false;
		} else {
			Gson gson = new Gson();
			String errorText = getErrorText(apiResponseHolder);
			if (errorText.toLowerCase()
					.contains("has invalid value 'proj_kkr_12222000000000000000000000000000000000000000'")
					&& errorText.toLowerCase().contains("property 'project' at offset")) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * This sample calls for Create Project API
	 * for Invalid Dates Scenario
	 * 
	 */
	public boolean validateInvalidDateError() {
		String startDate = "2018-10-07T0000:00:0000";
		boolean value = verifyIncorrectDateError(startDate);
		return value;
	}
	
	/**
	 * This Method will verify if it gives the correct error on 
	 * creation of project for Invalid Dates
	 * 
	 */
	public boolean verifyIncorrectDateError(String startDate) {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description", startDate,
				"2018-12-31T00:00:00", "YB600", "10101501", "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			return false;
		} else {
			Gson gson = new Gson();
			String errorText = getErrorText(apiResponseHolder);
			if (errorText.toLowerCase().contains("conversion error for property 'projectstartdate'")) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * This sample calls for Create Project API
	 * for Invalid Responsible Cost Center Scenario
	 * 
	 */
	public boolean validateInvalidRespCCError() {
		String respCostCenter = "1010150100000";
		boolean value = verifyIncorrectRespCCError(respCostCenter);
		return value;
	}
	
	/**
	 * This Method will verify if it gives the correct error on 
	 * creation of project for Invalid Responsible Cost Center
	 * 
	 */
	public boolean verifyIncorrectRespCCError(String respCostCenter) {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", respCostCenter, "YP03");

		if (apiResponseHolder.getStatusCode() == 201) {
			return false;
		} else {
			Gson gson = new Gson();
			String errorText = getErrorText(apiResponseHolder);
			if (errorText.toLowerCase().contains("property 'responsiblecostcenter' at offset")
					&& errorText.toLowerCase().contains("has invalid value '1010150100000'")) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * This Method Verifies whether the WBS Hierarchy is Successfully Created
	 * 
	 */
	public boolean verifySamplesample() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();

		String projName = "Proj_" + time;
		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";
		
		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP05");

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB700");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB700");
			list.add(request1);
			list.add(request2);

			apiResponseHolder = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700", "2019-10-07T00:00:00",
					"2019-12-31T00:00:00", "10101501", "YB700", list, createProjectResponse.getProjectUUID());

			if (apiResponseHolder.getStatusCode() == 201) {
				String newResponse1 = sanitizeOutput(apiResponseHolder.getResponse());
				CreateWBSElementsResponse createWBSResponse = gson.fromJson(newResponse1,
						CreateWBSElementsResponse.class);

				System.out.println(createWBSResponse);
				System.out.println("Project UUID ---->" + createWBSResponse.getProjectUUID());
				
				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();
				
				System.out.println("result--->" + results);
				System.out.println(results.get(0).getProjectElement());
				System.out.println(results.get(1).getProjectElement());

				if (results.size() == 2 && results.get(1).getProfitCenter().equals("YB600") && results.get(0).getProfitCenter().equals("YB600")) {
					return true;
				}
			}

		}
		return false;
	}
	
	/**
	 * This Method is CREATE API for BLOCK FUNCTION
	 * for PST level
	 * 
	 */
	public ApiResponseHolder createBlockFunctionForPSTAPI(String projectUUID, boolean EntProjTimeRecgIsBlkd, boolean EntProjStaffExpensePostgIsBlkd,
			boolean EntProjServicePostingIsBlkd, boolean EntProjOtherExpensePostgIsBlkd, boolean EntProjPurchasingIsBlkd) {

		CreateBlockFunctionsPSTRequest blockFunctionObject = new CreateBlockFunctionsPSTRequest();

		blockFunctionObject.setProjectUUID(projectUUID);
		blockFunctionObject.setEntProjServicePostingIsBlkd(EntProjServicePostingIsBlkd);
		blockFunctionObject.setEntProjTimeRecgIsBlkd(EntProjTimeRecgIsBlkd);
		blockFunctionObject.setEntProjStaffExpensePostgIsBlkd(EntProjStaffExpensePostgIsBlkd);
		blockFunctionObject.setEntProjOtherExpensePostgIsBlkd(EntProjOtherExpensePostgIsBlkd);
		blockFunctionObject.setEntProjPurchasingIsBlkd(EntProjPurchasingIsBlkd);
		
		String body = new GsonBuilder().create().toJson(blockFunctionObject);

		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPost(createBlockFunctionPSTUrl, body, getCsrfToken(),
				requestHeader);
		return apiResponseHolder;

	}
	
	/**
	 * This Method will verify Whether Block Function has been created
	 * successfully for PST in Created Status
	 * 
	 */
	public boolean verifyCreateBlockFunctionPST(boolean EntProjTimeRecgIsBlkd, boolean EntProjStaffExpensePostgIsBlkd,
			boolean EntProjServicePostingIsBlkd, boolean EntProjOtherExpensePostgIsBlkd,
			boolean EntProjPurchasingIsBlkd, String projectProfile) {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String myProjectUUID = "";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			myProjectUUID = createProjectResponse.getProjectUUID();

		}

		ApiResponseHolder apiresponseHolder2 = service.createBlockFunctionForPSTAPI(myProjectUUID,
				EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd);

		if (apiresponseHolder2.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse2 = sanitizeOutput(apiresponseHolder2.getResponse());

			CreateBlockFunctionsPSTResponse createblockFunctionresponse = gson.fromJson(newResponse2,
					CreateBlockFunctionsPSTResponse.class);

			System.out.println("**** repsonse is *****" +createblockFunctionresponse);
			
			if (createblockFunctionresponse.getEntProjOtherExpensePostgIsBlkd() == EntProjOtherExpensePostgIsBlkd
					&& createblockFunctionresponse.getEntProjServicePostingIsBlkd() == EntProjServicePostingIsBlkd
					&& createblockFunctionresponse.getEntProjStaffExpensePostgIsBlkd() == EntProjStaffExpensePostgIsBlkd
					&& createblockFunctionresponse.getEntProjTimeRecgIsBlkd() == EntProjTimeRecgIsBlkd
					&& createblockFunctionresponse.getEntProjPurchasingIsBlkd() == EntProjPurchasingIsBlkd) {

				return true;

			}
		}
		return false;

	}
	
	/**
	 * This methods verifies whether Single Block functions is
	 * created for OVH project
	 * 
	 */
	public boolean verifyCreateSingleBFPSTOVHProj() {
		String projectProfile = "YP03";
		boolean EntProjTimeRecgIsBlkd = true;
		boolean EntProjStaffExpensePostgIsBlkd = false;
		boolean EntProjServicePostingIsBlkd = false;
		boolean EntProjOtherExpensePostgIsBlkd = false;
		boolean EntProjPurchasingIsBlkd = false;
		
		boolean value = verifyCreateBlockFunctionPST(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);
		
		return value;
	}
	
	
	/**
	 * This Method will verify Whether Block Function has been Updated
	 * successfully for PST
	 * 
	 */
	public boolean verifyUpdateBlockFunctionPST(boolean EntProjTimeRecgIsBlkd, boolean EntProjStaffExpensePostgIsBlkd,
			boolean EntProjServicePostingIsBlkd, boolean EntProjOtherExpensePostgIsBlkd,
			boolean EntProjPurchasingIsBlkd, String projectProfile) {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String myProjectUUID = "";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			myProjectUUID = createProjectResponse.getProjectUUID();

		}

		ApiResponseHolder apiresponseHolder2 = service.createBlockFunctionForPSTAPI(myProjectUUID,
				EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd);

		if (apiresponseHolder2.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse2 = sanitizeOutput(apiresponseHolder2.getResponse());

			CreateBlockFunctionsPSTResponse createblockFunctionresponse = gson.fromJson(newResponse2,
					CreateBlockFunctionsPSTResponse.class);

			System.out.println("**** repsonse is *****" + createblockFunctionresponse);

			if (createblockFunctionresponse.getEntProjTimeRecgIsBlkd() == EntProjTimeRecgIsBlkd) {
//				boolean newTimeRecordingValue = false;
//				boolean newOtherExpensepostingvalue = true;

				CreateBlockFunctionsPSTRequest updateRequest = new CreateBlockFunctionsPSTRequest();
				updateRequest.setEntProjServicePostingIsBlkd(true);
				updateRequest.setEntProjTimeRecgIsBlkd(false);
				

				ApiResponseHolder apiResponseHolder3 = service.updatePSTBlockFunctionAPI(updateRequest, myProjectUUID);

				if (apiResponseHolder3.getStatusCode() == 204) {
					

					return true;

				}
				return false;
			}

		}
		return false;

	}
	
	/**
	 * This is a UPDATE API used for Updating Block function for PST
	 * 
	 */
	public ApiResponseHolder updatePSTBlockFunctionAPI(CreateBlockFunctionsPSTRequest request,
			String projectUUID) {
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPatch(
				UPDATE_PST_BLOCK_FUNCTION_URL.replace("{GUID}", projectUUID), body, csrf, requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}
	
	/**
	 * This methods verifies whether Single Block functions is
	 * created for OVH project
	 * 
	 */
	public boolean verifyUpdateMultipleBlockFunctionPST() {
		String projectProfile = "YP03";
		boolean EntProjTimeRecgIsBlkd = true;
		boolean EntProjStaffExpensePostgIsBlkd = false;
		boolean EntProjServicePostingIsBlkd = false;
		boolean EntProjOtherExpensePostgIsBlkd = true;
		boolean EntProjPurchasingIsBlkd = false;

		boolean value = verifyUpdateBlockFunctionPST(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd,
				EntProjServicePostingIsBlkd, EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);

		return value;
	}
	
	/**
	 * This methods verifies whether Multiple Block functions is
	 * created for INvestment project
	 * 
	 */
	public boolean verifyCreateMultipleBFPSTINVProj() {
		String projectProfile = "YP04";
		boolean EntProjTimeRecgIsBlkd = true;
		boolean EntProjStaffExpensePostgIsBlkd = true;
		boolean EntProjServicePostingIsBlkd = false;
		boolean EntProjOtherExpensePostgIsBlkd = true;
		boolean EntProjPurchasingIsBlkd = false;
		
		boolean value = verifyCreateBlockFunctionPST(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);
		
		return value;
	}
	
	/**
	 * This methods verifies whether Multiple Block functions is
	 * created for Statistical project
	 * 
	 */
	public boolean verifyCreateMultipleBFPSTSTATProj() {
		String projectProfile = "YP04";
		boolean EntProjTimeRecgIsBlkd = false;
		boolean EntProjStaffExpensePostgIsBlkd = true;
		boolean EntProjServicePostingIsBlkd = true;
		boolean EntProjOtherExpensePostgIsBlkd = false;
		boolean EntProjPurchasingIsBlkd = true;
		
		boolean value = verifyCreateBlockFunctionPST(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);
		
		return value;
	}
	
	/**
	 * This methods verifies whether Multiple Block functions is
	 * created for revenue project
	 * 
	 */
	public boolean verifyCreateMultipleBFPSTRevProj() {
		String projectProfile = "YP05";
		boolean EntProjTimeRecgIsBlkd = true;
		boolean EntProjStaffExpensePostgIsBlkd = true;
		boolean EntProjServicePostingIsBlkd = false;
		boolean EntProjOtherExpensePostgIsBlkd = true;
		boolean EntProjPurchasingIsBlkd = true;
		
		boolean value = verifyCreateBlockFunctionPST(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);
		
		return value;
	}
	
	/**
	 * This Method will verify Whether Block Function has been created
	 * successfully for PST in Released Status
	 * 
	 */
	public boolean verifyCreateBlockFunctionReleasedPST(boolean EntProjTimeRecgIsBlkd,
			boolean EntProjStaffExpensePostgIsBlkd, boolean EntProjServicePostingIsBlkd,
			boolean EntProjOtherExpensePostgIsBlkd, boolean EntProjPurchasingIsBlkd, String projectProfile) {
		Service service = new Service();

		Date date = new Date();
		long time = date.getTime();
		String projName = "Proj_" + time;
		String myProjectUUID = "";

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			myProjectUUID = createProjectResponse.getProjectUUID();

			CreateProjectRequest requestObjectt = new CreateProjectRequest();

			ApiResponseHolder apiResponseHoldeNew2 = service.setProjectStatusReleasedAPI(requestObjectt, myProjectUUID);

			if (!(apiResponseHoldeNew2.getStatusCode() == 200)) {
				return false;
			}

		}

		ApiResponseHolder apiresponseHolder2 = service.createBlockFunctionForPSTAPI(myProjectUUID,
				EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd);

		if (apiresponseHolder2.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse2 = sanitizeOutput(apiresponseHolder2.getResponse());

			CreateBlockFunctionsPSTResponse createblockFunctionresponse = gson.fromJson(newResponse2,
					CreateBlockFunctionsPSTResponse.class);

			System.out.println("**** repsonse is *****" + createblockFunctionresponse);

			if (createblockFunctionresponse.getEntProjOtherExpensePostgIsBlkd() == EntProjOtherExpensePostgIsBlkd
					&& createblockFunctionresponse.getEntProjServicePostingIsBlkd() == EntProjServicePostingIsBlkd
					&& createblockFunctionresponse.getEntProjStaffExpensePostgIsBlkd() == EntProjStaffExpensePostgIsBlkd
					&& createblockFunctionresponse.getEntProjTimeRecgIsBlkd() == EntProjTimeRecgIsBlkd
					&& createblockFunctionresponse.getEntProjPurchasingIsBlkd() == EntProjPurchasingIsBlkd) {

				return true;

			}
		}
		return false;

	}
	
	/**
	 * This methods verifies whether Multiple Block functions is
	 * created for Released project
	 * 
	 */
	public boolean verifyCreateMultipleBFPSTReleasedProj() {
		String projectProfile = "YP05";
		boolean EntProjTimeRecgIsBlkd = true;
		boolean EntProjStaffExpensePostgIsBlkd = true;
		boolean EntProjServicePostingIsBlkd = false;
		boolean EntProjOtherExpensePostgIsBlkd = true;
		boolean EntProjPurchasingIsBlkd = true;
		
		boolean value = verifyCreateBlockFunctionReleasedPST(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
				EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);
		
		return value;
	}
	
	/**
	 * This methods is Get API for values of
	 * Block Functions for PST
	 * 
	 */
	public ApiResponseHolder getPSTBlockFunctionsAPI(String projectUUID) {
		CreateBlockFunctionsPSTRequest createBFObj = new CreateBlockFunctionsPSTRequest();
		String body = new GsonBuilder().create().toJson(createBFObj);
//		
//		ApiResponseHolder apiResponseHolder = RestAssuredClient.doGet(GET_PST_BLOCK_FUNCTIONS_URL.replace("{pUUID}", projectUUID), getCsrfToken(), requestHeader);

		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doGet(GET_PST_BLOCK_FUNCTIONS_URL.replace("{pUUID}", projectUUID), getCsrfToken(), requestHeader);
		
		return apiResponseHolder;

	}
	
	/**
	 * This methods verifies GET Block functions 
	 *  for PST
	 * 
	 */
	public boolean verifyGetBFPST() {
		String projectUUID = "0894ef45-8f01-1eda-a4ba-4f851d052078";

		Service service = new Service();
		ApiResponseHolder apiResponseHolder = service.getPSTBlockFunctionsAPI(projectUUID);

		if (apiResponseHolder.getStatusCode() == 200) {
			Gson gson = new Gson();
			String newResponse2 = sanitizeOutput(apiResponseHolder.getResponse());

			CreateBlockFunctionsPSTResponse createblockFunctionresponse = gson.fromJson(newResponse2,
					CreateBlockFunctionsPSTResponse.class);

			System.out.println("**** repsonse is *****" + createblockFunctionresponse);

			if (createblockFunctionresponse.getEntProjOtherExpensePostgIsBlkd() == false
					&& createblockFunctionresponse.getEntProjServicePostingIsBlkd() == true
					&& createblockFunctionresponse.getEntProjStaffExpensePostgIsBlkd() == false
					&& createblockFunctionresponse.getEntProjTimeRecgIsBlkd() == true
					&& createblockFunctionresponse.getEntProjPurchasingIsBlkd() == false) {

				return true;
			}
		}
		return false;

	}
	
	/**
	 * This methods verifies GET Block functions 
	 *  for Released PST
	 * 
	 */
	public boolean verifyGetBFReleasedPST() {
		String projectUUID = "0894ef45-7d01-1eda-a4ba-8e82425ceefa";

		Service service = new Service();
		ApiResponseHolder apiResponseHolder = service.getPSTBlockFunctionsAPI(projectUUID);

		if (apiResponseHolder.getStatusCode() == 200) {
			Gson gson = new Gson();
			String newResponse2 = sanitizeOutput(apiResponseHolder.getResponse());

			CreateBlockFunctionsPSTResponse createblockFunctionresponse = gson.fromJson(newResponse2,
					CreateBlockFunctionsPSTResponse.class);

			System.out.println("**** repsonse is *****" + createblockFunctionresponse);

			if (createblockFunctionresponse.getEntProjOtherExpensePostgIsBlkd() == false
					&& createblockFunctionresponse.getEntProjServicePostingIsBlkd() == true
					&& createblockFunctionresponse.getEntProjStaffExpensePostgIsBlkd() == false
					&& createblockFunctionresponse.getEntProjTimeRecgIsBlkd() == true
					&& createblockFunctionresponse.getEntProjPurchasingIsBlkd() == false) {

				return true;
			}
		}
		return false;

	}
	
	/**
	 * This methods verifies GET Block functions 
	 *  for Deleted PST
	 * 
	 */
	public boolean verifyGetBFDeletedPST() {
		String projectUUID = "0894ef45-8f01-1eda-a4ba-9c483e47e574";

		Service service = new Service();
		ApiResponseHolder apiResponseHolder = service.getPSTBlockFunctionsAPI(projectUUID);

		if (apiResponseHolder.getStatusCode() == 404) {

			String errorText = getErrorText(apiResponseHolder);
			if (errorText.toLowerCase().equals("resource not found for segment 'a_enterpriseprojblkfunctype'")) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * This Method is CREATE API for BLOCK FUNCTION
	 * for WBS level
	 * 
	 */
	public ApiResponseHolder createWBSBlockFunctionAPI(String projectElementUUID, boolean EntProjTimeRecgIsBlkd, boolean EntProjStaffExpensePostgIsBlkd,
			boolean EntProjServicePostingIsBlkd, boolean EntProjOtherExpensePostgIsBlkd, boolean EntProjPurchasingIsBlkd) {

		CreateBlockFunctionWBSRequest blockFunctionObject = new CreateBlockFunctionWBSRequest();

		blockFunctionObject.setProjectElementUUID(projectElementUUID);
		blockFunctionObject.setEntProjServicePostingIsBlkd(EntProjServicePostingIsBlkd);
		blockFunctionObject.setEntProjTimeRecgIsBlkd(EntProjTimeRecgIsBlkd);
		blockFunctionObject.setEntProjStaffExpensePostgIsBlkd(EntProjStaffExpensePostgIsBlkd);
		blockFunctionObject.setEntProjOtherExpensePostgIsBlkd(EntProjOtherExpensePostgIsBlkd);
		blockFunctionObject.setEntProjPurchasingIsBlkd(EntProjPurchasingIsBlkd);
		
		String body = new GsonBuilder().create().toJson(blockFunctionObject);

		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPost(Create_Block_Function_WBS_URl, body, getCsrfToken(),
				requestHeader);
		return apiResponseHolder;

	}
	

	/**
	 * This Method Verifies Creation of Block Function
	 * for WBS level
	 * 
	 */
	public boolean verifyCreateWBSBlockFunctionsOVH(boolean EntProjTimeRecgIsBlkd,
			boolean EntProjStaffExpensePostgIsBlkd, boolean EntProjServicePostingIsBlkd,
			boolean EntProjOtherExpensePostgIsBlkd, boolean EntProjPurchasingIsBlkd, String projectProfile) {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		String myProjectElementUUID = "";

		String projName = "Proj_" + time;
		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";

		System.out.println("Project name was --->" + projName);

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", projectProfile);

		if (apiResponseHolder.getStatusCode() == 201) {
			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName, "firstChildWBSName",
					"750", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2019-10-07T00:00:00", "2019-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);

			apiResponseHolder = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700", "2019-10-07T00:00:00",
					"2019-12-31T00:00:00", "10101501", "YB600", list, createProjectResponse.getProjectUUID());

			if (apiResponseHolder.getStatusCode() == 201) {
				String newResponse1 = sanitizeOutput(apiResponseHolder.getResponse());
				CreateWBSElementsResponse createWBSResponse = gson.fromJson(newResponse1,
						CreateWBSElementsResponse.class);

				System.out.println(createWBSResponse);
				System.out.println("Project UUID ---->" + createWBSResponse.getProjectUUID());

				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();
				if (results.size() == 2) {
					myProjectElementUUID = results.get(1).getProjectElementUUID();

					System.out.println("result--->" + results);
					System.out.println(results.get(1).getProjectElementDescription());
					System.out.println(results.get(1).getProjectElementUUID());

					ApiResponseHolder apiresponseHolder2 = service.createWBSBlockFunctionAPI(myProjectElementUUID,
							EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd, EntProjServicePostingIsBlkd,
							EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd);

					if (apiresponseHolder2.getStatusCode() == 201) {
						// Gson gson = new Gson();
						String newResponse2 = sanitizeOutput(apiresponseHolder2.getResponse());

						CreateBlockFunctionWBSResponse createBlockFunctionWBSResponse = gson.fromJson(newResponse2,
								CreateBlockFunctionWBSResponse.class);

						System.out.println("**** repsonse is *****" + createBlockFunctionWBSResponse);

						if (createBlockFunctionWBSResponse
								.getEntProjOtherExpensePostgIsBlkd() == EntProjOtherExpensePostgIsBlkd
								&& createBlockFunctionWBSResponse
										.getEntProjServicePostingIsBlkd() == EntProjServicePostingIsBlkd
								&& createBlockFunctionWBSResponse
										.getEntProjStaffExpensePostgIsBlkd() == EntProjStaffExpensePostgIsBlkd
								&& createBlockFunctionWBSResponse.getEntProjTimeRecgIsBlkd() == true
								&& createBlockFunctionWBSResponse
										.getEntProjPurchasingIsBlkd() == EntProjPurchasingIsBlkd) {

							return true;

						}
						return false;
					}
					return false;
				}
				return false;
			}
			return false;
		}

		return false;

	}
	
	/**
	 * This methods verifies whether Multiple Block functions is
	 * created for OVH WBS 
	 * 
	 */
	public boolean verifyCreateWBSBlockFunctionOVH() {
		String projectProfile = "YP03";
		boolean EntProjTimeRecgIsBlkd = true;
		boolean EntProjStaffExpensePostgIsBlkd = false;
		boolean EntProjServicePostingIsBlkd = false;
		boolean EntProjOtherExpensePostgIsBlkd = true;
		boolean EntProjPurchasingIsBlkd = false;

		boolean value = verifyCreateWBSBlockFunctionsOVH(EntProjTimeRecgIsBlkd, EntProjStaffExpensePostgIsBlkd,
				EntProjServicePostingIsBlkd, EntProjOtherExpensePostgIsBlkd, EntProjPurchasingIsBlkd, projectProfile);

		return value;
	}

	
	


}
