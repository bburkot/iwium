package pl.edu.agh.iwium.blackjack;

import java.util.ArrayList;

import pl.edu.agh.iwium.game.Card;
import pl.edu.agh.iwium.game.Hand;
import pl.edu.agh.iwium.game.Rank;

public class BlackjackHand extends Hand {

	private boolean stand;
	
	

	/** 
	 * return values from -1 to 21  
	 * @see pl.edu.agh.iwium.game.Hand#evaluateHand()
	 */
	public int evaluateHand() {
		int value = 0;
		for (Card card : getCards())
			value += evaluateCard(card);
		if (value > 21)
			return -1;
		return value;
	}

	private static int evaluateCard(Card card) {
		Rank rank = card.getRank();
		if (rank.isNumericRank())
			return rank.getNumericValue();
		if (rank == Rank.ACE)
			return 11;
		return 10;
	}

	public void stand() {
		this.stand = true;
	}
	public boolean isStand() {
		return stand;
	}
	public void setStand(boolean stand) {
		this.stand = stand;
	}
	public boolean isLimitExceeded() {
		return evaluateHand() < 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public BlackjackHand copy(){
		BlackjackHand bh = new BlackjackHand();
		bh.cards = new ArrayList<Card>(cards);
		bh.stand = stand;
		return bh;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (stand ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlackjackHand other = (BlackjackHand) obj;
		if (stand != other.stand)
			return false;
		return true;
	}

}
