package ibiapi.richbastard;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity 
{
	private static GameManager mGameManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AudioPlayer.init(getApplicationContext());
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
		AudioPlayer.stopPlaying();
		mGameManager.stop();
        super.onStop();
    }

    @Override
	protected void onDestroy() {
    	AudioPlayer.stopPlaying();
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
    }

}
