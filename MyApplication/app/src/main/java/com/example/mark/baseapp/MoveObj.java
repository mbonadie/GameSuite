package com.example.mark.baseapp;


import android.util.Pair;

/**
 * Created by Mark on 10/1/2017.
 */
/**
 * Name:
 *
 *      MoveObj - store move information
 *
 * Description:
 *
 *      Used to catalog moves for use in multiple jump moves
 *      and to keep track of what jumped pieces should later be removed
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class MoveObj {

    // If the move had a jump in it
    boolean jumpedOpponent = false;

    // The selected end location
    Pair <Integer,Integer> endRowCol;

    // Location of jumped piece if one exists
    Pair <Integer,Integer> jumpedPiece ;

    // Location of selected piece to be moved
    Pair <Integer,Integer> startRowCol;

    /**
     * Name:
     *
     *      MoveObj - Move start and end
     *
     * Synopsis:
     *
     *      public void setEndRowCol(int a_Row, int a_Col)
     *
     *          a_Row --> row of starting location
     *          a_Col --> column of starting location
     *
     * Description:
     *
     *      Sets the selected pieces starting location
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public MoveObj(int a_Row, int a_Col)
    {
        startRowCol = new Pair(a_Row,a_Col);
    }

    /**
     * Name:
     *
     *      setEndRowCol - assigns end location
     *
     * Synopsis:
     *
     *      public void setEndRowCol(int a_Row, int a_Col)
     *
     *          a_Row --> row of ending location
     *          a_Col --> column of ending location
     *
     * Description:
     *
     *      Sets the location the piece ends in after the move is complete
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setEndRowCol(int a_Row, int a_Col)
    {
        endRowCol = new Pair(a_Row, a_Col);
    }

    /**
     * Name:
     *
     *      setJumpedPiece - assigns jumped location
     *
     * Synopsis:
     *
     *      public void setJumpedPiece(int a_Row, int a_Col)
     *
     *          a_Row --> row of jumped location
     *          a_Col --> column of jumped location
     *
     * Description:
     *
     *      Sets the jumped location and sets jumpedOpponent to true
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setJumpedPiece(int a_Row, int a_Col)
    {
        jumpedPiece = new Pair(a_Row, a_Col);
        jumpedOpponent = true;
    }

}
