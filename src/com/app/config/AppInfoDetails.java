package com.app.config;

import android.content.pm.ApplicationInfo;

public class AppInfoDetails implements Comparable {

	private ApplicationInfo aInfo;
	private String appName;
	private Integer userLaunchCount;

	public AppInfoDetails(ApplicationInfo aInfo, String appName,
			Integer userLaunchCount) {
		this.aInfo = aInfo;
		this.appName = appName;
		this.userLaunchCount = userLaunchCount;
	}

	public String getAppName() {
		return this.appName;
	}

	public ApplicationInfo getAppInfo() {
		return this.aInfo;
	}

	@Override
	public int compareTo(Object another) {

		Integer launchCount = 0;
		if ((AppInfoDetails) another != null) {
			launchCount = ((AppInfoDetails) another).userLaunchCount;
			if (launchCount == null) {
				launchCount = 0;
			}
		}

		if (this.userLaunchCount == null) {
			this.userLaunchCount = 0;
		}

		return launchCount.compareTo(this.userLaunchCount);
	}

}
