package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static int DB_VERSION = 1;
	private static String DB_NAME = "RichBastard";
	
	//tables' variables descriptions
	public static final String TABLE_QUESTION = "Question";
	public static final String COLUMN_ID = "id_qstn";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_ID_BONUS_FK = "id_bonus";
	
	public static final String TABLE_BONUS = "Bonus";
	public static final String COLUMN_ID_BONUS = "id_bonus";
	public static final String COLUMN_DIFFICULTY = "difficulty";
	public static final String COLUMN_SCORE = "score";
	
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
		String CREATE_TABLE_QUESTION = "CREATE TABLE "+ TABLE_QUESTION +"("+
				COLUMN_ID + " INTEGER PRIMARY KEY autoincrement," +
				COLUMN_TEXT + " TEXT," +
				COLUMN_ID_BONUS_FK + " INTEGER);";
		
		String CREATE_TABLE_BONUS = "CREATE TABLE " + TABLE_BONUS +"("+
				COLUMN_ID_BONUS + " INTEGER PRIMARY KEY autoincrement ,"+
				COLUMN_DIFFICULTY + " INTEGER," +
				COLUMN_SCORE + "INTEGER);";
		
		String CREATE_TABLE_ANSWER = "CREATE TABLE "+ TABLE_ANSWER + "("+
				COLUMN_ID_ANSWER + " INTEGER PRIMARY KEY autoincrement,"+
				COLUMN_ID_QUESTION_FK + " INTEGER,"+
				COLUMN_TEXT_ANSWER + " TEXT" +
				COLUMN_CORRECT + " INTEGER);";
		
		sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
		sqLiteDatabase.execSQL(CREATE_TABLE_BONUS);
		sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER);
		
		_should_fill = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_QUESTION);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BONUS);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
		onCreate(sqLiteDatabase);		
	}


}
