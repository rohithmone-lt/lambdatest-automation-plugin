package com.lambdatest.jenkins.freestyle.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "operatingSystem",
    "device"
})
public class AppiumCapabilityRequest {
    
    @JsonProperty("operatingSystem")
    private String operatingSystem;
    @JsonProperty("device")
    private String device;

    @JsonProperty("operatingSystem")
    public String getOperatingSystem() {
        return operatingSystem;
    }

    @JsonProperty("operatingSystem")
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @JsonProperty("device")
    public String getDevice() {
        return device;
    }

    @JsonProperty("device")
    public void setDevice(String device) {
        this.device = device;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{operatingSystem=");
		builder.append(operatingSystem);
		builder.append(", device=");
		builder.append(device);
		builder.append("}");
		return builder.toString();
	}

}
