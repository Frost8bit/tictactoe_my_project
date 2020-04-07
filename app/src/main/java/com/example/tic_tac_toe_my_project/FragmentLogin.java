package com.example.tic_tac_toe_my_project;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentLogin extends Fragment {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;

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
                R.layout.fragment_login,
                container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Name = view.findViewById(R.id.etUsername);
        Password = view.findViewById(R.id.etPassword);
        Login = view.findViewById(R.id.bntLogin);
        Info = view.findViewById(R.id.tvInfo);

        Info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });


    }

    private void validate(String userName, String userPassword) {
        if((userName.equals("Admin") && userPassword.equals("12345"))) {
            getMainContext().launchFragment(null, new FragmentGay());
        }
        else {
            counter--;

            Info.setText("No of attempts remaining:" + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        }
    }
}
