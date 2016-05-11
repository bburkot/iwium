package pl.edu.agh.iwium.blackjack.piqle;

import environment.IAction;
import pl.edu.agh.iwium.blackjack.BlackjackActionEnum;

public class BlackjackAction implements IAction {
	private static final long serialVersionUID = 1L;
	
	private BlackjackActionEnum action;
	
	
	public BlackjackAction(BlackjackActionEnum action) {
		this.action = action;
	}


	@Override
	public IAction copy() {
		return new BlackjackAction(action);
	}

	@Override
	public int nnCodingSize() {
		return BlackjackActionEnum.values().length;
	}
	@Override
	public double[] nnCoding() {
		double code[] = new double[ nnCodingSize() ];
		code[ action.ordinal() ] = 1.0;
		return code;
	}
	
	public BlackjackActionEnum getAction() {
		return action;
	}

	@Override
	public int hashCode() {
		return action.ordinal();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlackjackAction other = (BlackjackAction) obj;
		return action == other.action;
	}
	
	@Override
	public String toString() {
		return "BlackjackAction [action=" + action + "]";
	}
}
