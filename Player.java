

public class Player
{
   private int playerId;
   private HandOfCards playerHand;
   
   public Player(int playerId) 
   {
      this.playerId = playerId;
      
      playerHand = new HandOfCards();

   }

   public int getPlayerId() {
	   return playerId;
   }

   public HandOfCards getPlayerHand() {
	   return playerHand;
   }
   
   
   

}

