package ai;

public class Node {
	
	Node parent;
	public int col, row;
	int gCost, hCost, fCost;
	boolean solid, open, checked;
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void Clear() {
		this.open = false;
		this.checked = false;
		this.solid = false;
	}
	
}