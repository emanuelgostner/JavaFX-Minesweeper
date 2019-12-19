package at.ac.fhcampuswien;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Board {
    public static final int CELL_SIZE = 15;
    public static final int ROWS = 20;
    public static final int COLS = 15;
    public static final int NUM_IMAGES = 13;
    public static final int NUM_MINES = 5;

    // Add further constants or let the cell keep track of its state.

    private Cell cells[][];
    private Image[] images;
    private int cellsUncovered;
    private int minesMarked;
    private boolean gameOver;

    /**
     * Constructor preparing the game. Playing a new game means creating a new Board.
     */
    public Board(){
        cells = new Cell[ROWS][COLS];
        cellsUncovered = 0;
        minesMarked = 0;
        gameOver = false;
        loadImages();
        // at the beginning every cell is covered
        // TODO cover cells. complete the grid with calls to new Cell();
        for(int i = 0; i < Board.ROWS; i++){
            for(int j = 0; j < Board.COLS; j++) {
                cells[i][j] = new Cell("/res/10.png", "covered","normal");
            }
        }
        // set neighbours for convenience
        // TODO compute all neighbours for a cell.

        // then we place NUM_MINES on the board and adjust the neighbours (1,2,3,4,... if not a mine already)
        // TODO place random mines.
        Integer[] arr = new Integer[(ROWS*COLS)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        List<Integer> arrList = Arrays.asList(arr);
        Collections.shuffle(arrList);
        Integer[] randomFields = new Integer[NUM_MINES];
        for(int i = 0; i< NUM_MINES; i++)
            randomFields[i] = arrList.get(i);
        for(int i=0; i< randomFields.length;i++) {
            int row = (randomFields[i]-1) / COLS;
            int column = (randomFields[i]-1) % COLS;
            if (column < 0)
            	column = 0;
            cells[row][column].update("/res/10.png","covered","mine");
            
            if (row > 0) //topNeighbor
                cells[row - 1][column].addNearbyMine();
            
            if (column < COLS - 1){ //rightNeighbor
                cells[row][column + 1].addNearbyMine();
                if(row > 0) //top-right
                    cells[row-1][column +1].addNearbyMine();
                if(row < ROWS-1) //bottom-right
                    cells[row+1][column +1].addNearbyMine();
            }
            
            if (row < ROWS - 1) //bottomNeighbor
                cells[row + 1][column].addNearbyMine();
            
            if (column > 0) { //left Neighbor
                cells[row][column - 1].addNearbyMine();
                if(row > 0) //top-left
                    cells[row-1][column -1].addNearbyMine();
                if(row < ROWS-1) //bottom-left
                    cells[row+1][column -1].addNearbyMine();
            }
            
        }
    }

    public boolean uncover(int row, int col) {
        // TODO uncover the cell, check if it is a bomb, if it is an empty cell you may! uncover all adjacent empty cells.
        String cellState = cells[row][col].getSate();
        String cellType = cells[row][col].getType();
        int mineNeighbors = cells[row][col].getNearbyMines();
        
        if(cellType.equals("mine")){
            cells[row][col].update("/res/9.png","uncovered","mine");
           return false;
        } else if (cellState.equals("covered")){
            System.out.println(mineNeighbors);
            cells[row][col].update("/res/"+Integer.toString(mineNeighbors)+".png","uncovered","normal");
            if(mineNeighbors == 0)
                checkNeighbors(row, col);
        }
        return true; // could be a void function as well
    }
    public void checkNeighbors(int row, int column) {
        if (row > 0){ //topNeighbor
        	int neighborRow = row -1;
        	int neighborCol = column;
        	Cell neighborCell = cells[neighborRow][neighborCol];
        	String neighborCellType = neighborCell.getType();
	        String neighborCellState = neighborCell.getSate();
	        int neighborCellMines = neighborCell.getNearbyMines();
        	Cell actualCell = cells[row][column];
	        int actualCellMines = actualCell.getNearbyMines();
        	
            if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
                neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
                checkNeighbors(neighborRow,neighborCol);
            }
        }
        
        if (row < ROWS - 1){ //bottomNeighbor
	        int neighborRow = row +1;
            int neighborCol = column;
            Cell neighborCell = cells[neighborRow][neighborCol];
            String neighborCellType = neighborCell.getType();
            String neighborCellState = neighborCell.getSate();
            int neighborCellMines = neighborCell.getNearbyMines();
            Cell actualCell = cells[row][column];
            int actualCellMines = actualCell.getNearbyMines();
            
            if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
                neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
                checkNeighbors(neighborRow,neighborCol);
            }
        }
        
        if (column < COLS - 1){ //rightNeighbor
	        int neighborRow = row;
            int neighborCol = column+1;
            Cell neighborCell = cells[neighborRow][neighborCol];
            String neighborCellType = neighborCell.getType();
            String neighborCellState = neighborCell.getSate();
            int neighborCellMines = neighborCell.getNearbyMines();
            Cell actualCell = cells[row][column];
            int actualCellMines = actualCell.getNearbyMines();
            
            if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
                neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
                checkNeighbors(neighborRow,neighborCol);
            }
            if(row > 0) {//top-right
            	neighborRow = row-1;
                neighborCol = column+1;
                neighborCell = cells[neighborRow][neighborCol];
                neighborCellType = neighborCell.getType();
                neighborCellState = neighborCell.getSate();
                neighborCellMines = neighborCell.getNearbyMines();
                actualCell = cells[row][column];
                actualCellMines = actualCell.getNearbyMines();
                
                if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
                    neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
                    checkNeighbors(neighborRow,neighborCol);
                }
            }
            if(row < ROWS-1) {//bottom-right
	            neighborRow = row+1;
                neighborCol = column+1;
                neighborCell = cells[neighborRow][neighborCol];
                neighborCellType = neighborCell.getType();
                neighborCellState = neighborCell.getSate();
                neighborCellMines = neighborCell.getNearbyMines();
                actualCell = cells[row][column];
                actualCellMines = actualCell.getNearbyMines();
                
                if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
                    neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
                    checkNeighbors(neighborRow,neighborCol);
                }
           
            }
        }
        
        if (column > 0) { //left Neighbor
	        int neighborRow = row;
            int neighborCol = column-1;
            Cell neighborCell = cells[neighborRow][neighborCol];
            String neighborCellType = neighborCell.getType();
            String neighborCellState = neighborCell.getSate();
            int neighborCellMines = neighborCell.getNearbyMines();
            Cell actualCell = cells[row][column];
            int actualCellMines = actualCell.getNearbyMines();
            
            if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
                neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
                checkNeighbors(neighborRow,neighborCol);
            }
            if(row > 0) { //top-left
				neighborRow = row;
				neighborCol = column-1;
				neighborCell = cells[neighborRow][neighborCol];
				neighborCellType = neighborCell.getType();
				neighborCellState = neighborCell.getSate();
				neighborCellMines = neighborCell.getNearbyMines();
				actualCell = cells[row][column];
				actualCellMines = actualCell.getNearbyMines();
				
				if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
					neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
					checkNeighbors(neighborRow,neighborCol);
				}
            }
            if(row < ROWS-1) { //bottom-left
				neighborRow = row+1;
				neighborCol = column-1;
				neighborCell = cells[neighborRow][neighborCol];
				neighborCellType = neighborCell.getType();
				neighborCellState = neighborCell.getSate();
				neighborCellMines = neighborCell.getNearbyMines();
				actualCell = cells[row][column];
				actualCellMines = actualCell.getNearbyMines();
				
				if ((neighborCellMines== 0 || (actualCellMines == 0 && neighborCellMines > 0 )) && neighborCellState.equals("covered") && neighborCellType.equals("normal")) {
					neighborCell.update("/res/"+neighborCellMines+".png","uncovered","normal");
					checkNeighbors(neighborRow,neighborCol);
				}
            }
        }
    }
    public boolean markCell(int row, int col) {
	    // TODO mark the cell if it is not already marked.
	    if (cells[row][col].getSate().equals("covered") && this.minesMarked < NUM_MINES){
		    cells[row][col].update("/res/11.png", "marked", cells[row][col].getType());
            this.minesMarked++;
        }
        else if(cells[row][col].getSate().equals("marked")) {
	        cells[row][col].update("/res/10.png", "covered", cells[row][col].getType());
	        this.minesMarked--;
        }
            return true;
    }

    public void uncoverEmptyCells(Cell cell) {
       // TODO you may implement this function. Not a must. It's usually implemented by means of a recursive function.
    }


    public void uncoverAllCells(){
        //TODO Uncover everything in case a mine was hit and the game is over.
    }


    public List<Cell> computeNeighbours(int x, int y){
        List<Cell> neighbours = new ArrayList<>();
        // TODO get all the neighbours for a given cell. this means coping with mines at the borders.
        return neighbours;
    }

    /**
     * Loads the given images into memory. Of course you may use your own images and layouts.
     */
    private void loadImages(){
        images = new Image[NUM_IMAGES];
        for(int i = 0; i < NUM_IMAGES; i++){
            var path = "src/res/" + i + ".png";
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                this.images[i] = new Image(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Cell[][] getCells() {
        return cells;
    }
    
    /**
     * Computes a random int number between min and max.
     * @param min the lower bound. inclusive.
     * @param max the upper bound. inclusive.
     * @return a random int.
     */
    private int getRandomNumberInts(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }
    
    public int getMinesMarked() {
        return minesMarked;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isGameWon() {
        for(int i = 0; i < Board.ROWS; i++){
            for(int j = 0; j < Board.COLS; j++) {
                if(cells[i][j].getSate().equals("covered") || (cells[i][j].getType().equals("mine") && !cells[i][j].getSate().equals("marked")) || (cells[i][j].getType().equals("normal") && cells[i][j].getSate().equals("marked")))
                    return false;
            }
        }
        return true;
    }
    
    public int getCellsUncovered() {
        return cellsUncovered;
    }

}
