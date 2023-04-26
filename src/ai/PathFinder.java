package ai;

import java.util.ArrayList;
import logic.base.Map;
import static utilz.Constants.GameProcess.*;

public class PathFinder {

	Node[][] node;
	int[][] mapTileNum;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, endNode, currentNode;
	boolean Reached = false;
	int step = 0;
	
	private int dx[] = {-1, 0, 1, 0};
	private int dy[] = {0, -1, 0, 1};
	
	public PathFinder() {
		this.mapTileNum = Map.getInstance().getMapTileNum();
		initialNodes();
	}
	
	public void initialNodes() {
		node = new Node[MAX_SCREEN_ROW][MAX_SCREEN_COL];
		
		int row = 0;
		int col = 0;
		
		while(row < MAX_SCREEN_ROW && col < MAX_SCREEN_COL) {
			node[row][col] = new Node(row, col);
			
			col++;
			if(col == MAX_SCREEN_COL) {
				col = 0;
				row++;
			}
		}	
	}
	
	public void resetNode() {
		
		int row = 0;
		int col = 0;
		
		while(row < MAX_SCREEN_ROW && col < MAX_SCREEN_COL) {
			
			node[row][col].Clear();
			
			col++;
			if(col == MAX_SCREEN_COL) {
				col = 0;
				row++;
			}
		}
		
		openList.clear();
		pathList.clear();
		Reached = false;
		step = 0;
	}
	
	public void setNode(int startRow, int startCol, int endRow, int endCol) {
		
		resetNode();
		
		// Set Start and End Node
		startNode = node[startRow][startCol];
		currentNode = startNode;
		endNode = node[endRow][endCol];
		openList.add(currentNode);
		
		int row = 0;
		int col = 1;
		
		while(row < MAX_SCREEN_ROW && col < MAX_SCREEN_COL) {
			
			int tileNum = mapTileNum[row][col-1];
			
			if(tileNum == 0) {
				node[row][col].solid = true;
			}
			
			setCost(node[row][col]);
			
			col++;
			if(col == MAX_SCREEN_COL) {
				col = 1;
				row++;
			}
		}
	}
	
	public void setCost(Node node) {
		
		int xDist = Math.abs(node.col - startNode.col);
		int yDist = Math.abs(node.row - startNode.row);
		node.gCost = xDist + yDist;
		
		xDist = Math.abs(node.col - endNode.col);
		yDist = Math.abs(node.row - endNode.row);
		node.hCost = xDist + yDist;
		
		node.fCost = node.gCost + node.hCost;
		
	}
	
	public boolean search() {
		
		int stepLimit = 400;
		
		while(Reached == false && step < stepLimit) {
			int row = currentNode.row;
			int col = currentNode.col;
			
			node[row][col].checked = true;
			currentNode.checked = true;
			openList.remove(currentNode);
			
			for(int i = 0; i < 4; i++) {
				int newRow = row + dy[i];
				int newCol = col + dx[i];
				if(newRow < 0 || newRow >= MAX_SCREEN_ROW || newCol < 0 || newCol >= MAX_SCREEN_COL) continue;
				openNode(node[newRow][newCol]);
			}
			
			int bestNodefCost = 999;
			int bestNodeIndex = 0;
			
			for(int i = 0; i < openList.size(); i++) {
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			if(openList.size() == 0) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == endNode) {
				Reached = true;
				trackThePath();
				break;
			}
			
			step++;
			
		}
		
		return Reached;
	}
	
	public void openNode(Node node) {
		
		if(node.open == true || node.checked == true || node.solid == true) return;
		
		node.open = true;
		node.parent = currentNode;
		openList.add(node);
		
	}
	
	public void trackThePath() {
		Node current = endNode;
		
		while(current != startNode) {
			pathList.add(0, current);
			current = current.parent;
		}
		
	}
}