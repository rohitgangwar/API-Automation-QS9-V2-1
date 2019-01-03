package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import eppmAPIFramework.beans.ApiResponseHolder;
import eppmAPIFramework.com.rest.responsepojo.CreateProjectResponse;
import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test Cases for 
 * CREATE PROJECT SCENARIOS
 * 
 */
public class CreateProjectTest {

	Service service = new Service();

	/**
	 * Assert to Validate Creation of Investment Project
	 * 
	 */
	@Test
	public void canCreateProject() {
		Assert.assertTrue(service.verifyCreateProject(),
				"Create Project API Failed for Investment Project. The Project ID in the Response does not match with the Project ID used to Create the Project");
	}

}
