package pl.edu.agh.iwium.game;

import java.util.*;

/**
 * Represents a deck of playing cards. In order to have maximum flexibility,
 * this class does not implement a standard deck of playing cards; it only
 * provides the functionality of a deck of cards. The client programmer must
 * instantiate a Deck object, then populate it with the set of playing cards
 * appropriate for the card game being implemented. This allows for proper
 * implementation of card games such as pinochle (a 48-card deck containing only
 * aces, nines, tens, jacks, queens, and kings in all four suits, with each card
 * present twice in the deck) or casino-style blackjack (where six standard
 * decks are used for a game).
 * 
 * @author John K. Estell
 * @version 1.0
 */
public class DealersCards {
	private List<Card> cards;
	private int index;

	
	public DealersCards() {
		cards = new ArrayList<Card>();
		index = 0;
	}


	public void addCard(Card card) {
		cards.add(card);
	}

	public int size() {
		return cards.size();
	}

	/**
	 * The number of cards left in the deck.
	 * @return the number of cards left to be dealt from the deck.
	 */
	public int getNumberOfCardsRemaining() {
		return cards.size() - index;
	}

	/**
	 * Deal one card from the deck.
	 * 
	 * @return a card from the deck, or the null reference if there are no cards
	 *         left in the deck.
	 */
	public Card dealCard() {
		if (index >= cards.size())
			return null;
		else
			return cards.get(index++);
	}

	/**
	 * Shuffles the cards present in the deck.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * Looks for an empty deck.
	 * 
	 * @return <code>true</code> if there are no cards left to be dealt from the
	 *         deck.
	 */
	public boolean isEmpty() {
		if (index >= cards.size())
			return true;
		else
			return false;
	}

	/**
	 * Restores the deck to "full deck" status.
	 */
	public void restore() {
		index = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + index;
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
		DealersCards other = (DealersCards) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	public DealersCards copy() {
		DealersCards dc = new DealersCards();
		dc.cards = new ArrayList<>(cards);
		dc.index = index;
		return dc;
	}

}
