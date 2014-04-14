package db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SdCardPath")
public class DataBaseHelper extends SQLiteOpenHelper{
	private static String DB_PATH = "/data/data/assets/databases/"; 
    private static String DB_NAME = "RichBastard";     
    private SQLiteDatabase myDataBase;    
    private final Context myContext;

    public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
    	this.myContext = context;
    }	

    
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
     
    	if(dbExist){   		
    	}else{
    		this.getReadableDatabase();
    		try { 
    			copyDataBase();    
    		} catch (IOException e) {     
    			throw new Error("Error copying database");    
    		}
    	}     
    }
     
    
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
     
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
    		}catch(SQLiteException e){     
    }
     
    	if(checkDB != null){    
    		checkDB.close();  
    	}     
    	return checkDB != null ? true : false;
    }
     
    
    private void copyDataBase() throws IOException{
    	InputStream myInput = myContext.getAssets().open(DB_NAME);

    	String outFileName = DB_PATH + DB_NAME;
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	byte[] buffer = new byte[1024];
    	int length;
    	
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
     
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
     
    }
     
    
    public void openDataBase() throws SQLException{
    	String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
     
    @Override
    public synchronized void close() { 
    	if(myDataBase != null)
    		myDataBase.close();
    	super.close();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
	
	public Map<Question, ArrayList<Answer>> getQuestionWithAnswers(long quest_number){
		//quest_number is the number of the question or difficulty(in db)
		
		//first we will select the important question
		Cursor cursor = myDataBase.rawQuery(
		"SELECT * FROM Question WHERE id_qstn=?;",
						new String[] { String.valueOf(quest_number) });
		
		//let's get the selected question
		Question question = new Question(
				cursor.getLong(cursor.getColumnIndex("id_qstn")),
				cursor.getString(cursor.getColumnIndex("text")),
				quest_number,
				cursor.getString(cursor.getColumnIndex("topic"))				
				);
		
		System.out.println(question.getText()+" ; "+question.get_difficulty());
		
		//this is the id of the question
		long id = cursor.getLong(cursor.getColumnIndex("id_qstn"));
		cursor.close();
		
		//let's select the answers for this question
		Cursor curs = myDataBase.rawQuery(
				"SELECT * FROM Answer WHERE id_qstn = ?;",
			     new String[] { String.valueOf(id) }
				);
		
		ArrayList<Answer> ans = new ArrayList<Answer>();
		if(curs != null){
			if (curs.moveToFirst()){
				do{
					Answer answer = new Answer(
							curs.getLong(curs.getColumnIndex("id_answer")),
							curs.getString(curs.getColumnIndex("text_answer")),
							id,
							curs.getLong(curs.getColumnIndex("correct"))
							);
					ans.add(answer);
				} while(curs.moveToNext());
			}
		};
		
		Map<Question, ArrayList<Answer>> result = new LinkedHashMap<Question, ArrayList<Answer>>();
		result.put(question, ans);
		
		return result;	
	}
	
	
     
}
     