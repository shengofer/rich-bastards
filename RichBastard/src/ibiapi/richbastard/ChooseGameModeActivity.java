package ibiapi.richbastard;

import ibiapi.fontpackage.MyButtonFont;
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

		MyButtonFont classicGameButton = (MyButtonFont) findViewById(R.id.classicGameButton);
		classicGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ChooseGameModeActivity.this,GameActivity.class);
				startActivity(intent);
			}
		});
		
		MyButtonFont thematicalGameButton = (MyButtonFont) findViewById(R.id.thematicalGameButton);
		thematicalGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ChooseGameModeActivity.this,ChooseTopicActivity.class);
				startActivity(intent);
			}
		});
		
		MyButtonFont blitzGameButton = (MyButtonFont) findViewById(R.id.blitzGameButton);
		blitzGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ChooseGameModeActivity.this,GameActivity.class);
				intent.putExtra("Blitz", true);
				startActivity(intent);
			}
		});
	}
	
}
