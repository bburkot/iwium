package pl.edu.agh.iwium.blackjack.piqle;

import org.apache.log4j.Logger;

import environment.ActionList;
import environment.IAction;
import environment.IEnvironmentTwoPlayers;
import environment.IState;
import environment.ITwoPlayerState;
import pl.edu.agh.iwium.blackjack.BlackjackActionEnum;

public class BlackjackEnvironment implements IEnvironmentTwoPlayers {
	private static final long serialVersionUID = 1L;	
	private static final Logger logger = Logger.getLogger(BlackjackEnvironment.class);
	
	@Override
	public ActionList getActionList(IState s) {
		BlackjackState state = (BlackjackState) s;		
		ActionList actionList = new ActionList(state);
		actionList.add( new BlackjackAction(BlackjackActionEnum.STAND) );
		if ( !state.isCurrentPlayerStand() )
			actionList.add( new BlackjackAction(BlackjackActionEnum.HIT) );
		return actionList;
	}

	@Override
	public IState successorState(IState s, IAction a) {
		BlackjackState state = (BlackjackState) s;
		BlackjackAction action = (BlackjackAction) a;
		
		BlackjackState newState = state.copy();
		newState.apply(action);
		newState.toggleTurn();
		logger.debug(state + " -> " + newState);		
		return newState;
	}

	@Override
	public double getReward(IState s1, IState s2, IAction a) {
		BlackjackState state = (BlackjackState) s2;

		// 100 win, 0 tie, - 100 lose
		if ( isFinal(state) ){
			if ( state.isTie() )
				return 50.0;
			if ( state.isPlayerOneTurn() )
				return state.isPlayerOneWinner() ? 100.0 : -50.0;
			return state.isPlayerTwoWinner() ? 100.0 : -50.0;
		}
		
		// diff * 10
		return state.getDifference() * 10.0;
	}

	@Override
	public boolean isFinal(IState state) {
		return state.isFinal();
	}

	@Override
	public int whoWins(IState state) {
		return ((BlackjackState) state).getWinner();
	}

	@Override
	public ITwoPlayerState defaultInitialTwoPlayerState() {
		return new BlackjackState(this);
	}

}
