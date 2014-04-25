package kyotoshogi;

import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author JAMESSILVERA
 */
public class GameFrame extends JFrame{
    
    //JFrame Dimensions
    private static final int F_WIDTH = 750;
    private static final int F_HEIGHT = 700;
    
    //Board Dimensions - x, y, w, h
    private static final Rectangle BOARD_DIM = new Rectangle(
            F_HEIGHT/9, F_HEIGHT/6, //x, y
            F_HEIGHT-(F_HEIGHT/3), F_HEIGHT-(F_HEIGHT/3)); // w, h
    
    //Drop List Panel Dimensions - x, y, w, h
    private static final Rectangle DROP0_DIM = new Rectangle(
            BOARD_DIM.width + (BOARD_DIM.x*2), 0,  //x, y
            F_WIDTH-(BOARD_DIM.width + (BOARD_DIM.x*2)), F_HEIGHT/2);  //w, h
    
    private static final Rectangle DROP1_DIM = new Rectangle(
            DROP0_DIM.x, F_HEIGHT/2,  //x, y
            DROP0_DIM.width, DROP0_DIM.height);  //w, h
    
    public static JLabel[] playerLabel; 
 
    public Board board;
    public DropPanel dp[];
    private int playerTurn;
    
    private JLabel[] statusL;
    private static final String p0t = "PLAYER 1 TURN";
    private static final String p1t = "PLAYER 2 TURN";

    
    
    public GameFrame()
    {
        super( "Kyoto Shogi" );
        getContentPane().setSize(F_WIDTH, F_HEIGHT);       
        
        getContentPane().setBackground(Color.BLACK);
        
        setLayout( null );  

        board = new Board();
        board.setBounds(BOARD_DIM);
        playerTurn = 0;
        
        statusL = new JLabel[2];
        statusL[0] = new JLabel(p0t);                
        statusL[1] = new JLabel();
        
        statusL[0].setForeground(Color.WHITE);
        statusL[1].setForeground(Color.WHITE);

        dp = new DropPanel[2];
        
        //The name is set to Identify which DropPanel is communicating
        // with the board, in later code
        dp[0] = new DropPanel("0");
        dp[0].setBounds(DROP0_DIM);

        dp[1] = new DropPanel("1");
        dp[1].setBounds(DROP1_DIM);

        playerLabel = new JLabel[2];
        
        for(int i = 0; i < 2; i++)
        {
            playerLabel[i] = new JLabel();            
            playerLabel[i].setText("PLAYER " + (i+1));
            playerLabel[i].setBackground(Color.WHITE);
            playerLabel[i].setOpaque(true); //This will let the color show.
            playerLabel[i].setBorder(BorderFactory
                    .createLineBorder(Color.GRAY));
            playerLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
            playerLabel[i].setBounds(
                    i*(DROP1_DIM.x - playerLabel[0].getWidth()), //x
                    i*(F_HEIGHT - playerLabel[0].getHeight()),// y
                    F_WIDTH/10, F_WIDTH/20); //w, h

            statusL[i].setHorizontalAlignment(SwingConstants.CENTER);
            statusL[i].setBounds(BOARD_DIM.x,  //X
                    Math.abs((F_HEIGHT-board.getHeight())/4 - 
                        (i * (F_HEIGHT-((F_HEIGHT-board.getHeight())/6)))), //y
                    BOARD_DIM.width, //WIDTH
                    playerLabel[i].getHeight()); //HEIGHT
            
            add(playerLabel[i]);
            add(statusL[i]);
        }
        
        playerLabel[0].setForeground(Color.BLUE);
        playerLabel[1].setForeground(Color.RED);
        
        add(board);
        add(dp[0]);
        add(dp[1]);
        
                                
    }// end GameFrame() constructor
    
    
    
    public int getPlayerTurn()
    {    
        return playerTurn;
    }
   
    
    
    public void setPlayerTurn(int pt)        
    {
        playerTurn = pt;
        
        if(pt == 0)
        {
            statusL[0].setText(p0t);
            statusL[1].setText("");
        }
        else
        {
            statusL[0].setText("");
            statusL[1].setText(p1t);        
        }
    }
    
    
    
    //This Method is used by the board component to communicate
    // with a droppanel component.
    // The captured piece's owner will be a determining factor.
    public void updateDPL(BasePiece cp)
    {  
        dp[cp.getOwner()].setPiece(cp); 
    }
    
   
    
    //This Method is used by DropPanels to communicate with the board component.
    // The dropPanel sends it's name, "dropList", to tell the board which
    //of the two droppanels is communicating with it.
    public boolean updateBoard(int x, int y, DropPanel.DropLabel dl, int dropL)
    {    
        return board.dropPiece(x, y, dl, dropL);
    }
   
   
    
    public void resetDropLists()
    {
        dp[0].reset();
        dp[1].reset();
    }

}// end Class GameFrame
