package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all the Test Cases for Creating Hierarchy 
 * of WBS Elements
 * 
 */
public class CreateWBSHierarchyTest {

	Service service = new Service();

	/**
	 * Assert to validate the Creation of Hierarchy for Project in Created Status
	 * 
	 */
	@Test
	public void canCreateWBSHierarchy() {
		Assert.assertTrue(service.verifyCreateWBSHierarchy(),
				"Creation of WBS Elements Hierarchy Failed for Project in Created Status");
	}

	// /**
	// * Assert to verify to Add the Qualify Instance
	// *
	// */
	// @Test
	// public void canCreateWBSHierarchyRELProj() {
	// Assert.assertTrue(service.verifyProjectStatusRelease(), "Project status not
	// chnaged");
	// }

}
