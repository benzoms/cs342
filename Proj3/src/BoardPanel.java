import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * (0,0) (1,0)
 * (1,0)
 * 
 * 
 * 
 */

public class BoardPanel extends JPanel{
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	final private int SIZE = 800;
	private int _width, _height;
	/*
	 * not sure why we might need this so i'll leave it for now
	 */
	private ArrayList<Piece> _pieces;
	
	
	
	/*
	 * construct a board panel from a file
	 */
	public BoardPanel( String fileName){
		ArrayList<String> initList = readFileList( fileName);
		this.initBoardFromList( initList);
	}
	
	/*
	 * construct a board panel from another board panel
	 * (copying the passed panel)
	 */
	public BoardPanel( ArrayList<String> initList){
		this.initBoardFromList( initList);
	}
	
	/*
	 * convert this board to a string that can uniquely construct this board
	 */
	public ArrayList<String> getBoardList(){
		ArrayList<String> toReturn = new ArrayList<String>();
		/*
		toReturn.add( Board.toString);
		for( Piece p : _pieces){
			toReturn.add(p.toString());
		}
		*/
		return toReturn; //TODO 
	}
	
	/*
	 * @param file name
	 * read the file and return its contents as a string
	 */
	private ArrayList<String> readFileList( String fileName){
		ArrayList<String> toReturn = new ArrayList<String>();
		BufferedReader reader = null;
		
		//open the file
		try {
			reader = new BufferedReader( new FileReader( "boards/" + fileName ));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		//read the file
		try {
			while( reader.ready()){
				toReturn.add(reader.readLine());
			}
		} catch (IOException e1) {
			System.out.println("ERROR READING FILE LINE");
			e1.printStackTrace();
		}
		
		//close the file
        try {
			reader.close();
		} catch (IOException e) {
			System.out.println("ERROR CLOSING FILE");
			e.printStackTrace();
		}
        return toReturn;
	}
	
	/*
	 * build the board from the string passed as an argument
	 */
	private void initBoardFromList( ArrayList<String> lines){
		String line = lines.get(0);
		String lineSplit[] = line.split("  "); //TWO SPACES
		_height = Integer.parseInt( lineSplit[0]);
		_width = Integer.parseInt( lineSplit[1] ); 
		
		//read in the cars
		int posX, posY;
		int height, width;
		char direction;
		String name = "TEMP";
		for(int lineIndex = 1; lineIndex < lines.size(); lineIndex++){
		    line = lines.get(lineIndex);
			lineSplit = line.split("  "); //TWO SPACES
			posX = Integer.parseInt( lineSplit[0]);
			posY = Integer.parseInt( lineSplit[1]);
			height = Integer.parseInt( lineSplit[2]);
			width = Integer.parseInt( lineSplit[3]);
			direction = lineSplit[4].charAt(0);
			
			//TODO add the card to the board (below)
			System.out.println( name +" "+ posX +" "+ posY +" "+ height +" "+ width +" "+ direction);
			//board.add( new Car( name, posX, posY, height, width); 
		}
	}
	
	/*
	 * determines if the board is solved
	 * the 'z' piece is in the final position
	 */
	private boolean isSolved() {
		// TODO write code in this stub
		// for( Piece p : this._pieces)
		//   if( p.isFinalPiece() && p.isInFinalPosition() )
		//		return true;
		return false;
	}

	/*
	 * 
	 */
	public ArrayList<String> solvePuzzle(){
		ConcurrentLinkedQueue<ArrayList<String>> queue = new ConcurrentLinkedQueue<ArrayList<String>>();
		ConcurrentLinkedQueue<ArrayList<String>> visited = new ConcurrentLinkedQueue<ArrayList<String>>();
		ArrayList<String> toReturn = new ArrayList<String>();
		
		queue.add( this.getBoardList() );
		visited.add( this.getBoardList() );
		
		while( !queue.isEmpty() ){
			ArrayList<String> queueList = queue.poll();
			BoardPanel queueBoard = new BoardPanel( queueList);
			if( queueBoard.isSolved() ){ //TODO implement this function
				toReturn = queueBoard.getBoardList();
				break;
			}
			
			//possible moves ("adjacent edges")
			ArrayList<ArrayList<String>> possibleMoves = BoardPanel.getPossibleMoveLists( queueBoard);
			for( ArrayList<String> possibleBoard : possibleMoves){ 
				if( visited.contains( possibleBoard) ) //XXX this might be dangerous if it doesn't do what we think it does
					break;
				else{
					visited.add( possibleBoard);
					queue.add( possibleBoard);
				}
			}
		}
		//ERROR THIS BOARD CANT BE SOLVED, TODO
		// maybe changes this to Boolean instead of returning the solved board
		// IF the board can be solved, store its solution in a member variable of
		// *this* (the board the user was using) (this shouldd work because of scope)
		return toReturn;
	}
	
	/*
	 * get a list of 
	 */
	private static ArrayList<ArrayList<String>> getPossibleMoveLists(BoardPanel board) {
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		for( Piece p : board._pieces){
			if( board.simulateMove( p, BoardPanel.LEFT) != null )
				toReturn.add( board.simulateMove( p, BoardPanel.LEFT ));
			if( board.simulateMove( p, BoardPanel.RIGHT) != null)
				toReturn.add( board.simulateMove( p, BoardPanel.RIGHT ));
			if( board.simulateMove( p, BoardPanel.UP) != null)
				toReturn.add( board.simulateMove( p, BoardPanel.UP ));
			if( board.simulateMove( p, BoardPanel.DOWN) != null)
				toReturn.add( board.simulateMove( p, BoardPanel.DOWN ));
		}
		
		return null;
	}
	
	/*
	 * simulate the move of a specific piece in a specific direction.
	 * the directions are determined by the ones IN THIS CLASS.
	 */
	private ArrayList<String> simulateMove(Piece p, int direction){
		BoardPanel simulated = new BoardPanel( this.getBoardList() );
		
		// this instruction here is tentative because the panel is
		// more likely to know where the piece can move instead of the
		// piece
		if( p.canMove(direction)) //TODO actually move the piece too
			return simulated.getBoardList();
		else
			return null;
	}

	/*
	 * show the user
	 */
	public void showHint(){
		//TODO
	}
	
	private class Piece extends JPanel{
		private String _name;
		private Boolean _canMoveHoriz;
		private int _x, _y;
		private int _height, _width;
		
		/*
		 * construct a piece
		 */
		public Piece( String name, int x, int y, int height, int width, Boolean canMoveHoriz){
			//TODO 
		}

		public boolean canMove(int direction){
			//TODO
			return false;
		}
	}
}
