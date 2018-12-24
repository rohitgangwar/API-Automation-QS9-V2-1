package eppmAPIFramework.com.rest.service;

public class ServiceUrl {
	
	public final static  String createProjectUrl =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/sap/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProject";
	public final static  String GET_SYSTEM_DETAILS =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/sap/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProject";
	public final static  String GET_SYSTEM_DETAILS1 =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/sap/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProjectElement?$filter=ProjectElement eq 'TEST_QW12345_PJT1_1'";
	public final static  String CREATE_WBS_ELEMENTS_URL =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/SAP/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProject(guid'{GUID}')/to_EnterpriseProjectElement";

	public final static  String GET_PROJECTDETAILS_URL =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/sap/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProject?$filter=Project eq '{pName}'";

	
	public final static  String UPDATE_WBS_ELEMENT_ATTRIBUTES_URL =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/sap/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProjectElement(guid'{GUID}')";

	public final static  String DELETE_PROJECT_URL =  "https://ccf-715-api.wdf.sap.corp/sap/opu/odata/sap/API_ENTERPRISE_PROJECT_SRV/A_EnterpriseProject(guid'{GUID}')";

}
