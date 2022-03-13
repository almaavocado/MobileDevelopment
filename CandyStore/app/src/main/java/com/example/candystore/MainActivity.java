package com.example.candystore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private double total;
    private ScrollView scrollView;
    private int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //YOUR CODE – Create DatabaseManager object, scrollview, initialize total to
        // 0,stet the buttonWidth to half the width of the screen. Call updateView
        // which you create it later.
        // YOUR CODE

        dbManager = new DatabaseManager(this);
        total = 0.0;
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        buttonWidth = size.x / 2;
        updateView();


    }

    protected void onResume( ) {
        super.onResume( );
        //YOUR CODE
        updateView();

    }

    @SuppressLint("SetTextI18n")
    public void updateView( ) {
        ArrayList<Candy> candies = dbManager.selectAll();

        if( candies.size( ) > 0 ) {
            // remove subviews inside scrollView if necessary
            scrollView.removeAllViewsInLayout( );
            // set up the grid layout
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( ( candies.size( ) + 1 ) / 2 );
            grid.setColumnCount( 2 );
            // create array of buttons, 2 per row
            CandyButton [] buttons = new CandyButton[candies.size()];
            ButtonHandler bh = new ButtonHandler();
            // fill the grid
            int i=0;
            for ( Candy candy : candies ) {
                // create the button
                buttons[i] = new CandyButton( this, candy );
                buttons[i].setText( candy.getName() + "\n" + candy.getPrice() );
                // set up event handling
                buttons[i].setOnClickListener(bh);
                // add the button to grid
                grid.addView( buttons[i], buttonWidth,
                        GridLayout.LayoutParams.WRAP_CONTENT );
                i++;
            }
            scrollView.addView( grid ); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch ( id ) {
            case R.id.action_add:
                Log.w("MainActivity", "Add selected");
                Intent insertIntent = new Intent(
                        this, InsertActivity.class );
                this.startActivity( insertIntent );
                return true;
            case R.id.action_delete:
                Log.w("Main Activity", "Delete option selected");
                Intent deleteIntent = new Intent(
                        this,DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;
            case R.id.action_update:
                Log.w("Main Activity", "Update selected");
                Intent updateIntent = new Intent(this, UpdateActivity.class);
                this.startActivity(updateIntent);
                return true;
            case R.id.action_reset:
                Log.w("Main Activity", "Reset Selected");
                total = 0.0;
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            // retrieve price of the candy and add it to total
            total += (  (CandyButton)v ).getPrice( );
            String pay = NumberFormat.getCurrencyInstance( ).format( total );
            Toast.makeText( MainActivity.this, pay, Toast.LENGTH_LONG ).show( );
        }
    }
}
