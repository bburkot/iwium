package pl.edu.agh.iwium.texasholdem.piqle;

import org.apache.log4j.Logger;

import environment.ActionList;
import environment.IAction;
import environment.IEnvironmentTwoPlayers;
import environment.IState;
import environment.ITwoPlayerState;
import pl.edu.agh.iwium.texasholdem.TexasholdemActionEnum;

public class TexasholdemEnvironment implements IEnvironmentTwoPlayers {
	private static final long serialVersionUID = 1L;	
	private static final Logger logger = Logger.getLogger(TexasholdemEnvironment.class);
	
	
	@Override
	public ActionList getActionList(IState s) {
		TexasholdemState state = (TexasholdemState) s;
		ActionList list = new ActionList(state);
		for (TexasholdemActionEnum act : state.getGame().stage.getActions())
			list.add(new TexasholdemAction(act));
		return list;
	}
	
	@Override
	public IState successorState(IState s, IAction a) {
		TexasholdemState state = (TexasholdemState) s;
		TexasholdemAction act = (TexasholdemAction) a;
				
		logger.debug(String.format("Idx=%d, P0=%s, P1=%s, act=%s", 
			state.getCurrentPlayerIdx(), state.getPlayer(0), state.getPlayer(1), act));
	
		TexasholdemState newState = state.copy();
		newState.getCurrentPlayer().apply(act);
		
		if ( newState.isStageEnded() ){
			logger.debug("Stage Is Ended -> " + newState);
			newState.nextStage();
		}
		newState.toggleTurn();
		
		if ( newState.isSubgameEnded() ){
			logger.debug("Subgame Is Ended -> " + newState + "\n");
			newState.sumUpSubgame();
			if ( !newState.isFinal() )
				newState.startNewGame();
		} 
			
		return newState;
	}
	
	@Override
	public double getReward(IState s1, IState s2, IAction a) {
		TexasholdemState state = (TexasholdemState) s1;
		TexasholdemState newState = (TexasholdemState) s2;
		
		if ( newState.isFinal() ){
			if ( newState.isPlayerOneTurn() )
				return newState.isPlayerOneWinner() ? 100.0 : -50.0;
			return newState.isPlayerTwoWinner() ? 100.0 : -50.0;
		}
		
		TexasholdemPlayerState p1 = newState.getCurrentPlayer(); // new
		TexasholdemPlayerState p2 = state.getCurrentPlayer();    // old
		
		// money: max 400, min -400  -- scaled to -- max 20, min -20   
		// points: max ~11e4, min 1e4 -- scaled to -- max ~1, min ~0
		return (p1.money - p2.money) / 20.0 + (p1.points - p2.points) / 100000;
	}
	
	@Override
	public boolean isFinal(IState s) {		
		TexasholdemState state = (TexasholdemState) s;
		return state.getPlayer(0).money < 0 || state.getPlayer(1).money < 0;
	}
	
	@Override
	public int whoWins(IState s) {
		TexasholdemState state = (TexasholdemState) s;
		if (state.getPlayer(0).money < 0)
			return 1;
		if (state.getPlayer(0).money < 0)
			return 1;
		return 0;
	}
	
	@Override
	public ITwoPlayerState defaultInitialTwoPlayerState() {
		return new TexasholdemState(this);
	}
}
