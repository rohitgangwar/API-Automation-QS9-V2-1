package eppmAPIFramework.com.rest.requestpojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateWBSElementsRequest {

	@SerializedName("ProjectElement")
	@Expose
	private String projectElement;
	
	@SerializedName("ProjectElementDescription")
	@Expose
	private String projectElementDescription;
	
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
	
	@SerializedName("ProfitCenter")
	@Expose
	private String profitCenter;
	
	@SerializedName("to_SubProjElement")
	@Expose
	private List<CreateWBSElementsRequest> to_SubProjElement;

	public CreateWBSElementsRequest() {
		
	}
	
	public CreateWBSElementsRequest(String projectElement, String projectElementDescription, String sortingNumber,
			String plannedStartDate, String plannedEndDate, String responsibleCostCenter, String profitCenter) {
		this.projectElement=projectElement;
		this.projectElementDescription = projectElementDescription;
		this.sortingNumber = sortingNumber;
		this.plannedStartDate = plannedStartDate;
		this.plannedEndDate = plannedEndDate;
		this.responsibleCostCenter =responsibleCostCenter;
		this.profitCenter=profitCenter;
	}
	public String getProjectElement() {
		return projectElement;
	}

	public void setProjectElement(String projectElement) {
		this.projectElement = projectElement;
	}

	public String getProjectElementDescription() {
		return projectElementDescription;
	}

	public void setProjectElementDescription(String projectElementDescription) {
		this.projectElementDescription = projectElementDescription;
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

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}
	
	public List<CreateWBSElementsRequest> getTo_SubProjElement() {
		return to_SubProjElement;
	}

	public void setTo_SubProjElement(List<CreateWBSElementsRequest> to_SubProjElement) {
		this.to_SubProjElement = to_SubProjElement;
	}

}
