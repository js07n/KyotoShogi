package kyotoshogi;


import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author JAMESSILVERA
 */
public class Board extends JPanel{
       
    ArrayList< JPanel > board;
    ArrayList< BasePiece > pieceList;
    ArrayList< BasePiece >[] reserveList;
    
    final static int PIECE_SIZE = 10;
    
    final static Color HIGHLGT_COLOR = Color.cyan.darker();
    final static Color UNHIGHLGT_COLOR = Color.GRAY;
       
    public Board()
    {
        super();
        setLayout(new GridLayout(5, 5));

        board = new ArrayList<>();
        
        pieceList = new ArrayList<>(PIECE_SIZE);

        reserveList = (ArrayList< BasePiece >[]) new ArrayList[2];
        reserveList[0] = new ArrayList<>();
        reserveList[1] = new ArrayList<>();
        
        MMHandler handler = new MMHandler();
                        
        for(int i = 0; i < 25; i++)
        {

            board.add(new JPanel());
    
            board.get(i).setBackground(UNHIGHLGT_COLOR);
                
            board.get(i).setBorder(
                    BorderFactory.createLineBorder(Color.WHITE));
         
            board.get(i).setLayout(new GridBagLayout());
            
            board.get(i).setName(Integer.toString(i));
            
            board.get(i).addMouseMotionListener(handler);
            board.get(i).addMouseListener(handler);
            
            add(board.get(i));                            
        }// end for-loop   
        
        createPieces();
        setup();

    }// end Constructor
    
    
    private void createPieces()
    {
        pieceList.add(new PawnRook(0, 0));
        pieceList.add(new PawnRook(1, 24));
        pieceList.add(new GoldGeneralKnight(0, 1));
        pieceList.add(new GoldGeneralKnight(1, 23));
        pieceList.add(new King(0, 2));        
        pieceList.add(new King(1, 22));
        pieceList.add(new SilverGeneralBishop(0, 3));
        pieceList.add(new SilverGeneralBishop(1, 21));
        pieceList.add(new TokinLance(0, 4));
        pieceList.add(new TokinLance(1, 20));
    } //end createPieces Method
    
    
    private class MMHandler extends MouseAdapter
    {
        JPanel lastEntered;
        boolean dragInProgress;

        public void mouseClicked(MouseEvent e)
        {
            if(!dragInProgress && lastEntered.getComponentCount() != 0)
                highlight(true, (BasePiece) lastEntered.getComponent(0));

        }//end MouseClicked
         
        public void mouseEntered(MouseEvent e)
        {
            lastEntered = (JPanel) e.getComponent();
            
            if(!dragInProgress && lastEntered.getComponentCount() != 0)
                highlight(true, (BasePiece) lastEntered.getComponent(0));
            
        }//end MouseEntered
        
        public void mouseExited(MouseEvent e)
        {
            if(lastEntered.getComponentCount() != 0)
                highlight(false, (BasePiece) lastEntered.getComponent(0));
        }// end MouseExited       
        
        public void mouseDragged(MouseEvent e)
        {
            dragInProgress = true;
        }
        
        public void mouseReleased(MouseEvent e)
        {
            dragInProgress = false;
            
            JPanel src = (JPanel) e.getComponent();
            
            //Can only drag something in the JPanel if there is something to
            //drag
            if(src.getComponentCount() != 0 )
            {
                BasePiece bp = (BasePiece) src.getComponent(0);

                //Piece can only be moved if it belongs to the player
                // who's currently allowed to move a piece
                if(bp.getOwner() == getPlayerTurn())
                {
                    ArrayList< Integer > ml = refineMoveList(bp);
                    if(ml.contains(Integer.parseInt(lastEntered.getName())))
                    {
                        bp.setPos(Integer.parseInt(lastEntered.getName()));
                        lastEntered.add(src.getComponent(0));

                        //Capture
                        if(lastEntered.getComponentCount() > 1)
                            capture((BasePiece) lastEntered.getComponent(0));
                       
                        src.repaint();
                        
                        //change player turn only if a piece has been moved
                        changePlayerTurn();
                    }// end if contains

                    //This creates smoother gameplay incase game over
                    // was reached in previous if statement
                    if(lastEntered.getComponentCount() > 0)
                        highlight(true, (BasePiece)lastEntered.getComponent(0));

                }//end if bp.getowner == playerturn
            }// end if src.getComponentCout() != 0
        }//end MouseReleased
        
    }//end MMHandler
    
    
    
    private int getPlayerTurn()
    {
        return ((GameFrame) SwingUtilities.getRoot(this)).getPlayerTurn();
    }
    
    
    
    private void changePlayerTurn()
    {
        ((GameFrame) SwingUtilities.getRoot(this))
                .setPlayerTurn((((GameFrame) SwingUtilities.getRoot(this))
                    .getPlayerTurn()+1)%2);
    }
    
    
    
    //Set pieces on board panels 
    //Sets pieces to their starting position
    private void setup()
    {
        for(int i = 0; i < pieceList.size(); i++)
        {
            //The Position assigned to each Piece will determine which
            //board panel it will be placed on
            board.get(pieceList.get(i).getPos()).add(pieceList.get(i));
        }    
    }//end setup Method
    
    
    
    //This Method returns a refined move list
    //It removes potential move spaces that are occupied by friendly pieces.
    private ArrayList< Integer > refineMoveList(BasePiece bp)
    {
        ArrayList< Integer > ml = new ArrayList<>();
        if(!bp.getMoveList().isEmpty())
        {            
            for(Integer i : bp.getMoveList())
            {
                //if space is occupied..
                if(board.get(i).getComponentCount() != 0)
                {
                    //if piece is occupied by enemy..
                    if(bp.getOwner() != 
                         ((BasePiece) board.get(i).getComponent(0)).getOwner())
                    {
                        ml.add(i);//make it a potential move place
                    }                            
                }
                else//space is empty, keep it as a potential move
                {
                    ml.add(i);
                }
            }//end For Loop
        }//end if !bp.movelist.isEmpty()
    
        return ml;
    }//end refineMoveList Method
    

    //This Method highlights and unhighlights board spaces
    // int mode == true, highlight
    // int mode == false, un-highlight
    private void highlight(boolean mode, BasePiece bp)
    {        
        ArrayList< Integer > rml = refineMoveList(bp);
        
        Color colore = (mode) ? HIGHLGT_COLOR : UNHIGHLGT_COLOR;
                        
        for(Integer i : rml)
        {
            board.get(i).setBackground(colore);
            board.get(i).repaint();
        }
        
    }//end highlight method
    
        
    
    private void capture(BasePiece cp)
    {        
        if(!(cp instanceof King))
        {            
            //Change captured piece's owner
            cp.setOwner((cp.getOwner() == 0) ? 1 : 0);                    
            
            //add captured piece to new owner's reserve list(drop list)
            reserveList[cp.getOwner()].add(cp);
            
            //unhighlight the captured pieces movement paths
            highlight(false, cp);
            
            //remove captured Piece from board
            board.get(cp.getPos()).remove(0);        
            
            //update the Droplist board
            ((GameFrame) SwingUtilities.getRoot(this)).updateDPL(cp);

        }//end if
        else //King CAPTURED GAME OVER
        {
            board.get(cp.getPos()).remove(0); //remove King piece from board
            gameOver();            
        }
                
    }//end capture            
    
    
   
    public boolean dropPiece(int x, int y, DropPanel.DropLabel dl, int dropList)
    {
        //Scan through each board space to see if the drop piece was released in
        // the screen dimensions that belong to one of them.
        for(int i = 0; i < board.size(); i++)
        {
            //Look for JPanel on board that is at those screen coordinates
            Point dimen = board.get(i).getLocationOnScreen();
            if(new Rectangle(dimen.x, dimen.y, board.get(i).getWidth(), 
                    board.get(i).getHeight()).contains(x, y))
            {
                //Check to see if board space is empty
                if(board.get(i).getComponentCount() == 0)
                {
                    //Since it's board space is empty, grab piece from 
                    //reserve list that relates to the dropped piece
                    //and put it on the board space
                    for(int z = 0; z < reserveList[dropList].size(); z++)
                    {

                        //Make sure piece is the correct type 
                        //and is owned by the right person
                        if(reserveList[dropList].get(z).getClass().toString()
                                .equals(dl.getPieceType())
                                && (reserveList[dropList].get(z).getOwner()  
                                        == getPlayerTurn()))
                        {
                            reserveList[dropList].get(z)
                                    .setPos(Integer.parseInt(board.get(i)
                                    .getName()));
                            
                            //add piece to board from reserve list
                            board.get(i).add(reserveList[dropList].get(z));
                            
                            //Set proper promotion status 
                            //- Determined by class DropPanel & double clicks
                            ((BasePiece)board.get(i).getComponent(0))
                                    .setPromotion(dl.isPromoted());

                            reserveList[dropList].remove(z);
                            
                            board.get(i).repaint();

                            changePlayerTurn();
                            return true; //Piece can be succesfully dropped
                        }//end if statement for matching piece type
                    
                        
                    }//end for loop
                }//end if componentcount == 0
                
                //Piece cannot be dropped off at released coordinates
                return false; 
            }// end if coordinates belong inside a board space
        }//end for loop
        
        return false;
    }//end dropPiece Method
    
    
    
    private void gameOver()
    {        
        int n = JOptionPane.showConfirmDialog(
            this,
            "PLAYER " + (getPlayerTurn()+1) + " WINS!\n" 
            + "Would you like to play again?",
            "GAME OVER",
            JOptionPane.YES_NO_OPTION);                
        
        if(n == 0)
            restart();      
        else  
            System.exit(0);        
    
    }//end game over

    
    
    private void restart()
    {
        for(int i = 0; i < board.size(); i++)
        {
            while(board.get(i).getComponentCount() != 0)
            {
                board.get(i).remove(0);                
            }//end while loop
        }//end for loop

        //remove pieces from list
        for(int i = 0; i < PIECE_SIZE; i++)
        {
            pieceList.remove(0);            
        }

        //remove pieces from both reservelists
        while(!reserveList[0].isEmpty())
        {
            reserveList[0].remove(0);            
        }        
        
        while(!reserveList[1].isEmpty())
        { 
            reserveList[1].remove(0);
        }
        
        //create new pieces & set them properly on board
        createPieces();
        setup();

        resetDropLists();
    
        //make sure game starts on player 1's turn
        if(getPlayerTurn() == 0)
        {            
            changePlayerTurn();
        }
   
    }//end restart Method
    
    
       
    private void resetDropLists()
    {
        ((GameFrame) SwingUtilities.getRoot(this)).resetDropLists();
    }
  
}//end Class Board
