package com.storage;

public enum STORAGE_NAME {
	
	APP_USAGE("appUsage2");
	
	private String name;
	
	private STORAGE_NAME(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return this.name;
	}
}
