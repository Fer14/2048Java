package tp.pr2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import tp.pr2.logic.multigames.GameRules;
import tp.pr2.util.*;
/**
 * The Board class represents the board in which the game will be played, and holds
 * most of its functions. 
 */
public class Board {
	private Cell board[][];
	private int dim;
	
	/**
	 * The constructor for this class.
	 * @param dim The size of the desired board(dim * dim)
	 */
	public Board(int dim)
	{
		this.dim=dim;
		this.board = new Cell[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				this.board[i][j] = new Cell(0);
		//Only creates an empty board. The initial cells must be inserted from Game.
	}
	
	/**
	 * Draws a horizontal line for the string representation of the board with the desired characters. 
	 * @param hDelimiter The char used as the horizontal separation.
	 * @param hvDelimiter The char between horizontal and vertical separations.
	 * @param cellSize The number of characters that a cell can hold.
	 * @return A string with the complete horizontal line.
	 */
	private String drawHorizontalLine(String hDelimiter, String hvDelimiter, int cellSize)
	{
		String str = hvDelimiter;
		for (int i = 0; i < dim; i++)
			{
				str += MyStringUtils.repeat( hDelimiter , cellSize);
				str += hvDelimiter;
			}
		str += MyStringUtils.NEWLINE;
		return str;
	}
	
	/**
	 * Represents the current board in a string.
	 */
	public String toString()
	{
		//Fully customizable. Only one character for each string.
		final int cellSize = 7;
		final String vDelimiter= "|";
		final String hDelimiter= "-";
		final String hvDelimiter= "+";
		
		
		String board = "";
		board += drawHorizontalLine(hDelimiter, hvDelimiter, cellSize);
		for (int i = 0; i < this.dim; i++)
		{
			for (int j = 0; j < this.dim; j++)
			{
				board += vDelimiter;
				if (!this.board[i][j].isEmpty())
					board += MyStringUtils.centre(this.board[i][j].toString(), cellSize); //to center the value
				else 
					board += MyStringUtils.repeat(" " ,cellSize);;
			}
			board += vDelimiter;
			board += MyStringUtils.NEWLINE;
			board += drawHorizontalLine(hDelimiter, hvDelimiter, cellSize);
			
		}
		
		return board;
	}
	
	/**
	 * Transforms the board according to the direction, in order to simplifly the movement.
	 * @param dir The desired direction to move.
	 */
	private void transform(Direction dir)
	{
		switch (dir)
		{
		case LEFT: 
			this.invert();
			break;
		case DOWN:
			this.transpose();
			break;
		case UP:
			this.transpose();
			this.invert();
		default:
			break;
			
		}
	}
	
	/**
	 * Transforms the board back from a transform() to return it to the original state.
	 * Required for the correct order of transformations.
	 * @param dir The original direction of the move.
	 */
	private void transformBack(Direction dir)
	{
		if (dir == Direction.UP)//In this case alone, doing the same order of inversion/transposition would alter the original board.
		{
			this.invert();
			this.transpose();
		}
		else transform(dir);
	}
	
	/**
	 * Transforms the board into a int[][] for other classes to use.
	 * @return This board as a matrix of integers.
	 */
	public int[][] getState()
	{
		int[][] board = new int[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
			{
				board[i][j] = this.board[i][j].getValue();
			}
		return board;
	}
	
	/**
	 * Changes the board to copy a given matrix of integers of the same size.
	 * @param aState The matrix you want to copy.
	 */
	public void setState(int[][] aState)
	{
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
			{
				setCell(new Position(i, j), aState[i][j]);
			}
	}
	
	/**
	 * Moves the board to the desired direction, according to the 2048 and the game rules.
	 * @param dir The direction you want to move the board to.
	 * @param gr The sets of rules that will decide some things of the movement.
	 * @return The results of moving the board.
	 */
	public MoveResults executeMove(Direction dir, GameRules gr)
	{
		MoveResults results = new MoveResults();
		
		transform(dir);
		for (int i = 0; i < this.dim; i++)
		{
			MoveResults aux = this.moveRowRight(i,gr);
			results.setMoved(results.hasMoved() || aux.hasMoved());
			results.addScore(aux.getScore());
		}
		transformBack(dir); //transform wouldn't do right the case of Direction.UP.
		//}
		return results;
	}
	
	/**
	 * Moves a single row right, according to the game rules.
	 * @param row The row you desire to move.
	 * @param gr The sets of rules that will decide some things of the movement.The set
	 * @return The results of moving a row.
	 */
	private MoveResults moveRowRight(int row, GameRules gr)
	{
		MoveResults results = new MoveResults();
		boolean end = false; 
		int i = dim - 1, j = i - 1;
		int mergeValue;
		while (i >= 0 && !end)
		{
			while (j >= 0 && board[row][j].isEmpty()) //Looks for the first occupied cell.
				j--;
			end = j == -1; //ends if there are no occupied cells.
			if (!end)
			{
				if (board[row][i].isEmpty()) //if the current cell is empty, brings the found occupied cell there.
				{
					setCell(new Position(row, i), board[row][j].getValue());
					setCell(new Position(row, j), 0);
					results.setMoved(true);
					j--; //We have set the value of the j cell to 0, so we know for a fact it will be empty and we don't have to check it on the next iteration.
				}
				else
				{
					mergeValue = board[row][i].doMerge(board[row][j],gr);
					if (mergeValue != 0) //if the current cell is not empty, it tries to merge.
					{
						results.setMoved(true);
						results.addScore(mergeValue);
						j--; //If they have merged, we know for a fact that the cells between i and j, including j, are empty, so we can start looking from j--.
					}
					else if (j == i - 1) j--; //Without this 2 cells next to each other with different value would cause comparing the cell with itself on the next iteration.
					i--; //Only decreases i if the cell wasn't empty.
					
				}
			}
		}
		
		return results;
	}
	
	/**
	 * Swaps the value of 2 cells.
	 * @param pos1 The position of the first cell.
	 * @param pos2 The position of the second cell.
	 */
	
	private void swap(Position pos1, Position pos2)
	{
		Cell aux = new Cell();
		aux.duplicate(this.board[pos1.getx()][pos1.gety()]); 
		this.board[pos1.getx()][pos1.gety()].duplicate(this.board[pos2.getx()][pos2.gety()]);
		this.board[pos2.getx()][pos2.gety()].duplicate(aux);
	}
	
	/**
	 * Inverts the board, changen each column with its simetric one.
	 */
	private void invert()
	{
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim/2; j++)
			{
				swap(new Position (i,j), new Position(i, dim - 1 - j)); 
			}
	}
	
	/**
	 * Does a transposition of the matrix.
	 */
	private void transpose()
	{
		for (int i = 0; i < dim; i++)
			for (int j = 0; j <= i; j++)
			{
				swap(new Position (i,j), new Position(j, i));
			}
	}
	
	/**
	 * Selects the position of the cells that are empty.
	 * @return The list of positions which are empty.
	 */
	public ArrayAsList emptyCells()
	{
		ArrayAsList pos = new ArrayAsList(dim*dim);
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				if (board[i][j].isEmpty())
					pos.add(new Position(i, j));
		return pos;
		
	}
	
	/**
	 * Checks if the board is full.
	 * @return True if the board is full, false otherwise.
	 */
	public boolean isFull() {
		boolean full = true;
		int i = 0, j = 0;
		while (i < dim && full)
		{
			j = 0;
			while (j < dim && full)
			{
				full = !board[i][j].isEmpty();
				j++;
			}
			i++;
		}
		return full;
	}
	
	/**
	 * Checks, assuming that the board is full, if you can move any cell, according to the game rules.
	 * @param gr The sets of rules that decide the merging.
	 * @return Whether a move will be able to be done.
	 */
	
	public boolean cantMove(GameRules gr) 
	{
		
		if(this.isFull()) //Inside the if looks for a possible merge in case the board is full.
		{
		boolean esposible = false;	
		int i= 0, j=0;
		
			while(!esposible && i<this.dim)
			{
				j=0;
				while(!esposible && j<this.dim)
				{
					if (i < this.dim - 1)
						esposible = gr.combines(board[i][j].getValue(), board[i+1][j].getValue());
					if (j < this.dim - 1)
						esposible = gr.combines(board[i][j].getValue(), board[i][j+1].getValue());
					j++;
				}
				i++;
			}
			
			return !esposible;
		}

			return false;
	}
	
	/**
	 * Sets the value of the cell on a position to the given one.
	 * @param pos The position of the cell you want changed.
	 * @param value The new value for that cell.
	 */
	public void setCell(Position pos, int value)
	{
		board[pos.getx()][pos.gety()].setValue(value);
	}
	
	/**
	 * Empties the board, setting all cells to 0.
	 */
	public void clear() 
	{
		Position pos = new Position(0 , 0);
		for (int i = 0; i < dim; i++)
		{
			pos.setx(i);
			for (int j = 0; j < dim;j++)
			{	
				pos.sety(j);
				setCell(pos, 0);
			}
		}
	}
	
	/**
	 * Gets the maximum cell value on the board.
	 * @return The maximum value.
	 */
	
	public int getMax() {
		int max = 0, cellValue;
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				{cellValue = board[i][j].getValue();
				if (cellValue > max)
					max = cellValue;
				}
		return max;
	}
	
	/**
	 * Gets the minimum cell value on the board, -1 if there are no cells (should always be at least one).
	 * @return The minimum value.
	 */
	public int getMin() {
		boolean getFirst =false;
		int cellValue, min = -1;
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				{
				cellValue = board[i][j].getValue();
				if (!getFirst && !board[i][j].isEmpty())
					{
						min = cellValue;
						getFirst = true;
					}
				else if (!board[i][j].isEmpty() && cellValue < min)
					min = cellValue;
					
				}
		return min;
	}
	/**
	 * Saves the board to a chosen file.
	 * @param writer The bufferedWriter with the opened file.
	 * @throws IOException An error indicating something went wrong with the file.
	 */
	public void store(BufferedWriter writer) throws IOException
	{
		String separador="\t";
		for (int i = 0; i < dim; i++)
		{
			for (int j = 0; j < dim; j++)
			{
				writer.write(board[i][j].toString());
				if (j != dim - 1)
					writer.write(separador);
			}
			writer.write(MyStringUtils.NEWLINE);
		}
	}

	/**
	 * Loads the board from an open file.
	 * @param reader The bufferedReader with the opened file.
	 * @throws Exception Any error, from wrong number of readers to IOExceptions
	 */
	public void load(BufferedReader reader) throws Exception //IOException, ArrayOutOfBounds, NumberFormat...
	{
		String separador="\t";
		String[] line = reader.readLine().trim().split(separador);
		dim = line.length;
		Cell[][] newBoard = new Cell[dim][dim]; 
		
		for(int i=0; i < dim; i++) {
			
			for(int j = 0; j < line.length; j++) { //Uses line length in order to throw an exception if the line is larger than expected
				newBoard[i][j] = new Cell(Integer.parseInt(line[j]));
			} 
			if (i < dim - 1)
				line = reader.readLine().trim().split(separador);

		}
		board = newBoard;
	}


	public int getDim() {
		return dim;
	}


	
}
