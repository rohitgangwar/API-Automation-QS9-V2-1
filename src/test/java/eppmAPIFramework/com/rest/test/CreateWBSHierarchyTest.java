package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all the Test Cases for Creating Hierarchy 
 * of WBS Elements fro different Project Status Scenarios
 * 
 */
public class CreateWBSHierarchyTest {

	Service service = new Service();

	/**
	 * Assert to validate the Creation of Hierarchy for Project in Created Status
	 * 
	 */
	@Test(priority = 1)
	public void canCreateWBSHierarchy() {
		Assert.assertTrue(service.verifyCreateWBSHierarchy(),
				"Creation of WBS Elements Hierarchy Failed for Project in Created Status");
	}

	/**
	 * Assert to validate the Creation of Hierarchy for Project in RELEASED Status
	 * 
	 */
	@Test(priority = 2)
	public void canCreateWBSHierarchyRELEASEDProj() {
		Assert.assertTrue(service.verifyCreateHierarchyRELProj(), "WBS Hierarchy creation Failed for Released Project");
	}

}
