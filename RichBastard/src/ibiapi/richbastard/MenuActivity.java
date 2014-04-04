package ibiapi.richbastard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


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
}
