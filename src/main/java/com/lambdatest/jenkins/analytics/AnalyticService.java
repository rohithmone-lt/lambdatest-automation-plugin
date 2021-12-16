package com.lambdatest.jenkins.analytics;


import com.lambdatest.jenkins.analytics.data.AnalyticRequest;
import com.lambdatest.jenkins.freestyle.api.Constant;
import com.lambdatest.jenkins.freestyle.api.service.CapabilityService;

import java.util.logging.Logger;


public class AnalyticService implements Runnable {

	
	private static final Logger logger = Logger.getLogger(AnalyticService.class.getName());

	private AnalyticRequest analyticRequest;

	@Override
	public void run() {
		try {
			CapabilityService.sendPostRequest(Constant.ANALYTICS_URL, analyticRequest);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
	}

	public AnalyticRequest getAnalyticRequest() {
		return analyticRequest;
	}

	public void setAnalyticRequest(AnalyticRequest analyticRequest) {
		this.analyticRequest = analyticRequest;
	}

}
