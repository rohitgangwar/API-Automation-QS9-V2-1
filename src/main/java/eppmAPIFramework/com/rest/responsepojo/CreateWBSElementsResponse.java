package eppmAPIFramework.com.rest.responsepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Response POJO Class for Create WBS Elements Scenarios.
 * This class contains the attributes for Response of Create WBS Element Scenario
 * 
 */
public class CreateWBSElementsResponse {

	@SerializedName("ProjectElementUUID")
	@Expose
	private String projectElementUUID;

	@SerializedName("ProjectElement")
	@Expose
	private String projectElement;

	@SerializedName("WBSElementInternalID")
	@Expose
	private String wBSElementInternalID;

	@SerializedName("ProjectUUID")
	@Expose
	private String projectUUID;

	@SerializedName("ProjectElementDescription")
	@Expose
	private String projectElementDescription;

	@SerializedName("ParentObjectUUID")
	@Expose
	private String parentObjectUUID;

	@SerializedName("ProcessingStatus")
	@Expose
	private String processingStatus;

	@SerializedName("SortingNumber")
	@Expose
	private String sortingNumber;

	@SerializedName("PlannedStartDate")
	@Expose
	private String plannedStartDate;

	@SerializedName("PlannedEndDate")
	@Expose
	private String plannedEndDate;

	@SerializedName("ResponsibleCostCenter")
	@Expose
	private String responsibleCostCenter;

	@SerializedName("CompanyCode")
	@Expose
	private String companyCode;

	@SerializedName("ProfitCenter")
	@Expose
	private String profitCenter;

	@SerializedName("FunctionalArea")
	@Expose
	private String functionalArea;

	@SerializedName("ControllingArea")
	@Expose
	private String controllingArea;

	@SerializedName("Plant")
	@Expose
	private String plant;

	@SerializedName("FactoryCalendar")
	@Expose
	private String factoryCalendar;

	@SerializedName("CostingSheet")
	@Expose
	private String costingSheet;

	@SerializedName("InvestmentProfile")
	@Expose
	private String investmentProfile;

	@SerializedName("WBSIsStatisticalWBSElement")
	@Expose
	private Boolean wBSIsStatisticalWBSElement;

	@SerializedName("CostCenter")
	@Expose
	private String costCenter;

	@SerializedName("CreatedByUser")
	@Expose
	private String createdByUser;

	@SerializedName("CreationDateTime")
	@Expose
	private String creationDateTime;

	@SerializedName("LastChangeDateTime")
	@Expose
	private String lastChangeDateTime;

	@SerializedName("LastChangedByUser")
	@Expose
	private String lastChangedByUser;

	@SerializedName("ProjectLastChangedDateTime")
	@Expose
	private String projectLastChangedDateTime;

	@SerializedName("to_SubProjElement")
	@Expose
	private SubProjectResponse to_SubProjElement;

	public String getProjectElementUUID() {
		return projectElementUUID;
	}

	public void setProjectElementUUID(String projectElementUUID) {
		this.projectElementUUID = projectElementUUID;
	}

	public String getProjectElement() {
		return projectElement;
	}

	public void setProjectElement(String projectElement) {
		this.projectElement = projectElement;
	}

	public String getWBSElementInternalID() {
		return wBSElementInternalID;
	}

	public void setWBSElementInternalID(String wBSElementInternalID) {
		this.wBSElementInternalID = wBSElementInternalID;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getProjectElementDescription() {
		return projectElementDescription;
	}

	public void setProjectElementDescription(String projectElementDescription) {
		this.projectElementDescription = projectElementDescription;
	}

	public String getParentObjectUUID() {
		return parentObjectUUID;
	}

	public void setParentObjectUUID(String parentObjectUUID) {
		this.parentObjectUUID = parentObjectUUID;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public String getSortingNumber() {
		return sortingNumber;
	}

	public void setSortingNumber(String sortingNumber) {
		this.sortingNumber = sortingNumber;
	}

	public String getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public String getPlannedEndDate() {
		return plannedEndDate;
	}

	public void setPlannedEndDate(String plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}

	public String getResponsibleCostCenter() {
		return responsibleCostCenter;
	}

	public void setResponsibleCostCenter(String responsibleCostCenter) {
		this.responsibleCostCenter = responsibleCostCenter;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getFunctionalArea() {
		return functionalArea;
	}

	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
	}

	public String getControllingArea() {
		return controllingArea;
	}

	public void setControllingArea(String controllingArea) {
		this.controllingArea = controllingArea;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getFactoryCalendar() {
		return factoryCalendar;
	}

	public void setFactoryCalendar(String factoryCalendar) {
		this.factoryCalendar = factoryCalendar;
	}

	public String getCostingSheet() {
		return costingSheet;
	}

	public void setCostingSheet(String costingSheet) {
		this.costingSheet = costingSheet;
	}

	public String getInvestmentProfile() {
		return investmentProfile;
	}

	public void setInvestmentProfile(String investmentProfile) {
		this.investmentProfile = investmentProfile;
	}

	public Boolean getWBSIsStatisticalWBSElement() {
		return wBSIsStatisticalWBSElement;
	}

	public void setWBSIsStatisticalWBSElement(Boolean wBSIsStatisticalWBSElement) {
		this.wBSIsStatisticalWBSElement = wBSIsStatisticalWBSElement;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(String creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public String getLastChangeDateTime() {
		return lastChangeDateTime;
	}

	public void setLastChangeDateTime(String lastChangeDateTime) {
		this.lastChangeDateTime = lastChangeDateTime;
	}

	public String getLastChangedByUser() {
		return lastChangedByUser;
	}

	public void setLastChangedByUser(String lastChangedByUser) {
		this.lastChangedByUser = lastChangedByUser;
	}

	public String getProjectLastChangedDateTime() {
		return projectLastChangedDateTime;
	}

	public void setProjectLastChangedDateTime(String projectLastChangedDateTime) {
		this.projectLastChangedDateTime = projectLastChangedDateTime;
	}

	public SubProjectResponse getTo_SubProjElement() {
		return to_SubProjElement;
	}

	public void setTo_SubProjElement(SubProjectResponse to_SubProjElement) {
		this.to_SubProjElement = to_SubProjElement;
	}

}