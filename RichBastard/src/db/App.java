package db;

import android.app.Application;
import android.content.Context;

public class App extends Application {

private static Context mContext;
public static BaseHelper db;

//	@Override
//	public void onCreate() {
//    	super.onCreate();
//    	mContext = getApplicationContext();
//	}
	
	
	 @Override
	  public void onCreate() {
	    super.onCreate();
	    db = new BaseHelper(getApplicationContext());
	    db.openDataBase();

	  }
	
	
	public static Context getContext() {
		return mContext;
	}
}