package drools.motorEmociones;

public class Palabra {
	private String word;
	private int id;
	private int pos;
	
	
	public Palabra(String word,int id, int pos){
		this.word = word;
		this.id = id;
		this.pos = pos;
	}


	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getPos() {
		return pos;
	}


	public void setPos(int pos) {
		this.pos = pos;
	}
	
}
