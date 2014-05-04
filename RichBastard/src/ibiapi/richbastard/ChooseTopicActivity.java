package ibiapi.richbastard;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseTopicActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_topic);
		
		TextView chooseTopicTextView = (TextView) findViewById(R.id.chooseTopicTextView);
		
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
