

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

        whoseTurnIsIt=0;

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

                System.out.println("Adding " + deck.getTopCard());
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

}

