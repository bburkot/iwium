package pl.edu.agh.iwium.blackjack.piqle;

import pl.edu.agh.iwium.Utils;
import pl.edu.agh.iwium.game.DealersCards;

@SuppressWarnings("unused")
public class BlackjackGameProperties {
	
	// for BlackjackEnvironment.defaultInitialTwoPlayerState()
	public static final int NUMBER_OF_DECKS = 6;
	public static final boolean RANDOMIZE_DEALERS_CARDS = true;
	public static DealersCards DEALERS_CARDS = null;
	// or
//	public static final boolean RANDOMIZE_DEALERS_CARDS = false;
//	public static DealersCards DEALERS_CARDS = Utils.createDealersCards(NUMBER_OF_DECKS);
}
