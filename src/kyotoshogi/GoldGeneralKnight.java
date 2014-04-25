package kyotoshogi;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author JAMESSILVERA
 */
public class GoldGeneralKnight extends BasePiece{
    
    GoldGeneralKnight(int o, int p)
    {
        super();
        owner = o;
        pos = p; //position on board
        isPromoted = false;
        
        symbol = new ImageIcon[2];
        symbol[0] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/ggen.png"));
        symbol[1] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/knight.png"));
        
        if(o == 0)// rotate player 1 icons
            rotateIcons();
            
        setIcon(symbol[0]);
        setLayout(new BorderLayout());
                                           
    }//end Constructor
    
    

    //Move list return depends on whether piece is Promoted or Demoted
    public ArrayList < Integer > getMoveList()
    {      
        if(!isPromoted)        
            return goldGeneralMoves();                   
        else
            return knightMoves();  
    }
    
    
    
    private ArrayList< Integer > goldGeneralMoves()
    {
        ArrayList< Integer > rml = new ArrayList<>();

        int temp = pos;  //current position on board
        //FORWARD POSITION Diagnol within board bounds
        int OFFSET = (owner == 0) ? 5 : -5;
        if(((owner == 0) ? ((temp + OFFSET) < 25) : ((temp + OFFSET) >= 0))
            && (temp % 5) != 4)
        {
            temp += (OFFSET + 1);
            rml.add(temp);
        }

        
        temp = pos;
        //FORWARD POSITION DIAGNOL within board bounds
        if(((owner == 0) ? ((temp + OFFSET) < 25) : ((temp + OFFSET) >= 0))
            && (temp % 5) != 0)
        {
            temp += (OFFSET - 1);
            rml.add(temp);        
        }
 
        temp = pos;
        //Up position within board bounds
        if((temp - 5) >= 0)
        {
            temp -= 5;
            rml.add(temp);
        }
      
        temp = pos;
        //Down position within board bounds
        if((temp + 5) < 25)
        {
            temp += 5;
            rml.add(temp);
        }
        
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
    }//end goldGeneralMoves Method

    
    
    private ArrayList< Integer > knightMoves()
    {
        ArrayList< Integer > rml = new ArrayList<>();

        int temp = pos;                
        int OFFSET = (owner == 0) ? 10 : -10;
        //FORWARD POSITION
        if(((owner == 0) ? ((temp + OFFSET) < 25) : ((temp + OFFSET) >= 0))
            && (temp % 5) != 4)
        {
            temp += (OFFSET + 1);
            rml.add(temp);
        }
        
        temp = pos;
        //FORWARD POSITION DIAGNOL within board bounds
        if(((owner == 0) ? ((temp + OFFSET) < 25) : ((temp + OFFSET) >= 0))
            && (temp % 5) != 0)
        {
            temp += (OFFSET - 1);
            rml.add(temp);        
        }

        return rml;
    }//end knightMoves Method

}//end Class goldGeneralKnight
