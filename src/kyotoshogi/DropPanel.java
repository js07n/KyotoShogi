package kyotoshogi;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author JAMESSILVERA
 */
public class DropPanel extends JPanel{
    
    private ArrayList< DropLabel > RPImage;
    private ImageIcon symbol[];
    
    public DropPanel(String name)
    {
        super();
        setName(name);
        setBackground(Color.gray.brighter());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        setLayout(new GridLayout(4, 0, 10, 10));

        MMHandler handler = new MMHandler();
        
        symbol = new ImageIcon[8];
        //Demotoed symbols
        symbol[0] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/pawn0.png")); //pawn
        symbol[1] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/tokin0.png")); //tokin
        symbol[2] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/sgen0.png")); //silverGen
        symbol[3] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/ggen0.png")); //goldGen
        
        //Promoted symbols
        symbol[4] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/rook0.png")); //rook
        symbol[5] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/lance0.png")); //lance
        symbol[6] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/bishop0.png")); //Bishop
        symbol[7] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/knight0.png")); //Knight
        

        //player 1 icons should be upside down
        if(name.equals("0"))
            rotateIcons();
                 

        // Reserve Piece list        
        RPImage = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {

            RPImage.add(new DropLabel("0", symbol[i], 0, i));
            RPImage.get(i).addMouseListener(handler);
            RPImage.get(i).addMouseMotionListener(handler);
            RPImage.get(i).setVisible(false); //
            RPImage.get(i).setName(Integer.toString(i));
            
            add(RPImage.get(i));

        }//end for loop
        
    }// end constructor
    
    
    
    //This Method was influenced from code found in an online forum
    public void rotateIcons()
    {    
        for(int i = 0; i < symbol.length; i++)
        {
            int w = symbol[i].getIconWidth();  
            int h = symbol[i].getIconHeight();  
            int type = BufferedImage.TRANSLUCENT;  // other options, see api  
            BufferedImage image = new BufferedImage(h, w, type);  
            Graphics2D g2 = image.createGraphics();  
            double x = (h - w)/2.0;  
            double y = (w - h)/2.0;  
            AffineTransform at = AffineTransform.getTranslateInstance(x, y);  
            at.rotate(Math.toRadians(180), w/2.0, h/2.0);          
            g2.drawImage(symbol[i].getImage(), at, null);  
            g2.dispose();  
        
            symbol[i] = new ImageIcon(image);
        }//end For Loop
       
    }//end rotateIcons Method
    
    
    
    public class DropLabel extends JLabel
    {
        private int numType;
        private boolean isPromoted;
        private String pieceType;
    
        public DropLabel(String s, Icon i, int m, int nt)
        {
            super(s, i, m);
            
            numType = nt;
            isPromoted = false;
            
            //Piecetype is determined by the num type
            // study code below
            switch(nt)
            {
                case 0:
                    pieceType = "class " + PawnRook.class.getName();
                    break;
                case 1:
                    pieceType = "class " + TokinLance.class.getName();
                    break;
                case 2:
                    pieceType = "class " + SilverGeneralBishop.class.getName();                    
                    break;
                default:
                    pieceType = "class " + GoldGeneralKnight.class.getName();
            }//end switch statement
            
        }//end constructor
        

        public String getPieceType()
        {        
            return pieceType;
        }
        
        public void setNumType(int nt)
        {
            numType = nt;
        }
        
        //This is mostly used for calculations
        public int getNumType()
        {        
            return numType;
        }
        
    
        //Maybe not use this method?
        public void changePromotion()
        {
            isPromoted = (isPromoted) ? false : true;   
        }
        
        public boolean isPromoted()
        {
            return isPromoted;
        }
    
    }//end Class DropLabel
    
    
    
    public void setPiece(BasePiece cp)
    {        
        int piece;
        if(cp instanceof PawnRook)            
            piece = 0;                        
        else if(cp instanceof TokinLance)
            piece = 1;
        else if(cp instanceof SilverGeneralBishop)
            piece = 2;
        else //GoldGeneralKnight
            piece = 3;       
        
        int count = Integer.parseInt(RPImage.get(piece).getText()) + 1;
        RPImage.get(piece).setText(Integer.toString(count));
        RPImage.get(piece).setVisible(true);
        repaint();

    }// end setPiece Method
 
    
    
    private class MMHandler extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getClickCount() == 2)
            {
                DropLabel dpl = (DropLabel) e.getComponent();
                
                if(dpl.isPromoted)
                    dpl.setIcon(symbol[dpl.getNumType()]);
                else
                    dpl.setIcon(symbol[dpl.getNumType()+4]);
 
                dpl.changePromotion();
            }//end doubleclick
        
        }//end mouseClicked

        public void mouseReleased(MouseEvent e)
        {
            fromDLToBoard(e.getXOnScreen(), e.getYOnScreen(), 
                    (DropLabel) e.getComponent());
        }//end MouseReleased
        
    }//end MMHandler
    
    
    
    //This method uses the GameFrame Method, updateBoard, to send data from 
    // The DropPanel to the Board.
    public void fromDLToBoard(int x, int y, DropLabel dl)
    {
        //The udpateBoard Method returns true if the piece is dragged to a valid
        //spot on the board.
        if(((GameFrame) SwingUtilities.getRoot(this)).updateBoard(x, y, dl, 
                Integer.parseInt(getName())))
        {     
            //Decrement number of specific piece found in Drop List
            int count = Integer.parseInt(dl.getText()) - 1;
            dl.setText(Integer.toString(count));
            
            //If there aren't any pieces of this specific type the user can 
            //drop, then VISIBLY remove it from the drop list.             
            if(count == 0)
                dl.setVisible(false);
        }//end if
    }// end fromDLtoBoard Method
    
    
    
    public void reset()
    {
        for(int i = 0; i < 4; i++)
        {
            RPImage.get(i).setText("0");
            RPImage.get(i).setVisible(false);
        }//end for loop
    
        repaint();
    }//end reset MEthod
    
}//end Class DropPanel
