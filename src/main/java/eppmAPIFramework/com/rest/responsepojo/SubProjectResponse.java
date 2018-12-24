package eppmAPIFramework.com.rest.responsepojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
