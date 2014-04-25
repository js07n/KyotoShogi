package kyotoshogi;

import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author JAMESSILVERA
 */
public class KyotoShogi {

    /**
     * @param args the command line arguments
     */
   public static void main( String args[] )
   { 
      Dimension frameDim = new Dimension(750, 700);
       
      GameFrame gameFrame = new GameFrame(); 
      gameFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      gameFrame.getContentPane().setPreferredSize(frameDim);
      gameFrame.setResizable(false);
      gameFrame.setMaximumSize(new Dimension(750, 700));
      gameFrame.pack();
      gameFrame.setVisible( true ); // display frame
   } // end main
}//end class KyotoShogi
