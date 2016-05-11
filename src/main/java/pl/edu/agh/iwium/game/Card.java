package pl.edu.agh.iwium.game;

/**
 * Representation of a single playing card. A card consists of a suit value
 * (e.g. hearts, spades), a rank value (e.g. ace, 7, king), and an image of the
 * front of the card. A card object is immutable; once instantiated, the values
 * cannot change.
 *
 * @author John K. Estell
 * @version 1.0
 */
public class Card {

	private Suit suitValue;
	private Rank rankValue;


	public Card(Suit suit, Rank rank) {
		suitValue = suit;
		rankValue = rank;
	}

	public Suit getSuit() {
		return suitValue;
	}

	public Rank getRank() {
		return rankValue;
	}
	public String toString() {
		return rankValue.toString() + " of " + suitValue.toString();
	}


	/**
	 * Compares two cards to determine if they have the same value. This is not
	 * the same as the use of <code>equals</code> which compares two objects for
	 * equality.
	 * 
	 * @param card the other card
	 * @return <code>true</code> if the two cards have the same rank and suit
	 *         values, <code>false</code> if they do not.
	 */
	public boolean isSameAs(Card card) {
		if ((rankValue != card.rankValue) || (suitValue != card.suitValue))
			return false;
		else
			return true;
	}

}
