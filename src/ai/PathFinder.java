package ai;

import java.util.ArrayList;
import logic.base.Map;
import static utilz.Constants.GameProcess.*;

public class PathFinder {
	
	private int step;
	private int[][] mapTileNum;
	private boolean Reached;
	private Node[][] node;
	private Node startNode, endNode, currentNode;
	private ArrayList<Node> openList;
	private ArrayList<Node> pathList;
	
	private int[] dx = {-1, 0, 1, 0};
	private int[] dy = {0, -1, 0, 1};
	
	public PathFinder() {
		setStep(0);
		setReached(false);
		setMapTileNum(Map.getInstance().getMapTileNum());
		setOpenList(new ArrayList<>());
		setPathList(new ArrayList<>());
		
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
			node[row][col].clear();	
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
				node[row][col].setSolid(true);
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
		int xDist = Math.abs(node.getCol() - startNode.getCol());
		int yDist = Math.abs(node.getRow() - startNode.getRow());
		node.setgCost(xDist + yDist);
		
		xDist = Math.abs(node.getCol() - endNode.getCol());
		yDist = Math.abs(node.getRow() - endNode.getRow());
		node.sethCost(xDist + yDist);
		node.setfCost(node.getgCost() + node.gethCost());
	}
	
	public boolean search() {
		
		int stepLimit = 400;
		
		while(Reached == false && step < stepLimit) {
			int row = currentNode.getRow();
			int col = currentNode.getCol();
			
			node[row][col].setChecked(true);
			currentNode.setChecked(true);
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
				if(openList.get(i).getfCost() < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).getfCost();
				}
				else if(openList.get(i).getfCost() == bestNodefCost) {
					if(openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()) {
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
		
		if(node.isOpen() == true || node.isChecked() == true || node.isSolid() == true) return;
		
		node.setOpen(true);
		node.setParent(currentNode);
		openList.add(node);
		
	}
	
	public void trackThePath() {
		Node current = endNode;
		
		while(current != startNode) {
			pathList.add(0, current);
			current = current.getParent();
		}
		
	}
	
	// Getter & Setter
	
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int[][] getMapTileNum() {
		return mapTileNum;
	}

	public void setMapTileNum(int[][] mapTileNum) {
		this.mapTileNum = mapTileNum;
	}

	public boolean isReached() {
		return Reached;
	}

	public void setReached(boolean reached) {
		Reached = reached;
	}

	public Node[][] getNode() {
		return node;
	}

	public void setNode(Node[][] node) {
		this.node = node;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public ArrayList<Node> getOpenList() {
		return openList;
	}

	public void setOpenList(ArrayList<Node> openList) {
		this.openList = openList;
	}

	public ArrayList<Node> getPathList() {
		return pathList;
	}

	public void setPathList(ArrayList<Node> pathList) {
		this.pathList = pathList;
	}

	public int[] getDx() {
		return dx;
	}

	public void setDx(int[] dx) {
		this.dx = dx;
	}

	public int[] getDy() {
		return dy;
	}

	public void setDy(int[] dy) {
		this.dy = dy;
	}
}