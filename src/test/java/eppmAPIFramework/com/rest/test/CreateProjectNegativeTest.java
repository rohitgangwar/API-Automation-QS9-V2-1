package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test Cases for 
 * CREATE PROJECT Negative SCENARIOS
 * 
 */
public class CreateProjectNegativeTest {
	
	Service service = new Service();

	
	/**
	 * Test Case to validate Project Creation for Incorrect value for Project Profile
	 * 
	 */
	@Test(priority = 1)
	public void canNotCreateProjInvalidProfile() {
		Assert.assertTrue(service.validateIncorrectProjProferror(),
				"Test Case failed for Creation of Project for Invalid Project Profile");
	}
	
	/**
	 * Test Case to validate Project Creation for Incorrect value for Profit Center
	 * 
	 */
	@Test(priority = 2)
	public void canNotCreateProjInvalidProfitCenter() {
		Assert.assertTrue(service.validateInvalidProfitCenterError(),
				"Test Case failed for Creation of Project for Invalid Profit Center");
	}
	
	/**
	 * Test Case to validate Project Creation for Incorrect value for Project ID
	 * 
	 */
	@Test(priority = 3)
	public void canNotCreateProjInvalidProjectID() {
		Assert.assertTrue(service.validateInvalidProjectIDError(),
				"Test Case failed for Creation of Project for Invalid Project ID");
	}
	
	/**
	 * Test Case to validate Project Creation for Incorrect value for Dates
	 * 
	 */
	@Test(priority = 4)
	public void canNotCreateProjInvalidStartDate() {
		Assert.assertTrue(service.validateInvalidDateError(),
				"Test Case failed for Creation of Project for Invalid Start Dates");
	}
	
	/**
	 * Test Case to validate Project Creation for Incorrect value for Responsible Cost center
	 * 
	 */
	@Test(priority = 5)
	public void canNotCreateProjInvalidRespCostCenter() {
		Assert.assertTrue(service.validateInvalidRespCCError(),
				"Test Case failed for Creation of Project for Invalid Responsible Cost Center");
	}

}
