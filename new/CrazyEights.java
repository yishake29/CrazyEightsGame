import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CrazyEights
{ 

    final int NUM_PLAYERS;

    private Deck deck;
    private List<Player> players;

    private int whoseTurnIsIt;
    private Card starterCard;   

    public CrazyEights(int numPlayers)
    {
        NUM_PLAYERS = numPlayers;

        deck= new Deck();

        initPlayer();

        starterCard=deck.getTopCard();

        whoseTurnIsIt=1;

    }

    public CrazyEights(int numPlayers,boolean shuffleDeck,boolean playWithComputer)
    {
        this(numPlayers);

        players.get(1).setPlayerMachine(playWithComputer);	   

        if (shuffleDeck)
            deck.shuffleDeck();
    }

    public CrazyEights(int numPlayers,boolean shuffleDeck)
    {
        this(numPlayers);

        if (shuffleDeck)
            deck.shuffleDeck();
    }

    public void setStarterCard() 
    {
        starterCard=deck.getTopCard();

        deck.removeTopCard();
    }



    public void setStarterCard(Card starterCard) 
    {
        this.starterCard = starterCard;
    }

    private void initPlayer()
    {
        players=new ArrayList<Player>();

        for (int playerId = 0; playerId < NUM_PLAYERS; playerId++) {

            Player p = new Player(playerId);

            players.add(p);

        }

    }

    public void initCardsOnPlayersHand()
    {

        for (Player p : players) 
        {
            for(int i=0; i < 5; i++)
            {

                //System.out.println("Adding " + deck.getTopCard());
                p.getPlayerHand().addCard(deck.getTopCard());
                deck.removeTopCard();

            }

        }

    }

    public int getWhoseTurnIsIt() {
        return whoseTurnIsIt;
    }

    public Card getStarterCard() {
        return starterCard;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void showMenu() 
    {
        System.out.println("Welcome to CrazyEights Game ");

        System.out.println("Please enter your choice" );

        System.out.println("1. Play ");
        System.out.println("2. Quit");

    }

    private void playMenu(Player currPlayer, boolean cardFromDeck)
    {
        System.out.println("======================================================");

        if(currPlayer.isPlayerMachine())
        {
            System.out.println("It's Computer's turn to play ...");   
        }
        else {
            System.out.println("Player"+ (currPlayer.getPlayerId() + 1) +" it is your turn");   
        }

        System.out.println("Starter Card is "+ starterCard.toString());

        if (cardFromDeck)
            System.out.println("Choose card to play or enter 99 to skip");
        else
            System.out.println("Choose card to play or enter 0 to pick card from deck ");

        System.out.println("======================================================");

        currPlayer.getPlayerHand().printCards();

        System.out.println("======================================================");

    }

    public  String getInput() 
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;

        int x;
        boolean isInputValid=false;

        try 
        {
            do
            {
                line = reader.readLine();

                try 
                {
                    x=Integer.parseInt(line);
                }
                catch (java.lang.NumberFormatException nfe)
                {
                    System.out.println("Invalid Input, Value should be a number. ");
                    continue;
                }

                isInputValid=(x>=0 && x<=51 || x==99);

                if(!isInputValid)

                    System.out.println("Invalid Input,Value should be between 0 and 51 or 99 to skip after drawing from deck");

            }

            while (!isInputValid);

        }

        catch (IOException ioe) {

            System.out.println(ioe);

        }

        return line;

    }

    public Card changeStarterCard(Player p)
    {

        int selSuit = -1;
        Card newStarterCard = null;
        Suit selectedSuit;

        StringBuilder sb = new StringBuilder();

        System.out.println("Please choose a Suit for the new Starter Card");

        sb.append("Please choose a Suit for the new Starter Card\n");

        Suit[] suitArr=Suit.values();

        for(int i=0; i<suitArr.length; i++)
        {
            sb.append(i+1 + ". " + suitArr[i] + "\n");
            System.out.println(i+1 + ". " + suitArr[i]);
        }

        if (p.isPlayerMachine())
        {

            OptionPanelHelper.showMessageDialog("Computer is selecting a Suit for the new Starter Card ");
            System.out.println("Computer is selecting a Suit for the new Starter Card ");

            Map<Integer,Card> computerCards = 
                p.getPlayerHand().getCardsOnPlayersHands();

            for(Integer k : computerCards.keySet())
            {
                Card c = computerCards.get(k);

                for(int i=0; i<suitArr.length; i++)
                {
                    if(c.getSuit() == suitArr[i])
                    {
                        selectedSuit = suitArr[i];
                        newStarterCard = new Card(selectedSuit,Rank.R8);

                        //newStarterCard.setRank(Rank.R8);
                        //newStarterCard.setSuit(selectedSuit);
                        OptionPanelHelper.showMessageDialog("Computer selected " + selectedSuit);
                        System.out.println("Computer selected " + selectedSuit);

                        break;

                    }
                }
            }
        }
        else
        {
            selSuit = Integer.parseInt(OptionPanelHelper.showInputDialog(sb.toString()));
            // selSuit = Integer.valueOf(getInput());

            while(true)
            {   
                if (selSuit >= 0 && selSuit <= 4) {
                    selectedSuit = suitArr[selSuit-1];
                    System.out.println(selectedSuit);
                    OptionPanelHelper.showMessageDialog(selectedSuit.toString());
                    break;
                } else {
                    OptionPanelHelper.showMessageDialog("Invalid Input, Try again ..");
                    System.out.println("Invalid Input, Try again ..");
                    selSuit = Integer.parseInt(OptionPanelHelper.showInputDialog(sb.toString()));
                    //selSuit = Integer.valueOf(getInput());
                }   
            }

            newStarterCard = new Card(selectedSuit,Rank.R8);   
        }

        return newStarterCard;

    }

    public  int pickACard(Player currPlayer)
    {
        int selectedCard = -1;

        if(currPlayer.isPlayerMachine())
        {
            System.out.println("Computer is picking up a card ...");

            Map<Integer,Card> computerCards = 
                currPlayer.getPlayerHand().getCardsOnPlayersHands();

            for(Integer k : computerCards.keySet())
            {
                Card c = computerCards.get(k);

                System.out.print("Current Card :" + c);
                System.out.print("Starter Card :" + starterCard);

                if(c != null && (c.equals(starterCard,false) 
                            || c.getRank() == Rank.R8) )
                {
                    selectedCard = k;
                    System.out.println("Computer selected " + selectedCard + " " + c);

                    break;
                } 

            }

            System.out.println("Ouside for loop ...");

            /*
               try 
               {
               Thread.sleep(2000);
               } 
               catch (InterruptedException e) 
               {
               e.printStackTrace();
               }
             */

        }

        System.out.println("selectedCard=" + selectedCard);

        return selectedCard;
    }

    private int pickACard(Player currPlayer,boolean cardPickedUpFromDeck)
    {
        int selectedCard = 0;

        if(!currPlayer.isPlayerMachine())
            selectedCard = Integer.parseInt(getInput());
        else
        {
            System.out.println("Computer is picking up a card ...");


            Map<Integer,Card> computerCards = 
                currPlayer.getPlayerHand().getCardsOnPlayersHands();

            for(Integer k : computerCards.keySet())
            {
                Card c = computerCards.get(k);

                if(c != null && (c.equals(starterCard,false) 
                            || c.getRank() == Rank.R8) )
                {
                    selectedCard = k;
                    System.out.println("Computer selected " + selectedCard + " " + c);

                    break;
                } 

            }

            if (cardPickedUpFromDeck)
            {
                selectedCard = 99;  
            }		  

            /*
               try 
               {
               Thread.sleep(2000);
               } 
               catch (InterruptedException e) 
               {
               e.printStackTrace();
               }
             */

        }

        return selectedCard;
    }

    public void nextMove(int playerId)
    {

        Player currPlayer = players.get(playerId);

        Card c = null;

        boolean cardPickedUpFromDeck = false;

        int selectedCard;

        do
        {

            // Show cards to play/select
            playMenu(currPlayer,cardPickedUpFromDeck);

            //OptionPanelHelper.showMessageDialog("Please select a card ...");

            // Prompt the user to select a card
            //selectedCard=Integer.parseInt(getInput());
            selectedCard=pickACard(currPlayer,cardPickedUpFromDeck);

            if(!cardPickedUpFromDeck)
            {
                if (selectedCard != 0) // player picks a card to play
                {
                    c = currPlayer.getPlayerHand().playCard(selectedCard, starterCard);	

                    if(c != null) // a valid card, change the starter card and exit
                    {
                        if (c.getRank() == Rank.R8) // if selected card is 8, will let the player change the next starter card
                            starterCard = changeStarterCard(currPlayer);
                        else
                            starterCard = c;
                    } 
                }
                else // player choose 0 to pick a card from deck 
                {	
                    cardPickedUpFromDeck = true;

                    // add card to player hand
                    currPlayer.getPlayerHand().addCard(deck.getTopCard());


                    // change top card index of the Deck
                    deck.removeTopCard();

                    continue;
                } 	

            }
            else { // card picked up from deck

                if(selectedCard != 99)
                {
                    c = currPlayer.getPlayerHand().playCard(selectedCard, starterCard);	

                    if(c != null) // a valid card, change the starter card and exit
                    {
                        if (c.getRank() == Rank.R8) // if selected card is 8, will let the player change the next starter card
                            starterCard = changeStarterCard(currPlayer);
                        else
                            starterCard = c;
                    } 

                }
                else {
                    // System.out.println("User select 99, go to next player !!");
                    break;
                }

            }

            if(c==null)
                System.out.println("Incorrect input, Please enter again");


        }

        while(c==null);

    } 

    public void advanceTurn() 
    {
        whoseTurnIsIt += 1;

        if (whoseTurnIsIt == NUM_PLAYERS) {

            whoseTurnIsIt=0;

        }
    }

    public boolean gameOver()
    {

        boolean somePlayerHasNoCards = false;

        for (Player p : players) 
        { 
            somePlayerHasNoCards = somePlayerHasNoCards 
                || p.getPlayerHand().numCardsInHand() == 0;
        }

        return deck.getTopCardIndex() > 51 || somePlayerHasNoCards;

    }

    public Player whoWonTheGame()
    {
        Player gameWonBy = null;

        if (gameOver())
        {
            for(Player p : players)
            {
                if(p.getPlayerHand().numCardsInHand() == 0)
                {
                    gameWonBy = p;
                    break;
                }
            }
        }

        return gameWonBy;
    }

}


