package db;

public class Question {
	
	private int id_qstn;
	private String text;
	private int id_bonus;
	
	public Question(){
	}

	public int getId_qstn() {
		return id_qstn;
	}

	public void setId_qstn(int id_qstn) {
		this.id_qstn = id_qstn;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId_bonus() {
		return id_bonus;
	}

	public void setId_bonus(int id_bonus) {
		this.id_bonus = id_bonus;
	}
	
}
