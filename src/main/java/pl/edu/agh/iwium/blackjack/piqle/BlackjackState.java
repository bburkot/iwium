package pl.edu.agh.iwium.blackjack.piqle;

import org.apache.log4j.Logger;

import environment.AbstractTwoPlayerState;
import environment.IEnvironment;
import pl.edu.agh.iwium.blackjack.BlackjackActionEnum;
import pl.edu.agh.iwium.blackjack.BlackjackHand;
import pl.edu.agh.iwium.game.DealersCards;

public class BlackjackState extends AbstractTwoPlayerState {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BlackjackState.class);

	private DealersCards dealersCards;
	private BlackjackHand player1Hand, player2Hand;
	
	public BlackjackState(IEnvironment ct) {
		super(ct);
	}

	public void init(DealersCards dealersCards) {
		this.dealersCards = dealersCards;
		this.player1Hand = new BlackjackHand();
		this.player2Hand = new BlackjackHand();
		this.player1Hand.addCard(dealersCards.dealCard());
		this.player2Hand.addCard(dealersCards.dealCard());
		this.player1Hand.addCard(dealersCards.dealCard());
		this.player2Hand.addCard(dealersCards.dealCard());
	}
	
	//TODO
	public void apply(BlackjackAction action) {
		if ( action.getAction().equals(BlackjackActionEnum.STAND) )
			getCurrentPlayerHand().stand();
		else 
			getCurrentPlayerHand().addCard( dealersCards.dealCard() );
	}

	@Override
	public BlackjackState copy() {
		BlackjackState bs = new BlackjackState( myEnvironment );
		bs.dealersCards = dealersCards.copy();
		bs.player1Hand = player1Hand.copy();
		bs.player2Hand = player2Hand.copy();
		bs.turn = turn;
		return bs;
	}

	
	@Override
	public int nnCodingSize() {
		return 46; // 21
	}
	@Override
	public double[] nnCoding() {
		double code[] = new double[46]; // 2 rows x 23 cols
		code[player1Hand.evaluateHand() + 1] = 1;
		code[23 + player2Hand.evaluateHand() + 1] = 1;
		return code;
	}
	
	@Override
	public int getWinner() {
		int diff = player2Hand.evaluateHand() - player1Hand.evaluateHand();
		return diff == 0 ? 0 : (diff > 0 ? 1 : -1);
	}
	
	@Override
	public boolean isFinal() {
		return (player1Hand.isStand() && player2Hand.isStand()) 
				|| player1Hand.isLimitExceeded() 
				|| player2Hand.isLimitExceeded();
	}
	public void logFinalReason(){
		String msg = String.format("isFormat=%b, P1 stand=%b, "
				+ "P2 stand=%b, P1 exc=%b, P2 exc=%b", isFinal(), 
				player1Hand.isStand(), player2Hand.isStand(), 
				player1Hand.isLimitExceeded(), player2Hand.isLimitExceeded());
		logger.debug(msg);
	}
	
	public boolean isCurrentPlayerStand() {
		return getCurrentPlayerHand().isStand();
	}
	/**@return difference between current player cards and waiting player cards */
	public double getDifference(){
		return getCurrentPlayerHand().evaluateHand() 
				- getWaitingPlayerHand().evaluateHand();
	}	
	private BlackjackHand getCurrentPlayerHand(){
		return isPlayerOneTurn() ? player1Hand : player2Hand;
	}
	private BlackjackHand getWaitingPlayerHand(){
		return isPlayerOneTurn() ? player2Hand : player1Hand;
	}
	public int getCurrentPlayerPoints(){
		return getCurrentPlayerHand().evaluateHand();
	}


	@Override
	public String toString() {
		return "BlackjackState [player1Hand=" + player1Hand.toSimpleString() 
			+ ", player2Hand=" + player2Hand.toSimpleString() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dealersCards == null) ? 0 : dealersCards.hashCode());
		result = prime * result + ((player1Hand == null) ? 0 : player1Hand.hashCode());
		result = prime * result + ((player2Hand == null) ? 0 : player2Hand.hashCode());
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
		BlackjackState other = (BlackjackState) obj;
		if (dealersCards == null) {
			if (other.dealersCards != null)
				return false;
		} else if (!dealersCards.equals(other.dealersCards))
			return false;
		if (player1Hand == null) {
			if (other.player1Hand != null)
				return false;
		} else if (!player1Hand.equals(other.player1Hand))
			return false;
		if (player2Hand == null) {
			if (other.player2Hand != null)
				return false;
		} else if (!player2Hand.equals(other.player2Hand))
			return false;
		return true;
	}
}
