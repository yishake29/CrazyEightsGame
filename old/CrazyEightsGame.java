

public class CrazyEightsGame
{
   public static void main(String[] args)
   {
	   
      int numPlayers = 2;
      
      CrazyEights ce = new CrazyEights(numPlayers);
     
      ce.showMenu();

      ce.getPlayers().get(1).setPlayerMachine(true);
      
      String userSelection= ce.getInput();

          
      if(userSelection.equals("1") )
      {
    	 ce.getDeck().shuffleDeck();
         ce.initCardsOnPlayersHand();
         ce.setStarterCard();

         System.out.println("*************** Game is started ***************");

         do 
         {
        	System.out.println("Deck Size ::" + ce.getDeck().deckSize()); 
       
            ce.nextMove(ce.getWhoseTurnIsIt());
            
            //switch to other player
            ce.advanceTurn();

         }

         while(!ce.gameOver());

         System.out.println("*************** Game Over !!! ***************");
         
         Player gameWonBy = ce.whoWonTheGame();
         
         if (gameWonBy != null) {
        	if (!gameWonBy.isPlayerMachine()) 
        	    System.out.println("Player" + (gameWonBy.getPlayerId()+1) + " won the game !!");
        	else
        		System.out.println("Computer won the game !!");
         }
         else {
        	 System.out.println("There is no winner !!");
         }
         
      }

      else if (userSelection.equals("2")){

         System.out.println("Bye");

      }

      else {

         System.out.println("Incorrect input");

      }
      
      
   }
   
   
}

