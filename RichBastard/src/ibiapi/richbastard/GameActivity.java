package ibiapi.richbastard;

import ibiapi.fontpackage.MyButtonFont;
import ibiapi.fontpackage.MyTextViewFont;
import ibiapi.richbastard.AudioPlayer.Theme;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity 
{
	private static GameManager mGameManager;
	private static AudioPlayer mAudioPlayer;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initAudioPlayer();

        String topic = null;
        boolean blitz = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
        	topic = bundle.getString("topic");
        	blitz = bundle.getBoolean("Blitz");
        }
        
        initGameManager(blitz);
        initButtons();
        
        mGameManager.startGame(topic);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }
    
    private void initAudioPlayer()
    {
        mAudioPlayer = AudioPlayer.getInstance();
        mAudioPlayer.setContext(getApplicationContext());    
        mAudioPlayer.setMusicEnabled(getMusicPref());
        mAudioPlayer.setEffectsEnabled(getEffectPref());
        mAudioPlayer.setTheme(Theme.Classic);
    }
    
    private boolean getMusicPref(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        boolean prefMusic = sharedPrefs.getBoolean("prefEnableMusic", true);
        return prefMusic;
    }
    
    private boolean getEffectPref(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        boolean prefEffect = sharedPrefs.getBoolean("prefEnableEffect", true);
        return prefEffect;
    }

    private void initGameManager(boolean blitz)
    {
        mGameManager = GameManager.getInstance(blitz);
    	mGameManager.setActivity(this);
    	mGameManager.initViewReferences();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
	protected void onPause() {
		super.onPause();
	}


	@Override
    protected void onStop() {
		mAudioPlayer.stopPlaying();
		mGameManager.stop();
        super.onStop();
    }

    @Override
	protected void onDestroy() {
    	mAudioPlayer.stopPlaying();
		super.onDestroy();
	}


	private void initButtons()
	{
        final MyButtonFont fiftyFiftyButton = (MyButtonFont) findViewById(R.id.fiftyFiftyButton);
        fiftyFiftyButton.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                if (mGameManager.useFiftyFifty())
                {
                	fiftyFiftyButton.setEnabled(false);//setClickable(false);
                	fiftyFiftyButton.setBackgroundResource(R.drawable.button_hint_used);
            	}
            }
        });
        
        final MyButtonFont changeQuestionButton = (MyButtonFont) findViewById(R.id.changeQuestionButton);
        changeQuestionButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (mGameManager.useChangeQuestion())
				{
					changeQuestionButton.setEnabled(false);//setClickable(false);
					changeQuestionButton.setBackgroundResource(R.drawable.button_hint_used);
				}
			}
		});
        
        final MyButtonFont askForAudienceButton = (MyButtonFont) findViewById(R.id.askForAudienceButton);
        askForAudienceButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (mGameManager.useAskForAudience())
				{
					askForAudienceButton.setEnabled(false);
					askForAudienceButton.setBackgroundResource(R.drawable.button_hint_used);
				}
			}
		});
        
        // I am so sorry...        
//        final MyButtonFont takeMoneyButton = (MyButtonFont) findViewById(R.id.takeMoneyButton);
//        takeMoneyButton.setOnClickListener(new View.OnClickListener() 
//        {
//            @Override
//            public void onClick(View view) 
//            {
//        		Handler handler = new Handler();
//				handler.postDelayed(new Runnable() {
//				    public void run() {
//				    	showTakeMoneyDialog();
//				    }}, 3000);      
//            }
//        });
        
        int[] ids = {R.id.variant_a, R.id.variant_b, R.id.variant_c, R.id.variant_d};
        for (int i = 0; i < 4; i++)
        {
        	MyTextViewFont answerView = (MyTextViewFont) findViewById(ids[i]);
        	final int id = i;
        	answerView.setOnClickListener(new View.OnClickListener() 
        	{		
				@Override
				public void onClick(View v) {
					mGameManager.chooseAnswer(id);					
				}
			});
        }
        
        int[] percIds = {R.id.percentageTextViewA, R.id.percentageTextViewB, 
        		R.id.percentageTextViewC,R.id.percentageTextViewD};
        for (int i = 0; i < 4; ++i)
        	findViewById(percIds[i]).setVisibility(View.INVISIBLE);   
    }
	
	protected Dialog onCreateDialog(String message) 
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle(R.string.end);
        adb.setMessage(message);
        adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
				Intent menu = new Intent();
                menu.setClass(GameActivity.this, MenuActivity.class);
                menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(menu);
            }
        });
        return adb.create();
    }
	
    public void showWinDialog() {
		// create alert dialog
        String message = getResources().getString(R.string.congratz);
		Dialog dialog = onCreateDialog(message);
		// show it
		dialog.show();
      }
    
    public void showCondDialog(int sum){
    	// create alert dialog
		String message = String.format(getResources().getString(R.string.condolence), sum);
		Dialog dialog = onCreateDialog(message);
		// show it
		dialog.show();
    }
    
//    public void showTakeMoneyDialog(){
//    	Dialog Dialog = onCreateDialog(3);
//    	Dialog.show();
//    }
      

    public void onBackPressed()
    {
    	super.onBackPressed();
        mGameManager.onStopGame();
    }
    
}
