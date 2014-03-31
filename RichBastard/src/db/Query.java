package db;

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
	
	
	private void PushInformation(){
		//I will insert all questions that are in our file here
		
	}

	
}
