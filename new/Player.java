public class Player
{
   private int playerId;
   private boolean playerMachine;
   private HandOfCards playerHand;
   
   public Player(int playerId) 
   {
      this.playerId = playerId;
      
      playerHand = new HandOfCards();

   }
   
   public Player(int playerId, boolean playerMachine) 
   {
	   super();
	   this.playerId = playerId;
	   this.playerMachine = playerMachine;
   }

   public int getPlayerId() {
	   return playerId;
   }

   public HandOfCards getPlayerHand() {
	   return playerHand;
   }

   public boolean isPlayerMachine() {
	   return playerMachine;
   }

   public void setPlayerMachine(boolean playerMachine) {
	   this.playerMachine = playerMachine;
   }
     
}
