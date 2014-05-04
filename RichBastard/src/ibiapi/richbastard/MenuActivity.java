package ibiapi.richbastard;

import ibiapi.fontpackage.MyButtonFont;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

	
@SuppressLint("HandlerLeak")
public class MenuActivity extends Activity implements OnClickListener {   
	
	SharedPreferences sharedpreferences;
	public  boolean ENABLE_MUSIC = true; 
	public  boolean ENABLE_EFFECT = true;
 
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        // fullscreen app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // without title
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.menu_game);
        //setContentView(new GameView(this,null));
        
        /*BEGIN Click Button Listener*/
        MyButtonFont startButton = (MyButtonFont)findViewById(R.id.menuStartBtn);
        startButton.setOnClickListener(this);
        
        //settings
        MyButtonFont settingsButton = (MyButtonFont)findViewById(R.id.menuSettingsBtn);
        settingsButton.setOnClickListener(this);
        
        MyButtonFont exitButton = (MyButtonFont)findViewById(R.id.menuExitBtn);
        exitButton.setOnClickListener(this);
        
        //Help
        MyButtonFont helpButton = (MyButtonFont)findViewById(R.id.menuHelpBtn);
        helpButton.setOnClickListener(this);
        
        /*END Click Button Listener*/
        
        //set preferences
        /*
         * Get preferences where you want, not exactly here
         * 
         * SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
         * 	boolean prefMusic = sharedPrefs.getBoolean("prefEnableEffect", true);
         */
        setPreferences();
        
        // splash screen
        splash = (ImageView) findViewById(R.id.splashscreen); 
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }

    /** Button handling */
    public void onClick(View v) {
                switch (v.getId()) {
                        //Start game
                        case R.id.menuStartBtn: {
                            Intent intent = new Intent();
                            intent.setClass(this, ChooseGameModeActivity.class);
                            startActivity(intent);
                        }break;
                        
                        //Settings
                        case R.id.menuSettingsBtn: {
                            Intent set = new Intent();
                            set.setClass(MenuActivity.this, SettingsActivity.class);
                        	//Intent set = new Intent(MenuActivity.this, SettingsActivity.class);
                            startActivity(set);
                        } break;
                        
                        //Help
                        case R.id.menuHelpBtn: {
                            Intent help = new Intent();
                            help.setClass(MenuActivity.this, HelpActivity.class);
                            startActivity(help);
                        } break;
                        
                         //exit
                        case R.id.menuExitBtn: {
                             finish();
                        }break;
                        
                        default:
                                break;
                }
        }
    
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 5000; //time of view splash picture 5 sec
    private ImageView splash;
    
    private Handler splashHandler = new Handler() { 
             public void handleMessage(Message msg) {
                 switch (msg.what) {
                 case STOPSPLASH:
                     //get out Splash picture
                     splash.setVisibility(View.GONE);
                     break;
                 }
                 super.handleMessage(msg);
             }
          };
          
    private void setPreferences(){
    	// sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
    	this.ENABLE_EFFECT = sharedPrefs.getBoolean("prefEnableEffect", true);
    	this.ENABLE_MUSIC = sharedPrefs.getBoolean("prefEnableMusic", true);
    }
}
