package com.lambdatest.jenkins.freestyle;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.QueryParameter;
import org.springframework.util.CollectionUtils;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.lambdatest.jenkins.credential.MagicPlugCredentialsImpl;
import com.lambdatest.jenkins.freestyle.api.Constant;
import com.lambdatest.jenkins.freestyle.api.service.CapabilityService;
import com.lambdatest.jenkins.freestyle.api.service.AppiumCapabilityService;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.ItemGroup;
import hudson.tasks.BuildWrapperDescriptor;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;

@Extension
public class MagicPlugDescriptor extends BuildWrapperDescriptor {

	@Override
	public boolean isApplicable(AbstractProject<?, ?> item) {
		return true;
	}

	@Override
	public String getDisplayName() {
		return "LAMBDATEST";
	}

	public MagicPlugDescriptor() {
		super(MagicPlugBuildWrapper.class);
		load();
	}

	public FormValidation doPing() throws IOException, ServletException {
		System.out.println("doPing");
		if (new CapabilityService().ping()) {
			return FormValidation.ok("Ping Successful");
		} else {
			return FormValidation.error("Ping Failed");
		}
	}

	/**
	 * Return list of supported `operatingSystem`
	 * 
	 * @return ListBoxModel
	 */
	public ListBoxModel doFillOperatingSystemItems() {
		Map<String, String> supportedOS = CapabilityService.getOperatingSystems();
		ListBoxModel items = new ListBoxModel();
		items.add(Constant.DEFAULT_OPERATING_SYSTEM_VALUE, Constant.EMPTY);
		supportedOS.forEach((key, value) -> {
			items.add(value, key);
		});
		return items;
	}

	public ListBoxModel doFillBrowserNameItems(@QueryParameter String operatingSystem) {
		ListBoxModel items = new ListBoxModel();
		if (StringUtils.isBlank(operatingSystem)) {
			items.add(Constant.DEFAULT_BROWSER_NAME_VALUE, Constant.EMPTY);
			return items;
		}
		System.out.println(operatingSystem);
		Set<String> supportedBrowsers = CapabilityService.getBrowserNames(operatingSystem);
		if (!CollectionUtils.isEmpty(supportedBrowsers)) {
			supportedBrowsers.forEach(br -> {
				items.add(br, br);
			});
		}
		return items;
	}

	public ListBoxModel doFillBrowserVersionItems(@QueryParameter String operatingSystem,
			@QueryParameter String browserName) {
		ListBoxModel items = new ListBoxModel();
		System.out.println(operatingSystem + "::" + browserName);
		if (!StringUtils.isBlank(operatingSystem) && StringUtils.isBlank(browserName)) {
			browserName = "Chrome";
			System.out.println("Chrome added");
		} else if (StringUtils.isBlank(operatingSystem) || StringUtils.isBlank(browserName)) {
			items.add(Constant.DEFAULT_BROWSER_VERSION_VALUE, Constant.EMPTY);
			return items;
		}
		Set<String> supportedBrowserVersions = CapabilityService.getBrowserVersions(operatingSystem, browserName);
		if (!CollectionUtils.isEmpty(supportedBrowserVersions)) {
			supportedBrowserVersions.forEach(ver -> {
				items.add(ver, ver);
			});
		}
		return items;
	}

	public ListBoxModel doFillResolutionItems(@QueryParameter String operatingSystem) {
		ListBoxModel items = new ListBoxModel();
		if (StringUtils.isBlank(operatingSystem)) {
			items.add(Constant.DEFAULT_RESOLUTION_VALUE, Constant.EMPTY);
			return items;
		}
		System.out.println(operatingSystem);
		List<String> supportedBrowsers = CapabilityService.getResolutions(operatingSystem);
		if (!CollectionUtils.isEmpty(supportedBrowsers)) {
			supportedBrowsers.forEach(br -> {
				items.add(br, br);
			});
		}
		return items;
	}

	public ListBoxModel doFillAppiumOperatingSystemItems() {
		Map<String, String> appiumSupportedOS = AppiumCapabilityService.getAppiumOperatingSystems();
		ListBoxModel items = new ListBoxModel();
		items.add(Constant.DEFAULT_OPERATING_SYSTEM_VALUE, Constant.EMPTY);
		appiumSupportedOS.forEach((key, value) -> {
			items.add(value, key);
		});
		return items;
	}

	public ListBoxModel doFillDeviceNameItems(@QueryParameter String operatingSystem) {
		ListBoxModel items = new ListBoxModel();
		if (StringUtils.isBlank(operatingSystem)) {
			items.add(Constant.DEFAULT_DEVICE_NAME_VALUE, Constant.EMPTY);
			return items;
		}
		System.out.println(operatingSystem);
		Set<String> supportedDevices = AppiumCapabilityService.getDeviceNames(operatingSystem);
		if (!CollectionUtils.isEmpty(supportedDevices)) {
			supportedDevices.forEach(br -> {
				items.add(br, br);
			});
		}
		return items;
	}

	public ListBoxModel doFillDeviceVersionItems(@QueryParameter String operatingSystem,
			@QueryParameter String deviceName) {
		ListBoxModel items = new ListBoxModel();
		System.out.println(operatingSystem + "::" + deviceName);
		if (!StringUtils.isBlank(operatingSystem) && StringUtils.isBlank(deviceName)) {
			deviceName = "Zenfone 6";
			System.out.println("Zenfone 6 added");
		} else if (StringUtils.isBlank(operatingSystem) || StringUtils.isBlank(deviceName)) {
			items.add(Constant.DEFAULT_DEVICE_VERSION_VALUE, Constant.EMPTY);
			return items;
		}
		Set<String> supportedDeviceVersions = AppiumCapabilityService.getDeviceVersions(operatingSystem, deviceName);
		if (!CollectionUtils.isEmpty(supportedDeviceVersions)) {
			supportedDeviceVersions.forEach(ver -> {
				items.add(ver, ver);
			});
		}
		return items;
	}

	public String doFillAppUrl() {
		return Constant.DEFAULT_APP_URL;
	}
	
	public ListBoxModel doFillCredentialsIdItems(@QueryParameter String credentialsId) {
		if (!StringUtils.isBlank(credentialsId)) {
			System.out.println(credentialsId);
		} else {
			System.out.println("Not Found");
		}
		return new ListBoxModel();
	}

	public ListBoxModel doFillCredentialsIdItems(ItemGroup context) {
		return new StandardListBoxModel().withEmptySelection().withMatching(CredentialsMatchers.always(),
				MagicPlugCredentialsImpl.all(context));
	}
}
