package com.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class ObjectAccessor {
	
	private Context ctx;
	
	public ObjectAccessor(Context ctx){
		this.ctx = ctx;
	}
	
	public void writeObjectToMemory(String filename, Object object) {
        FileOutputStream fos;
        try {
            fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();

        } 
        catch (IOException e) {
            e.printStackTrace();

        }

    }
	
	public Object readObjectFromMemory(String filename) {
	    FileInputStream fis;
	    Object obj = null;
	    try {
	        fis = this.ctx.openFileInput(filename);
	        ObjectInputStream is = new ObjectInputStream(fis);
	        obj = is.readObject();
	        is.close();
	    } 
	    catch (Exception e) {
	    	e.printStackTrace();
	        return null;
	    }

	    return obj;
	}


}
