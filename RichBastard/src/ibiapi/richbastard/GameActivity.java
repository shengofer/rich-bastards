package ibiapi.richbastard;

import ibiapi.richbastard.AudioPlayer.Theme;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
        initGameManager();
        mGameManager.startGame();
        initButtons();

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
        mAudioPlayer.setMusicEnabled(true);
        mAudioPlayer.setEffectsEnabled(true);
        mAudioPlayer.setTheme(Theme.Classic);
    }

    private void initGameManager()
    {
        mGameManager = GameManager.getInstance();
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
        final Button fiftyFiftyButton = (Button) findViewById(R.id.fiftyFiftyButton);
        fiftyFiftyButton.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                if (mGameManager.useFiftyFifty())
                	fiftyFiftyButton.setEnabled(false);//setClickable(false);
            }
        });
        
        final Button changeQuestionButton = (Button) findViewById(R.id.changeQuestionButton);
        changeQuestionButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (mGameManager.useChangeQuestion())
					changeQuestionButton.setEnabled(false);//setClickable(false);
			}
		});
        
        final Button askForAudienceButton = (Button) findViewById(R.id.askForAudienceButton);
        askForAudienceButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (mGameManager.useAskForAudience())
				{
					askForAudienceButton.setEnabled(false);
				}
			}
		});
        
        final Button takeMoneyButton = (Button) findViewById(R.id.takeMoneyButton);
        takeMoneyButton.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                showTakeMoneyDialog();
            }
        });
        
        int[] ids = {R.id.variant_a, R.id.variant_b, R.id.variant_c, R.id.variant_d};
        for (int i = 0; i < 4; i++)
        {
        	TextView answerView = (TextView) findViewById(ids[i]);
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
	
	protected Dialog onCreateDialog(int id) {
		   //   if (id == DIALOG_EXIT) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		switch (id) {
			//player win
			case 1:{
				        
				        adb.setTitle(R.string.end);
				        adb.setMessage(R.string.congratz);	       
				      //  adb.setIcon(android.R.drawable.ic_dialog_info);	        
				     //  adb.setPositiveButton(R.string.ok, myClickListener);
				        break;
			}
			//player lose
			case 2:{
				adb.setTitle(R.string.end);
				adb.setMessage(R.string.condolence);
				break;
			}
			// for take money
			case 3:{
				adb.setTitle(R.string.end);
				adb.setMessage(R.string.takemoney);
				break;
			}
			default:{
				break;
			}
		}
	        adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
    				Intent menu = new Intent();
                    menu.setClass(GameActivity.this, MenuActivity.class);
                    startActivity(menu);
	            }
	        });
		        // create dialog
		        return adb.create();
		 //    }
		  //    return onCreateDialog(id);
		    }
	
    public void showWinDialog() {
		// create alert dialog
		Dialog Dialog = onCreateDialog(1);
		// show it
		Dialog.show();
      }
    
    public void showCondDialog(){
    	Dialog Dialog = onCreateDialog(2);
    	Dialog.show();
    }
    
    public void showTakeMoneyDialog(){
    	Dialog Dialog = onCreateDialog(3);
    	Dialog.show();
    }
      

}
