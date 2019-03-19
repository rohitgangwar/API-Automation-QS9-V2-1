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
	public void canCreateWBSHierarchyForRELProj() {
		Assert.assertTrue(service.verifyCreateHierarchyForRELProj(), "WBS Hierarchy creation Failed for Released Project");
	}
	
	/**
	 * Assert to validate the Creation of Hierarchy for Project in TCHO Status
	 * 
	 */
	@Test(priority = 3)
	public void canCreateWBSHierarchyForTCHOProj() {
		Assert.assertTrue(service.verifyCreateHierarchyForTCHOProj(), "WBS Hierarchy creation Failed for TCHO Project");
	}
	
	/**
	 * Assert to validate the Creation of Hierarchy for Project in LOCKED Status
	 * 
	 */
	@Test(priority = 4)
	public void canCreateWBSHierarchyForLOCKEDProj() {
		Assert.assertTrue(service.verifyCreateHierarchyForLOCKEDProj(), "WBS Hierarchy creation Failed for LOCKED Project");
	}
	
	/**
	 * Assert to validate the Creation of Hierarchy for Project in CLOSED Status
	 * 
	 */
	@Test(priority = 5)
	public void canCreateWBSHierarchyForCLOSEDProj() {
		Assert.assertTrue(service.verifyCreateHierarchyForCLOSEDProj(), "WBS Hierarchy creation Failed for CLOSED Project");
	}

}
