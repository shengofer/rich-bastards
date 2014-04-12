package ibiapi.richbastard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseGameModeActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_game_mode);

		Button classicGameButton = (Button) findViewById(R.id.classicGameButton);
		classicGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ChooseGameModeActivity.this,GameActivity.class);
				startActivity(intent);
			}
		});
		
		Button thematicalGameButton = (Button) findViewById(R.id.thematicalGameButton);
		thematicalGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ChooseGameModeActivity.this,ChooseTopicActivity.class);
				startActivity(intent);
			}
		});
		
		Button blitzGameButton = (Button) findViewById(R.id.blitzGameButton);
		blitzGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO: start Blitz Activity
			}
		});
	}
	
}
