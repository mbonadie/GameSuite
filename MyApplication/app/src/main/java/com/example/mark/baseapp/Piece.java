package com.example.mark.baseapp;

import android.util.Pair;

/**
 * Name:
 *
 *      Piece - Game piece information
 *
 * Description:
 *
 *      Keeps information about the piece for multiple gamed types
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class Piece {

    private Pair tileLocation;
    private String tileColor;
    private int playersPiece = -1;

    // Used to identify a King in checkers
    private boolean king = false;

    // Used to as a place holder when empty is true
    private boolean empty = true;

    /**
     * Name:
     *
     *      Piece - Constructor for Piece object
     *
     * Synopsis:
     *
     *      public Piece(boolean a_empty)
     *
     *          a_empty --> the state of the piece you create
     *
     * Description:
     *
     *      Creates a Pieces object and sets it to either empty or not empty depending on a_empty
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Piece(boolean a_empty){
        this.empty = a_empty;
    }

    /**
     * Name:
     *
     *      Piece - Constructor for Piece object
     *
     * Synopsis:
     *
     *      public Piece(int a_row, int a_col)
     *
     *          a_row --> the row in which the piece is located
     *          a_col --> the column where the piece is located
     *
     * Description:
     *
     *      Creates an empty Pieces object and sets its location
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Piece(int a_row, int a_col)
    {
        this.tileLocation = Pair.create(a_row,a_col);
    }

    /**
     * Name:
     *
     *      setTileColor - set tile color identifier
     *
     * Synopsis:
     *
     *      void setTileColor(int a_player)
     *
     *          a_player --> the current players id
     *
     * Description:
     *
     *      Sets the string, tileColor, with the appropriate identifier for that player.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    void setTileColor(int a_player)
    {
        if (a_player == 0)
        {
            this.tileColor = "BLACK";
        }
        else if (a_player == 1)
        {
            this.tileColor = "RED";
        }
        else
        {
            this.tileColor = null;
        }
    }

    /**
     * Name:
     *
     *      getEmpty - gets empty state
     *
     * Synopsis:
     *
     *      public void getEmpty()
     *
     * Description:
     *
     *      To tell the calling function the state of the piece
     *
     * Returns:
     *
     *      true --> if the piece is empty
     *      false --> if the piece is occupied
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public boolean getEmpty() {
        return empty;
    }

    /**
     * Name:
     *
     *      setEmpty - alters empty flag
     *
     * Synopsis:
     *
     *      public void setEmpty()
     *
     * Description:
     *
     *      Sets the empty flag to true
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setEmpty(){this.empty = true;}

    /**
     * Name:
     *
     *      setNotEmpty - alters empty flag
     *
     * Synopsis:
     *
     *      public void setNotEmpty()
     *
     * Description:
     *
     *      Sets the empty flag to false
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setNotEmpty(){
        this.empty = false;
    }

    /**
     * Name:
     *
     *      getPlayersPiece - getter for playersPiece
     *
     * Synopsis:
     *
     *      public int getPlayersPiece()
     *
     * Description:
     *
     *      Gets the value of playersPiece which is -1 if it's empty or
     *      a number that is associated with a player when the piece is not empty
     *
     * Returns:
     *
     *      The id of the owner of the piece or -1 if empty
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int getPlayersPiece() {
        return playersPiece;
    }

    /**
     * Name:
     *
     *      setPlayersPiece - setter for owner of piece
     *
     * Synopsis:
     *
     *      public void setPlayersPiece(int a_playersPiece)
     *
     *          a_playersPiece --> number associated with each player
     *
     * Description:
     *
     *      Sets the an identifier to the players id to keep track of who owns
     *      the piece on the board.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setPlayersPiece(int a_playersPiece) {
        this.playersPiece = a_playersPiece;
    }

    /**
     * Name:
     *
     *      isKing - checks for king
     *
     * Synopsis:
     *
     *      public boolean isKing()
     *
     * Description:
     *
     *      gets whether the piece is a king or not. Only for checkers.
     *
     * Returns:
     *
     *      true --> if the piece is a king
     *      false --> if the piece is not a king
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public boolean isKing(){return this.king;}

    /**
     * Name:
     *
     *      kinged - sets flag
     *
     * Synopsis:
     *
     *      public void kinged()
     *
     * Description:
     *
     *      Sets a flag to indicate the piece is a kinged piece. Only for checkers.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void kinged(){this.king = true;}

    /**
     * Name:
     *
     *      reset - removes piece configurations
     *
     * Synopsis:
     *
     *      public void reset()
     *
     * Description:
     *
     *      Resets the pieces attributes back to a default state.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void reset()
    {
        playersPiece = -1;
        king = false;
        empty = true;
        setTileColor(-1);
    }

}
