package barayuda.com.movieapp.model.apiresponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by barayuda on 7/28/2017.
 */

public class TrailerResponse{

	@SerializedName("id")
	private long id;

	@SerializedName("results")
	private List<TrailerItem> results;

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setResults(List<TrailerItem> results){
		this.results = results;
	}

	public List<TrailerItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"TrailerResponse{" + 
			"barayuda = '" + id + '\'' +
			",results = '" + results + '\'' + 
			"}";
		}
}