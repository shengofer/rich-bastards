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
	
	
	private void PushInformation(){
		//I will insert all questions that are in our file here
		
	}

	
}
