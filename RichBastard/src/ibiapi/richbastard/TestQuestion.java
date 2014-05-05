package ibiapi.richbastard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import db.Answer;
import db.App;
import db.DatabaseHelper;
import db.Question;

public class TestQuestion 
{
	private Question question;
	private Answer[] answer_options;
	private String correct_answer;
	
	public TestQuestion(Question q, Answer[] answers)
	{
		setQuestion(q);
		setAnswerOptions(answers);
		shuffle();
		for (int i = 0; i < 4; ++i)
			if (answers[i].getCorrect() == 1)
			{
				correct_answer = answers[i].getAnswer_text();
				break;
			}
		
//		question = "Who discovered America?";
//		answer_options = new Answer[4];
//		answer_options[0].setAnswer_text("Vasco da Gama");
//		answer_options[1].setAnswer_text("Marko Polo");
//		answer_options[2].setAnswer_text("Christopher Columbus");
//		answer_options[3].setAnswer_text("Hernan Cortes");
//		correct_answer = "Christopher Columbus";
		
//		DataBaseHelper db = App.db;
//		Map<Question, ArrayList<Answer>> result = db.getQuestionWithAnswers(10);
//		quest = (Question) result.keySet();
//		
//		int i = 0;
//		for(ArrayList<Answer> val: result.values()){
//			if(val.get(i).getCorrect() == 1){
//				correct_answer = val.get(i).getAnswer_text();
//			}
//			answer_options[i] = val.get(i).getAnswer_text();
//			i++;
//		}
		
		//shuffle();
	}
	
	public String getCorrectAnswer()
	{
		return correct_answer;
	}
	
	public String getQuestion()
	{
		return question.getText();
		//return question;
	}
	public void setQuestion(Question q)
	{
		question = q;
	}
	
//	public Answer[] getAnswerOptions()
//	{
//		return answer_options;
//	}
	
	public String getAnswerOption(int index)
	{
		return answer_options[index].getAnswer_text();
	}
	
	public void setAnswerOptions(Answer[] options)
	{
		answer_options = options;
	}
	
	private void shuffle()
	{
		Collections.shuffle(Arrays.asList(answer_options));
	}
}
