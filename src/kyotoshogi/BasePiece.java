package kyotoshogi;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author JAMESSILVERA
 */
public abstract class BasePiece extends JLabel{
    
    protected boolean isPromoted;
    protected ImageIcon[] symbol;
    protected int owner;
    protected int pos;
    
    
    
    
    public BasePiece()
    {
        super();  
    }

    public abstract ArrayList< Integer > getMoveList();

    public int getOwner()
    {
        return owner;
    }
    
    public void setOwner(int o)
    {
        owner = o;
        rotateIcons();
    }
    
    public int getPos()
    {
        return pos;
    }
    
            
    //Override this function for King class
    // because King doesn't get promoted/demoted
    public void setPos(int p)
    {
        //Promote / Demote after each move
        if(!(this instanceof King))
        {
            isPromoted = (isPromoted ? false : true);
                
            //Change Icon
            if(isPromoted)
                setIcon(symbol[1]);
            else
                setIcon(symbol[0]);
        }
        
        pos = p;
        
        repaint();
    }
    
    public boolean isPromoted()
    {        
        return isPromoted;
    }
    
    public void setPromotion(boolean p)
    {
        isPromoted = p;
        
        //Change Icon
        if(isPromoted)
            setIcon(symbol[1]);
        else
            setIcon(symbol[0]);
    }
    
        
    public void rotateIcons()
    {
        //King is only peice that has one symbol image
        //King doesn't promote or demote
        int size = (this instanceof King) ? 1 : 2;
        
        //This for-loop code was taken from a forum.
        for(int i = 0; i < size; i++)
        {
            int w = symbol[i].getIconWidth();  
            int h = symbol[i].getIconHeight();  
            int type = BufferedImage.TRANSLUCENT;    
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
    

}//end Class BasePiece 
