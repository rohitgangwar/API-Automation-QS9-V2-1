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

public class Service {
	private static Logger logger = Logger.getLogger("Service");
	
	private static String csrfToken = null;
	
	static Map<String, String> requestHeader = new HashMap();
	{
		requestHeader.put("Authorization", "Basic Q0NGX0NPTU1fMDMwODpXZWxjb21lMSE=");

	}

	public static String getCsrfToken() {
		if (csrfToken == null) {
			int count = 0;
			while (count++ < 5) {
				ApiResponseHolder apiResponseHolder = RestAssuredClient.doGet(GET_SYSTEM_DETAILS, "FETCH", requestHeader);
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
	 * This API will create Person
	 * 
	 * @param name
	 * @param surname
	 * @param city
	 * @param landmark
	 * @param state
	 * @param zipcode
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

		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPost(createProjectUrl, body, getCsrfToken(), requestHeader);
		return apiResponseHolder;

	}
	
	
	// Get the Project Details //
	
	public ApiResponseHolder getProjectDetails(String projectName) {

		ApiResponseHolder apiResponseHolder = RestAssuredClient.doGet(GET_PROJECTDETAILS_URL.replace("{pName}", projectName), getCsrfToken(), null);
		return apiResponseHolder;

	}

	public static String sanitizeOutput(String body) {
		HashMap<String, Object> objectVal = new GsonBuilder().create().fromJson(body, HashMap.class);
		logger.info("Map has: " + objectVal.keySet());
		LinkedTreeMap<String, Object> map = (LinkedTreeMap) objectVal.get("d");
		return new GsonBuilder().create().toJson(map);
	}


	
	public ApiResponseHolder createWBSElementsAPI(String projectElement, String projectElementDescription, String sortingNumber,
			String plannedStartDate, String plannedEndDate, String responsibleCostCenter, String profitCenter, List<CreateWBSElementsRequest> to_SubProjElement, String projectElementUUID) {

		CreateWBSElementsRequest createWBSElementsRequestObj = new CreateWBSElementsRequest(projectElement,projectElementDescription,sortingNumber,plannedStartDate
				,plannedEndDate,responsibleCostCenter,profitCenter);
		createWBSElementsRequestObj.setTo_SubProjElement(to_SubProjElement);
		
		String body = new GsonBuilder().create().toJson(createWBSElementsRequestObj);
		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPost(CREATE_WBS_ELEMENTS_URL.replace("{GUID}", projectElementUUID), body, getCsrfToken(), requestHeader);
		return apiResponseHolder;

	}
	
	public boolean verifyCreateProject() {
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

			System.out.println(createProjectResponse);
			System.out.println("Project ID is ---->" + createProjectResponse.getProject());
			System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());
			
			if (!createProjectResponse.getProject().equalsIgnoreCase(projName)) {
				return false;
			}

		}

		return true;
	}
	
	// PATCH //
	
	public ApiResponseHolder updateWBSElementAttributesAPI(CreateWBSElementsRequest request, String projectElementUUID) {
		
		String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient.doPatch(UPDATE_WBS_ELEMENT_ATTRIBUTES_URL.replace("{GUID}", projectElementUUID), body,csrf , requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}
	
	
	// PATCH MAIN //
	
	
	public boolean verifyUpdateWBSAttributeDescription() {
		Service service = new Service();
		CreateWBSElementsRequest request = new CreateWBSElementsRequest();
		request.setProjectElementDescription("updated description 88");
		
		ApiResponseHolder apiResponseHolder = service.updateWBSElementAttributesAPI(request, "9418820a-c0a9-1ed9-81d5-adf9860e8076");
		
		return apiResponseHolder.getStatusCode() == 204;
		
		
	}
	
	
	
	
	
	
	
	// CREATE PROJECT //
	
//	public static void main(String[] args) {
//		Service service = new Service();
//		ApiResponseHolder apiResponseHolder = service.createProjectAPI("test_qweasd12", "test_demo qweasd12",
//				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP02");
//
//		if (apiResponseHolder.getStatusCode() == 201) {
//
//			Gson gson = new Gson();
//			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
//			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);
//
//			System.out.println(createProjectResponse);
//			System.out.println("Project ID is ---->" + createProjectResponse.getProject());
//			System.out.println("Project UUID is ---->" + createProjectResponse.getProjectUUID());
//
//			Assert.assertTrue(createProjectResponse.getProject().equalsIgnoreCase("test_qweasd12"));
//		}
//	}
	
	
	
	
	//  GET PROJECT DETAILS //
	
//	public static void main(String[] args) {
//		Service service = new Service();
//		ApiResponseHolder apiResponseHolder = service.getProjectDetails(projectName);
//
//		if (apiResponseHolder.getStatusCode() == 201) {
//
//			Gson gson = new Gson();
//			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
//			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);
//
//			System.out.println(createProjectResponse);
//			System.out.println("Project-->" + createProjectResponse.getProject());
//			System.out.println("Project UUID--->" + createProjectResponse.getProjectUUID());
//
//			Assert.assertTrue(createProjectResponse.getProject().equalsIgnoreCase("test_the9876789214"));
//		}
//	}
	
	// DELETE CALL //
	
	
	public ApiResponseHolder deleteProjectAPI(String projectUUID) {

	//	String body = new GsonBuilder().create().toJson(request);
		String csrf = getCsrfToken();
		requestHeader.put("If-Match", "*");
		ApiResponseHolder apiResponseHolder = RestAssuredClient
				.doDelete(DELETE_PROJECT_URL.replace("{GUID}", projectUUID), csrf, requestHeader);
		requestHeader.remove("If-Match");
		return apiResponseHolder;

	}
	
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

			if (apiResponseHoldeNew.getStatusCode() == 206) {
				return true;
			}
		}

		return false;

	}
	
    // WBS HIERARCHY //
	
	public boolean verifyCreateWBSHierarchy() {
		Service service = new Service();
		Date date = new Date();
		long time = date.getTime();
		
		String projName = "Proj_" + time;
		String parentWBSName = "Test_" + time + "_PJT1";
		String firstChildWBSName = "Test_" + time + "_1";
		String secondChildWBSWBSName = "Test_" + time + "_2";
		

		ApiResponseHolder apiResponseHolder = service.createProjectAPI(projName, "Proj_Description",
				"2018-10-07T00:00:00", "2018-12-31T00:00:00", "YB600", "10101501", "YP02");

		if (apiResponseHolder.getStatusCode() == 201) {

			Gson gson = new Gson();
			String newResponse = sanitizeOutput(apiResponseHolder.getResponse());
			CreateProjectResponse createProjectResponse = gson.fromJson(newResponse, CreateProjectResponse.class);

			List<CreateWBSElementsRequest> list = new ArrayList<CreateWBSElementsRequest>();
			CreateWBSElementsRequest request1 = new CreateWBSElementsRequest(firstChildWBSName,
					"firstChildWBSName", "750", "2018-10-07T00:00:00", "2018-12-31T00:00:00", "10101501", "YB600");
			CreateWBSElementsRequest request2 = new CreateWBSElementsRequest(secondChildWBSWBSName,
					"secondChildWBSWBSName", "760", "2018-10-07T00:00:00", "2018-12-31T00:00:00", "10101501", "YB600");
			list.add(request1);
			list.add(request2);
			apiResponseHolder = service.createWBSElementsAPI(parentWBSName, parentWBSName, "700",
					"2018-10-07T00:00:00", "2018-12-31T00:00:00", "10101501", "YB600", list,
					createProjectResponse.getProjectUUID());

			if (apiResponseHolder.getStatusCode() == 201) {
				String newResponse1 = sanitizeOutput(apiResponseHolder.getResponse());
				CreateWBSElementsResponse createWBSResponse = gson.fromJson(newResponse1,
						CreateWBSElementsResponse.class);

				System.out.println(createWBSResponse);
				System.out.println("Company Code ---->" + createWBSResponse.getCompanyCode());
				System.out.println("Project UUID ---->" + createWBSResponse.getProjectUUID());
				List<CreateWBSElementsResponse> results = createWBSResponse.getTo_SubProjElement().getResults();
				System.out.println("result--->" + results);
				
				if (results.size() == 2) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	

}
