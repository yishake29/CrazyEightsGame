import java.io.InputStream;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.awt.*;

public class CrazyEightGame extends Canvas {
    private static final String CARD_IMAGES_PATH = "Cards"; //"/root/mac2010/A7/Cards";
    /**
     * 
     */

    private static Image SkipButton;
    static{
        try{
            SkipButton = ImageIO.read(CrazyEightGame.class.getResourceAsStream("skippicture.png"));
        }catch (IOException iioe) {
            System.out.println(iioe);
        }
    }

    private static final long serialVersionUID = 1L;

    private static final int NUM_OF_PLAYERS = 2;

    private static final int CARD_WIDTH = 150; 
    private static final int CARD_HEIGHT = 150; 

    private static final int DECK_POS_X = 10;
    private static final int DECK_POS_Y = 200;	

    private static final int STARTER_CARD_POS_X = 200;
    private static final int STARTER_CARD_POS_Y = 200;	

    private static final int PLAYER1_POS_X = 10;
    private static final int PLAYER1_POS_Y = 10;

    private static final int PLAYER2_POS_X = 10;
    private static final int PLAYER2_POS_Y = 400;

    private boolean cardPickedUpFromDeck = false;

    private CrazyEights ce;

    public CrazyEightGame() 
    {        
        initNewGame();

        setSize(600, 600);
        setBackground(Color.BLUE);

        addMouseListener(new MouseEventListener());

    }
    private void initNewGame()
    {
        repaint();

        ce = new CrazyEights(NUM_OF_PLAYERS);

        ce.getDeck().shuffleDeck();
        ce.initCardsOnPlayersHand();
        ce.setStarterCard();

        ce.getPlayers().get(0).setPlayerMachine(true);

        System.out.println("*************** Game is started ***************");

        OptionPanelHelper.showMessageDialog("*************** Game is started ***************");

    }
    public void paint(Graphics g) 
    {

        Graphics2D g2d = (Graphics2D) g;

        drawCardsOnPlayerHands(0,g2d,PLAYER1_POS_X,PLAYER1_POS_Y,true);

        drawDeck(g2d,ce.getDeck().getTopCard(),DECK_POS_X,DECK_POS_Y);

        drawStarterCard(g2d,ce.getStarterCard(),STARTER_CARD_POS_X,STARTER_CARD_POS_Y);    

        drawCardsOnPlayerHands(1,g2d,PLAYER2_POS_X,PLAYER2_POS_Y,true);


        drawSkipButton(g2d, 450,30);   
    }

    private void drawSkipButton(Graphics2D g2d, int x, int y) {
        g2d.drawImage(SkipButton, 375, 300, null);
    }

    private boolean clickedOnCard(int px, int py, int cx, int cy, int cw, int ch) {
        return px > cx+25 && px < cx + cw-25 && py > cy && py < cy + ch;
    }
    private boolean clickedOnSkip(int pointX, int pointY) {
        return clickedOnCard(pointX, pointY, 375, 300, 192, 72); 
    }
    

    private int clickedOnCPH(int playerId, int pointX, int pointY, int baseX, int baseY)
    {
        List<Card> cardsOnPlayerHand = 
            ce.getPlayers().get(playerId).getPlayerHand().getCardsOnPlayersHandsAsList();
        int x = baseX + (cardsOnPlayerHand.size()-1)*50;
        int y = baseY;

        for(int i = cardsOnPlayerHand.size() - 1; i >= 0; i--)

        {
            System.out.printf("%d %d %d %d \n",pointX,pointY,x,y);  
            if (clickedOnCard(pointX, pointY, x, y, CARD_WIDTH, CARD_HEIGHT)) {
                return i;
            }      			
            x-=50;	
        }
        return -1;
    }

    private boolean clickedOnDeck(int pointX, int pointY, int x, int y)
    {
        if (clickedOnCard(pointX, pointY, x, y, CARD_WIDTH, CARD_HEIGHT)) {
            return true;
        } else {
            return false;
        }
    }
    private Player getCurrentPlayer()
    {
        return ce.getPlayers().get(ce.getWhoseTurnIsIt());	
    }

    private void handleClick(int x, int y) 
    {      	
        boolean deckClicked = false;	

        deckClicked = clickedOnDeck(x, y, DECK_POS_X, DECK_POS_Y);

        if(deckClicked)
        {
            handleDeckClick();	
        }
        else if (clickedOnSkip(x, y)) {
            System.out.println("clicked on skip");
        }
        else 
        {
            int cardIndex = clickedOnCPH(1, x, y, PLAYER2_POS_X, PLAYER2_POS_Y); 

            if(cardIndex != -1)
            {

                //OptionPanelHelper.showMessageDialog(getCurrentPlayer().getPlayerHand().getCardsOnPlayersHandsAsList().get(cardIndex).toString());

                if(!getCurrentPlayer().isPlayerMachine())
                    playCard(cardIndex+1);
            }
        }
    }
    private void handleDeckClick()
    {
        System.out.println("Deck is clicked !!");   

        if (!cardPickedUpFromDeck) 
        {
            System.out.println(ce.getWhoseTurnIsIt());

            Card cardFromDeck = getCardFromDeck();

            System.out.println("cardFromDeck:" + cardFromDeck);

            repaint();   		
        }

    }
    private Card getCardFromDeck()
    {
        Player currPlayer = getCurrentPlayer();
        Card   topCard = ce.getDeck().getTopCard();

        currPlayer.getPlayerHand().addCard(topCard);
        ce.getDeck().removeTopCard();

        cardPickedUpFromDeck = true;

        return topCard;

    }
    private void checkGameStatus()
    {
        if (ce.gameOver())
        {
            OptionPanelHelper.showMessageDialog("*************** Game Over !!! ***************");

            Player gameWonBy = ce.whoWonTheGame();

            if (gameWonBy != null) {
                if (!gameWonBy.isPlayerMachine()) 

                    OptionPanelHelper.showMessageDialog("You" + (gameWonBy.getPlayerId()+1) + " won the game !!");
                else
                    OptionPanelHelper.showMessageDialog("Computer won the game !!");
            }
            else {
                OptionPanelHelper.showMessageDialog("There is no winner !!");
            }

            boolean yes = OptionPanelHelper.showConfirmDialog("New Game ?");

            if(yes)
                initNewGame();
            else
                System.exit(0);

        }          
    }

    private void playCard(int selectedIndex)
    {

        //OptionPanelHelper.showMessageDialog(ce.getStarterCard().toString());

        Player player = getCurrentPlayer();

        if(player.isPlayerMachine())
        {
            //OptionPanelHelper.showMessageDialog("Computer is playing ...");

            int selectedCard = ce.pickACard(player);

            if(selectedCard != -1)
                nextMove(player,ce.pickACard(player));
            else
            {
                OptionPanelHelper.showMessageDialog("Computer will take a card from deck");

                getCardFromDeck();

                repaint();

                /*try {
                  Thread.sleep(2000);
                  } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
                 */

                nextMove(player,player.getPlayerHand().numCardsInHand());

            }
        }
        else 
        {
            //OptionPanelHelper.showMessageDialog("Index:" + selectedIndex);
            nextMove(player,selectedIndex);	
        }

    }

    private void nextMove(Player player,int selectedCard)
    {	
        Card c = player.getPlayerHand().playCard(selectedCard, ce.getStarterCard());
        Card starterCard = c;

        if(c != null)
        {
            // OptionPanelHelper.showMessageDialog("Correct Card  " + c);
            if (c.getRank() == Rank.R8) // if selected card is 8, will let the player change the next starter card
            {
                starterCard = ce.changeStarterCard(player);
                OptionPanelHelper.showMessageDialog("New Starter Card :" + starterCard);
            }

            ce.advanceTurn();
            ce.setStarterCard(starterCard);
            cardPickedUpFromDeck = false;

            repaint();

            checkGameStatus();

            if(getCurrentPlayer().isPlayerMachine())
                playCard(-1);

        }
       // else {

         //   if(!cardPickedUpFromDeck)
           //     OptionPanelHelper.showMessageDialog("Incorrect Card. You can take a card from deck");
           // else
          //  {
             //   if(!player.isPlayerMachine())
               // {
                   // boolean skip = OptionPanelHelper.showConfirmDialog("Incorrect Card. Skip ?");

                //    if(skip)
                  //  {
                    //    ce.advanceTurn();
                      //  cardPickedUpFromDeck = false;

                       // if(getCurrentPlayer().isPlayerMachine())
                         //   playCard(-1);
                   // }
              //  }
               // else 
               // {   
                 //   ce.advanceTurn();
                  //  cardPickedUpFromDeck = false;

               // }

           // }    				
       // }

    }
    private void drawStarterCard(Graphics2D g2d,Card starterCard,int x,int y)
    {
        String imagePath = CARD_IMAGES_PATH +"/" + starterCard.getCardId() + ".png";
        starterCard.draw(g2d, x, y,CARD_WIDTH,CARD_HEIGHT, imagePath,true);    
    }

    private void drawDeck(Graphics2D g2d,Card topCard,int x,int y)
    {
        String imagePath = CARD_IMAGES_PATH + "/FaceDownn.png";
        topCard.draw(g2d, x, y,CARD_WIDTH,CARD_HEIGHT, imagePath,false);    
    }

    private void drawCardsOnPlayerHands(int playerId,Graphics2D g2d,int baseX,int baseY,boolean hidden)
    {
        List<Card> cardsOnPlayerHand = 
            ce.getPlayers().get(playerId).getPlayerHand().
            getCardsOnPlayersHandsAsList();
        int x = baseX;
        int y = baseY;

        for(Card card : cardsOnPlayerHand)
        {
            String fileName = hidden ? "FaceDownn.png" : card.getCardId() + ".png";
            String imagePath = CARD_IMAGES_PATH + "/" + fileName;

            File file = new File(imagePath);

            System.out.println(imagePath);
            if(!file.exists())
            {
                imagePath = CARD_IMAGES_PATH + "/FaceDownn.png"; 
            }

            card.draw(g2d, x, y,CARD_WIDTH,CARD_HEIGHT, imagePath,true);      			
            x+=50;

        }
    }


    private class MouseEventListener  implements MouseListener 
    {
        public void mousePressed(MouseEvent e) {

            println("Mouse pressed; # of clicks: "
                    + e.getClickCount(), e); 
        }

        public void mouseReleased(MouseEvent e) {
            println("Mouse released; # of clicks: "
                    + e.getClickCount(), e);
        }

        public void mouseEntered(MouseEvent e) {
            println("Mouse entered", e);
        }

        public void mouseExited(MouseEvent e) {
            println("Mouse exited", e);
        }

        public void mouseClicked(MouseEvent e) {

            println("Mouse clicked (# of clicks: "
                    + e.getClickCount() + ")", e);
            System.out.println("Clicked on " + e.getX() +  ":" + e.getY());

            handleClick(e.getX(), e.getY());
        }

        void println(String eventDescription, MouseEvent e) 
        {
            System.out.println(eventDescription + " detected on "
                    + e.getComponent().getClass().getName());
        }
    }

}





