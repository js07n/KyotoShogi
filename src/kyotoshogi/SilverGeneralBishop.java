package kyotoshogi;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author JAMESSILVERA
 */
public class SilverGeneralBishop extends BasePiece{
    
    SilverGeneralBishop(int o, int p)
    {
        super();
        owner = o;
        pos = p; //position on board
        isPromoted = false;
        
        symbol = new ImageIcon[2];
        symbol[0] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/sgen.png"));
        symbol[1] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/bishop.png"));
        
        if(o == 0)
            rotateIcons();

        setIcon(symbol[0]);
        setLayout(new BorderLayout());
                                           
    }//end Constructor
    

    
    //Move list return depends on whether piece is Promoted or Demoted
    public ArrayList < Integer > getMoveList()
    {      
        if(!isPromoted)
             return silverGeneralMoves();
        else                             
            return bishopMoves();
    }
    

    
    private ArrayList< Integer > silverGeneralMoves()
    {
        ArrayList< Integer > rml = new ArrayList<>();
        
        int temp = pos;  //current position on board
        //FORWARD POSITION within board bounds
        int OFFSET = (owner == 0) ? 5 : -5;
        if((owner == 0) ? ((temp + OFFSET) < 25) : ((temp + OFFSET) >= 0))
        {
            temp += OFFSET;
            rml.add(temp);
        }
        
        temp = pos;
        //Up-right position within board bounds
        if((temp - 5) >= 0 && (temp % 5) != 4)
        {
            temp -= 4;
            rml.add(temp);
        }
        
        temp = pos;
        //Up-left position within board bounds
        if((temp - 5) >= 0 && (temp % 5) != 0)
        {
            temp -= 6;
            rml.add(temp);
        }
      
        temp = pos;
        //Down-right position within board bounds
        if((temp + 5) < 25 && (temp % 5) != 4)
        {
            temp += 6;
            rml.add(temp);
        }
        
        temp = pos;
        //Down-left position within board bounds
        if((temp + 5) < 25 && (temp % 5) != 0)
        {
            temp += 4;
            rml.add(temp);
        }
        
        return rml;
    }//end SilverGeneralMoves Method
    
    
    private ArrayList< Integer > bishopMoves()
    {
        ArrayList< Integer > rml = new ArrayList<>();

        int temp = pos;                
        //Up-right position within board bounds
        while((temp - 5) >= 0 && (temp % 5) != 4)
        {
            temp -= 4;
            rml.add(temp);
        }
        
        temp = pos;
        //Up-left position within board bounds
        while((temp - 5) >= 0 && (temp % 5) != 0)
        {
            temp -= 6;
            rml.add(temp);
        }
      
        temp = pos;
        //Down-right position within board bounds
        while((temp + 5) < 25 && (temp % 5) != 4)
        {
            temp += 6;
            rml.add(temp);
        }
        
        temp = pos;
        //Down-left position within board bounds
        while((temp + 5) < 25 && (temp % 5) != 0)
        {
            temp += 4;
            rml.add(temp);
        }
  
        return rml;
    }//end BishopMoves Method
     
}//end Class SilverGeneralBishop
