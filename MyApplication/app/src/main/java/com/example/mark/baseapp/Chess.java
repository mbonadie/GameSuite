package com.example.mark.baseapp;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Chess extends AppCompatActivity {

    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    Button boardButtons[][] = new Button[NUM_ROWS][NUM_COLS];
    Game mainBoard = new Game(64,3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        buildBoard();
    }

    private void showToast(CharSequence a_msg){
        Toast.makeText(this, a_msg, Toast.LENGTH_SHORT).show();
    }

    private void buildBoard() {
        ConstraintLayout device = (ConstraintLayout) findViewById(R.id.chessBoard);

        TableLayout board = new TableLayout(this);

        device.addView(board);

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

                newButton.setHeight(135);
                if(color){
                    newButton.setBackgroundColor(Color.rgb(170,90,35));
                    color = !color;
                }
                else {
                    newButton.setBackgroundColor(Color.rgb(255,255,240));
                    color = !color;
                }

                newRow.addView(newButton);
                final int Final_Row = row;
                final int Final_Col = col;
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("You clicked button R:" + (Final_Row + 1) + " C:" + (Final_Col + 1));

                    }
                });
                boardButtons[row][col] = newButton;

            }
        }
    }
}
