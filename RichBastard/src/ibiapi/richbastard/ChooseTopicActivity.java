package ibiapi.richbastard;

import java.util.ArrayList;

import ibiapi.fontpackage.MyButtonFont;
import ibiapi.fontpackage.MyTextViewFont;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseTopicActivity extends Activity
{

	private static String[] topics = {"Art", "Geography"};
	private static int currentTopicIndex = 0;
	private static final int numberOfTopics = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_topic);
		
		final MyTextViewFont chooseTopicTextView = (MyTextViewFont) findViewById(R.id.topicTextView);
		chooseTopicTextView.setText(topics[currentTopicIndex]);		
		
		final MyButtonFont playButton = (MyButtonFont) findViewById(R.id.playThematicGameButton);
		playButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(ChooseTopicActivity.this,GameActivity.class);
				String topic = (String) chooseTopicTextView.getText();
				intent.putExtra("topic", topic);
				startActivity(intent);
			}
		});
		
		final Button rightButton = (Button) findViewById(R.id.rightArrow);
		rightButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentTopicIndex = (currentTopicIndex + 1) % numberOfTopics;
				chooseTopicTextView.setText(topics[currentTopicIndex]);
			}
		});
		
		final Button leftButton = (Button) findViewById(R.id.leftArrow);
		leftButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentTopicIndex = (currentTopicIndex - 1 + numberOfTopics) % numberOfTopics;
				chooseTopicTextView.setText(topics[currentTopicIndex]);
			}
		});
		
		/*ListView topicslistView = (ListView) findViewById(R.id.topicslistView);
		//topicslistView.setTextFilterEnabled(true);

		String[] topics = {"Geschichte", "Kunst", "Sport"};
		
		ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,topics);
		topicslistView.setAdapter(adapter);
		topicslistView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,	long id)
			{
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				// TODO: start GameActivity
			}
			
		});*/
	}
	
}
