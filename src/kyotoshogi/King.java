package kyotoshogi;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author JAMESSILVERA
 */
public class King extends BasePiece{
    
    King(int o, int p)
    {
        super();
        owner = o;
        pos = p; //position on board
        isPromoted = true;
        
        
        symbol = new ImageIcon[1];
        symbol[0] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/king.png"));
        
        if(o == 0)
            rotateIcons();

        setIcon(symbol[0]);
        setLayout(new BorderLayout());
                                           
    }//end Constructor
        
    
    
    public ArrayList < Integer > getMoveList()
    {      
        ArrayList< Integer > rml = new ArrayList<>();
        
        int temp = pos;  //current position on board
    
        //Up position
        if((temp - 5) >= 0)
        {
            temp -= 5;
            rml.add(temp);
            
            int uTemp = temp;
            //Up-Left position
            if((uTemp % 5) != 0)
            {            
                rml.add(--uTemp);
            }
            
            uTemp = temp;
            //Up-Right position
            if(((uTemp) % 5) != 4)
            {            
                rml.add(++uTemp);
            }                        
        }//end if Up statements
        
        
        temp = pos;
        //Down position
        if((temp + 5) < 25)
        {
            temp += 5;
            rml.add(temp);
            
            int dTemp = temp;
            
            //Down-Left position
            if((dTemp % 5) != 0)
            {            
                rml.add(--dTemp);
            }
            
            dTemp = temp;
            //Down-Right position
            if(((dTemp) % 5) != 4)
            {            
                rml.add(++dTemp);
            }                        
        }//end if Down statements
        
        
        temp = pos;
        //Left position within board bounds
        if((temp % 5) != 0)
        {            
            rml.add(--temp);
        }
                
        temp = pos;
        //Right position within board bounds
        if((temp % 5) != 4)
        {            
            rml.add(++temp);
        }
        

        return rml;

    }//end moveList Method
    
}//end Class King
