package com.lambdatest.jenkins.freestyle.api.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "version"
})

public class DeviceVersion {
    
    // @JsonProperty("id")
    // private String id;
    // @JsonProperty("name")
    // private String name;
    @JsonProperty("version")
    private String version;

    // @JsonProperty("id")
    // public String getId() {
    //     return id;
    // }

    // @JsonProperty("id")
    // public void setId(String id) {
    //     this.id = id;
    // }

    // @JsonProperty("name")
    // public String getName() {
    //     return name;
    // }

    // @JsonProperty("name")
    // public void setName(String name) {
    //     this.name = name;
    // }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

}
