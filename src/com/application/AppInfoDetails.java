package com.application;

import android.content.pm.ApplicationInfo;

public class AppInfoDetails {

	private ApplicationInfo aInfo;
	private String appName;

	public AppInfoDetails(ApplicationInfo aInfo, String appName) {
		this.aInfo = aInfo;
		this.appName = appName;
	}

	public String getAppName() {
		return this.appName;
	}

	public ApplicationInfo getAppInfo() {
		return this.aInfo;
	}

}
