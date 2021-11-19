

import java.util.ArrayList;
import java.util.List;

class HandOfCards
{

   private List<Card> onPlayersHands;
   
   public HandOfCards()
   {
      onPlayersHands = new ArrayList<>();
   }

   public void addCard(Card c)
   {
	   onPlayersHands.add(c); 
   }
   
   public boolean removeCard(Card c)
   {
	   return onPlayersHands.remove(c);   
   }

   public void printCards()
   {

      for(Card c : onPlayersHands)
      {
    	  System.out.println(c.toString());
      }

   }

   public int numCardsInHand()  { 
 	  return onPlayersHands.size(); 
   }

   public List<Card> getCardsOnPlayersHands() {
	   return onPlayersHands;
   }
   
   
 
}