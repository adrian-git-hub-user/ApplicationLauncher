package com.applicationlauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.application.AppInfoDetails;
import com.application.AppInfoSerializable;
import com.applicationlauncher.R;
import com.storage.ObjectAccessor;
import com.storage.STORAGE_NAME;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppInfoAdapter extends BaseAdapter {
	private Context context;
	private List<AppInfoDetails> mListAppInfo;
	private List<AppInfoSerializable> appInfoSer = new ArrayList<AppInfoSerializable>();

	public List<AppInfoSerializable> getAppInfoSer() {
		return appInfoSer;
	}

	public void setAppInfoSer(List<AppInfoSerializable> appInfoSer) {
		this.appInfoSer = appInfoSer;
	}

	private PackageManager mPanager;

	public AppInfoAdapter(Context context, List<AppInfoDetails> list,
			PackageManager pm) {
		this.context = context;
		this.mListAppInfo = list;
		this.mPanager = pm;

	}

	@Override
	public int getCount() {
		return mListAppInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mListAppInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get the selected entry
		AppInfoDetails entry = mListAppInfo.get(position);

		ApplicationInfo aInfo = entry.getAppInfo();

		// reference to convertView
		View view = convertView;

		// inflate new layout if null
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.layout_appinfo, null);
		}

		// load controls from layout resources
		ImageView ivAppIcon = (ImageView) view.findViewById(R.id.ivIcon);
		TextView tvAppName = (TextView) view.findViewById(R.id.tvName);

		// set data to display
		ivAppIcon.setImageDrawable(aInfo.loadIcon(mPanager));

		System.out.println("Loading label " + aInfo.loadLabel(mPanager));

		ObjectAccessor oa = new ObjectAccessor(this.context);

		String storageName = STORAGE_NAME.APP_USAGE.toString();

		String packageName = aInfo.packageName;

		Map<String, Integer> appUsage = (HashMap<String, Integer>) oa
				.readObjectFromMemory(storageName);

		Integer currentVal = appUsage.get(packageName);
		if(currentVal == null){
			currentVal = 0;
		}
		tvAppName.setText(aInfo.loadLabel(mPanager) + " ("
				+ currentVal + ")");

		// return view
		return view;
	}
}
