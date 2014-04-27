package Game;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

/*
 * !!!!!!!!!!!!!!!!!!!!! I don't have pictures for wild or skip cards yet
 */

public class Card extends JRadioButton implements Serializable{
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
	//colors
	public final static int YELLOW = 1;
	public final static int RED = 2;
	public final static int GREEN = 3;
	public final static int BLUE = 4;
	
	private static final long serialVersionUID = 1L;
	private int _rank;		//1,2,3,4,5,6,7,8,9,10,11,12, wild, skip
	private int _color;		//blue, yellow, red, green
	private Hand _hand;
	
	/**
	 * construct the card, validate its parameters
	 * 
	 * @param rank  should match those in CardAttributes Interface
	 * @param color should match those in CardAttributes Interface
	 */
	public Card(int rank, int color){
		//validate type
		if( rank < ONE || rank > WILD){
			this._rank = BLANK;
		}
		else{
			this._rank = rank;
		}
		
		//validate color
		if( color != YELLOW && color != RED && color != GREEN && color != BLUE){
			this._color = BLANK;
		}
		else{
			this._color = color;
		}
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
	 * 
	 */
	public void setHand(Hand containedBy){
		this._hand = containedBy;
	}
	
	/**
	 * 
	 */
	public void onClick(){
		this._hand.cardSelected(this);
	}
	
	/**
	 * 
	 */
	public void render(){
		//construct the path to the image that this card represents
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
		}

		ClassLoader cl = this.getClass().getClassLoader();
		try {
			this.setIcon( new ImageIcon( cl.getResource(path)));
		} catch(Exception e){
			this.setIcon( new ImageIcon( cl.getResource("Game/CardImages/1_Blue.png")));
			e.printStackTrace();
		}
	}
}
