

import javax.swing.JFrame;

public class CrazyEightGui {
    
	private static JFrame frame;
    
	private static void createAndShowGUI() {
        frame = new JFrame("Crazy Eight");
        frame.add(new CrazyEightGame());
        frame.setDefaultCloseOperation(
           JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });        
    }
}
