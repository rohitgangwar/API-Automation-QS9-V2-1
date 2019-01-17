package eppmAPIFramework.com.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import eppmAPIFramework.com.rest.requestpojo.CreateProjectRequest;
import eppmAPIFramework.com.rest.requestpojo.CreateWBSElementsRequest;
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

//	/**
//	 * This method contains the AUTHORIZATION Key for CCF/715 SYSTEM Post call by API
//	 * 
//	 */
//	static Map<String, String> requestHeader = new HashMap();
//	{
//		requestHeader.put("Authorization", "Basic Q0NGX0NPTU1fMDMwODpXZWxjb21lMSE=");
//
//	}
	
	/**
	 * This method contains the AUTHORIZATION Key for CC2/715 SYSTEM Post call by API
	 * 
	 */
	static Map<String, String> requestHeader = new HashMap();
	{
		requestHeader.put("Authorization", "Basic Q0MyX0NPTU1fMDMwODpXZWxjb21lMSE=");

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
				"1c98ec18-1855-1ee9-85b5-3ab9e93bf020");

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
	 * This method verifies if the Newly Created project Gets Deleted or not
	 * 
	 */
	public boolean verifyDeleteProject() {
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

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2019-10-07T00:00:00", "2019-12-31T00:00:00", "YB600", "10101501", "YP02");

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
				System.out.println("Company Code ---->" + createWBSResponse.getCompanyCode());
				System.out.println("Project UUID ---->" + createWBSResponse.getProjectUUID());
				
				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();
				
				System.out.println("result--->" + results);
				System.out.println(results.get(0).getProjectElement());
				System.out.println(results.get(1).getProjectElement());

				if ((results.size() == 2) && (results.get(0).getProjectElement().equalsIgnoreCase(firstChildWBSName))
						&& (results.get(1).getProjectElement().equalsIgnoreCase(secondChildWBSWBSName))) {
					return true;
				}
			}

		}
		return false;
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
	 * This methods verifies whether the Project Status is Released or Not
	 * 
	 */
	public boolean verifyCreateHierarchyRELProj() {
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

				if ((results.size() == 2) && (results.get(0).getProjectElement().equalsIgnoreCase(firstChildWBSName))
						&& (results.get(1).getProjectElement().equalsIgnoreCase(secondChildWBSWBSName))) {
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

}
