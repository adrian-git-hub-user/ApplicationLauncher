package com.deviceapplications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.application.AppInfoDetails;
import com.google.gson.Gson;
import com.storage.ObjectAccessor;

public class DeviceApplicationDetails {

	private List<AppInfoDetails> appInfoDetails = new ArrayList<AppInfoDetails>();

	public DeviceApplicationDetails(Context context) {
		appInfoDetails = Utilities.getInstalledApplication(context);
	}

	public List<AppInfoDetails> getInstalledApplicationByName(String name) {

		List<AppInfoDetails> filteredApps = new ArrayList<AppInfoDetails>();
		long startTime = System.currentTimeMillis();

/*		Try to find if applicaiton contains an icon. e.g google plus contains '+' 
		but does not have a plus in its name*/
		if (name.equalsIgnoreCase("p") || name.equalsIgnoreCase("l")
				|| name.equalsIgnoreCase("u") || name.equalsIgnoreCase("s")) {
			name = "google";
		}
		for (AppInfoDetails aInfo : appInfoDetails) {

			if (aInfo.getAppName().toUpperCase().contains(name.toUpperCase())) {
				filteredApps.add(aInfo);
			}

		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken : " + (endTime - startTime));

		Collections.sort(filteredApps);
		
		return filteredApps;
	}

}
