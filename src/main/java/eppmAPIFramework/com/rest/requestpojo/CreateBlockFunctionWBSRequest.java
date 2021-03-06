package eppmAPIFramework.com.rest.requestpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBlockFunctionWBSRequest {
	
	/**
	 * This is a Request POJO Class for Create Block Functions for Existing WBS
	 * This class contains the attributes required to Create a BF for WBS
	 * 
	 */
	

		@SerializedName("ProjectElementUUID")
		@Expose
		private String projectElementUUID;

		@SerializedName("EntProjTimeRecgIsBlkd")
		@Expose
		private boolean EntProjTimeRecgIsBlkd;
		
		@SerializedName("EntProjStaffExpensePostgIsBlkd")
		@Expose
		private boolean EntProjStaffExpensePostgIsBlkd;
		
		@SerializedName("EntProjServicePostingIsBlkd")
		@Expose
		private boolean EntProjServicePostingIsBlkd;
		
		@SerializedName("EntProjOtherExpensePostgIsBlkd")
		@Expose
		private boolean EntProjOtherExpensePostgIsBlkd;
		
		@SerializedName("EntProjPurchasingIsBlkd")
		@Expose
		private boolean EntProjPurchasingIsBlkd;


		public String getProjectElementUUID() {
			return projectElementUUID;
		}

		public void setProjectElementUUID(String projectElementUUID) {
			this.projectElementUUID = projectElementUUID;
		}
 
		public boolean getEntProjTimeRecgIsBlkd() {
			return EntProjTimeRecgIsBlkd;
		}

		public void setEntProjTimeRecgIsBlkd(Boolean EntProjTimeRecgIsBlkd) {
			this.EntProjTimeRecgIsBlkd = EntProjTimeRecgIsBlkd;
		}
		
		public boolean getEntProjStaffExpensePostgIsBlkd() {
			return EntProjStaffExpensePostgIsBlkd;
		}

		public void setEntProjStaffExpensePostgIsBlkd(Boolean EntProjStaffExpensePostgIsBlkd) {
			this.EntProjStaffExpensePostgIsBlkd = EntProjStaffExpensePostgIsBlkd;
		}
		
		public boolean getEntProjOtherExpensePostgIsBlkd() {
			return EntProjOtherExpensePostgIsBlkd;
		}

		public void setEntProjOtherExpensePostgIsBlkd(Boolean EntProjOtherExpensePostgIsBlkd) {
			this.EntProjOtherExpensePostgIsBlkd = EntProjOtherExpensePostgIsBlkd;
		}
		
		public boolean getEntProjPurchasingIsBlkd() {
			return EntProjPurchasingIsBlkd;
		}

		public void setEntProjPurchasingIsBlkd(Boolean EntProjPurchasingIsBlkd) {
			this.EntProjPurchasingIsBlkd = EntProjPurchasingIsBlkd;
		}
		
		public boolean getEntProjServicePostingIsBlkd() {
			return EntProjServicePostingIsBlkd;
		}

		public void setEntProjServicePostingIsBlkd(Boolean EntProjServicePostingIsBlkd) {
			this.EntProjServicePostingIsBlkd = EntProjServicePostingIsBlkd;
		}

}
