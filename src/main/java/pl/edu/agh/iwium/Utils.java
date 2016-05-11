package pl.edu.agh.iwium;

import java.util.Comparator;

import pl.edu.agh.iwium.game.Card;
import pl.edu.agh.iwium.game.DealersCards;
import pl.edu.agh.iwium.game.Rank;
import pl.edu.agh.iwium.game.Suit;

public class Utils {
	private Utils(){
	}
	
	public static DealersCards createDealersCards(int numberOfDecks){
		if (numberOfDecks < 1 || numberOfDecks > 10)
			throw new IllegalArgumentException("" + numberOfDecks);
		
		DealersCards cards = new DealersCards();
		for (int i=0; i<numberOfDecks; ++i)
			Suit.list().stream().forEach(
				suit -> Rank.list().stream().forEach(
					rank -> cards.addCard(new Card(suit, rank))));
		cards.shuffle();
		return cards;
	}
	
	public static Comparator<Card> createCardComparator(boolean sortRankMajorOrder){
		if (sortRankMajorOrder)
			return (card1, card2) -> {
				int rank = card1.getRank().compareTo(card2.getRank());
				return rank != 0 ? rank : card1.getSuit().compareTo( card2.getSuit() );
			};
		else 
			return (card1, card2) -> {
				int suit = card1.getSuit().compareTo( card2.getSuit() );
				return suit != 0 ? suit : card1.getRank().compareTo(card2.getRank());
			};			
	}
}
