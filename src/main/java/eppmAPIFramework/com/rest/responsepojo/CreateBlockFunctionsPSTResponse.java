package eppmAPIFramework.com.rest.responsepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBlockFunctionsPSTResponse {
	
	@SerializedName("ProjectUUID")
	@Expose
	private String projectUUID;

	@SerializedName("EntProjTimeRecgIsBlkd")
	@Expose
	private boolean entProjTimeRecgIsBlkd;

	@SerializedName("EntProjStaffExpensePostgIsBlkd")
	@Expose
	private boolean entProjStaffExpensePostgIsBlkd;

	@SerializedName("EntProjServicePostingIsBlkd")
	@Expose
	private boolean entProjServicePostingIsBlkd;

	@SerializedName("EntProjOtherExpensePostgIsBlkd")
	@Expose
	private boolean entProjOtherExpensePostgIsBlkd;

	@SerializedName("EntProjPurchasingIsBlkd")
	@Expose
	private boolean entProjPurchasingIsBlkd;

	

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public boolean getEntProjTimeRecgIsBlkd() {
		return entProjTimeRecgIsBlkd;
	}

	public void setEntProjTimeRecgIsBlkd(boolean entProjTimeRecgIsBlkd) {
		this.entProjTimeRecgIsBlkd = entProjTimeRecgIsBlkd;
	}
	
	public boolean getEntProjStaffExpensePostgIsBlkd() {
		return entProjStaffExpensePostgIsBlkd;
	}

	public void setEntProjStaffExpensePostgIsBlkd(boolean entProjStaffExpensePostgIsBlkd) {
		this.entProjStaffExpensePostgIsBlkd = entProjStaffExpensePostgIsBlkd;
	}
	
	public boolean getEntProjServicePostingIsBlkd() {
		return entProjServicePostingIsBlkd;
	}

	public void setEntProjServicePostingIsBlkd(boolean entProjServicePostingIsBlkd) {
		this.entProjServicePostingIsBlkd = entProjServicePostingIsBlkd;
	}
	
	public boolean getEntProjOtherExpensePostgIsBlkd() {
		return entProjOtherExpensePostgIsBlkd;
	}

	public void setEntProjOtherExpensePostgIsBlkd(boolean entProjOtherExpensePostgIsBlkd) {
		this.entProjOtherExpensePostgIsBlkd = entProjOtherExpensePostgIsBlkd;
	}
	
	public boolean getEntProjPurchasingIsBlkd() {
		return entProjPurchasingIsBlkd;
	}

	public void setEntProjPurchasingIsBlkd(boolean entProjPurchasingIsBlkd) {
		this.entProjPurchasingIsBlkd = entProjPurchasingIsBlkd;
	}
	
	

	@Override
	public String toString() {
		return "CreateBlockFunctionResponse for PST [projectUUID=" + projectUUID + ", Time Recording=" + entProjTimeRecgIsBlkd
				+ ", Staff Expense=" + entProjStaffExpensePostgIsBlkd + ", Service Posting=" + entProjServicePostingIsBlkd + ", other Expense="
				+ entProjOtherExpensePostgIsBlkd + ", purchasing=" + entProjPurchasingIsBlkd +  "]";
	}

}
