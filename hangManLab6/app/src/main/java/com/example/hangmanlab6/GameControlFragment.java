package com.example.hangmanlab6;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class GameControlFragment extends Fragment {
    public GameControlFragment() {

    }// end constructor()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.fragment_game_control, container, false);
    }// end onCreateView

}// end GameControlFragment class
