package eppmAPIFramework.com.rest.responsepojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Response POJO Class for Create WBS Elements Scenarios.
 * This class contains the list of WBS Elemensts inside the Parent WBS Element
 * 
 */
public class SubProjectResponse {

	@SerializedName("results")
	@Expose
	private List<CreateWBSElementsResponse> results;

	public List<CreateWBSElementsResponse> getResults() {
		return results;
	}

	public void setResults(List<CreateWBSElementsResponse> results) {
		this.results = results;
	}
}
