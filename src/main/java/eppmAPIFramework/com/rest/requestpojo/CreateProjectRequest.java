package eppmAPIFramework.com.rest.requestpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Request POJO Class for Create Project Scenarios
 * This class contains the attributes required to Create a Project
 * 
 */
public class CreateProjectRequest {

	@SerializedName("Project")
	@Expose
	private String project;

	@SerializedName("ProjectDescription")
	@Expose
	private String projectDescription;

	@SerializedName("ProjectStartDate")
	@Expose
	private String projectStartDate;

	@SerializedName("ProjectEndDate")
	@Expose
	private String projectEndDate;

	@SerializedName("ProfitCenter")
	@Expose
	private String profitCenter;

	@SerializedName("ResponsibleCostCenter")
	@Expose
	private String responsibleCostCenter;

	@SerializedName("ProjectProfileCode")
	@Expose
	private String projectProfileCode;

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

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getResponsibleCostCenter() {
		return responsibleCostCenter;
	}

	public void setResponsibleCostCenter(String responsibleCostCenter) {
		this.responsibleCostCenter = responsibleCostCenter;
	}

	public String getProjectProfileCode() {
		return projectProfileCode;
	}

	public void setProjectProfileCode(String projectProfileCode) {
		this.projectProfileCode = projectProfileCode;
	}

}