package ibiapi.richbastard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import db.Answer;
import db.App;
import db.DataBaseHelper;
import db.Question;

public class TestQuestion 
{
	private Question quest;
	private String[] answer_options;
	private String correct_answer;
	
	public TestQuestion()
	{
		/*
		question = "Who discovered America?";
		answer_options = new String[4];
		answer_options[0] = "Vasco da Gama";
		answer_options[1] = "Marko Polo";
		answer_options[2] = "Christopher Columbus";
		answer_options[3] = "Hernan Cortes";
		correct_answer = "Christopher Columbus";*/
		DataBaseHelper db = App.db;
		Map<Question, ArrayList<Answer>> result = db.getQuestionWithAnswers(10);
		quest = (Question) result.keySet();
		
		int i = 0;
		for(ArrayList<Answer> val: result.values()){
			if(val.get(i).getCorrect() == 1){
				correct_answer = val.get(i).getAnswer_text();
			}
			answer_options[i] = val.get(i).getAnswer_text();
			i++;
		}
		
		shuffle();
	}
	
	public String getCorrectAnswer(){
		return correct_answer;
	}
	
	public String getQuestion()
	{
		return quest.getText();
	}
	
	public String[] getAnswerOptions()
	{
		return answer_options;
	}
	
	private void shuffle()
	{
		Collections.shuffle(Arrays.asList(answer_options));
	}
}
