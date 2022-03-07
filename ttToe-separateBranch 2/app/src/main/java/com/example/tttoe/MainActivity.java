package com.example.tttoe;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TicTacToe tttGame;
    private Button [][] buttons;
    private TextView status;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        // setContentView( R.layout.activity_main );
        buildGuiByCode();
        tttGame = new TicTacToe();
    }// end onCreate()

    public void buildGuiByCode( ) {
        // Get width of the screen
        Point size = new Point( );
        //YOUR CODE – Retrieve the width of the screen
        WindowManager wm = this.getWindowManager();
        Display display = wm.getDefaultDisplay();
        display.getSize((Point) size);
        //YOUR CODE – Assign one third of the width of the screen to a variable w
        double w = size.x / (TicTacToe.SIDE + 1);

        // Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE + 1);
        // Create the buttons and add them to gridLayout
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        //Instantiate a ButtonHandler object
        ButtonHandler bh = new ButtonHandler();
        for( int row = 0; row < TicTacToe.SIDE; row++ ) {
            for( int col = 0; col < TicTacToe.SIDE; col++ ) {
                buttons[row][col] = new Button(this);
                //Set the textsize for each button to w * 0.2
                buttons[row][col].setTextSize((float) (w*0.2));
                //Register the event for each button
                buttons[row][col].setOnClickListener(bh);
                gridLayout.addView(buttons[row][col],(int) w, (int) w);
            }
        }
        // set up layout parameters of 4th row of gridLayout
        status = new TextView( this );
        GridLayout.Spec rowSpec = GridLayout.spec( TicTacToe.SIDE, 1 ); //double check these
        GridLayout.Spec columnSpec = GridLayout.spec( 0, TicTacToe.SIDE );
        GridLayout.LayoutParams lpStatus = new GridLayout.LayoutParams( rowSpec, columnSpec );
        status.setLayoutParams( lpStatus );

        // set up status' characteristics
        status.setWidth((int) (TicTacToe.SIDE * (w)));
        status.setHeight((int) (2 * w));
        status.setGravity( Gravity.CENTER );
        status.setBackgroundColor( Color.GREEN );
        status.setTextSize( ( int ) ( w * .15 ) );
        status.setText( "PLAY"); //placeholder

        gridLayout.addView( status );
        // Set gridLayout as the View of this Activity
        this.setContentView(gridLayout);
    }// end buildGuiByCode()

    public void update(int row, int col) {
        int play = tttGame.play(row,col);
        // check who's turn it is
        if (play == 1)
            buttons[row][col].setText("X");
        else if (play == 2)
            buttons[row][col].setText("O");

        // if game is over, disable buttons
        if (tttGame.isGameOver()) {
            status.setBackgroundColor(Color.RED);
            enableButtons(false);
            status.setText("Player "+ tttGame.whoWon() + " won");
            showNewGameDialog( );
        }


        // log the row and col who we updated
        Log.w( "MainActivity", "Inside update: " + row + ", " + col );
    }// end update()
    public void resetButtons( ) {
        for( int row = 0; row < TicTacToe.SIDE; row++ )
            for( int col = 0; col < TicTacToe.SIDE; col++ )
                buttons[row][col].setText( "" );

    }

    public void showNewGameDialog( ) {
        AlertDialog.Builder alert = new AlertDialog.Builder( this );
        alert.setTitle( "This is fun" );
        alert.setMessage( "Play again?" );
        PlayDialog playAgain = new PlayDialog( );
        alert.setPositiveButton( "YES",playAgain );  //something here
        alert.setNegativeButton( "NO",playAgain );  //??
        alert.show();
    }


    public void enableButtons( boolean enabled ) {
        for( int row = 0; row < TicTacToe.SIDE; row++ )
            for( int col = 0; col < TicTacToe.SIDE; col++ )
                buttons[row][col].setEnabled(enabled);
    }// end enableButtons()


    //Implement the ButtonHandler event
    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick( View v) {
            Log.w("MainActivity", "Inside onClick, v = " + v);
            for( int row = 0; row < TicTacToe.SIDE; row++ )
                for( int column = 0; column < TicTacToe.SIDE; column++ )
                    if ( v == buttons[row][column])
                        update(row,column);
        }// end onClick()

    }// end ButtonHandler class

    public class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            Log.w("MainActivity", "Inside onClick, v = " + id);
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int column = 0; column < TicTacToe.SIDE; column++)
                    if (id == -1) /* YES button */ {
                        tttGame.resetGame();
                        enableButtons(true);
                        resetButtons();
                        status.setBackgroundColor(Color.GREEN);
                        status.setText("Play");
                    } else if (id == -2)
                        MainActivity.this.finish();


        }
    }


}// end MainActivity Class
