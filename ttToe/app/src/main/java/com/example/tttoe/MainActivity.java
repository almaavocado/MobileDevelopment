package com.example.tttoe;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    TicTacToe tttGame;
    ButtonGridAndTextView tttView;
    private Button [][] buttons;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        // setContentView( R.layout.activity_main );
        //buildGuiByCode();
        tttGame = new TicTacToe();
        tttView = new ButtonGridAndTextView(this,getWidth(),TicTacToe.SIDE,new ButtonHandler());
        tttView.setStatusText(tttGame.result());
        setContentView(tttView);
    }// end onCreate()

    public int getWidth(){
        // Get width of the screen
        Point size = new Point( );
        //YOUR CODE – Retrieve the width of the screen
        WindowManager wm = this.getWindowManager();
        Display display = wm.getDefaultDisplay();
        display.getSize((Point) size);
        //YOUR CODE – Assign one third of the width of the screen to a variable w
        int w = size.x / TicTacToe.SIDE;
        return w;
    }// end getWidth()

    public void buildGuiByCode( ) {
        // Get width of the screen
        Point size = new Point( );
        //YOUR CODE – Retrieve the width of the screen
        WindowManager wm = this.getWindowManager();
        Display display = wm.getDefaultDisplay();
        display.getSize((Point) size);
        //YOUR CODE – Assign one third of the width of the screen to a variable w
        double w = size.x * 0.3333;

        // Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(3);
        gridLayout.setRowCount(3);
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
        if (tttGame.isGameOver())
            enableButtons(false);

        // log the row and col who we updated
        Log.w( "MainActivity", "Inside update: " + row + ", " + col );

    }// end update()

    public void enableButtons( boolean enabled ) {
        for( int row = 0; row < TicTacToe.SIDE; row++ )
            for( int col = 0; col < TicTacToe.SIDE; col++ )
                buttons[row][col].setEnabled(enabled);
    }// end enableButtons()

    public void showNewGameDialog( ) {
        AlertDialog.Builder alert = new AlertDialog.Builder( this );
        alert.setTitle( "This is fun" );
        alert.setMessage( "Play again?" );
        PlayDialog playAgain = new PlayDialog( );
        alert.setPositiveButton( "YES", playAgain );
        alert.setNegativeButton( "NO", playAgain );
        alert.show( );
    }
    //Implement the PlayDialog object
    private class PlayDialog implements
            DialogInterface.OnClickListener {
        public void onClick( DialogInterface dialog, int id ) {
            if( id == -1 ) /* YES button */ {
                tttGame.resetGame( );
                tttView.enableButtons( true );
                tttView.resetButtons( );
                tttView.setStatusBackgroundColor( Color.GREEN );
                tttView.setStatusText( tttGame.result( ) );
            }
            else if( id == -2 ) // NO button
                MainActivity.this.finish( );
        }
    }

    //Implement the ButtonHandler event
    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {
            for( int row = 0; row < TicTacToe.SIDE; row++ ) {
                for( int column = 0; column < TicTacToe.SIDE; column++ ) {
                    if( tttView.isButton( ( Button ) v, row, column ) ) {
                        int play = tttGame.play( row, column );
                        if( play == 1 )
                            tttView.setButtonText( row, column, "X" );
                        else if( play == 2 )
                            tttView.setButtonText( row, column, "O" );
                        // check if game is over
                        if(tttGame.isGameOver()) {
                            tttView.setStatusBackgroundColor(Color.RED);
                            tttView.enableButtons(false);
                            tttView.setStatusText(tttGame.result());
                            showNewGameDialog(); // offer to play again
                        }// end if
                    }// end if
                }// end for loop
            }// end for loop
        }// end onClick()

    }// end ButtonHandler class



}// end MainActivity Class
