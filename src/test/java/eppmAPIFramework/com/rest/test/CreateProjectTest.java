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
	@Test(priority=1)
	public void canCreateInvestmentProject() {
		Assert.assertTrue(service.verifyCreateInvestmentProject(),
				"Create Project API Failed for Investment Project. The Project ID in the Response does not match with the Project ID used to Create the Project");
	}
	
	/**
	 * Assert to Validate Creation of Overhead Project
	 * 
	 */
	@Test(priority=2)
	public void canCreateOverheadProject() {
		Assert.assertTrue(service.verifyCreateOverheadProject(),
				"Create Project API Failed for Overhead Project. The Project ID in the Response does not match with the Project ID used to Create the Project");
	}
	
	/**
	 * Assert to Validate Creation of Statistical Project
	 * 
	 */
	@Test(priority=3)
	public void canCreateStatisticalProject() {
		Assert.assertTrue(service.verifyCreateStatisticalProject(),
				"Create Project API Failed for Statistical Project. The Project ID in the Response does not match with the Project ID used to Create the Project");
	}
	
	/**
	 * Assert to Validate Creation of Revenue Project
	 * 
	 */
	@Test(priority=4)
	public void canCreateRevenueProject() {
		Assert.assertTrue(service.verifyCreateRevenueProject(),
				"Create Project API Failed for Revenue Project. The Project ID in the Response does not match with the Project ID used to Create the Project");
	}
	
//	/**
//	 * Assert to Validate Creation of Multiple Investment Project
//	 * 
//	 */
//	@Test(priority=5)
//	public void canCreateMultipleInvestmentProject() {
//		Assert.assertTrue(service.verifyCreateMultipleInvestmentProject(),
//				"Create Project API Failed for Multiple Investment Project Creation. The Project ID in the Response does not match with the Project ID used to Create the Project");
//	}
//	
//	/**
//	 * Assert to Validate Creation of Multiple Investment Project
//	 * 
//	 */
//	@Test(priority=6)
//	public void canCreateMultipleOverheadProject() {
//		Assert.assertTrue(service.verifyCreateMultipleOverheadProject(),
//				"Create Project API Failed for Multiple Overhead Project Creation. The Project ID in the Response does not match with the Project ID used to Create the Project");
//	}
//	
//	/**
//	 * Assert to Validate Creation of Multiple Statistical Project
//	 * 
//	 */
//	@Test(priority=7)
//	public void canCreateMultipleStatisticalProject() {
//		Assert.assertTrue(service.verifyCreateMultipleStatisticalProject(),
//				"Create Project API Failed for Multiple Statistical Project Creation. The Project ID in the Response does not match with the Project ID used to Create the Project");
//	}
//	
//	/**
//	 * Assert to Validate Creation of Multiple Statistical Project
//	 * 
//	 */
//	@Test(priority=8)
//	public void canCreateMultipleRevenueProject() {
//		Assert.assertTrue(service.verifyCreateMultipleRevenueProject(),
//				"Create Project API Failed for Multiple Revenue Project Creation. The Project ID in the Response does not match with the Project ID used to Create the Project");
//	}

}
