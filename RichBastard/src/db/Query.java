package db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//this class contains all queries used in application
public class Query {
	private DatabaseHelpe dbOpenHelper;
	private SQLiteDatabase db;

	static Query _obj;

	public static Query getInstance() {
		if (_obj == null)
			_obj = new Query();
		return _obj;
	}
	
	public Query() {
		dbOpenHelper = new DatabaseHelpe(App.getContext());
		db = dbOpenHelper.getWritableDatabase();		
		if (dbOpenHelper.shouldFill()){
			PushInformation();
		}
	}

		
	public void addQuestion(Question question){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelpe.COLUMN_TEXT, question.getText());
		values.put(DatabaseHelpe.COLUMN_DIFFICULTY, question.get_difficulty());
		values.put(DatabaseHelpe.COLUMN_TOPIC, question.getTopic());
		long id = db.insert(DatabaseHelpe.TABLE_QUESTION, null, values);
		question.setId_qstn(id);
		
	}
	
	
	public void addAnswer(Answer answer, Question question){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelpe.COLUMN_TEXT_ANSWER, answer.getAnswer_text());
		values.put(DatabaseHelpe.COLUMN_ID_QUESTION_FK, question.getId_qstn());
		values.put(DatabaseHelpe.COLUMN_CORRECT, answer.getCorrect());
		long id = db.insert(DatabaseHelpe.TABLE_ANSWER, null, values);
		answer.setId_answer(id);
	}
	
	
	public Map<Question, ArrayList<Answer>> getQuestionWithAnswers(long quest_number){
		//quest_number is the number of the question or difficulty(in db)
		
		//first we will select the important question
		Cursor cursor = db.rawQuery(
		"SELECT * FROM " +
				DatabaseHelpe.TABLE_QUESTION + " " +
						"WHERE "+ DatabaseHelpe.COLUMN_DIFFICULTY + "=?;",
						new String[] { String.valueOf(quest_number) });
		
		//let's get the selected question
		Question question = new Question(
				cursor.getLong(cursor.getColumnIndex(DatabaseHelpe.COLUMN_ID)),
				cursor.getString(cursor.getColumnIndex(DatabaseHelpe.COLUMN_TEXT)),
				quest_number,
				cursor.getString(cursor.getColumnIndex(DatabaseHelpe.COLUMN_TOPIC))				
				);
		
		//this is the id of the question
		long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelpe.COLUMN_ID));
		cursor.close();
		
		//let's select the answers for this question
		Cursor curs = db.rawQuery(
				"SELECT * FROM " + DatabaseHelpe.TABLE_ANSWER + " WHERE "+ 
			     DatabaseHelpe.COLUMN_ID_QUESTION_FK + " = ?;",
			     new String[] { String.valueOf(id) }
				);
		
		ArrayList<Answer> ans = new ArrayList<Answer>();
		if(curs != null){
			if (curs.moveToFirst()){
				do{
					Answer answer = new Answer(
							curs.getLong(curs.getColumnIndex(DatabaseHelpe.COLUMN_ID_ANSWER)),
							curs.getString(curs.getColumnIndex(DatabaseHelpe.COLUMN_TEXT_ANSWER)),
							id,
							curs.getLong(curs.getColumnIndex(DatabaseHelpe.COLUMN_CORRECT))
							);
					ans.add(answer);
				} while(curs.moveToNext());
			}
		};
		
		Map<Question, ArrayList<Answer>> result = new LinkedHashMap<Question, ArrayList<Answer>>();
		result.put(question, ans);
		
		return result;	
	}
	
	
	public void insertionFullData(String text_question, ArrayList<String>answers, 
			int correct, int difficulty, String topic){
		
		Question question1 = new Question(text_question, difficulty, topic);
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
//		Names of the topics
//		1) geo
//		2) history
//		3) tech
//		4) nature
//		5) sport
		
		//I will insert all questions that are in our file here
		
		//the first question
		int difficulty = 1;	
		String text_question = "What is the capital of the UK?";		
		String topic = "geo";
		
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
		
		insertionFullData(text_question, listAnswer, correct, difficulty, topic);
		
		//A lot of questions and answers will be inserted here.
		//To study how to do that, view the example above
		
		//Second question
		text_question= "What country gifted the Liberty Statue to the USA?";
		listAnswer.clear();
		answer1 = "France";
		answer2 = "Germany";
		answer3 = "Russia";
		answer4 = "Britain";
		correct = 1;
		topic = "art";
		
		listAnswer.add(answer1);
		listAnswer.add(answer2);
		listAnswer.add(answer3);
		listAnswer.add(answer4);
		
		insertionFullData(text_question, listAnswer, correct, difficulty, topic);	
		
		
	}

	
}
