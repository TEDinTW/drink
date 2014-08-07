package com.example.drinkorder.bean.jackson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ResponseErrorIdentifier {
	protected boolean hasErrors;
	protected List<Error> errors;
	
	@JsonProperty("HasErrors")
	public boolean isHasErrors() {
		return hasErrors;
	}

	@JsonProperty("HasErrors")
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	
	@JsonProperty("Errors")
	public List<Error> getErrors() {
		if (errors == null) {
			errors = new ArrayList<Error>();
		}

		return errors;
	}

	@JsonProperty("Errors")
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
