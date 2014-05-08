package com.applicationlauncher;

import java.util.List;

import com.application.AppInfoDetails;
import com.application.AppInfoSerializable;
import com.applicationlauncher.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class AppInfoAdapterSer extends BaseAdapter {
    private Context context;
    private List<AppInfoSerializable> mListAppInfo;
    private PackageManager mPanager;
 
    public AppInfoAdapterSer(Context context, List<AppInfoSerializable> list, PackageManager pm) {
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
    	AppInfoSerializable entry = mListAppInfo.get(position);
 
    	//ApplicationInfo aInfo = entry.getAppInfo();
    	
        // reference to convertView
        View view = convertView;
 
        // inflate new layout if null
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_appinfo, null);
        }
 
        // load controls from layout resources
        ImageView ivAppIcon = (ImageView)view.findViewById(R.id.ivIcon);
        TextView tvAppName = (TextView)view.findViewById(R.id.tvName);
    
        // set data to display
       // ivAppIcon.setImageDrawable(aInfo.loadIcon(mPanager));
      //  tvAppName.setText(aInfo.loadLabel(mPanager));
 
        // return view
        return view;
    }
}
