package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

public class DeleteProjectTest {
  
Service service = new Service();
	
	/**
     * Assert to verify to Add the Qualify Instance 
     * 
     */
	@Test
	public void canDeleteProject() {
		Assert.assertTrue(service.verifyDeleteProject(), "Delete Project API Failed");
	}	
	
}
