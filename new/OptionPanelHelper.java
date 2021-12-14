import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OptionPanelHelper {
	
	public static void showMessageDialog(String msg)
	{
		JFrame f=new JFrame();  
	    JOptionPane.showMessageDialog(f,msg);  
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public static String showInputDialog(String msg)
	{
		  JFrame f=new JFrame();   
		  return JOptionPane.showInputDialog(f,msg);  
	}
	
	public static boolean showConfirmDialog(String msg)
	{
		JFrame f=new JFrame();
		boolean confirm = false;
		
		int a=JOptionPane.showConfirmDialog(f,msg);  
		
		if(a==JOptionPane.YES_OPTION){  
			confirm = true;  
		}
		
		return confirm;
	}
	  
	public static void main(String[] args)
	{
		String name = showInputDialog("Enter Name");
		showMessageDialog("Hello " + name);	
	}

}
