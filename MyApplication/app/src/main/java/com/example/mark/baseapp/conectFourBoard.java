package com.example.mark.baseapp;

/**
 * Created by Mark on 4/22/2017.
 */

public class conectFourBoard {

    // Predefined size of the board
    final int ROW = 6, COL = 7;

    private Piece [][] board = new Piece[ROW][COL];

    //Places left in the board
    private int placesLeft = 42;

    /**
     * Name:
     *
     *      conectFourBoard - Constructor for new connect four board
     *
     * Synopsis:
     *
     *      public conectFourBoard()
     *
     * Description:
     *
     *      This creates a an array of Piece objects, setting them all to empty
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public conectFourBoard(){

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                Piece temp = new Piece(true);
                this.setBoard(row,col, temp);
            }
        }

    }

    /**
     * Name:
     *
     *      getPlacesLeft - gives board Pieces empty
     *
     * Synopsis:
     *
     *      public int getPlacesLeft()
     *
     * Description:
     *
     *      Returns variable that maintains the correct amount of empty spaces on the board
     *
     * Return:
     *
     *      An int of how many places are left empty on the board
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int getPlacesLeft() {
        return placesLeft;
    }

    /**
     * Name:
     *
     *      setPlacesLeft - decrements placesLeft
     *
     * Synopsis:
     *
     *      public void setPlacesLeft()
     *
     * Description:
     *
     *      Removes one from the count of placesLeft
     *
     * Return:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setPlacesLeft() {
        this.placesLeft--;
    }

    /**
     * Name:
     *
     *      getBoardIntArr - creates an integer array of the board
     *
     * Synopsis:
     *
     *      public int[][] getBoardIntArr()
     *
     * Description:
     *
     *      Creates and integer array of the board with all the players represented as 0, 1 or -1.
     *      0 and 1 are players and -1 is empty
     *
     * Return:
     *
     *      An integer array of the current board and the pieces in each spot
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int[][] getBoardIntArr(){
        int [][] temp = new int[ROW][COL];
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                temp[row][col] = this.getLocation(row, col).getPlayersPiece();
            }
        }
        return temp;
    }

    /**
     * Name:
     *
     *      resetBoardToState - reverses an operation on the board
     *
     * Synopsis:
     *
     *      public void resetBoardToState(int[][] a_state)
     *
     *          a_state --> An integer array of the board in which to reset to
     *
     * Description:
     *
     *      returns board to a previous state given an older version stored, a_state
     *
     * Return:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void resetBoardToState(int[][] a_state)
    {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                this.board[row][col].setPlayersPiece(a_state[row][col]);
                if (this.board[row][col].getPlayersPiece() == -1)
                {
                    this.board[row][col].setEmpty();
                }
            }
        }
    }

    /**
     * Name:
     *
     *      getLocation - gets location in board
     *
     * Synopsis:
     *
     *      public Piece  getLocation(int a_row, int a_col)
     *
     *          a_row --> row of desired location
     *          a_col --> column of desired location
     *
     * Description:
     *
     *      Gets a Piece from the board using the selected location
     *
     * Returns:
     *
     *      Piece in the a_row and a_col of the board.
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Piece  getLocation(int a_row, int a_col) {
        return board[a_row][a_col];
    }

    /**
     * Name:
     *
     *      setBoard - assigns piece to a location in board
     *
     * Synopsis:
     *
     *      public void setBoard(int a_row, int a_col, Piece a_new)
     *
     *          a_row --> row of desired location
     *          a_col --> column of desired location
     *          a_new --> Piece to be assign to the desired location
     *
     * Description:
     *
     *      Sets the a_new Piece to the board at the desired a_row and a_col
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setBoard(int a_row, int a_col, Piece a_new) {
        this.board[a_row][a_col] = a_new;
    }

}
