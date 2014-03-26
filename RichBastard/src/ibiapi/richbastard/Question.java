package ibiapi.richbastard;

import java.util.Arrays;
import java.util.Collections;

public class Question 
{
	private String question;
	private String[] answer_options;
	private String correct_answer;
	
	public Question()
	{
		question = "Who discovered America?";
		answer_options = new String[4];
		answer_options[0] = "Vasco da Gama";
		answer_options[1] = "Marko Polo";
		answer_options[2] = "Christopher Columbus";
		answer_options[3] = "Hernan Cortes";
		correct_answer = "Christopher Columbus";
		shuffle();
	}
	
	public String getCorrectAnswer()
	{
		return correct_answer;
	}
	
	public String getQuestion()
	{
		return question;
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
