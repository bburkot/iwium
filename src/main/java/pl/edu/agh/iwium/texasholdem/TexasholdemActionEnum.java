package pl.edu.agh.iwium.texasholdem;

import static pl.edu.agh.iwium.texasholdem.TexasholdemGameProperties.*;

public enum TexasholdemActionEnum {
	
	BLIND (BLIND_VALUE),	//SMALL_BLIND, BIG_BLIND,
	CHECK (CHECK_VALUE),  
//	BET, 
//	CALL, 
//	RAISE, 
	FOLD (0.0);
	
	
	private double cost;
	
	private TexasholdemActionEnum(double value){
		this.cost = value;
	}

	public double getCost() {
		return cost;
	}
}
