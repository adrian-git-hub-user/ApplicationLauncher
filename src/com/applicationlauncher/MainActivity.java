package com.applicationlauncher;

import com.deviceapplications.DeviceApplicationDetails;
import com.deviceapplications.Utilities;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
 
public class MainActivity extends Activity {
	
	private EditText yourEditText;
    private ListView mListAppInfo;
    private DeviceApplicationDetails dad;  
    private AppInfoAdapter adapter;
    private String userInput;
    
    private void updateView(String userInput){ 	
    	adapter = new AppInfoAdapter(this, dad.getInstalledApplicationByName(userInput) , getPackageManager());
       	mListAppInfo.setAdapter(adapter);
    }
    
    private final Handler myHandler = new Handler();
    
    final Runnable updateRunnable = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            updateUI();
        }
    };
    
    private void updateUI()
    {
    	adapter = new AppInfoAdapter(this, dad.getInstalledApplicationByName(this.userInput) , getPackageManager());
       	mListAppInfo.setAdapter(adapter);
    }
    
    private void doSomeHardWork(final String userInput)
    {
    	this.userInput = userInput;
         //update the UI using the handler and the runnable
         myHandler.post(updateRunnable);

    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set layout for the main screen
        setContentView(R.layout.layout_main);
 
        yourEditText = (EditText) findViewById(R.id.editText);
        
        dad = new DeviceApplicationDetails(this.getApplicationContext());
        
        // load list application
        mListAppInfo = (ListView)findViewById(R.id.lvApps);
        // create new adapter
        adapter = new AppInfoAdapter(this, Utilities.getInstalledApplication(this), getPackageManager());
        // set adapter to list view
        mListAppInfo.setAdapter(adapter);
        //adapter.getp
        // implement event when an item on list view is selected
        mListAppInfo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int pos, long id) {
                // get the list adapter
                AppInfoAdapter appInfoAdapter = (AppInfoAdapter)parent.getAdapter();
                // get selected item on the list
                ApplicationInfo appInfo = (ApplicationInfo)appInfoAdapter.getItem(pos);

                // launch the selected application
                Utilities.launchApp(parent.getContext(), getPackageManager(), appInfo.packageName);
            }
        });
        
        yourEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(final CharSequence s, int start, int before,
					int count) {
				
				Runnable runnable = new Runnable() {
		            @Override
		            public void run() {                
		                {                    
		                	updateView(s.toString());
		                }
		            }
		        };   
		        
		        myHandler.post(runnable);
		        
			
	/*			 new Thread(new Runnable() {

					@Override
					public void run() {
						
						
						doSomeHardWork(s.toString());
					} 
   
				 }).start();*/

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}


         });
    }
}