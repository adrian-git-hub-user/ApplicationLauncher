package com.deviceapplications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.application.AppInfoDetails;
import com.application.AppInfoSerializable;
import com.google.gson.Gson;
import com.storage.ObjectAccessor;
import com.storage.STORAGE_NAME;

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

			Integer currentVal = appUsage.get(aInfo.packageName);
			
			String appName = aInfo.loadLabel(context.getPackageManager())
					.toString();

			if (!appName.equalsIgnoreCase("com.android.sdksetup")
					&& !appName.equalsIgnoreCase("Package Access Helper")) {
				appInfoFilteredList.add(new AppInfoDetails(aInfo, appName , currentVal));
			}
		}

		
		/*
		 * for(ApplicationInfo ai : appInfoList){ appInfoFilteredList.add(ai); }
		 */

/*		Gson gson = new Gson();
		String json = gson.toJson(appInfoFilteredList);

		ObjectAccessor oa = new ObjectAccessor(context);
		if (oa.readObjectFromMemory("allobjects") == null) {
			oa.writeObjectToMemory("allobjects", json);
		}

		List<AppInfoDetails> l2 = new ArrayList<AppInfoDetails>();
		
		List<AppInfoDetails> lll = gson.fromJson((String) new ObjectAccessor(
				context).readObjectFromMemory("allobjects"), List.class);
		
		return lll;*/
		Collections.sort(appInfoFilteredList);
		
		return appInfoFilteredList;

	}

	/*public static List<AppInfoSerializable> getInstalledApplicationSer(
			Context context) {

		ObjectAccessor oa = new ObjectAccessor(context);

		List<ApplicationInfo> appInfoList = context.getPackageManager()
				.getInstalledApplications(PackageManager.GET_META_DATA);
		List<AppInfoSerializable> appInfoFilteredList = new ArrayList<AppInfoSerializable>();

		for (ApplicationInfo aInfo : appInfoList) {

			String appName = aInfo.loadLabel(context.getPackageManager())
					.toString();

			if (!appName.equalsIgnoreCase("com.android.sdksetup")
					&& !appName.equalsIgnoreCase("Package Access Helper")) {

				AppInfoSerializable ais = new AppInfoSerializable();
				ais.setAppName(appName);
				appInfoFilteredList.add(ais);

			}

		}

		
		 * for(ApplicationInfo ai : appInfoList){ appInfoFilteredList.add(ai); }
		 

		return appInfoFilteredList;
	}
*/
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
		}
		// by default, fail to launch
		return false;
	}
}
