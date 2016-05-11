package pl.edu.agh.iwium.game;

import java.util.Arrays;
import java.util.List;

/**
 * Specification of the suit values for a standard deck of cards.
 */
public enum Suit {
	CLUBS    ("Trefl"),
	DIAMONDS ("Karo"),
	HEARTS   ("Kier"),
	SPADES   ("Pik");
	

	private final static List<Suit> list = Arrays.asList(values());

	private String namePL;
	private String symbol;

	
	private Suit() {
		this.symbol = name().substring(0, 1).toLowerCase();
	}

		
	public String getNamePL() {
		return namePL;
	}
	private Suit(String namePL) {
		this.namePL = namePL;
	}
	public String getSymbol() {
		return symbol;
	}
	public String toString() {
		return namePL;
	}
	public static List<Suit> list(){
		return list;
	}
}
