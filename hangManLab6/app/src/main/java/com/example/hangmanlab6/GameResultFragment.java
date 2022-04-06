package com.example.hangmanlab6;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GameResultFragment extends Fragment {
    private TextView gameResultTV;
    private float ourFontsize = 50f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setUpFragmentGui( container );
        return super.onCreateView(inflater, container, savedInstanceState) ;
    }// end onCreateView()

    public void setUpFragmentGui( ViewGroup container ) {
        if( gameResultTV == null ) {
            gameResultTV = new TextView(getActivity());
            gameResultTV.setGravity( Gravity.CENTER );
            gameResultTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourFontsize);
            container.addView(gameResultTV);
        }// end if
    }// end setUpFragmentGui()

    public void onStart( ) {
        super.onStart();
        gameResultTV.setText("GOOD LUCK");
    }// end onStart()

    public void setResult( String result ) {
        gameResultTV.setText(result);
    }// end setResult()

}// end GameResultFragment class

