/**
 * Name:
 *
 *      Checkers - Class controlling checkers play
 *
 * Description:
 *
 *      Handles the GUI associated with checkers and the logic for game play.
 *      When objects created, buildBoard() is called and setPlayersTurn().
 *
 */

package com.example.mark.baseapp;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Checkers extends AppCompatActivity {

    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    private static final int PLAYER_ONE = 0;
    private static final int PLAYER_TWO = 1;

    // Used to keep track of on screen buttons
    Button boardButtons[][] = new Button[NUM_ROWS][NUM_COLS];

    // Creates a new Game object to store all board related properties
    Game mainBoard = new Game(64, 2);

    // Move to be executed if valid
    MoveObj selectMove;

    // Lets functions know whether a piece has been selected to be moved
    boolean moving = false;

    // Tells functions if the last move was a jump and part of a chain
    boolean lastMoveJump = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);

        buildBoard();
        setPlayersTurn();
    }

    /**
     *
     * Name:
     *
     *      showToast - Displays on screen messages
     *
     * Synopsis:
     *
     *      private void showToast(CharSequence a_msg);
     *
     *          a_msg   --> The message to be displayed
     *
     * Description:
     *
     *      This function is used to display a_msg on the android device.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *
     *      Mark Bonadies
     *
     * Date:
     *
     *      12:30PM 3/20/2017
     */
    private void showToast(CharSequence a_msg){
        Toast.makeText(this, a_msg, Toast.LENGTH_SHORT).show();
    }

    /**/
    /**
     buildBoard() buildBoard()

    NAME

        buildBoard - Builds the GUI for the checkers board

    SYNOPSIS

        buildBoard()

    Description

        This function creates the board buttons for checkers GUI and places the players pieces on the board.
        It handles what happens when a player selects a piece and a move location.
        It calls the function, hasMove, to fill the available moves list,
        moveSet2, and checks to see if the player is selecting an available move.

    RETURNS

        Nothing

    AUTHOR

        Mark Bonadies

     */
    /**/
    private void buildBoard() {
        ConstraintLayout device = (ConstraintLayout) findViewById(R.id.checkersBoard);

        TableLayout board = new TableLayout(this);

        device.addView(board);

        TableRow newRow1 = new TableRow(this);

        board.addView(newRow1);
        //Creates the numbering for the columns
        for (int col = 0; col != NUM_COLS; col++) {
            Button newButtonLabel = new Button(this);

            newButtonLabel.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));
            newRow1.addView(newButtonLabel);

            //Number buttons for each column
            newButtonLabel.setText(Integer.toString(col+1));
            newButtonLabel.setTextColor(Color.BLACK);
            newButtonLabel.setBackgroundColor(0x00000000);
        }


        //Sets up Game Over button for use after a player wins
        final Button end = (Button) findViewById(R.id.btnGameOver);

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        end.setVisibility(View.INVISIBLE);


        boolean color = true;
        //Used to dynamically create buttons
        for (int row = 0; row != NUM_ROWS; row++) {
            TableRow newRow = new TableRow(this);
            color = !color;

            board.addView(newRow);
            for (int col = 0; col != NUM_COLS; col++) {
                Button newButton = new Button(this);

                newButton.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                newRow.addView(newButton);

                //Creating a button on valid spaces and coloring the board in correctly
                if(color){
                    newButton.setBackgroundColor(Color.RED);
                    color = !color;
                }
                else {
                    newButton.setTextColor(Color.WHITE);
                    newButton.setBackgroundColor(Color.BLACK);
                    color = !color;
                    if (row < 3)
                    {
                        newButton.setText("BLACK");
                        mainBoard.board.getLocation(row,col).setNotEmpty();
                        mainBoard.board.getLocation(row,col).setPlayersPiece(PLAYER_ONE);
                        mainBoard.board.getLocation(row,col).setTileColor(PLAYER_ONE);
                    }

                    if(row > 4)
                    {
                        newButton.setText("RED");
                        mainBoard.board.getLocation(row,col).setNotEmpty();
                        mainBoard.board.getLocation(row,col).setPlayersPiece(PLAYER_TWO);
                        mainBoard.board.getLocation(row,col).setTileColor(PLAYER_TWO);
                    }

                    final int Final_Row = row;
                    final int Final_Col = col;
                    newButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Pair<Integer,Integer> currentPos = new Pair<>(Final_Row, Final_Col);

                            //Selects the piece to be moved and checks if it has an available move
                            if (!moving && mainBoard.board.getLocation(Final_Row,Final_Col).getPlayersPiece() == mainBoard.currentPlayer && hasMove(mainBoard, currentPos))
                            {
                                moving = true;
                                boardButtons[Final_Row][Final_Col].setTextColor(Color.YELLOW);
                            }
                            //Moves the piece to the selected location and deals with a jumped piece
                            else if (moving && (selectMove = moveAvailable(currentPos)) != null)
                            {
                                //Checks for possible multi-jump situation
                                if(selectMove.jumpedOpponent == true)
                                {
                                    removeJumped(mainBoard, selectMove);
                                    //After each jumped opponent check to see if that player has won.
                                    if (checkWin())
                                    {
                                        setPlayerWin();
                                        setBoardInactive();
                                        end.setVisibility(View.VISIBLE);
                                    }
                                    movePiece(selectMove);
                                    mainBoard.board.moveSet2.clear();

                                    if(hasMove(mainBoard, currentPos) && hasJumpMove())
                                    {
                                        lastMoveJump = true;
                                        boardButtons[Final_Row][Final_Col].setTextColor(Color.YELLOW);
                                    }else
                                    {
                                        moving = false;
                                        lastMoveJump = false;
                                        mainBoard.board.moveSet2.clear();
                                        mainBoard.currentPlayer = mainBoard.getNextPlayer();
                                        if (mainBoard.active)
                                        {
                                            setPlayersTurn();
                                        }
                                    }

                                }
                                //Changes players turn if the move their move is over.
                                else
                                {
                                    if(lastMoveJump)
                                    {
                                        showToast("Not Valid");
                                    }
                                    else
                                    {
                                        lastMoveJump = false;
                                        moving = false;
                                        movePiece(selectMove);
                                        mainBoard.board.moveSet2.clear();
                                        mainBoard.currentPlayer = mainBoard.getNextPlayer();
                                        if (mainBoard.active)
                                        {
                                            setPlayersTurn();
                                        }
                                    }
                                }
                            }
                            else
                            {
                                showToast("Not Valid");
                            }

                        }
                    });
                }
                boardButtons[row][col] = newButton;
            }
        }
    }

    /**
     *
     * Name:
     *
     *      setPlayerWin - Displays winner
     *
     * Synopsis:
     *
     *      setPlayerWin()
     *
     * Description:
     *
     *      Updates the text on the android device to display the winner under the board view.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     */
    private void setPlayerWin() {
        TextView playersTurn = (TextView) findViewById(R.id.turn);

        if (mainBoard.currentPlayer == 0)
        {
            playersTurn.setText( mainBoard.playerOne.getName() + " (BLACK) WINS!");
        }
        else
        {
            playersTurn.setText(mainBoard.playerTwo.getName() + " (RED) WINS!");
        }

    }

    /**
     * Name:
     *
     *      setPlayersTurn - handles text on GUI
     *
     * Synopsis:
     *
     *      private void setPlayersTurn()
     *
     * Description:
     *
     *      When it is called, it looks at the current player and sets the text under the
     *      checkers board with the appropriate players information
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private void setPlayersTurn() {
        TextView playersTurn = (TextView) findViewById(R.id.turn);

        if (mainBoard.currentPlayer == 0)
        {
            playersTurn.setText("Players Turn: " + mainBoard.playerOne.getName() + " (BLACK)");
        }
        else
        {
            playersTurn.setText("Players Turn: " + mainBoard.playerTwo.getName() + " (RED)");
        }

    }

    /**
     * Name:
     *
     *      removeJumped - removes jumped piece from GUI and board
     *
     * Synopsis:
     *
     *      private void removeJumped(Game a_main, MoveObj a_selectedMove)
     *
     *          a_main --> The Object that contains the game board, board
     *          a_selectedMove --> MoveObj containing all information about the move
     *
     * Description:
     *
     *      Removes the jumped opponents piece from the GUI and resets the jumped board location.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private void removeJumped(Game a_main, MoveObj a_selectedMove)
    {
        if(a_selectedMove.jumpedOpponent)
        {
            boardButtons[a_selectedMove.jumpedPiece.first][a_selectedMove.jumpedPiece.second].setText("");
            a_main.board.getLocation(a_selectedMove.jumpedPiece.first, a_selectedMove.jumpedPiece.second).reset();
        }
    }

    /**
     * Name:
     *
     *      moveAvailable - Checks for available moves and adds them to moveSet2
     *
     * Synopsis:
     *
     *      private MoveObj moveAvailable(Pair<Integer, Integer> a_currentPos)
     *
     *          a_currentPos --> a pair containing the row and column of the user selected move
     *
     * Description:
     *
     *      Given the selected position, a_currentPos, the function looks through available
     *      moves in moveSet2 and if the selected location is one of the MoveObj endRowCol
     *
     * Returns:
     *
     *      MoveObj --> If move exists in moveSet2 it returns the complete move
     *      null --> If no such move exists it returns null
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private MoveObj moveAvailable(Pair<Integer, Integer> a_currentPos)
    {
        for (MoveObj move : mainBoard.board.moveSet2)
        {
            if (move.endRowCol.first == a_currentPos.first && move.endRowCol.second == a_currentPos.second)
            {
                return move;
            }
        }
        return null;
    }

    /**
     * Name:
     *
     *      hasMove - Checks for available moves and adds them to moveSet2
     *
     * Synopsis:
     *
     *      private boolean hasMove(Game a_main, Pair<Integer, Integer> a_currentPos)
     *
     *          a_main --> The Object that contains the game board, board
     *          a_currentPos --> a pair containing the row and column to be inspected
     *
     * Description:
     *
     *      Given the selected position, a_currentPos, the function looks through available
     *      moves, if any exists, and adds them to moveSet2. It also updates a boolean variable,
     *      canMove, if moves exist.
     *
     * Returns:
     *
     *      true --> If there exists any moves
     *      false --> If no move exists
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private boolean hasMove(Game a_main, Pair<Integer, Integer> a_currentPos) {
        boolean canMove = false;

        if (a_main.currentPlayer == PLAYER_ONE || a_main.board.getLocation(a_currentPos.first, a_currentPos.second).isKing())
        {
            ///Move position 3
            /*
            * move3 is down one and left one on the board
            */
            if ((a_currentPos.first + 1) < NUM_ROWS && (a_currentPos.second - 1) >= 0)
            {
                if (a_main.board.getLocation(a_currentPos.first + 1, a_currentPos.second - 1).getEmpty())
                {
                    canMove = true;
                    MoveObj move3 = new MoveObj(a_currentPos.first, a_currentPos.second);
                    move3.setEndRowCol(a_currentPos.first + 1, a_currentPos.second - 1);
                    a_main.board.moveSet2.add(move3);
                }
                //Move position 7
                /*
                * move7 is down two and left two on the board
                */
                else if (a_main.board.getLocation(a_currentPos.first + 1, a_currentPos.second - 1).getPlayersPiece() == a_main.getNextPlayer())
                {
                    if ((a_currentPos.first + 2) < NUM_ROWS && (a_currentPos.second - 2) >= 0) {
                        if (a_main.board.getLocation(a_currentPos.first + 2, a_currentPos.second - 2).getEmpty()) {
                            canMove = true;
                            MoveObj move7 = new MoveObj(a_currentPos.first, a_currentPos.second);
                            move7.setJumpedPiece(a_currentPos.first + 1, a_currentPos.second - 1);
                            move7.setEndRowCol(a_currentPos.first + 2, a_currentPos.second - 2);
                            a_main.board.moveSet2.add(move7);
                        }
                    }
                }
            }

            //Move position 4
            /*
            * move4 is down one and right one on the board
            */
            if ((a_currentPos.first + 1) < NUM_ROWS && (a_currentPos.second + 1) < NUM_COLS)
            {
                if (a_main.board.getLocation(a_currentPos.first + 1, a_currentPos.second + 1).getEmpty())
                {
                    canMove = true;
                    MoveObj move4 = new MoveObj(a_currentPos.first, a_currentPos.second);
                    move4.setEndRowCol(a_currentPos.first + 1, a_currentPos.second + 1);
                    a_main.board.moveSet2.add(move4);
                }
                else if (a_main.board.getLocation(a_currentPos.first + 1, a_currentPos.second + 1).getPlayersPiece() == a_main.getNextPlayer())
                {
                    //Move position 8
                    /*
                    * move8 is down two and right two on the board
                    */
                    if ((a_currentPos.first + 2) < NUM_ROWS && (a_currentPos.second + 2) < NUM_COLS) {
                        if (a_main.board.getLocation(a_currentPos.first + 2, a_currentPos.second + 2).getEmpty()) {
                            canMove = true;
                            MoveObj move8 = new MoveObj(a_currentPos.first, a_currentPos.second);
                            move8.setJumpedPiece(a_currentPos.first + 1, a_currentPos.second + 1);
                            move8.setEndRowCol(a_currentPos.first + 2, a_currentPos.second + 2);
                            a_main.board.moveSet2.add(move8);
                        }
                    }
                }
            }
        }
        if (a_main.currentPlayer == PLAYER_TWO || a_main.board.getLocation(a_currentPos.first, a_currentPos.second).isKing())
        {
            //Move position 1
            /*
            * move1 is up one and right one on the board
            */
            if ((a_currentPos.first - 1) >= 0 && (a_currentPos.second + 1) < NUM_COLS)
            {
                if (a_main.board.getLocation(a_currentPos.first - 1, a_currentPos.second + 1).getEmpty())
                {
                    canMove = true;
                    MoveObj move1 = new MoveObj(a_currentPos.first, a_currentPos.second);
                    move1.setEndRowCol(a_currentPos.first - 1, a_currentPos.second + 1);
                    a_main.board.moveSet2.add(move1);
                }
                else if (a_main.board.getLocation(a_currentPos.first - 1, a_currentPos.second + 1).getPlayersPiece() == a_main.getNextPlayer())
                {
                    //Move position 5
                    /*
                    * move5 is up two and right two on the board
                    */
                    if ((a_currentPos.first - 2) >= 0 && (a_currentPos.second + 2) < NUM_COLS) {
                        if (a_main.board.getLocation(a_currentPos.first - 2, a_currentPos.second + 2).getEmpty()) {
                            canMove = true;
                            MoveObj move5 = new MoveObj(a_currentPos.first, a_currentPos.second);
                            move5.setJumpedPiece(a_currentPos.first - 1, a_currentPos.second + 1);
                            move5.setEndRowCol(a_currentPos.first - 2, a_currentPos.second + 2);
                            a_main.board.moveSet2.add(move5);
                        }
                    }
                }
            }
            //Move position 2
            /*
            * move2 is up one and left one on the board
            */
            if ((a_currentPos.first - 1) >= 0 && (a_currentPos.second - 1) >= 0)
            {
                if (a_main.board.getLocation(a_currentPos.first - 1, a_currentPos.second - 1).getEmpty())
                {
                    canMove = true;
                    MoveObj move2 = new MoveObj(a_currentPos.first, a_currentPos.second);
                    move2.setEndRowCol(a_currentPos.first - 1, a_currentPos.second - 1);
                    a_main.board.moveSet2.add(move2);
                }
                else if (a_main.board.getLocation(a_currentPos.first - 1, a_currentPos.second - 1).getPlayersPiece() == a_main.getNextPlayer())
                {
                    //Move position 6
                    /*
                    * move6 is up two and left two on the board
                    */
                    if ((a_currentPos.first - 2) >= 0 && (a_currentPos.second - 2) >= 0) {
                        if (a_main.board.getLocation(a_currentPos.first - 2, a_currentPos.second - 2).getEmpty()) {
                            canMove = true;
                            MoveObj move6 = new MoveObj(a_currentPos.first, a_currentPos.second);
                            move6.setJumpedPiece(a_currentPos.first - 1, a_currentPos.second - 1);
                            move6.setEndRowCol(a_currentPos.first - 2, a_currentPos.second - 2);
                            a_main.board.moveSet2.add(move6);
                        }
                    }
                }
            }
        }

        return canMove;
    }

    /**
     * Name:
     *
     *      hasJumpMove - Checks for a win vertically and horizontally
     *
     * Synopsis:
     *
     *      private boolean hasJumpMove()
     *
     * Description:
     *
     *      Checks list of moves, moveSet2, generated by the function hasMove and determines if any of the
     *      moves involve jumping the opponents piece.
     *
     * Returns:
     *
     *      true --> If there exists a move that involves a jump
     *      false --> If no jump move exists in moveSet2
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private boolean hasJumpMove()
    {
        for (MoveObj move : mainBoard.board.moveSet2)
        {
            if (move.jumpedOpponent)
            {
                return true;
            }
        }
        return false;
    }

    /**/
    /**
     *  movePiece(MoveObj a_selectedMove) movePiece(MoveObj a_selectedMove)
     *
     *  NAME
     *
     *      movePiece - Updated the board pieces changed by the move
     *
     *  SYNOPSIS
     *
     *      private void movePiece(MoveObj a_selectedMove)
     *
     *          a_selectedMove --> Move that is to be carried out
     *
     *  Description
     *
     *      The function updates the boards physical appearance to reflect the move made. This is
     *      limited to the start and end point of the piece during the move. It also updates a
     *      kinged piece both visually and by changing Piece's boolean value to true.
     *
     *  RETURNS
     *
     *      NOTHING
     *
     *  AUTHOR
     *      Mark Bonadies
     */
    /**/
    private void movePiece(MoveObj a_selectedMove)
    {
        mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).setNotEmpty();
        mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).setPlayersPiece(mainBoard.currentPlayer);
        mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).setTileColor(mainBoard.currentPlayer);
        if (mainBoard.currentPlayer == 0) {
            if (mainBoard.board.getLocation(a_selectedMove.startRowCol.first, a_selectedMove.startRowCol.second).isKing() || a_selectedMove.endRowCol.first >= 7) {
                mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).kinged();
            }
        }
        else
        {
            if (mainBoard.board.getLocation(a_selectedMove.startRowCol.first, a_selectedMove.startRowCol.second).isKing() || a_selectedMove.endRowCol.first <= 0) {
                mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).kinged();
            }
        }

        //Remove properties from location that no longer has the piece
        boardButtons[a_selectedMove.startRowCol.first][a_selectedMove.startRowCol.second].setText("");
        mainBoard.board.getLocation(a_selectedMove.startRowCol.first, a_selectedMove.startRowCol.second).reset();

        //Set the pieces properties for the GUI
        if(mainBoard.currentPlayer == 0)
        {
            boardButtons[a_selectedMove.endRowCol.first][a_selectedMove.endRowCol.second].setTextColor(Color.WHITE);
            if(mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).isKing())
            {
                boardButtons[a_selectedMove.endRowCol.first][a_selectedMove.endRowCol.second].setText("(BLACK)");
            }
            else
            {
                boardButtons[a_selectedMove.endRowCol.first][a_selectedMove.endRowCol.second].setText("BLACK");
            }

        }
        else
        {
            boardButtons[a_selectedMove.endRowCol.first][a_selectedMove.endRowCol.second].setTextColor(Color.WHITE);
            if(mainBoard.board.getLocation(a_selectedMove.endRowCol.first, a_selectedMove.endRowCol.second).isKing())
            {
                boardButtons[a_selectedMove.endRowCol.first][a_selectedMove.endRowCol.second].setText("(RED)");
            }
            else
            {
                boardButtons[a_selectedMove.endRowCol.first][a_selectedMove.endRowCol.second].setText("RED");
            }

        }
    }

    /**/
    /**

    NAME

        checkWin - Checks if current player has won

    SYNOPSIS

        private boolean checkWin();

    Description

        The function looks at the board, counting active opponent pieces, and if the total count of
        opponent pieces is equal to zero the player has won.

    RETURNS

        true - if current player has won
        false - current player hasn't won

    AUTHOR

        Mark Bonadies

    */
    /**/
    private boolean checkWin()
    {
        int opponentCount = 0;
        for(Piece[] piece1 : mainBoard.board.getBoard())
        {
            for(Piece piece2 : piece1)
            {
                if (piece2.getPlayersPiece() == mainBoard.getNextPlayer())
                {
                    opponentCount++;
                }
            }
        }
        if ( opponentCount == 0)
        {
            return true;
        }
        return false;
    }

    /**/
    /**

    NAME

       setBoardInactive - Disables buttons on board

    SYNOPSIS

       private void setBoardInactive();

    Description

       This function sets the game board inactive by disabling all buttons and setting
       Game mainBoard.active to false.

    RETURNS

       Nothing

    AUTHOR

       Mark Bonadies

    */
    /**/
    private void setBoardInactive() {
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button inactiveButton = boardButtons[row][col];
                inactiveButton.setClickable(false);
            }
        }
        mainBoard.active = false;
    }
}
