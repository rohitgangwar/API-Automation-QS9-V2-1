package eppmAPIFramework.com.rest.responsepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Response POJO Class for Create Project Scenarios.
 * This class contains all the attributes for the Create Project Response
 * 
 */
public class CreateProjectResponse {

	@SerializedName("ProjectUUID")
	@Expose
	private String projectUUID;

	@SerializedName("ProjectInternalID")
	@Expose
	private String projectInternalID;

	@SerializedName("Project")
	@Expose
	private String project;

	@SerializedName("ProjectDescription")
	@Expose
	private String projectDescription;

	@SerializedName("EnterpriseProjectType")
	@Expose
	private String enterpriseProjectType;

	@SerializedName("PriorityCode")
	@Expose
	private String priorityCode;

	@SerializedName("ProjectStartDate")
	@Expose
	private String projectStartDate;

	@SerializedName("ProjectEndDate")
	@Expose
	private String projectEndDate;

	@SerializedName("ProcessingStatus")
	@Expose
	private String processingStatus;

	@SerializedName("ResponsibleCostCenter")
	@Expose
	private String responsibleCostCenter;

	@SerializedName("ProfitCenter")
	@Expose
	private String profitCenter;

	@SerializedName("ProjectManagerUUID")
	@Expose
	private String projectManagerUUID;

	@SerializedName("ProjectProfileCode")
	@Expose
	private String projectProfileCode;

	@SerializedName("FunctionalArea")
	@Expose
	private String functionalArea;

	@SerializedName("CompanyCode")
	@Expose
	private String companyCode;

	@SerializedName("ControllingArea")
	@Expose
	private String controllingArea;

	@SerializedName("LastChangeDateTime")
	@Expose
	private String lastChangeDateTime;

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getProjectInternalID() {
		return projectInternalID;
	}

	public void setProjectInternalID(String projectInternalID) {
		this.projectInternalID = projectInternalID;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getEnterpriseProjectType() {
		return enterpriseProjectType;
	}

	public void setEnterpriseProjectType(String enterpriseProjectType) {
		this.enterpriseProjectType = enterpriseProjectType;
	}

	public String getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public String getResponsibleCostCenter() {
		return responsibleCostCenter;
	}

	public void setResponsibleCostCenter(String responsibleCostCenter) {
		this.responsibleCostCenter = responsibleCostCenter;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getProjectManagerUUID() {
		return projectManagerUUID;
	}

	public void setProjectManagerUUID(String projectManagerUUID) {
		this.projectManagerUUID = projectManagerUUID;
	}

	public String getProjectProfileCode() {
		return projectProfileCode;
	}

	public void setProjectProfileCode(String projectProfileCode) {
		this.projectProfileCode = projectProfileCode;
	}

	public String getFunctionalArea() {
		return functionalArea;
	}

	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getControllingArea() {
		return controllingArea;
	}

	public void setControllingArea(String controllingArea) {
		this.controllingArea = controllingArea;
	}

	public String getLastChangeDateTime() {
		return lastChangeDateTime;
	}

	public void setLastChangeDateTime(String lastChangeDateTime) {
		this.lastChangeDateTime = lastChangeDateTime;
	}

	@Override
	public String toString() {
		return "CreateProjectResponse [projectUUID=" + projectUUID + ", projectInternalID=" + projectInternalID
				+ ", project=" + project + ", projectDescription=" + projectDescription + ", enterpriseProjectType="
				+ enterpriseProjectType + ", priorityCode=" + priorityCode + ", projectStartDate=" + projectStartDate
				+ ", projectEndDate=" + projectEndDate + ", processingStatus=" + processingStatus
				+ ", responsibleCostCenter=" + responsibleCostCenter + ", profitCenter=" + profitCenter
				+ ", projectManagerUUID=" + projectManagerUUID + ", projectProfileCode=" + projectProfileCode
				+ ", functionalArea=" + functionalArea + ", companyCode=" + companyCode + ", controllingArea="
				+ controllingArea + ", lastChangeDateTime=" + lastChangeDateTime + "]";
	}

}