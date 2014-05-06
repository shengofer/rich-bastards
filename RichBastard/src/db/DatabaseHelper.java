package db;

import ibiapi.richbastard.TestQuestion;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
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
	
	  
    private SQLiteDatabase myDataBase;    
	
	public boolean shouldFill(){
		  return _should_fill;
	  }
	
	
	private DatabaseHelper(Context context){
		super(context, DB_NAME, null, (DB_VERSION+1));
	}
	
	private static DatabaseHelper instance = null;
    
    public static DatabaseHelper getInstance(Context context)
    {
    	if (instance == null)
    		instance = new DatabaseHelper(context);
    	return instance;
    }
	
    public void setDataBase(SQLiteDatabase db)
	{
		myDataBase = db;
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
	
	
	// TODO: select a question randomly
		// hint: SELECT * FROM table ORDER BY RANDOM() LIMIT 1;
		// more TODO: retrieve a question which id is not equal to a specified id
	public TestQuestion getQuestionWithAnswers(String topic, long quest_number, long curQuestId)
	{
		//quest_number is the number of the question or difficulty(in db)
		
		//first we will select the important question
		Cursor cursor = null;
		if (curQuestId == -1)
		{
			if (topic != null)
				cursor = myDataBase.rawQuery(
						"SELECT * FROM Question WHERE difficulty=? AND topic=? ORDER BY RANDOM() LIMIT 1",   
							new String[] { String.valueOf(quest_number), topic });
			else
				cursor = myDataBase.rawQuery(
						"SELECT * FROM Question WHERE difficulty=? ORDER BY RANDOM() LIMIT 1",   
							new String[] { String.valueOf(quest_number)});
			cursor.moveToFirst();
		}
		else
		{
			if (topic != null)
				cursor = myDataBase.rawQuery(
						"SELECT * FROM Question WHERE difficulty=? AND topic=? ORDER BY RANDOM()",   
							new String[] { String.valueOf(quest_number), topic });
			else
				cursor = myDataBase.rawQuery(
						"SELECT * FROM Question WHERE difficulty=? ORDER BY RANDOM()",   
							new String[] { String.valueOf(quest_number)});
			cursor.moveToFirst();
//			int cnt = cursor.getCount();
			long qId = cursor.getLong(cursor.getColumnIndex("id_question"));
			while (curQuestId == qId)
			{
				if (cursor.moveToNext())
					qId = cursor.getLong(cursor.getColumnIndex("id_question"));
				else
				{
					cursor.moveToFirst();
					break;
				}
			}
		}
		
//		int cnt = cursor.getCount();
		
		//let's get the selected question
		Question question = new Question(
				cursor.getLong(cursor.getColumnIndex("id_question")),
				cursor.getString(cursor.getColumnIndex("text")),
				quest_number,
				cursor.getString(cursor.getColumnIndex("topic"))				
				);
		
		
		System.out.println(question.getText()+" ; "+question.get_difficulty());
		
		//this is the id of the question
		long id = cursor.getLong(cursor.getColumnIndex("id_question"));
		cursor.close();
		
		//let's select the answers for this question
		Cursor curs = myDataBase.rawQuery(
				"SELECT * FROM Answer WHERE id_question = ?;",
			     new String[] { String.valueOf(id) }
				);
		
		Answer[] answers = new Answer[4];
//		ArrayList<Answer> ans = new ArrayList<Answer>();
		if(curs != null){
			if (curs.moveToFirst()){
				int i = 0;
				do{
					answers[i++] = new Answer(
							curs.getLong(curs.getColumnIndex("id_answer")),
							curs.getString(curs.getColumnIndex("answer_text")),
							id,
							curs.getLong(curs.getColumnIndex("correct"))
							);
//					ans.add(answer);
				} while(curs.moveToNext());
			}
		};
		
		
//		Map<Question, ArrayList<Answer>> result = new LinkedHashMap<Question, ArrayList<Answer>>();
//		result.put(question, ans);
//		
//		return result;	
		TestQuestion tQuestion = new TestQuestion(question,answers);
		return tQuestion;
	}


}
