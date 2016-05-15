package pl.edu.agh.iwium.texasholdem.piqle;

import static pl.edu.agh.iwium.texasholdem.TexasholdemStage.*;

import pl.edu.agh.iwium.game.DealersCards;
import pl.edu.agh.iwium.game.Hand;
import pl.edu.agh.iwium.texasholdem.TexasholdemHand;
import pl.edu.agh.iwium.texasholdem.TexasholdemStage;

public class TexasholdemGameState {

	public TexasholdemStage stage;
	public DealersCards dealersCards;
	public Hand onBoardCard;
	public double onBoardMoney;
	
	
	public void startNewGame(DealersCards dealers){
		stage = PRE_FLOP;		
		dealersCards = dealers;
		onBoardCard = new TexasholdemHand();	
		onBoardMoney = 0.0;
	}
	
	
	public TexasholdemGameState copy(){
		TexasholdemGameState state = new TexasholdemGameState();
		state.stage = stage;
		state.onBoardMoney = onBoardMoney;
		state.dealersCards = dealersCards.copy();
		state.onBoardCard = onBoardCard.copy();
		return state;
	}


	public boolean isSubgameEnded() {
		return stage.equals( SHOWDOWN );
	}

	public void nextStage() {
		stage = stage.getNextStage();
		
		switch(stage){
			case FLOP:
				for (int i=0; i<3; ++i)
					onBoardCard.addCard( dealersCards.dealCard());
				break;
				
			case TURN:
			case RIVER:
				onBoardCard.addCard( dealersCards.dealCard());
				break;
				
			default:
				break;
		}
	}
}
