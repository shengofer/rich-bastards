package ibiapi.richbastard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class MenuActivity extends Activity implements OnClickListener {          
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // fullscreen app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // without title
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.menu_game);
        //setContentView(new GameView(this,null));
        
        Button startButton = (Button)findViewById(R.id.menuStartBtn);
        startButton.setOnClickListener(this);
        
        Button exitButton = (Button)findViewById(R.id.menuExitBtn);
        exitButton.setOnClickListener(this);
        
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
                            intent.setClass(this, GameActivity.class);
                            startActivity(intent);
                        }break;
                        
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
}
