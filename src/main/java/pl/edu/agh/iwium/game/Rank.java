package pl.edu.agh.iwium.game;

import java.util.Arrays;
import java.util.List;

/**
 * Specification of the rank values for a standard deck of cards. Client has
 * ability to set either the ace or the king to be the highest ranking card;
 * default is king high. Ranks are established in the following ascending order:
 * <p>
 * King high: ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king.
 * <p>
 * Ace high: 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace.
 * <p>
 * Class can be extended for implementation of speciality decks containing a
 * subset of the standard ranks, e.g. pinochle.
 */
public enum Rank {
	
	TWO   (2),
	THREE (3),
	FOUR  (4),
	FIVE  (5),
	SIX   (6),
	SEVEN (7),
	EIGHT (8),
	NINE  (9),
	TEN   (10),
	JACK,
	QUEEN,
	KING,
	ACE;

	private final static List<Rank> list = Arrays.asList(values());

	private Integer numericValue;
	
	
	private Rank() {}
	private Rank(int value) {
		this.numericValue = value;
	}

	
	public String toString() {
		return name().toLowerCase();
	}
	public Integer getNumericValue() {
		return numericValue;
	}
	public boolean isFigureRank(){
		return numericValue == null;
	}
	public boolean isNumericRank(){
		return numericValue != null;
	}
	public static List<Rank> list(){
		return list;
	}
}