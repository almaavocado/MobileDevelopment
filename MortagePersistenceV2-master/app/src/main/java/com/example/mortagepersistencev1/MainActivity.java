package com.example.mortagepersistencev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String MA = "MainActivity";
    public static Mortgage mortgage;

    //@Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        //TO DO SOMETHING GOES HERE
        mortgage = new Mortgage(this);
        setContentView(R.layout.activity_main);
    }
    public void modifyData(View v) {
        Intent myIntent = new Intent(this, DataActivity.class);
        this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_from_left, 1);
    }

    protected void onStart() {
        super.onStart();
        updateView();
    }

    private void updateView() {
        TextView amountTV = ( TextView ) findViewById( R.id.amount );
        amountTV.setText(mortgage.getFormattedAmount( ) );
        TextView yearsTV = ( TextView ) findViewById( R.id.years );
        yearsTV.setText( "" + mortgage.getYears( ) );
        TextView interestTV = (TextView) findViewById(R.id.rate);
        interestTV.setText("" + mortgage.getRate());
        TextView monthlyPayment = (TextView) findViewById((R.id.monthly_payment));
        monthlyPayment.setText(mortgage.formattedMonthlyPayment());
        TextView totalPayment = (TextView) findViewById(R.id.total_payment);
        totalPayment.setText((mortgage.formattedTotalPayment()));
    }



}
