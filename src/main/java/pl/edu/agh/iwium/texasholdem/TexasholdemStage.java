package pl.edu.agh.iwium.texasholdem;

import static pl.edu.agh.iwium.texasholdem.TexasholdemActionEnum.*;

import java.util.Arrays;
import java.util.List;

public enum TexasholdemStage {
	
	COMPULSORY_BETS (BLIND), 		// wejscie do gry
	PRE_FLOP 		(CHECK, FOLD),  // 2 karty dla gracza
	FLOP 			(CHECK, FOLD),  // 3  - odkryte karty na stole
	TURN 			(CHECK, FOLD),  // 4  -||-
	RIVER 			(CHECK, FOLD),  // 5  -||-
	SHOWDOWN	
	
	;
	
	private List<TexasholdemActionEnum> actions;

	private TexasholdemStage(TexasholdemActionEnum ... actions){
		this.actions = Arrays.asList(actions);
	}
	
	public List<TexasholdemActionEnum> getActions() {
		return actions;
	}
	public TexasholdemStage getNextStage(){
		return TexasholdemStage.values()[ordinal() + 1];
	}
}
