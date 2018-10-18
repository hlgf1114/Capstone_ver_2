package eval.model;

public class Questions {
	final public static int DEFAULT_LIST_NO = 7;								//리스트를 쓰려다가 그냥 갯수 정해져있어서 배열
	private Question[] qs;
	
	public Questions() {
		qs = new Question[DEFAULT_LIST_NO];
		for(int i = 0; i<DEFAULT_LIST_NO; i++) {
			qs[i] = new Question();
			qs[i].setqNo(i+1);
		}
	}
	
	public Questions(Question[] qs) {
		this.qs = qs;
	}

	public Question[] getQs() {
		return qs;
	}

	public void setQs(Question[] qs) {
		this.qs = qs;
	}
	public int getQsItemScore(int i) {
		return qs[i].getScore();
	}
	public String getQsItemComment(int i) {
		return qs[i].getComment();
	}
	
	public void setQsItemScore(int i, int score) {
		qs[i].setScore(score);
	}
	public void setQsItemComment(int i,String comment) {
		qs[i].setComment(comment);
	}
}

class Question {
	private int qNo;
	private int score;
	private String comment;
	
	final int DEFAULT_QUSET_NO = 0;
	final String DEFAULT_COMMENT = null;
	final int DEFAULT_SCORE = 0;
	
	public Question() {
		qNo = DEFAULT_QUSET_NO;
		comment = DEFAULT_COMMENT;
		score = DEFAULT_SCORE;
	}
	
	public Question(int qno, int score, String comment) {
		this.qNo = qno;
		this.comment = comment;
		this.score = score;
	}

	public int getqNo() {
		return qNo;
	}

	public void setqNo(int qNo) {
		this.qNo = qNo;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}