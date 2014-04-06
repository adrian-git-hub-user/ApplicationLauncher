package com.deviceapplications;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class DeviceApplicationDetails {

	private Context context;
	private List<ApplicationInfo> allApps;
	private List<AppInfoDetails> appInfoDetails = new ArrayList<AppInfoDetails>();

	public DeviceApplicationDetails(Context context) {
		this.context = context;
		List<ApplicationInfo> allApps = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
		this.allApps = allApps;
		for (ApplicationInfo aInfo : allApps) {
			appInfoDetails.add(new AppInfoDetails(aInfo , aInfo.loadLabel(context.getPackageManager()).toString()));
		}
	}

	private class AppInfoDetails {

		private ApplicationInfo aInfo;
		private String appName;

		public AppInfoDetails(ApplicationInfo aInfo, String appName) {
			this.aInfo = aInfo;
			this.appName = appName;
		}

		private String getAppName() {
			return this.appName;
		}

		private ApplicationInfo getAppInfo() {
			return this.aInfo;
		}

	}

	public List<ApplicationInfo> getInstalledApplicationByName(String name) {

		List<ApplicationInfo> filteredApps = new ArrayList<ApplicationInfo>();
		long startTime = System.currentTimeMillis();

		// Load map with application name and ApplicationInfo to improve
		// performance
		for (AppInfoDetails aInfo : appInfoDetails) {

			if (aInfo.getAppName().toUpperCase().contains(name.toUpperCase())) {
				filteredApps.add(aInfo.getAppInfo());
			}

		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken : " + (endTime - startTime));

		return filteredApps;
	}

}
