package pl.edu.agh.iwium.blackjack;

import pl.edu.agh.iwium.game.DealersCards;

public class BlackjackGameProperties {
	
	// for BlackjackEnvironment.defaultInitialTwoPlayerState()
	public static int NUMBER_OF_DECKS = 6;
	
	public static boolean RANDOMIZE_DEALERS_CARDS = true;
	public static DealersCards DEALERS_CARDS = null;
	// or
//	public static final boolean RANDOMIZE_DEALERS_CARDS = false;
//	public static DealersCards DEALERS_CARDS = Utils.createDealersCards(NUMBER_OF_DECKS);
}
