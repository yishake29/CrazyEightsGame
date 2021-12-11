

import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Card 
{ 
    private final Suit suit;
    private final Rank rank;
    private Image mshi;
    private static Image back= null ;

    static{
       try{
        back = ImageIO.read(Card.class.getResourceAsStream("Cards/FaceDownn.png"));
             // System.out.println(back.getWidth(null)+ " " +back.getHeight(null));       
 }catch (IOException iioe) {
            System.out.println(iioe);
        }   
    }   


    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
        String imgName = "Cards/" + rank.toString() + suit.toString() +".png";
        try {
            mshi = ImageIO.read(getClass().getResourceAsStream(imgName));
        }catch(IOException iioe) {
            System.out.println(iioe);
        }
    }


    public void print()
    {
        System.out.println(this.rank+""+this.suit);
    }

    public String toString()
    {
        return  this.rank+" "+this.suit;
    }

    public String getCardId() {
        return rank.name() + suit.name();
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }


    public void draw(Graphics2D g2d,int x,int y,String path,boolean faceUp)
    {
        //Image mshi = new ImageIcon(path).getImage();
        if (faceUp) g2d.drawImage(mshi, x ,y,null);
        else g2d.drawImage(Card.back, x, y,null);

    }

    public void draw(Graphics2D g2d,int x,int y,int w,int h,String path,boolean faceUp)
    {
        //Image mshi = new ImageIcon(path).getImage();
        if(faceUp)g2d.drawImage(mshi, x, y,w,h,null);
        else g2d.drawImage(Card.back,x,y,w,h,null);
    }

    public boolean equals(Card c,boolean compareSuitOnly)
    {
        if (compareSuitOnly) {
            return c.suit == this.suit;
        }

        else {

            return c.rank == this.rank || c.suit == this.suit;
        }
    }

}

