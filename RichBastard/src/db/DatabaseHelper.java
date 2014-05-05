package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//it is useless now because I want to try to work with sql file
//but in case everything is bad I will use this, so please, do not delete it
public class DatabaseHelper extends SQLiteOpenHelper{

	private static int DB_VERSION = 1;
	private static String DB_NAME = "RichB2";
	
	//tables' variables descriptions
	public static final String TABLE_QUESTION = "Question";
	public static final String COLUMN_ID = "id_qstn";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_DIFFICULTY = "difficulty";
	public static final String COLUMN_TOPIC = "topic";
	
	
	public static final String TABLE_ANSWER = "Answer";
	public static final String COLUMN_ID_ANSWER = "id_answer";
	public static final String COLUMN_ID_QUESTION_FK = "id_qstn";
	public static final String COLUMN_TEXT_ANSWER = "answer_text";
	public static final String COLUMN_CORRECT = "correct";
	
	public boolean _should_fill = false;
	
	public boolean shouldFill(){
		  return _should_fill;
	  }
	
	
	public DatabaseHelper(Context context){
		super(context, DB_NAME, null, (DB_VERSION+1));
	}
	

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
/*		String CREATE_TABLE_QUESTION = "CREATE TABLE "+ TABLE_QUESTION +"("+
				COLUMN_ID + " INTEGER, " +
				COLUMN_TEXT + " TEXT," +
				COLUMN_DIFFICULTY + " INTEGER, "+
				COLUMN_TOPIC + " TEXT);";
		
		String CREATE_TABLE_ANSWER = "CREATE TABLE "+ TABLE_ANSWER + "("+
				COLUMN_ID_ANSWER + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
				COLUMN_ID_QUESTION_FK + " INTEGER,"+
				COLUMN_TEXT_ANSWER + " TEXT" +
				COLUMN_CORRECT + " INTEGER);";
		
		sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
		sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER);*/
		
		_should_fill = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_QUESTION);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
		onCreate(sqLiteDatabase);		
	}


}
