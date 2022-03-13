package com.example.candystore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.CollationElementIterator;
import java.util.ArrayList;


public class DeleteActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        updateView();
        Log.w("DeleteActivity","upDate called");
    }

    // Build a View dynamically with all the candies
    public void updateView( ) {
        ArrayList<Candy> candies = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout( this );
        ScrollView scrollView = new ScrollView( this );

        RadioGroup group = new RadioGroup( this );
        for ( Candy candy : candies) {
            Log.w("DeleteCandyCheck",candy.toString());
            RadioButton rb = new RadioButton( this );
            rb.setId( candy.getId() );
            rb.setText(candy.toString() );
            group.addView( rb );
            Log.w("DeleteActivity","radioButton Created");
        }

         //set up event handling
        RadioButtonHandler rbh = new RadioButtonHandler( );
        group.setOnCheckedChangeListener(rbh);

        // create a back button
        Button backButton = new Button( this );
        backButton.setText( R.string.button_back );

        backButton.setOnClickListener( new View.OnClickListener( ) {
            public void onClick(View v) {
                DeleteActivity.this.finish();
            }
        });

        // add view here
        scrollView.addView(group);
        layout.addView(scrollView);



         //add back button at bottom
        RelativeLayout.LayoutParams params
                = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT );
        params.addRule( RelativeLayout.ALIGN_PARENT_BOTTOM );
        params.addRule( RelativeLayout.CENTER_HORIZONTAL );
        params.setMargins( 0, 0, 0, 50 );
        layout.addView( backButton, params );

        setContentView(layout);
    }


    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener {

        public void onCheckedChanged(RadioGroup group, int checkedId ) {

            // delete candy from database
            dbManager.deleteById( checkedId );
            Toast.makeText( DeleteActivity.this, "Candy deleted",
                    Toast.LENGTH_SHORT ).show( );
            // update screen
            updateView();
        }

    }

}