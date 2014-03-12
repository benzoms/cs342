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
	final private int SIZE = 800;
	private int _width, _height;
	/*
	 * not sure why we might need this so i'll leave it for now
	 */
	private ArrayList<String> _pieceNames;
	/*
	 * when a button is clicked, we need the name of the "piece"
	 * (which is really a sequence of pieces) so that each of its pieces
	 * can be moved one location to the [left, right, up, down]
	 * Because each tile will have a name and if a board "car" is
	 * 3 long, we will have 3 pieces in the hash table
	 */
	private Hashtable<String, Piece> _nameToPiece;
	
	/*
	 * construct a board panel from a file
	 */
	public BoardPanel( String fileName){
		ArrayList<String> initList = readFile( fileName);
		this.initBoardFromString( initList);
		
		/*
		_pieceNames = new ArrayList<String>();
		_nameToPiece = new Hashtable<String,Piece>();
		
		this.setSize(SIZE,SIZE);
		this.setLayout( new GridLayout( _height, _width ));
		for( int numRows = 0; numRows < _width; numRows++){
			for( int numCols = 0; numCols < _height; numCols++){
				String hashVal = getHash( numRows, numCols); //this won't be the case when we read from the file
				Piece toAdd = new Piece( hashVal, numRows, numCols, false, false, false, false);
				this.add( toAdd);
				_nameToPiece.put( hashVal, toAdd);
				
				_pieceNames.add( hashVal );
			}
		}
		*/
	}
	
	/*
	 * construct a board panel from another board panel
	 * (copying the passed panel)
	 */
	public BoardPanel( BoardPanel BP){
		ArrayList<String> initList = BP.getBoardString();
		this.initBoardFromString( initList);
	}
	
	/*
	 * convert this board to a string that can uniquely construct this board
	 */
	public ArrayList<String> getBoardString(){
		ArrayList<String> toReturn = new ArrayList<String>();
		/*
		toReturn.add( Board.toString);
		for( Piece p : Pieces){
			toReturn.add(p.toString());
		}
		*/
		return toReturn; //TODO 
	}
	
	/*
	 * @param file name
	 * read the file and return its contents as a string
	 */
	private ArrayList<String> readFile( String fileName){
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
	private void initBoardFromString( ArrayList<String> lines){
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
	 * this is critical function
	 * a piece calls this function when it realizes it should be moved in a particular direction
	 * we must:
	 *  - when we move it, successfully move the tiles over and generate a blank tile into the area where the car left
	 */
	private void controlMove( String name, int direction){
		System.out.println( "TRYING TO MOVE: " + this._nameToPiece.get(name)._name );
	}
	
	/*
	 * get the hash value that our piece(s) will be at in the tables
	 */
	private String getHash( int height, int width){
		return Integer.toString(height*100 + width);
	}
	
	private class Piece extends JPanel{
		private String _name;
		private Boolean _leftNeighbor, _rightNeighbor;
		private Boolean _northNeighbor, _southNeighbor;
		private Boolean _canMoveVert, _canMoveHoriz;
		private int _x, _y;
		
		/*
		 * construct a piece
		 */
		public Piece( String name, int x, int y, Boolean leftNeighbor, Boolean rightNeighbor, Boolean northNeighbor, Boolean southNeighbor){
			this.setLayout( new FlowLayout());
			_name = name;
			_x = x;
			_y = y;
			_canMoveVert = true;
			_canMoveHoriz = true;
			_leftNeighbor = leftNeighbor; //change to up neighbor as well
			_rightNeighbor = rightNeighbor;
			_northNeighbor = northNeighbor;
			_southNeighbor = southNeighbor;
			if(!leftNeighbor) this.add( new ArrowKey( javax.swing.SwingConstants.LEFT) );
			if(!northNeighbor) this.add( new ArrowKey( javax.swing.SwingConstants.NORTH) );
			if(!southNeighbor) this.add( new ArrowKey( javax.swing.SwingConstants.SOUTH) );
			if(!rightNeighbor) this.add( new ArrowKey( javax.swing.SwingConstants.RIGHT) );
		}
		
		/*
		 * used to move the piece left
		 */
		void moveInDirection( int direction){
			if( direction == javax.swing.SwingConstants.NORTH && _canMoveVert && _y < 0 ){
				controlMove( _name, direction);
			}
			else if( direction == javax.swing.SwingConstants.SOUTH && _canMoveVert && _y < _height ){
				controlMove( _name, direction);
			}
			else if( direction == javax.swing.SwingConstants.LEFT && _canMoveHoriz && _x > 0 ){
				controlMove( _name, direction);
			}
			else if( direction == javax.swing.SwingConstants.RIGHT && _canMoveHoriz && _x < _width ){
				controlMove( _name, direction);
			}
		}
		
		private class ArrowKey extends JButton{
			int _direction;
			
			/*
			 * construct an arrow key to make the piece move in a certain direction
			 */
			public ArrowKey( int direction){
				_direction = direction;
				this.setActionCommand( _x + " " + _y + " " + _direction);
				this.addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent e)
		            {
						System.out.println( e.getActionCommand() );
						moveInDirection(_direction);
		            }
				});
				
				switch(direction){
				case LEFT: this.setText("<");
					break;
				case RIGHT: this.setText(">");
					break;
				case NORTH: this.setText("^");
					break;
				case SOUTH: this.setText("V");
				}
			}
		}
	}
}
