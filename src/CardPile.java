import java.util.ArrayList;

public class CardPile {
	private ArrayList<Card> pile = new ArrayList<Card>();
	
	public CardPile(){
	}
	public CardPile(int mode){
		if( mode == 1 ){
			pile = generateDeck();
		}
	}
	public Card drawCard(){
		return pile.remove(0);
	}
	public void returnCard(Card toReturn){
		pile.add(toReturn);
	}
	
	private ArrayList<Card> generateDeck(){
		/*
		 * CARD VALUE
		 * 1 = ace
		 * 2 = two
		 * 3 = three
		 * ...
		 * 9 = nine
		 * 10 = ten
		 * 11 = jack
		 * 12 = queen
		 * 13 = king
		 * 
		 * CARD SUIT
		 * 0 = club
		 * 1 = heart
		 * 2 = spade
		 * 3 = diamond
		 */
		ArrayList<Card> deckPile = new ArrayList<Card>();
		for(int rank = 1; rank < 14; ++rank){
			for(int suit = 0; suit < 4; ++suit){
				Card card = new Card(rank, suit);
				deckPile.add( card);
			}
		}
		return deckPile;
	}
	public void shufflePile(){
		//TODO
	}
}
