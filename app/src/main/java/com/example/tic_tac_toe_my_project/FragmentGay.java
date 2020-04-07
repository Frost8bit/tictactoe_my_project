package com.example.tic_tac_toe_my_project;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentGay extends Fragment {
    static Button restart_game_btn;
    Button exit_btn;


    private MainActivity appContract;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.appContract = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.appContract = null;
    }

    final MainActivity getMainContext() {
        return appContract;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_game,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restart_game_btn = view.findViewById(R.id.play);
        restart_game_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMainContext().cancel();
                        getMainContext().launchFragment(null, new FragmentGay());
                    }
                });
        exit_btn = view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  finish();
                        System.exit(0);
                    }
                });
    }
}
