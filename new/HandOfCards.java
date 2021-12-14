import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOfCards
{
	
   private Map<Integer,Card> onPlayersHands; 
   
   public HandOfCards()
   {
      onPlayersHands = new HashMap<>();
   }

   public void addCard(Card c)
   {
	   onPlayersHands.put(onPlayersHands.size()+1, c);
	   //onPlayersHands.put(onPlayersHands.size(), c);
   }
  
   public Card removeCard(int index)
   {
	   Card removedCard = onPlayersHands.remove(index);
	   
	   if (removedCard != null)
		   reset();
	   
	   return removedCard;
   }
   
   private void reset()
   {
	   List<Card> cards = getCardsOnPlayersHandsAsList();
	   
	   onPlayersHands.clear();
	   
	   for(int i=0; i < cards.size(); i++)
	   {
		   onPlayersHands.put(i+1,cards.get(i));
	   } 
   }

   public void printCards()
   {
      for(Integer i : onPlayersHands.keySet())
      {
    	  System.out.println(i + ":" + onPlayersHands.get(i));
      }

   }

   public int numCardsInHand()  { 
 	  return onPlayersHands.size(); 
   }

   public Map<Integer,Card> getCardsOnPlayersHands() {
	   return onPlayersHands;
   }
   
   public List<Card> getCardsOnPlayersHandsAsList() {
	   
	   List<Card> cardList = new ArrayList<>();
	   
	   for(Integer i : onPlayersHands.keySet())
	   {
		   cardList.add(onPlayersHands.get(i));
	   }
	   
	   return cardList;
	   
   }
   
   
   public Card playCard(int whichCard,Card starterCard) 
   {	 
	   
	   Card cardToReturn = null;

	   if(whichCard >= 0 && whichCard < 52)
	   {	   
		   cardToReturn = onPlayersHands.get(whichCard);
		   
		 
		   //OptionPaneHelper.showMessageDialog("cardToReturn:" + cardToReturn);
		   //OptionPaneHelper.showMessageDialog("starterCard:"+starterCard);

		   if(cardToReturn != null && (cardToReturn.equals(starterCard,false) || cardToReturn.getRank() == Rank.R8) )
		   {
			   //onPlayersHands.remove(whichCard);
			   removeCard(whichCard);
			   starterCard = cardToReturn;
		   } 
		   else {
			   cardToReturn = null;
		   }   
	   }

	   return cardToReturn;
	   	 
   }
   
   
 
}
