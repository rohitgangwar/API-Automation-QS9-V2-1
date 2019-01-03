package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test cases for DELETION of Project/WBS
 * 
 */
public class DeleteProjectTest {

	Service service = new Service();

	/**
	 * Assert to Validate Project Deletion Scenario
	 * 
	 */
	@Test
	public void canDeleteProject() {
		Assert.assertTrue(service.verifyDeleteProject(), "Delete Project API Failed");
	}

}
