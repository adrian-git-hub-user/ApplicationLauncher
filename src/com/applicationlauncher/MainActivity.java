package com.applicationlauncher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.application.AppInfoDetails;
import com.deviceapplications.DeviceApplicationDetails;
import com.deviceapplications.Utilities;
import com.storage.ObjectAccessor;
import com.storage.STORAGE_NAME;
 
public class MainActivity extends Activity {

	private EditText appSearchTextBox;
	private ListView mListAppInfo;
	private DeviceApplicationDetails dad;
	private AppInfoAdapter adapter;
	
	private void updateView(String userInput) {
		adapter = new AppInfoAdapter(this,
				dad.getInstalledApplicationByName(userInput),
				getPackageManager());
		mListAppInfo.setAdapter(adapter);
	}

	private final Handler myHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout for the main screen
		setContentView(R.layout.layout_main);

		appSearchTextBox = (EditText) findViewById(R.id.editText);

		dad = new DeviceApplicationDetails(this.getApplicationContext());

		setupUi();

	}

	private void addUsage(String packageName){
		
		final ObjectAccessor oa = new ObjectAccessor(
				this.getApplicationContext());
		
		String storageName = STORAGE_NAME.APP_USAGE.toString();
		
		if (oa.readObjectFromMemory(storageName) == null) {
			oa.writeObjectToMemory(storageName,
					new HashMap<String , Integer>());
		}

		Map<String , Integer> appUsage = (HashMap<String , Integer>) oa
				.readObjectFromMemory(storageName);

		if (appUsage.get(packageName) == null) {
			appUsage.put(packageName, 1);
		} else {
			appUsage.put(packageName, appUsage.get(packageName) + 1);
		}
		oa.writeObjectToMemory(STORAGE_NAME.APP_USAGE.toString(),
				appUsage);
		
		System.out.println("Current size of " + packageName + " : "
				+ appUsage.get(packageName));
		
	}
	
	private void setupUi() {

		mListAppInfo = (ListView) findViewById(R.id.lvApps);
		List<AppInfoDetails> aiList = Utilities.getInstalledApplication(this);
		adapter = new AppInfoAdapter(this, aiList, getPackageManager());

		mListAppInfo.setAdapter(adapter);

		// implement event when an item on list view is selected
		mListAppInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view, int pos,
					long id) {

				// get the list adapter
				AppInfoAdapter appInfoAdapter = (AppInfoAdapter) parent
						.getAdapter();

				// get selected item on the list
				AppInfoDetails appInfo = (AppInfoDetails) appInfoAdapter
						.getItem(pos);

				String packageName = appInfo.getAppInfo().packageName;
				
				addUsage(packageName);
				
				// launch the selected application
				Utilities.launchApp(parent.getContext(), getPackageManager(),
						packageName);
			}
		});

		appSearchTextBox.setTextSize(22.0f);

		appSearchTextBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(final CharSequence s, int start,
					int before, int count) {

				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						{
							updateView(s.toString());
						}
					}
				};

				myHandler.post(runnable);
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});
	}
}