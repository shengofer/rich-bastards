package ibiapi.richbastard;

import android.util.Log;

import db.Answer;
import db.DatabaseHelper;
import db.Question;

public class QuestionsManager
{
	private static QuestionsManager instance = null;
	
	//private static DatabaseHelper dbHelper = null;
	private static DatabaseHelper dao = null;
	
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
	
    private GameActivity mActivity;
	
	public void setActivity(GameActivity activity)
	{
		mActivity = activity;
	}
	
	public TestQuestion[] retrieveQuestions(String tpc)
	{
		dao = DatabaseHelper.getInstance(mActivity);
		topic = tpc;
		// TODO: retrieve questions by topic
		questions = new TestQuestion[GameManager.getNumberOfQuestions()];
		for (int q = 1; q <= questions.length; ++q)
		{
			Log.d("q ", String.valueOf(q));
			// TODO: what if appropriate question was not found?
			questions[q-1] = dao.getQuestionWithAnswers(topic,q,-1);
			//questions[q-1] = testQuestion;
		}
		return questions;
	}
	
	public TestQuestion retrieveAdditionalQuestion(int qNum)
	{
		// TODO: check if this method was invoked before the retrieveQuestions() was
		// TODO: retrieve question by topic
		//TestQuestion question = dbHelper.getQuestionWithAnswers(qNum);
		TestQuestion question = dao.getQuestionWithAnswers(topic,qNum,questions[qNum-1].getQuestionId());
		questions[qNum-1] = question;
		// TODO: what if appropriate question was not found?
		return question;
	}
	
}
