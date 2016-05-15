package pl.edu.agh.iwium.texasholdem.piqle;

import environment.IAction;
import pl.edu.agh.iwium.texasholdem.TexasholdemActionEnum;

public class TexasholdemAction implements IAction {
private static final long serialVersionUID = 1L;
	
	private TexasholdemActionEnum action;
	
	public TexasholdemAction(TexasholdemActionEnum action) {
		this.action = action;
	}
	
	
	public TexasholdemAction copy() {
		return new TexasholdemAction(action);
	}
	public int nnCodingSize() {
		return TexasholdemActionEnum.values().length;
	}
	public double[] nnCoding() {
		double code[] = new double[ nnCodingSize() ];
		code[ action.ordinal() ] = 1.0;
		return code;
	}
	public TexasholdemActionEnum getAction() {
		return action;
	}
	
	
	public int hashCode() {
		return action.ordinal();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TexasholdemAction other = (TexasholdemAction) obj;
		return action == other.action;
	}
	
	public String toString() {
		return "TexasholdemAction [action=" + action + "]";
	}
}
