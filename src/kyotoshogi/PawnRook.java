package kyotoshogi;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author JAMESSILVERA
 */
public class PawnRook extends BasePiece{
    
    
    PawnRook(int o, int p)
    {
        super();
        owner = o;
        pos = p;
        isPromoted = false;
        
        symbol = new ImageIcon[2];
        symbol[0] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/pawn.png"));
        symbol[1] = new ImageIcon(getClass()
                .getResource("/kyotoshogi/res/rook.png"));
        
        if(o == 0)
            rotateIcons();
            
        setIcon(symbol[0]);  
        setLayout(new BorderLayout());
                                           
    }//end Constructor
    
    
 
    //Move list return depends on whether piece is Promoted or Demoted
    public ArrayList < Integer > getMoveList()
    {      
        if(!isPromoted)
        {
            //Pawn Moves
            ArrayList< Integer > ml = new ArrayList<>();
            
            //check to see if move ahead is within boundaries
            if(owner == 0) // player 1
            {
                if((pos + 5) < 25)        
                    ml.add(pos+5);

            }
            else //owner = 1 // player 2
            {
                if((pos - 5) >= 0)        
                    ml.add(pos-5);
            }
            
            return ml;
        }     
        else
            return rookMoves();                            
    }
    
    
    
    private ArrayList< Integer > rookMoves()
    {
        ArrayList< Integer > rml = new ArrayList<>();
 
        int temp = pos;        

        //Up position within board bounds
        while((temp - 5) >= 0)
        {
            temp -= 5;
            rml.add(temp);
        }
      
        temp = pos;
        //Down position within board bounds
        while((temp + 5) < 25)
        {
            temp += 5;
            rml.add(temp);
        }
        
        temp = pos;
        //Left position within board bounds
        while((temp % 5) != 0)
        {            
            rml.add(--temp);
        }
                
        temp = pos;
        //Right position within board bounds
        while((temp % 5) != 4)
        {            
            rml.add(++temp);
        }

        return rml;
    }//end RookMoves
    
    
}//end Class PawnRook

