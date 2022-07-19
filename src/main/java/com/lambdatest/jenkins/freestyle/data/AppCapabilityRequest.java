package com.lambdatest.jenkins.freestyle.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "appiumCapabilityRequest"
})
public class AppCapabilityRequest {
    
    @JsonProperty("appiumCapabilityRequest")
    private List<AppiumCapabilityRequest> appiumCapabilityRequest = null;

    @JsonProperty("appiumCapabilityRequest")
    public List<AppiumCapabilityRequest> getAppiumCapabilityRequest() {
        return appiumCapabilityRequest;
    }

    @JsonProperty("appiumCapabilityRequest")
    public void setAppiumCapabilityRequest(List<AppiumCapabilityRequest> appiumCapabilityRequest) {
        this.appiumCapabilityRequest = appiumCapabilityRequest;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{appiumCapabilityRequest=");
		builder.append(appiumCapabilityRequest);
		builder.append("}");
		return builder.toString();
	}

}
