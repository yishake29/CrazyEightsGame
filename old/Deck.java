


import java.util.ArrayList;
import java.util.List;

public class Deck 
{
   private List<Card> cards;
   private Card topCard;

   public Deck()
   {
      cards = new ArrayList<>();

      Suit[] suitArr=Suit.values();
      Rank[] rankArr=Rank.values();

      for(int r=0; r < rankArr.length;r++)
      {

         for(int s=0;s< suitArr.length;s++)
         {

            Card c= new Card(suitArr[s],rankArr[r]);

            cards.add(c);
       
         }

      }
      
      topCard = getTopCard(); 

   }

   public Card getTopCard()
   {
      return cards.get(cards.size()-1);
   }

   public int deckSize()
   {
      return cards.size();
   }

   public List<Card> getDeckCards()
   {
      return cards;
   }

   public void shuffleDeck()
   {
  
      for(int i=0;i<cards.size();i++)
      {

         int r = (int)(Math.random()*cards.size());

         Card randCard=cards.get(r);

         cards.set(r,cards.get(i));
         cards.set(i,randCard);
         
      }

      topCard = getTopCard(); 
      
   }

   public void printDeck()
   {

      System.out.println("Display the cards in the deck");
      
      for(Card c : cards)
      {
         c.print();    	  
      }
      
   }

   public void removeTopCard()
   {

	   cards.remove(cards.size()-1);
	   topCard = getTopCard();

   }

   public int getTopCardIndex()
   {
      return cards.size()-1;
   }
   
}

