import java.io.InputStream;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;

public class CrazyEightGame extends Canvas
{
    //private static final int CANVAS_SIZE = 500;
    //private static final int CANVAS_MARGIN = 50;
    private static final int NUM_OF_PLAYERS = 2;
    private static final String CARD_IMAGES_PATH = "Cards"; //"/root/mac2010/A7/Cards";
    private static final int CARD_WIDTH = 150;
    private static final int CARD_HEIGHT = 150;
  
    private CrazyEights ce;
    
    public CrazyEightGame() 
    {        	
        ce = new CrazyEights(NUM_OF_PLAYERS);
        
        ce.getDeck().shuffleDeck();
        ce.initCardsOnPlayersHand();
       ce.setStarterCard();
        
        // lastly we set our size
        setSize(600, 600);
        setBackground(Color.BLUE);

        addMouseListener(new MouseEventDemo());
    }

    public void paint(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g;
        
        drawCardsOnPlayerHands(0,g2d,10,10,true);
        
        drawDeck(g2d,ce.getDeck().getTopCard(),50,200);
        drawStarterCard(g2d,ce.getStarterCard(),250,200);
        
        drawCardsOnPlayerHands(1,g2d,10,400,false);
        	    
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
    			ce.getPlayers().get(playerId).getPlayerHand().getCardsOnPlayersHands();

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
	
    public class MouseEventDemo  implements MouseListener {

        public void mousePressed(MouseEvent e) {
            saySomething("Mouse pressed; # of clicks: "
                    + e.getClickCount(), e);
        }

        public void mouseReleased(MouseEvent e) {
            saySomething("Mouse released; # of clicks: "
                    + e.getClickCount(), e);
        }

        public void mouseEntered(MouseEvent e) {
            saySomething("Mouse entered", e);
        }

        public void mouseExited(MouseEvent e) {
            saySomething("Mouse exited", e);
        }

        public void mouseClicked(MouseEvent e) {
            saySomething("Mouse clicked (# of clicks: "
                    + e.getClickCount() + ")", e);
        }

        void saySomething(String eventDescription, MouseEvent e) {
            System.out.println(eventDescription + " detected on "
                    + e.getComponent().getClass().getName());
        }
    }
}

