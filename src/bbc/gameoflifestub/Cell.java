package bbc.gameoflifestub;

public class Cell {
	private int x;
	private int y;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int hashCode(){
		return 31 * 31 * x + 31 * y;
	}
	
	public boolean equals(Object other){
		Cell otherCell = (Cell)other;
		return otherCell.x == x && otherCell.y == y; 
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
