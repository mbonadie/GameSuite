package com.example.mark.baseapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Name:
 *
 *      ConnectFour - Class dedicated to the control flow of connect four
 *
 * Description:
 *
 *      Fills the GUI with buttons and the logic of what code to execute on a button press.
 *      Controls the updating of the GUI and takes the information stored in conectFourBoard in order
 *      to play the game.
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class ConnectFour extends AppCompatActivity {

    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 7;

    //Used for keeping track of the buttons on the GUI
    Button boardButtons[][] = new Button[NUM_ROWS][NUM_COLS];

    //Keeps track of the pieces on the board and variable related to game play
    Game mainBoard;

    //Class that keeps all the logic for checking selected position
    moveCheckCF moves = new moveCheckCF();

    //Seed random number generator for difficulty level default
    Random rand = new Random();

    //Used to reference lowest position in selected column
    private int lowestRow = 0;

    // Set to 0 if opponent is a computer, 1 if opponent is human
    private int opponentType;

    //Set to adjust difficulty - Implement dropdown if time permits
    private int difficulty = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_four);

        mainBoard = new Game(42, 1);

        //Passed in from ConnectFourType for selected opponent
        Intent i = getIntent();
        opponentType = i.getIntExtra("opp", -1 );

        if (opponentType == 0)
        {
            mainBoard.playerOne.setName("Human");
            mainBoard.playerTwo.setName("Computer");
        }
        else
        {
            mainBoard.playerOne.setName("Blue");
            mainBoard.playerTwo.setName("Red");
        }

        buildBoard();
        setPlayersTurn();

    }

    /**
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
            playersTurn.setText("Players Turn: " + mainBoard.playerOne.getName());
        }
        else
        {
            playersTurn.setText("Players Turn: " + mainBoard.playerTwo.getName());
        }

    }

    /**/
    /**
     buildBoard() buildBoard()

    NAME

        buildBoard - Builds the GUI for the checkers board

    SYNOPSIS

        buildBoard()

    Description

        This function creates the board buttons for connect four GUI and places empty pieces on the board.
        It handles what happens when the player selects a spot to drop their piece.
        When using playing against a computer the function uses functions such as computerMove(difficulty)
        to generate a move that correlates to the level of difficulty. If opponent is human this
        takes in both users inputs and calls functions to update the board for each move.

    RETURNS

        Nothing

    AUTHOR

        Mark Bonadies

     */
    /**/
    private void buildBoard() {
        final TableLayout board = (TableLayout) findViewById(R.id.boardConnect);

        TableRow newRow1 = new TableRow(this);

        board.addView(newRow1);
        //Creates the numbering for the columns
        for (int col = 0; col != NUM_COLS; col++) {
            Button newButton = new Button(this);

            newButton.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));
            newRow1.addView(newButton);

            //Number buttons for each column
            newButton.setText(Integer.toString(col+1));
            newButton.setTextColor(Color.BLACK);
            newButton.setBackgroundColor(0x00000000);
        }

        final Button end = (Button) findViewById(R.id.btnGameOver);

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        end.setVisibility(View.INVISIBLE);

        //Used to dynamically create buttons
        for (int row = 0; row != NUM_ROWS; row++) {
            TableRow newRow = new TableRow(this);

            board.addView(newRow);
            for (int col = 0; col != NUM_COLS; col++) {
                Button newButton = new Button(this);

                newButton.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                newButton.setHeight(135);

                newRow.addView(newButton);
                final int Final_Row = row;
                final int Final_Col = col;
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final TextView playersTurn = (TextView) findViewById(R.id.turn);
                        if (mainBoard.currentPlayer == 0){
                            if (placeTile(Final_Row, Final_Col, mainBoard)){

                                mainBoard.decrementMoveLeft();
                                mainBoard.b.setPlacesLeft();
                                updateButton(lowestRow, Final_Col);

                                if (checkWin(lowestRow, Final_Col, mainBoard)){
                                    playersTurn.setText(mainBoard.playerOne.getName() + " has won!!");
                                    setBoardInactive();
                                    end.setVisibility(View.VISIBLE);
                                    mainBoard.active = false;
                                }else {
                                    mainBoard.currentPlayer = 1;
                                    playersTurn.setText("Players Turn: " + mainBoard.playerTwo.getName());
                                }

                                /**Executed if opponent is computer. This places the computers move
                                 * directly after the human has placed piece.
                                 */
                                if (opponentType == 0 && mainBoard.active)
                                {
                                    //Getting the best move for difficulty level
                                    int bestCol = computerMove(difficulty);

                                    if (placeTile(0, bestCol, mainBoard))
                                    {
                                        mainBoard.decrementMoveLeft();
                                        mainBoard.b.setPlacesLeft();
                                        updateButton(lowestRow, bestCol);
                                        if (checkWin(lowestRow, bestCol, mainBoard)){
                                            playersTurn.setText(mainBoard.playerTwo.getName() + " has won!!");
                                            setBoardInactive();
                                            end.setVisibility(View.VISIBLE);
                                            mainBoard.active = false;
                                        }else {
                                            mainBoard.currentPlayer = 0;
                                            playersTurn.setText("Players Turn: " + mainBoard.playerOne.getName());
                                        }
                                    }

                                }
                                if (mainBoard.getMoveLeft() < 1) {
                                    playersTurn.setText("You have tied.");
                                    StringBuilder b = new StringBuilder();
                                    b.append("No more moves.\n");
                                    b.append("Game Over.\n");
                                    showToast(b);
                                    end.setVisibility(View.VISIBLE);
                                    mainBoard.active = false;
                                }
                            }else showToast("Invalid location");

                        }
                        //If opponent is human this code executes, if not than this section never executes
                        else
                        {

                            if (placeTile(Final_Row, Final_Col, mainBoard)){
                                mainBoard.decrementMoveLeft();
                                mainBoard.b.setPlacesLeft();
                                updateButton(lowestRow, Final_Col);

                                if (checkWin(lowestRow, Final_Col, mainBoard)){
                                    playersTurn.setText(mainBoard.playerTwo.getName() + " has won!!");
                                    setBoardInactive();
                                    end.setVisibility(View.VISIBLE);
                                    mainBoard.active = false;
                                }else {
                                    mainBoard.currentPlayer = 0;
                                    playersTurn.setText("Players Turn: " + mainBoard.playerOne.getName());
                                }
                                if (mainBoard.getMoveLeft() < 1) {
                                    playersTurn.setText("You have tied.");
                                    StringBuilder b = new StringBuilder();
                                    b.append("No more moves.\n");
                                    b.append("Game Over.\n");
                                    showToast(b);
                                    end.setVisibility(View.VISIBLE);
                                    mainBoard.active = false;
                                }

                            }else showToast("Invalid location");
                        }
                    }
                });

                // Adds the button, for GUI, to a board to be referenced later
                boardButtons[row][col] = newButton;

            }
        }
    }

    /**
     * Name:
     *
     *      computerMove - Determines a computers next move
     *
     * Synopsis:
     *
     *      private int computerMove(int a_difficulty)
     *
     *          a_difficulty   --> The selected level of difficulty chosen
     *
     * Description:
     *
     *      This function searches for an adequate move for the computer. This is depending on the
     *      level of difficulty chosen.
     *
     * Returns:
     *
     *      The column with the best move is then returned. If it fails to find a good solution a
     *      random, legal, move is generated.
     *
     * Author:
     *      Mark Bonadies
     */
    private int computerMove(int a_difficulty) {

        // Captures the current state of the board before this function alters it
        int [][] currentBoardState = mainBoard.b.getBoardIntArr();

        // Keeps the current player  to revert to later
        int savedCurrentPlayer = mainBoard.currentPlayer;

        // Used for the default difficulty
        int  n = rand.nextInt(7);

        // The move selected by the algorithm to be returns
        int bestCol = n;

        int prevBestCol = 0;
        int highestMove = 0;
        int prevHighestMove = 0;

        switch (a_difficulty)
        {
            //Determines a logical next move for opponent and picks that as its own move
            case 1:
                mainBoard.currentPlayer = 0;

                for (int col = 0; col < NUM_COLS; col++) {
                    prevBestCol = 0;
                    if (!(placeTile(0, col, mainBoard))) {
                        prevBestCol = 0;
                        continue;
                    }
                    if (checkWin(lowestRow, col, mainBoard)) {
                        bestCol = col;
                    }
                    if (highestMove < Math.max(moves.checkDiagonalMax(lowestRow, col, mainBoard), moves.checkPerpendicularMax(lowestRow, col, mainBoard))) {
                        //prevHighestMove = highestMove;
                        highestMove = Math.max(moves.checkDiagonalMax(lowestRow, col, mainBoard), moves.checkPerpendicularMax(lowestRow, col, mainBoard));
                        prevBestCol = bestCol;
                        bestCol = col;
                    }
                    mainBoard.b.resetBoardToState(currentBoardState);
                }
                break;

            case 2:
                // If time permits add a level of difficulty that actively tries to play a winning move
                break;

            default:
                // Randomly place tiles in accordance to the rules.
                while (!placeTile(0, n, mainBoard))
                {
                    n = rand.nextInt(7);
                }
                bestCol = n;

        }

        mainBoard.b.resetBoardToState(currentBoardState);
        mainBoard.currentPlayer = savedCurrentPlayer;

        return bestCol;

    }

    /**

    NAME

       setBoardInactive - Disables buttons on board

    SYNOPSIS

       private void setBoardInactive();

    Description

       This function sets the game board inactive by disabling all buttons.

    RETURNS

       Nothing

    AUTHOR

       Mark Bonadies

    */
    private void setBoardInactive() {
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button inactiveButton = boardButtons[row][col];
                inactiveButton.setClickable(false);
            }
        }
    }

    /**
     * Name:
     *
     *      updateButton - adds players piece to the GUI
     *
     * Synopsis:
     *
     *      private void updateButton(int a_row,int a_col)
     *
     *          a_row --> row of piece placed
     *          a_col --> column of piece placed
     *
     * Description:
     *
     *      Changes the background color on the piece selected to correspond to the player that
     *      placed it. Then sets that location as not empty.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private void updateButton(int a_row,int a_col) {
        Button updateButton = boardButtons[a_row][a_col];

        if (mainBoard.currentPlayer == 0){
            updateButton.setBackgroundColor(Color.BLUE);
        }
        else{
            updateButton.setBackgroundColor(Color.RED);
        }
        mainBoard.b.getLocation(a_row, a_col).setNotEmpty();
    }

    /**
     * Name:
     *
     *      placeTile - places pieces on the board (not GUI)
     *
     * Synopsis:
     *
     *      private boolean placeTile(int a_Row, int a_Col, Game a_board)
     *
     *          a_Row --> row of piece placed
     *          a_Col --> column of piece placed
     *          a_board --> contains all the other pieces on the current board
     *
     * Description:
     *
     *      The function takes in a selected location and places it on the the board, b, if the piece
     *      is in its lowest natural state. If not it calls dropdown until the piece is at its lowest
     *      position.
     *
     * Returns:
     *
     *      true - if the piece was placed on the board, b, at it lowest position
     *      false - if the selected move is on an opponents piece
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private boolean placeTile(int a_Row, int a_Col, Game a_board) {

        if (moves.checkCurrent(a_Row, a_Col, a_board)) {
            while (!(dropDown(a_Row, a_Col, a_board)))
            {
                a_Row++;
            }
            a_board.b.getLocation(a_Row,a_Col).setPlayersPiece(a_board.currentPlayer);
            return true;
        }else{
            return false;
        }
    }

    /**
    * Name:
    *
    *      checkWin - Checks if current player has won
    *
    * Synopsis:
    *
    *      private boolean checkWin(int a_Row, int a_Col, Game a_board)
    *
    *          a_Row --> row of piece placed
    *          a_Col --> column of piece placed
    *          a_board --> contains all the other pieces on the current board
    *
    * Description:
    *
    *      The function looks at the board and checking to see if the last piece placed created four
    *      connecting pieces. This calls moves.checkDiagonal and moves.checkPerpendicular, which if
    *      either returns true than checkWin also returns true.
    *
    * Returns:
    *
    *      true - if current player has won
    *      false - current player hasn't won
    *
    * Author:
    *      Mark Bonadies
     *
    */
    private boolean checkWin(int a_Row, int a_Col, Game a_board){
        if (moves.checkDiagonal(a_Row, a_Col,a_board) || moves.checkPerpendicular(a_Row,a_Col,a_board)){
            return true;
        }
        return false;
    }

    /**
    * Name:
    *
    *      dropDown - checks below selected piece
    *
    * Synopsis:
    *
    *      private boolean dropDown(int r, int c, Game a_board)
    *
    *          a_row --> row of piece placed
    *          a_col --> column of piece placed
    *          a_board --> contains all the other pieces on the current board
    *
    * Description:
    *
    *      The function checks the board to see if the selected location is the lowest location.
    *      It updates a global variable, lowestRow, to reflect a lower position. It is up to calling
    *      function call until this function returns false.
    *
    * Returns:
    *
    *      true - if the position below is empty
    *      false - if the piece is in its lowest possible position
    *
    * Author:
    *      Mark Bonadies
    *
    */
    private boolean dropDown(int a_row, int a_col, Game a_board) {
        if(a_row == 5){
            lowestRow = a_row;
            return true;
        }
        else if (!a_board.b.getLocation(a_row +1, a_col).getEmpty()){
            lowestRow = a_row;
            return true;
        }
        return false;
    }


}


