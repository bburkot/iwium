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

	private Suit suit;
	private Rank rank;


	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}
	public Rank getRank() {
		return rank;
	}
	public String toString() {
		return rank.toString() + " of " + suit.toString();
	}
	public Integer getRankOrdinal() {
		return rank.ordinal();
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
		if ((rank != card.rank) || (suit != card.suit))
			return false;
		else
			return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

}
