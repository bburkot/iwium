package pl.edu.agh.iwium.blackjack.bot;

import static pl.edu.agh.iwium.blackjack.BlackjackActionEnum.HIT;
import static pl.edu.agh.iwium.blackjack.BlackjackActionEnum.STAND;

import org.apache.log4j.Logger;

import environment.ActionList;
import environment.IAction;
import pl.edu.agh.iwium.AbstractStatelessSelector;
import pl.edu.agh.iwium.blackjack.BlackjackActionEnum;
import pl.edu.agh.iwium.blackjack.piqle.BlackjackAction;
import pl.edu.agh.iwium.blackjack.piqle.BlackjackState;

public class BlackjackLimitToStrategy extends AbstractStatelessSelector {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BlackjackLimitToStrategy.class);

	private final int limit;
	
	
	public BlackjackLimitToStrategy(int limit){
		this.limit = limit;
	}
	
	
	@Override
	public IAction getChoice(ActionList actionList) {
		if (actionList.size() <= 1)
			return actionList.get(0);
				
		BlackjackState state = (BlackjackState) actionList.getState();
		BlackjackActionEnum act = state.getCurrentPlayerPoints() < limit ? HIT : STAND;		
		IAction action = actionList.get( new BlackjackAction(act) );
		logger.debug("points=" + state.getCurrentPlayerPoints() + ", next action=" + action);
		return action;
	}

	

}
