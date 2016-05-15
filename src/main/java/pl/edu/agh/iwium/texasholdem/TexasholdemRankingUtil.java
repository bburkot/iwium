package pl.edu.agh.iwium.texasholdem;

import static pl.edu.agh.iwium.game.Rank.ACE;
import static pl.edu.agh.iwium.game.Rank.JACK;
import static pl.edu.agh.iwium.game.Rank.KING;
import static pl.edu.agh.iwium.game.Rank.QUEEN;
import static pl.edu.agh.iwium.game.Rank.TEN;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.FLUSH;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.FOUR_OF_A_KIND;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.FULL_HOUSE;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.HIGH_CARD;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.ONE_PAIR;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.ROYAL_FLUSH;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.STRAIGHT;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.STRAIGHT_FLUSH;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.THREE_OF_A_KIND;
import static pl.edu.agh.iwium.texasholdem.TexasholdemRanking.TWO_PAIR;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.iwium.game.Card;
import pl.edu.agh.iwium.game.Rank;
import pl.edu.agh.iwium.game.Suit;

/*
 * 	01) ROYAL_FLUSH,
 *	02) STRAIGHT_FLUSH,
 *	03) FOUR_OF_A_KIND,
 *	04) FULL_HOUSE,
 *	05) FLUSH,
 *	06) STRAIGHT,
 *	07) THREE_OF_A_KIND,
 *	08) TWO_PAIR,
 *	09) ONE_PAIR,
 *	10) HIGH_CARD
 */
public class TexasholdemRankingUtil {
	private TexasholdemRankingUtil(){
	}

	public static long countRanking(List<Card> sortedCards) {

		if ( isRoyalFlush(sortedCards) ) 			
			return countRanking(sortedCards, ROYAL_FLUSH);
		
		if ( isStraightFlush(sortedCards) != null)
			return countRanking(sortedCards, STRAIGHT_FLUSH);

		if ( isFourOfAKind(sortedCards) != null) 
			return countRanking(sortedCards, FOUR_OF_A_KIND);
			
		if ( isFullHouse(sortedCards) != null) 			
			return countRanking(sortedCards, FULL_HOUSE);

		if ( isFlush(sortedCards) != null)			
			return countRanking(sortedCards, FLUSH);
		
		if ( isStraight(sortedCards) != null)		
			return countRanking(sortedCards, STRAIGHT);
		
		if ( isThreeOfAKind(sortedCards) != null) 			
			return countRanking(sortedCards, THREE_OF_A_KIND);
		
		if ( isTwoPair(sortedCards) != null) 			
			return countRanking(sortedCards, TWO_PAIR);
		
		if ( isOnePair(sortedCards) != null ) 			
			return countRanking(sortedCards, ONE_PAIR);
				
		return countRanking(sortedCards, HIGH_CARD);
	}

	public static boolean isRoyalFlush(List<Card> sortedCards) {
		if (!isSameSuit(sortedCards)) 
			return false;		

		List<Rank> rankEnumList = toRankEnumList(sortedCards);
		if (rankEnumList.contains(TEN) 
				&& rankEnumList.contains(JACK)
				&& rankEnumList.contains(QUEEN) 
				&& rankEnumList.contains(KING)
				&& rankEnumList.contains(ACE)) {
			return true;
		}
		return false;
	}

	public static List<Card> isStraightFlush(List<Card> sortedCards) {
		return getSequence(sortedCards, 5, true);
	}

	public static List<Card> isFourOfAKind(List<Card> sortedCards) {
		return checkPair(sortedCards, 4);
	}

	public static List<Card> isFullHouse(List<Card> sortedCards) {
		List<Card> mergedList = new ArrayList<>(sortedCards);
		List<Card> threeList = checkPair(mergedList, 3);
		if (threeList != null) {
			mergedList.removeAll(threeList);
			List<Card> twoList = checkPair(mergedList, 2);
			if (twoList != null) {
				threeList.addAll(twoList);
				return threeList;
			}
		}
		return null;
	}

	public static List<Card> isFlush(List<Card> sortedCards) {
		List<Card> mergedList = sortedCards;
		List<Card> flushList = new ArrayList<Card>();

		for (Card card1 : mergedList) {
			for (Card card2 : mergedList) {
				if (card1.getSuit().equals(card2.getSuit())) {
					if (!flushList.contains(card1)) 
						flushList.add(card1);					
					if (!flushList.contains(card2)) 
						flushList.add(card2);					
				}
			}
			if (flushList.size() == 5) 
				return flushList;
			flushList.clear();
		}
		return null;
	}

	//S‹o 5 cartas seguidas de naipes diferentes, caso empate ganha aquele com a maior sequ�ncia.
	public static List<Card> isStraight(List<Card> sortedCards) {
		return getSequence(sortedCards, 5, false);
	}

	public static List<Card> isThreeOfAKind(List<Card> sortedCards) {
		return checkPair(sortedCards, 3);
	}

	public static List<Card> isTwoPair(List<Card> sortedCards) {
		List<Card> mergedList = new ArrayList<>(sortedCards);
		List<Card> twoPair1 = checkPair(mergedList, 2);
		if (twoPair1 != null) {
			mergedList.removeAll(twoPair1);
			List<Card> twoPair2 = checkPair(mergedList, 2);
			if (twoPair2 != null) {
				twoPair1.addAll(twoPair2);
				return twoPair1;
			}
		}
		return null;
	}

	public static List<Card> isOnePair(List<Card> sortedCards) {
		return checkPair(sortedCards, 2);
	}

	public static Card getHighCard(List<Card> sortedCards) {
		Card highCard = sortedCards.get(0);
		for (Card card : sortedCards) 
			if (card.getRankOrdinal() > highCard.getRankOrdinal())
				highCard = card;
		return highCard;
	}

	private static List<Card> getSequence(List<Card> sortedCards, 
			Integer sequenceSize, Boolean compareSuit) {
		List<Card> orderedList = sortedCards;
		List<Card> sequenceList = new ArrayList<Card>();

		Card cardPrevious = null;
		for (Card card : orderedList) {
			if (cardPrevious != null) {
				if ((card.getRankOrdinal() - cardPrevious.getRankOrdinal()) == 1) {
					if (!compareSuit || cardPrevious.getSuit().equals(card.getSuit())) {
						if (sequenceList.size() == 0) 
							sequenceList.add(cardPrevious);
						sequenceList.add(card);
					}
				} else {
					if (sequenceList.size() == sequenceSize) 
						return sequenceList;
					sequenceList.clear();
				}
			}
			cardPrevious = card;
		}
		return (sequenceList.size() == sequenceSize) ? sequenceList : null;
	}

	private static List<Card> checkPair(List<Card> mergedList, Integer pairSize) {
		List<Card> checkedPair = new ArrayList<Card>();
		for (Card card1 : mergedList) {
			checkedPair.add(card1);
			for (Card card2 : mergedList) {
				if (!card1.equals(card2)
						&& card1.getRank().equals(card2.getRank())) {
					checkedPair.add(card2);
				}
			}
			if (checkedPair.size() == pairSize) {
				return checkedPair;
			}
			checkedPair.clear();
		}
		return null;
	}

	private static Boolean isSameSuit(List<Card> sortedCards) {
		Suit suit = sortedCards.get(0).getSuit();
		for (Card card : sortedCards) 
			if (!card.getSuit().equals(suit)) 
				return false;
		return true;
	}

	private static List<Rank> toRankEnumList(List<Card> sortedCards) {
		List<Rank> rankEnumList = new ArrayList<Rank>();
		for (Card card : sortedCards) 
			rankEnumList.add(card.getRank());
		return rankEnumList;
	}

	private static long countRanking(List<Card> sortedCards, TexasholdemRanking rankingEnum) {
		long value = SHIFT(4, rankingEnum.ordinal() + 1);
		
		value += SHIFT(2, sortedCards.get(0).getRankOrdinal() + 1);
		value += SHIFT(0, sortedCards.get(1).getRankOrdinal() + 1);
		
		return value;
	}
	
	private static long SHIFT(int num, int base){
		long value = base;
		for (int i=0; i<num; ++i)
			value *= 10;
		return value;
	}
}
