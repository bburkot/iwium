package pl.edu.agh.iwium.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pl.edu.agh.iwium.Utils;



/**
 * Represents the basic functionality of a hand of cards.
 * Extensions of this class will provide the
 * definition of what constitutes a hand for that game and how hands are compared
 * to one another by overriding the <code>compareTo</code> method.
 * @author John K. Estell
 * @version 1.0
 */
public abstract class Hand implements Comparable<Hand> {
    
   protected List<Card> cards = new ArrayList<Card>(); 

   
	public abstract <T extends Hand> T copy();

   public void addCard( Card card ) {
      cards.add( card );
   }


   /**
   * Obtains the card stored at the specified location in the hand.  Does not
   * remove the card from the hand.
   * @param index position of card to be accessed.
   * @return the card of interest, or the null reference if the index is out of
   * bounds.
   */
   public Card getCard( int index ) {
      return cards.get( index );
   }
  
   public Collection<Card> getCards(){
	   return Collections.unmodifiableList(cards);
   }
  
   /**
   * Removes the specified card from the current hand.
   * @param card the card to be removed.
   * @return the card removed from the hand, or null if the card
   * was not present in the hand.
   */
   public Card removeCard( Card card ) {
      int index = cards.indexOf( card );
      if ( index < 0 )
         return null;
      else
         return cards.remove( index );     
   }


   /**
   * Removes the card at the specified index from the hand.
   * @param index poisition of the card to be removed.
   * @return the card removed from the hand, or the null reference if
   * the index is out of bounds.
   */
   public Card removeCard( int index ) {
      return cards.remove( index );
   }


   /**
   * Removes all the cards from the hand, leaving an empty hand.
   */
   public void discardHand() {
      cards.clear();
   }


   /**
   * The number of cards held in the hand.
   * @return number of cards currently held in the hand.
   */
   public int getNumberOfCards() {
      return cards.size();
   }


   /**
   * Sorts the card in the hand.
   * Sort is performed according to the order specified in the {@link Card} class.
   */
   public void sort() {
      Collections.sort( cards, Utils.createCardComparator(true) );
   }


   /**
   * Checks to see if the hand is empty.
   * @return <code>true</code> is the hand is empty.
   */
   public boolean isEmpty() {
      return cards.isEmpty();
   }


   /**
   * Determines whether or not the hand contains the specified card.
   * @param card the card being searched for in the hand.
   * @return <code>true</code> if the card is present in the hand.
   */
   public boolean containsCard( Card card ) {
      return cards.contains(card);
   }


   /**
   * Searches for the first instance of the specified card in the hand.
   * @param card card being searched for.
   * @return position index of card if found, or <code>-1</code> if not found.
   */
   public int findCard( Card card ) {
      return cards.indexOf( card );
   }
  
  
   /**
   *  Compares two hands.  
   *  @param otherHandObject the hand being compared.
   *  @return < 0 if this hand is less than the other hand, 0 if the two hands are
   *  the same, or > 0 if this hand is greater then the other hand.
   */
   public int compareTo( Hand otherHand ) {
      return evaluateHand() - otherHand.evaluateHand();
   }
   
   
   /**
   *  Evaluates the hand.  Must be defined in the subclass that implements the hand
   *  for the game being written by the client programmer.
   *  @return an integer corresponding to the rating of the hand.
   */
   public abstract int evaluateHand();
   
   
    /**
    * Returns a description of the hand.
    * @return a list of cards held in the hand.
    */
    public String toString() {
        return getClass().getSimpleName() + " " + cards.toString();
    }
    
    public String toSimpleString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(getClass().getSimpleName() + " [");
    	boolean first = true;
    	
    	for (Card card : cards){
    		if (first)
    			first = false;
    		else
    			sb.append(", ");
    		
    		if (card.getRank().isNumericRank())
    			sb.append(card.getRank().getNumericValue());
    		else
    			sb.append(card.getRank().toString());   		
    	}
    	sb.append("]");
    	return sb.toString();
    }
    
    
    /**
    * Replaces the specified card with another card.  Only the first
    * instance of the targeted card is replaced.  No action occurs if
    * the targeted card is not present in the hand.
    * @return <code>true</code> if the replacement occurs.
    */
    public boolean replaceCard( Card oldCard, Card replacementCard ) {
        int location = findCard( oldCard );
        if ( location < 0 )
           return false;
        cards.set( location, replacementCard );
        return true;
    }



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
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
		Hand other = (Hand) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		return true;
	}
}