package pl.edu.agh.iwium.texasholdem;

import java.util.ArrayList;

import pl.edu.agh.iwium.game.Hand;

public class TexasholdemHand extends Hand {

	
	@Override
	@SuppressWarnings("unchecked")
	public TexasholdemHand copy() {
		TexasholdemHand th = new TexasholdemHand();
		th.cards = new ArrayList<>(cards);
		return th;
	}

	@Override
	public int evaluateHand() {
		throw new UnsupportedOperationException();
	}
	
	public int evaluateHand(Hand onBoardCards) {
		
		
		return 0;
	}
	
}
