package pl.edu.agh.iwium;

import java.util.Arrays;

import agents.TwoPlayerAgent;
import algorithms.ISelector;
import algorithms.QLearningSelector;
import algorithms.RandomSelector;
import pl.edu.agh.iwium.blackjack.BlackjackHand;
import pl.edu.agh.iwium.blackjack.bot.BlackjackDealerStrategy;
import pl.edu.agh.iwium.blackjack.bot.BlackjackNeverBustStrategy;
import pl.edu.agh.iwium.blackjack.piqle.BlackjackEnvironment;
import pl.edu.agh.iwium.game.Card;
import pl.edu.agh.iwium.game.DealersCards;
import pl.edu.agh.iwium.game.Rank;
import pl.edu.agh.iwium.game.Suit;
import referees.TwoPlayerReferee;

/**
 * Hello world!
 *
 */
@SuppressWarnings("unused")
public class App {

	public static void main(String[] args) {
		BlackjackEnvironment env = new BlackjackEnvironment();

		QLearningSelector qlearning1 = new QLearningSelector();
		QLearningSelector qlearning2 = new QLearningSelector();

		double epsilon = 0.3;
		qlearning1.setEpsilon(epsilon);
		qlearning1.setGeometricAlphaDecay();
		qlearning2.setEpsilon(epsilon);
		qlearning2.setGeometricAlphaDecay();

		TwoPlayerAgent p1 = new TwoPlayerAgent(env, qlearning1);
//		TwoPlayerAgent p2 = new TwoPlayerAgent(env, qlearning2);
		// TwoPlayerAgent p2 = new TwoPlayerAgent(env, new RandomSelector());
//		TwoPlayerAgent p2 = new TwoPlayerAgent(env, new BlackjackDealerStrategy());
		TwoPlayerAgent p2 = new TwoPlayerAgent(env, new BlackjackNeverBustStrategy());

		TwoPlayerReferee arbitre = new TwoPlayerReferee(p1, p2);
		// arbitre.setVerbosity();
		int resu[] = new int[3];

//		for (int i = 1; i < 10000; i++) {
//			epsilon *= 0.99999;
//			qlearning1.setEpsilon(epsilon);
//			qlearning2.setEpsilon(epsilon);
//
//			resu[arbitre.episode() + 1]++;
//			if (i % 100 == 0) {
//				System.out.println(i + " " + resu[0] + " " + resu[1] + " " + resu[2]);
//				resu = new int[3];
//			}
//		}
		for (int i = 1; i < 10; i++) {
			epsilon *= 0.99999;
			qlearning1.setEpsilon(epsilon);
			qlearning2.setEpsilon(epsilon);
			
			resu[arbitre.episode() + 1]++;
			System.out.println(resu[0] + " " + resu[1] + " " + resu[2]);
			resu = new int[3];
		}
	}

	private static void temp() {
		// Card card = new Card(Suit.CLUBS, Rank.TEN);
		// Card card2 = new Card(Suit.CLUBS, Rank.TEN);
		// Card card3 = new Card(Suit.CLUBS, Rank.TEN);

		BlackjackHand hand = new BlackjackHand();
		ISelector bot1 = new BlackjackNeverBustStrategy();
		ISelector bot2 = new BlackjackDealerStrategy();

		DealersCards deck = Utils.createDealersCards(1);
		deck.shuffle();

		hand.addCard(deck.dealCard());
		hand.addCard(deck.dealCard());
		hand.addCard(deck.dealCard());

		BlackjackHand hand2 = (BlackjackHand) hand.copy();
		System.out.println(hand);
		System.out.println(hand2);
		System.out.println(hand == hand2);
		System.out.println(hand.equals(hand));
		System.out.println(hand.equals(hand2));
		System.out.println(hand2.equals(hand));

		System.out.println();
		hand.addCard(deck.dealCard());
		System.out.println(hand.equals(hand));
		System.out.println(hand.equals(hand2));
		System.out.println(hand2.equals(hand));
	}
}