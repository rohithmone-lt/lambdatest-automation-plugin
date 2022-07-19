package com.lambdatest.jenkins.freestyle.api.service;

// import java.util.ArrayList;
// import java.util.Arrays;
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
	public static Set<String> supportedDevices;
	public static Map<String, Set<String>> allDeviceNames = new LinkedHashMap<>();
	public static Map<AppiumVersionKey, List<DeviceVersion>> allDeviceVersions = new LinkedHashMap<>();
	public static Set<String> supportedDeviceVersions;

    public static Map<String, String> getAppiumOperatingSystems() {
		try {
			if (MapUtils.isEmpty(appiumSupportedOS)) {
				appiumSupportedOS = new LinkedHashMap<>();
				String jsonResponse = CapabilityService.sendGetRequest(Constant.APPIUM_OS_API_URL);
				logger.info("got the response for getting os : " + jsonResponse);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				OsList osList = objectMapper.readValue(jsonResponse, OsList.class);
				parseAppiumSupportedOs(osList);
			}
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return AppiumCapabilityService.appiumSupportedOS;
	}

    private static void parseAppiumSupportedOs(OsList osList) {
		if (osList != null && osList.getOs() != null) {
			osList.getOs().forEach(os -> {
				AppiumCapabilityService.appiumSupportedOS.put(os.getId(), os.getName());
			});
		}
	}

    public static Set<String> getDeviceNames(String operatingSystem) {
		try {
			if (allDeviceNames.containsKey(operatingSystem)) {
				logger.info("Supported Device List Exists for " + operatingSystem);
				return allDeviceNames.get(operatingSystem);
			}
			supportedDevices = new LinkedHashSet<String>();
			String deviceApiURL = Constant.DEVICE_API_URL;
			String jsonResponse = CapabilityService.sendGetRequest(deviceApiURL);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JSONObject jsonObj = new JSONObject(jsonResponse);
            String jsonResponseOs = jsonObj.getJSONArray(operatingSystem).toString();
			List<Devices> devices = objectMapper.readValue(jsonResponseOs, new TypeReference<List<Devices>>() {});
			parseSupportedDevices(devices, operatingSystem);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return supportedDevices;
	}

    private static Set<String> parseSupportedDevices(List<Devices> devices, String operatingSystem) {
		if (!CollectionUtils.isEmpty(devices)) {
			devices.forEach(devs -> {
				List<Device> device = devs.getDevices();
				device.forEach(dev ->  {
					if (dev.getDeviceType().equals("real")) {
						supportedDevices.add(dev.getDeviceName());
						AppiumVersionKey avk = new AppiumVersionKey(operatingSystem, dev.getDeviceName());
						if (!CollectionUtils.isEmpty(dev.getVersions())) {
							allDeviceVersions.put(avk, dev.getVersions());
						}
					}
				});
			});
			allDeviceNames.put(operatingSystem, supportedDevices);
		}
		return supportedDevices;
	}

    public static Set<String> getDeviceVersions(String operatingSystem, String deviceName) {
		supportedDeviceVersions = new LinkedHashSet<String>();
		AppiumVersionKey avk = new AppiumVersionKey(operatingSystem, deviceName);
		if (allDeviceVersions.containsKey(avk)) {
			allDeviceVersions.get(avk).forEach(bv -> {
				supportedDeviceVersions.add(bv.getVersion());
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
	System.out.println(getDeviceNames("android"));
	System.out.println(getDeviceVersions("android", "Zenfone 6"));
	System.out.println(allDeviceVersions);
    }
    
}
