package Game;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/*
 * !!!!!!!!!!!!!!!!!!!!! I don't have pictures for wild or skip cards yet
 */

public class Card extends JToggleButton implements Serializable, MouseListener{
	//types
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int ELEVEN = 11;
	public static final int TWELVE = 12;
	public static final int SKIP = 13;
	public static final int WILD = 14;
	
	public static final int BLANK = -1;
	public static final int DRAW = -2;
	//colors
	public final static int YELLOW = 1;
	public final static int RED = 2;
	public final static int GREEN = 3;
	public final static int BLUE = 4;
	
	public final static Color SELECTED = java.awt.Color.ORANGE;
	public final static Color UNSELECTED = java.awt.Color.DARK_GRAY;
	public final static int BORDER_SIZE = 5;
	
	private static final long serialVersionUID = 1L;
	private int _rank;		//1,2,3,4,5,6,7,8,9,10,11,12, wild, skip
	private int _color;		//blue, yellow, red, green
	private Hand _hand;
	private boolean _selected;
	
	/**
	 * construct the card, validate its parameters
	 * 
	 * @param rank  should match those in CardAttributes Interface
	 * @param color should match those in CardAttributes Interface
	 */
	public Card(int rank, int color){
		this._color = color;
		this._rank = rank;
	}
	
	/**
	 * get the type of this card
	 */
	public int getRank(){
		return this._rank;
	}
	
	/**
	 * get the color of this card
	 */
	public int getColor(){
		return this._color;
	}
	
	/**
	 * @return 
	 * 
	 */
	public Card render(Hand containedBy){
		if(containedBy != null){
			_selected = false;
			this.setBorder( BorderFactory.createLineBorder(UNSELECTED, BORDER_SIZE));
			this.setBorderPainted(true);
		}
		
		//System.out.println( this._color + " " + this._rank);
		//construct the path to the image that this card represents
		this._hand = containedBy;
		String path = "Game/CardImages/"+ this._rank;
		switch(this._color){
		case BLUE: 
			path += "_Blue.png";
			break;
		case RED:
			path += "_Red.png";
			break;
		case YELLOW:
			path += "_Yellow.png";
			break;
		case GREEN:
			path += "_Green.png";
			break;
		case BLANK:
			path += "_Blank.png";
			break;
		case DRAW:
			path += "_Draw.png";
		}
		
		ClassLoader cl = this.getClass().getClassLoader();
		try {
			this.setIcon( new ImageIcon( cl.getResource(path)));
		} catch(Exception e){
			this.setIcon( new ImageIcon( cl.getResource("Game/CardImages/-1_Blank.png")));
			e.printStackTrace();
		}
		
		this.addMouseListener(this);
		
		this.setSize( this.getPreferredSize() );
		return this;
	}

	/**
	 * 
	 */
	public void select(){
		this.setBorder( BorderFactory.createLineBorder(SELECTED, BORDER_SIZE));
		this._selected = true;
	}
	
	/**
	 * 
	 */
	public void unselect(){
		this.setBorder( BorderFactory.createLineBorder(UNSELECTED, BORDER_SIZE));
		this._selected = false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isSelected(){
		return this._selected;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if( this._hand != null){
			this.select();
			this._hand.unselectOthers(this);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
