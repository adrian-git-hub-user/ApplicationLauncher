package com.application;

import java.io.Serializable;

import android.content.pm.ApplicationInfo;

public class AppInfoSerializable implements Serializable {

	/**
	 * 
	 */
	private String toSerialize;

	public String getToSerialize() {
		return toSerialize;
	}

	public void setToSerialize(String toSerialize) {
		this.toSerialize = toSerialize;
	}
	
/*	private static final long serialVersionUID = 1L;
	
	private String appName;
	private ApplicationInfo aInfo;
	
	public ApplicationInfo getaInfo() {
		return aInfo;
	}

	public void setaInfo(ApplicationInfo aInfo) {
		this.aInfo = aInfo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}*/

}
