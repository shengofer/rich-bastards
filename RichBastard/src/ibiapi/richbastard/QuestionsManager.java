package ibiapi.richbastard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import db.Answer;
import db.App;
import db.DatabaseHelper;
import db.Question;

public class QuestionsManager
{
	private static QuestionsManager instance = null;
	
	private static DatabaseHelper dbHelper = null;
	
	private TestQuestion[] questions = null;
	
	private String topic = null;
	
	private QuestionsManager()
	{
		
	}
	
	public static QuestionsManager getInstance()
	{
		// "exteremely thread-safe" Singleton
		if (instance == null)
		{
			instance = new QuestionsManager();
		}
		return instance;
	}
	
	// TODO: get rid of this
	private TestQuestion testQuestion = new TestQuestion(
			new Question("Who discovered America?",0,null), 
			new Answer[]
			{
				new Answer("Vasco da Gama",0,0),
				new Answer("Marko Polo",0,0),
				new Answer("Christopher Columbus",0,1),
				new Answer("Hernan Cortes",0,0)
			});
	
	public TestQuestion[] retrieveQuestions(String tpc)
	{
		//dbHelper = App.db;
		topic = tpc;
		// TODO: retrieve questions by topic
		questions = new TestQuestion[GameManager.getNumberOfQuestions()];
		for (int q = 1; q <= questions.length; ++q)
		{
			// TODO: what if appropriate question was not found?
			//questions[q-1] = dbHelper.getQuestionWithAnswers(q);
			questions[q-1] = testQuestion;
		}
		return questions;
	}
	
	public TestQuestion retrieveAdditionalQuestion(int qNum)
	{
		// TODO: check if this method was invoked before the retrieveQuestions() was
		// TODO: retrieve question by topic
		//TestQuestion question = dbHelper.getQuestionWithAnswers(qNum);
		TestQuestion question = testQuestion;
		questions[qNum-1] = question;
		// TODO: what if appropriate question was not found?
		return question;
	}
	
}
