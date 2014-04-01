package db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

//this class contains all queries used in application
public class Query {
	private DatabaseHelper dbOpenHelper;
	private SQLiteDatabase db;

	static Query _obj;

	public static Query getInstance() {
		if (_obj == null)
			_obj = new Query();
		return _obj;
	}
	
	public Query() {
		dbOpenHelper = new DatabaseHelper(App.getContext());
		db = dbOpenHelper.getWritableDatabase();		
		if (dbOpenHelper.shouldFill()){
			PushInformation();
		}
	}

		
	public void addQuestion(Question question){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TEXT, question.getText());
		values.put(DatabaseHelper.COLUMN_ID_BONUS_FK, question.getId_bonus());
		long id = db.insert(DatabaseHelper.TABLE_QUESTION, null, values);
		question.setId_qstn(id);
		
	}
	
	
	public void addAnswer(Answer answer, Question question){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TEXT_ANSWER, answer.getAnswer_text());
		values.put(DatabaseHelper.COLUMN_ID_QUESTION_FK, question.getId_qstn());
		values.put(DatabaseHelper.COLUMN_CORRECT, answer.getCorrect());
		long id = db.insert(DatabaseHelper.TABLE_ANSWER, null, values);
		answer.setId_answer(id);
	}
	
	
	public void addBonus(Bonus bonus){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_DIFFICULTY, bonus.getDifficulty());
		values.put(DatabaseHelper.COLUMN_SCORE, bonus.getScore());
		long id = db.insert(DatabaseHelper.TABLE_BONUS, null, values);
		bonus.setId_bonus(id);
	}
	
//	public void insertAnswers(ArrayList<Answer> listAnswer, Question question){
//		for(int i=0; i<listAnswer.size(); i++){
//			addAnswer(listAnswer.get(i), question);
//		}		
//	}
//	
	
	public void insertionFullData(String text_question, ArrayList<String>answers, 
			int correct, Bonus bonus){
		
		Question question1 = new Question(text_question, (int) bonus.getId_bonus());
		addQuestion(question1);		

		for(int i=0; i<answers.size(); i++){
			if(correct == i){
				Answer answer = new Answer(answers.get(i), (int) question1.getId_qstn(), 1);
				addAnswer(answer, question1);
			}
			else{
				Answer answer = new Answer(answers.get(i), (int) question1.getId_qstn(), 0);
				addAnswer(answer, question1);
			}
		}
		
		
	}
	
	private void PushInformation(){
		//I will insert all questions that are in our file here
		int difficulty = 1;
		int score = 100;
		Bonus bonus1 = new Bonus(difficulty, score);
		addBonus(bonus1);
		
		String text_question = "What is the capital of the UK?";		
		
		String answer1 = "Paris";
		String answer2 = "London";
		String answer3 = "Gondor";
		String answer4 = "Hogwarts";		
		int correct = 2; //put the position of correct answer
		
		ArrayList<String> listAnswer = new ArrayList<String>();
		listAnswer.add(answer1);
		listAnswer.add(answer2);
		listAnswer.add(answer3);
		listAnswer.add(answer4);
		
		insertionFullData(text_question, listAnswer, correct, bonus1);
		
		//A lot of questions and answers will be inserted here.
		//To study how to do that, view the example above
		
		
		
	}

	
}
