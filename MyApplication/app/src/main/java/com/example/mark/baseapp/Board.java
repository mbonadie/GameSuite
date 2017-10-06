package com.example.mark.baseapp;

import java.util.Vector;

/**
 * Name:
 *
 *      Board - Class controlling generic features of a board
 *
 * Description:
 *
 *      Keeps track of pieces on the board in an array, board.
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class Board {

    // Available moves list on the board for a selected location
    public Vector<MoveObj> moveSet2 = new Vector<>();

    // Keeps track of the size of the board, max ROW and COL
    private int ROW, COL;

    // Reflects the GUI board, and is filled with Piece objects
    private Piece [][] board;

    /**
     * Name:
     *
     *      Board - Constructor for new board of Piece objects
     *
     * Synopsis:
     *
     *      public Board(int a_row, int a_col)
     *
     *          a_col --> The max column of the board
     *          a_row --> The max row of the board
     *
     * Description:
     *
     *      This creates a an array of Piece objects, setting them all to empty
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Board(int a_row, int a_col)
    {
        this.ROW = a_row;
        this.COL = a_col;
        board = new Piece[ROW][COL];

        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                Piece temp = new Piece(true);
                this.setBoard(r,c, temp);
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

    /**
     * Name:
     *
     *      getBoard - returns the board
     *
     * Synopsis:
     *
     *      public Piece [][] getBoard()
     *
     * Description:
     *
     *      Give the calling function the entire board and its pieces
     *
     * Returns:
     *
     *      The entire board array and all its associated pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Piece [][] getBoard() {
        return board;
    }


}
