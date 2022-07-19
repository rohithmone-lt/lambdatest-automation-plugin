package com.lambdatest.jenkins.freestyle.api.service;

public class AppiumVersionKey {
    
    private String operatingSystem;
	private String deviceName;

	public AppiumVersionKey(String operatingSystem, String deviceName) {
		super();
		this.operatingSystem = operatingSystem;
		this.deviceName = deviceName;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((operatingSystem == null) ? 0 : operatingSystem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppiumVersionKey other = (AppiumVersionKey) obj;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (operatingSystem == null) {
			if (other.operatingSystem != null)
				return false;
		} else if (!operatingSystem.equals(other.operatingSystem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\noperatingSystem=");
		builder.append(operatingSystem);
		builder.append(",\ndeviceName=");
		builder.append(deviceName);
		builder.append("\n}");
		return builder.toString();
	}

}
