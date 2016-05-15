package pl.edu.agh.iwium.texasholdem.piqle;

import static pl.edu.agh.iwium.texasholdem.TexasholdemGameProperties.DEALERS_CARDS;
import static pl.edu.agh.iwium.texasholdem.TexasholdemGameProperties.NUMER_OF_PLAYERS;
import static pl.edu.agh.iwium.texasholdem.TexasholdemGameProperties.RANDOMIZE_DEALERS_CARDS;

import java.util.Arrays;

import org.apache.log4j.Logger;

import environment.AbstractTwoPlayerState;
import environment.IEnvironment;
import pl.edu.agh.iwium.Utils;
import pl.edu.agh.iwium.game.DealersCards;


public class TexasholdemState extends AbstractTwoPlayerState {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TexasholdemState.class);
	
	private TexasholdemGameState game;		
	private TexasholdemPlayerState[] players;
	
	
	public TexasholdemState(IEnvironment ct) {
		super(ct);	
		
		game = new TexasholdemGameState();
		
		players = new TexasholdemPlayerState[NUMER_OF_PLAYERS];
		for (int i=0; i<NUMER_OF_PLAYERS; ++i)
			players[i] = TexasholdemPlayerState.getDefault(game);
		
		startNewGame();
	}
	
	public void startNewGame(){
		game.startNewGame( newDeck() );
		for (int pidx=0; pidx<NUMER_OF_PLAYERS; ++pidx)
			players[pidx].blind();		
	}	
	
	
	@Override
	public TexasholdemState copy() {
		TexasholdemState state = new TexasholdemState(myEnvironment);
		state.turn = turn;
		state.game = game.copy();		
		state.players = new TexasholdemPlayerState[NUMER_OF_PLAYERS];
		for (int i=0; i<NUMER_OF_PLAYERS; ++i)
			state.players[i] = players[i].copy(state.game);
		return state;
	}
	

	public int nnCodingSize() {
		return NUMER_OF_PLAYERS;
	}
	public double[] nnCoding() {	
		double[] values = new double[NUMER_OF_PLAYERS];
		for (int idx=0; idx<NUMER_OF_PLAYERS; ++idx)
			values[idx] = players[idx].money;
		return values;
	}	
	
	private DealersCards newDeck(){
		return RANDOMIZE_DEALERS_CARDS ? Utils.createDealersCards(1) : DEALERS_CARDS;
	}

	public void sumUpSubgame() {
//		if ( !game.isSubgameEnded() )
//			return;
		
		if ( players[0].isFold() || players[1].getPoints() > players[0].getPoints()){
			logger.debug("win Player 1");
			players[0].lose();
			players[1].win();
		} else if ( players[1].isFold() || players[0].getPoints() > players[1].getPoints()){
			logger.debug("win Player 0");
			players[0].win();
			players[1].lose();
		} else {
			logger.debug("TIE");
			players[0].tie();
			players[1].tie();
		}
		
		
		
	}
	
	
	public TexasholdemPlayerState getPlayer(int idx) {
		return players[idx];
	}
	public TexasholdemPlayerState getCurrentPlayer() {
		return players[getCurrentPlayerIdx()];
	}
	public TexasholdemPlayerState getWaitingPlayer() {
		return players[getWaitingPlayerIdx()];
	}
	public TexasholdemPlayerState[] getPlayers() {
		return players;
	}
	public TexasholdemGameState getGame() {
		return game;
	}
	public boolean isSubgameEnded() {
		if (game.isSubgameEnded())
			return true;
		return players[0].isFold() || players[1].isFold();
	}
	public boolean isStageEnded() {
		return isPlayerTwoTurn();
	}

	public void nextStage() {
		game.nextStage();
		for (int i=0; i<NUMER_OF_PLAYERS; ++i)
			players[i].updateRankingPoints();
	}

	@Override
	public String toString() {
		return "TexasholdemState [players=" + Arrays.toString(players) + "]";
	}
}
