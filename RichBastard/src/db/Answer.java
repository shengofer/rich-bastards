package db;

public class Answer {

	private long id_answer;
	private String answer_text;
	private int id_question;
	private int correct;
	
	public Answer(){
	}
	
	public Answer(String answer_text, int id_question, int correct){
		this.answer_text = answer_text;
		this.id_question = id_question;
		this.correct = correct;
	}

	public long getId_answer() {
		return id_answer;
	}

	public void setId_answer(long id_answer) {
		this.id_answer = id_answer;
	}

	public String getAnswer_text() {
		return answer_text;
	}

	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}

	public int getId_question() {
		return id_question;
	}

	public void setId_question(int id_question) {
		this.id_question = id_question;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}
	
	

}
