package com.lambdatest.jenkins.freestyle.api.service;

public class AppiumDeviceKey {
    
    private String operatingSystem;
	private String brandName;

	public AppiumDeviceKey(String operatingSystem, String brandName) {
		super();
		this.operatingSystem = operatingSystem;
		this.brandName= brandName;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandName == null) ? 0 : brandName.hashCode());
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
		AppiumDeviceKey other = (AppiumDeviceKey) obj;
		if (brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!brandName.equals(other.brandName))
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
		builder.append(",\nbrandName=");
		builder.append(brandName);
		builder.append("\n}");
		return builder.toString();
	}

}
