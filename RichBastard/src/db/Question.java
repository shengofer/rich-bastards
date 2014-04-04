package db;

public class Question {
	
	private long id_qstn;
	private String text;
	private int difficulty; //means the number of the question
	
	public Question(){
	}
	
	public Question(String text, int difficulty){
		this.text = text;
		this.difficulty = difficulty;
	}

	public long getId_qstn() {
		return id_qstn;
	}

	public void setId_qstn(long id_qstn) {
		this.id_qstn = id_qstn;
	}
	
	public int get_difficulty() {
		return difficulty;
	}

	public void set_difficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
