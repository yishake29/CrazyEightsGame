
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Card 
{ 
	private final  Suit suit;
	private final Rank rank;


	public Card(Suit suit, Rank rank) 
	{
	        
		this.suit = suit;
		this.rank = rank;
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

    

	public void draw(Graphics2D g2d,int x,int y,String path)
	{
		Image mshi = new ImageIcon(path).getImage();
		g2d.drawImage(mshi, x, y,null);

	}

	public void draw(Graphics2D g2d,int x,int y,int w,int h,String path)
	{
		Image mshi = new ImageIcon(path).getImage();

		g2d.drawImage(mshi, x, y,w,h,null);

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

