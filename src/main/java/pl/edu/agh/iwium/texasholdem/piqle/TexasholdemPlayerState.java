package pl.edu.agh.iwium.texasholdem.piqle;

import static pl.edu.agh.iwium.texasholdem.TexasholdemActionEnum.BLIND;
import static pl.edu.agh.iwium.texasholdem.TexasholdemActionEnum.FOLD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.agh.iwium.Utils;
import pl.edu.agh.iwium.game.Card;
import pl.edu.agh.iwium.texasholdem.TexasholdemActionEnum;
import pl.edu.agh.iwium.texasholdem.TexasholdemGameProperties;
import pl.edu.agh.iwium.texasholdem.TexasholdemHand;
import pl.edu.agh.iwium.texasholdem.TexasholdemRankingUtil;

public class TexasholdemPlayerState {
	
	public TexasholdemGameState game;
	
	public TexasholdemActionEnum lastAction;
	public TexasholdemHand hand;
	public double money;
		
	public double points;
	
	
	
	
	public static TexasholdemPlayerState getDefault(TexasholdemGameState game){
		TexasholdemPlayerState state = new TexasholdemPlayerState(game);
		state.money = TexasholdemGameProperties.PLAYER_MONEY;
		return state;
	}
//	public TexasholdemPlayerState(){
//	}
	public TexasholdemPlayerState(TexasholdemGameState game){
		this.game = game;
	}
	
	public void blind(){
		this.lastAction = BLIND;
		this.money -= BLIND.getCost();

		this.hand = new TexasholdemHand();
		this.hand.addCard( this.game.dealersCards.dealCard() );
		this.hand.addCard( this.game.dealersCards.dealCard() );
		
		updateRankingPoints();
		
		game.onBoardMoney += BLIND.getCost();
	}
	
	public void updateRankingPoints() {
		List<Card> cards = new ArrayList<>(game.onBoardCard.getCards());
		cards.addAll( hand.getCards() );
		Collections.sort(cards, Collections.reverseOrder(Utils.createCardComparator(true)));
		points = TexasholdemRankingUtil.countRanking( cards );
	}
	public void apply(TexasholdemAction a) {
		TexasholdemActionEnum act = a.getAction();
		this.lastAction = act;
		this.money -= act.getCost();
		
		game.onBoardMoney += act.getCost();
	}
	
	public TexasholdemPlayerState copy(TexasholdemGameState game2){
		TexasholdemPlayerState state = new TexasholdemPlayerState(game2);
		state.lastAction = lastAction;
		state.hand = hand.copy();
		state.points = points;
		state.money = money;
		return state;
	}
	public boolean isFold() {
		return lastAction.equals(FOLD);
	}
	public void win() {
		money += game.onBoardMoney;
	}
	public void tie() {
		money += game.onBoardMoney / 2.0;
	}
	public void lose(){
	}
	public double getPoints() {
		return points;
	}
	public boolean isLoser(){
		return money <= 0;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + ((lastAction == null) ? 0 : lastAction.hashCode());
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TexasholdemPlayerState other = (TexasholdemPlayerState) obj;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		if (lastAction != other.lastAction)
			return false;
		if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TPState[money=" + money + ", points=" + points + "]";
	}
}
