package db;

public class Bonus {
	private long id_bonus;
	private int difficulty;
	private int score;
	
	public Bonus(){
	}

	public long getId_bonus() {
		return id_bonus;
	}

	public void setId_bonus(long id_bonus) {
		this.id_bonus = id_bonus;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
