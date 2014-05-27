package com.app.deviceapplications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.config.AppInfoDetails;
import com.app.storage.ObjectAccessor;
import com.app.storage.STORAGE_NAME;
import com.google.gson.Gson;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class Utilities {

	/*
	 * Get all installed application on mobile and return a list
	 * 
	 * @param c Context of application
	 * 
	 * @return list of installed applications
	 */
	public static List<AppInfoDetails> getInstalledApplication(Context context) {

		List<ApplicationInfo> appInfoList = context.getPackageManager()
				.getInstalledApplications(PackageManager.GET_META_DATA);
		List<AppInfoDetails> appInfoFilteredList = new ArrayList<AppInfoDetails>();

		ObjectAccessor oa = new ObjectAccessor(context);

		String storageName = STORAGE_NAME.APP_USAGE.toString();

		if (oa.readObjectFromMemory(storageName) == null) {
			oa.writeObjectToMemory(storageName,
					new HashMap<String , Integer>());
		}

		Map<String, Integer> appUsage = (HashMap<String, Integer>) oa
				.readObjectFromMemory(storageName);

		for (ApplicationInfo aInfo : appInfoList) {

			String packageName = aInfo.packageName;
			
			Integer currentVal = appUsage.get(packageName);
			
			String appName = aInfo.loadLabel(context.getPackageManager())
					.toString();

			if (!appName.startsWith("com.")
					&& !appName.equalsIgnoreCase("Package Access Helper")
						&& !appName.equalsIgnoreCase("AppTakeOff")
						&& !appName.equalsIgnoreCase("Google Search")
						&& !appName.equalsIgnoreCase("System UI")
						&& !appName.equalsIgnoreCase("Dual Clock (Digital)")
						&& !appName.equalsIgnoreCase("Little Pet Shop")
						&& !appName.equalsIgnoreCase("Samsung WatchON Video")
						&& !packageName.equalsIgnoreCase("com.sec.android.SimpleWidget")
						&& !appName.toUpperCase().contains("WIDGET")) {
				appInfoFilteredList.add(new AppInfoDetails(aInfo, appName , currentVal));
			}
		}

		Collections.sort(appInfoFilteredList);
		
		return appInfoFilteredList;

	}

	public static List<AppInfoDetails> getInstalledApplicationByName(String name , Context context) {

		List<AppInfoDetails> appInfoDetails = Utilities.getInstalledApplication(context);
		
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

	/*
	 * Launch an application
	 * 
	 * @param c Context of application
	 * 
	 * @param pm the related package manager of the context
	 * 
	 * @param pkgName Name of the package to run
	 */
	public static boolean launchApp(Context c, PackageManager pm, String pkgName) {
		
		if(pkgName.equalsIgnoreCase("com.android.phone")){
			c.startActivity(new Intent(Intent.ACTION_DIAL));
			
			return true;
		}
		else {
		// query the intent for lauching
		Intent intent = pm.getLaunchIntentForPackage(pkgName);
		// if intent is available
		if (intent != null) {
			try {
				// launch application	
				c.startActivity(intent);
				// if succeed
				return true;

				// if fail
			} catch (ActivityNotFoundException ex) {
				// quick message notification
				Toast toast = Toast.makeText(c, "Application Not Found",
						Toast.LENGTH_LONG);
				// display message
				toast.show();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		}
		// by default, fail to launch
		return false;
	}
}
