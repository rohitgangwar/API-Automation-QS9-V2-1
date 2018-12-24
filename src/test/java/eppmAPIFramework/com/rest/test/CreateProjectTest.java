package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import eppmAPIFramework.beans.ApiResponseHolder;
import eppmAPIFramework.com.rest.responsepojo.CreateProjectResponse;
import eppmAPIFramework.com.rest.service.Service;

public class CreateProjectTest {
	
	Service service = new Service();
	
	/**
     * Assert to verify to Add the Qualify Instance 
     * 
     */
	@Test
	public void canCreateProject() {
		Assert.assertTrue(service.verifyCreateProject(),
				"Create Project API Failed. The Project ID in the Response does not match with the Project ID used to Create the Project");
	}		
	
	  
  
}
