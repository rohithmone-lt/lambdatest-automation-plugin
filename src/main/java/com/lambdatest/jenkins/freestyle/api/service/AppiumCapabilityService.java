package com.lambdatest.jenkins.freestyle.api.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.collections.MapUtils;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdatest.jenkins.freestyle.api.Constant;
import com.lambdatest.jenkins.freestyle.api.device.Devices;
import com.lambdatest.jenkins.freestyle.api.device.Device;
import com.lambdatest.jenkins.freestyle.api.device.DeviceVersion;
import com.lambdatest.jenkins.freestyle.api.operatingsystem.OsList;

public class AppiumCapabilityService {

    private final static Logger logger = Logger.getLogger(CapabilityService.class.getName());

	public static Map<String, String> appiumSupportedOS = new LinkedHashMap<>();
	public static Set<String> supportedBrands;
	public static Set<String> supportedDevices;
	public static Map<String, Set<String>> allBrandNames = new LinkedHashMap<>();
	public static Map<AppiumDeviceKey, List<Device>> allDeviceNames = new LinkedHashMap<>();
	public static Map<AppiumVersionKey, List<DeviceVersion>> allDeviceVersions = new LinkedHashMap<>();
	public static Set<String> supportedDeviceVersions;

    public static Map<String, String> getAppiumOperatingSystems() {
		try {
			if (MapUtils.isEmpty(appiumSupportedOS)) {
				appiumSupportedOS = new LinkedHashMap<>();
				String jsonResponse = CapabilityService.sendGetRequest(Constant.APPIUM_OS_API_URL);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				OsList osList = objectMapper.readValue(jsonResponse, OsList.class);
				parseAppiumSupportedOs(osList);
			}
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return appiumSupportedOS;
	}

    private static void parseAppiumSupportedOs(OsList osList) {
		if (osList != null && osList.getOs() != null) {
			osList.getOs().forEach(os -> {
				AppiumCapabilityService.appiumSupportedOS.put(os.getId(), os.getName());
			});
		}
	}

	public static Set<String> getBrandNames(String operatingSystem) {
		try {
			if (allBrandNames.containsKey(operatingSystem)) {
				logger.info("Supported Brand List Exists for " + operatingSystem);
				return allBrandNames.get(operatingSystem);
			}
			supportedBrands = new LinkedHashSet<String>();
			String deviceApiURL = Constant.DEVICE_API_URL;
			String jsonResponse = CapabilityService.sendGetRequest(deviceApiURL);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JSONObject jsonObj = new JSONObject(jsonResponse);
            String jsonResponseOs = jsonObj.getJSONArray(operatingSystem).toString();
			List<Devices> devices = objectMapper.readValue(jsonResponseOs, new TypeReference<List<Devices>>() {});
			parseSupportedBrandsAndDevices(devices, operatingSystem);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return supportedBrands;
	}

    private static Set<String> parseSupportedBrandsAndDevices(List<Devices> devices, String operatingSystem) {
		if (!CollectionUtils.isEmpty(devices)) {
			devices.forEach(devs -> {
				List<Device> realDevices = new ArrayList<Device>();
				Set<String> supportedDevice = new LinkedHashSet<String>();
				devs.getDevices().forEach(dev ->  {
					if (dev.getDeviceType().equals("real")) {
						supportedDevice.add(dev.getDeviceName());
						realDevices.add(dev);
						AppiumVersionKey avk = new AppiumVersionKey(operatingSystem, dev.getDeviceName());
						if (!CollectionUtils.isEmpty(dev.getVersions())) {
							allDeviceVersions.put(avk, dev.getVersions());
						}
					}
				});
				if (!realDevices.isEmpty()) {
					supportedBrands.add(devs.getBrandName());
					AppiumDeviceKey adk = new AppiumDeviceKey(operatingSystem, devs.getBrandName());
					if (!CollectionUtils.isEmpty(realDevices)) {
						allDeviceNames.put(adk, realDevices);
					}
				}
			});
			allBrandNames.put(operatingSystem, supportedBrands);
		}
		return supportedBrands;
	}

	public static Set<String> getDeviceNames(String operatingSystem, String brandName) {
		supportedDevices = new LinkedHashSet<String>();
		AppiumDeviceKey adk = new AppiumDeviceKey(operatingSystem, brandName);
		if (allDeviceNames.containsKey(adk)) {
			logger.info("Supported Device List Exists for " + brandName);
			allDeviceNames.get(adk).forEach(dn -> {
				supportedDevices.add(dn.getDeviceName());
			});
		} else {
			System.out.println(adk + " not found");
		}
		return supportedDevices;
	}

    public static Set<String> getDeviceVersions(String operatingSystem, String deviceName) {
		supportedDeviceVersions = new LinkedHashSet<String>();
		AppiumVersionKey avk = new AppiumVersionKey(operatingSystem, deviceName);
		if (allDeviceVersions.containsKey(avk)) {
			logger.info("2. Supported Device Versions List Exists for " + operatingSystem + ":" + deviceName);
			allDeviceVersions.get(avk).forEach(dv -> {
				supportedDeviceVersions.add(dv.getVersion());
			});
		} else {
			System.out.println(avk + " not found");
		}
		return supportedDeviceVersions;
	}

	public static String appiumBuildHubURL(String username, String accessToken, String type) {
		try {
			StringBuilder sb = new StringBuilder("https://");
			sb.append(username).append(":").append(accessToken);
			if (Constant.STAGE.equals(type)) {
				sb.append(Constant.Stage.APPIUM_HUB_URL);
			} else {
				sb.append(Constant.APPIUM_HUB_URL);
			}
			return sb.toString();
		} catch (Exception e) {
			return Constant.NOT_AVAILABLE;
		}
	}

    public static void main(String[] args) throws Exception {
		System.out.println(getAppiumOperatingSystems());
		System.out.println(getBrandNames("android"));
		System.out.println(getDeviceNames("android", "Asus"));
		System.out.println(getDeviceVersions("android", "Zenfone 6"));
		System.out.println(allDeviceVersions);
	}
    
}
