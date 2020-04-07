package com.example.tic_tac_toe_my_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.MediaRouteButton;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public someService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.play).setOnClickListener(v -> {
//            if (service == null) return;
//            String str = () service.makeOwn();
//        });

        if (savedInstanceState == null) {
            launchFragment(null, new FragmentLogin());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(
                this,
                someService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name,
                                               IBinder binder) {
                    service = ((someService.someServiceBinder) binder).getService();
                }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    };

    public void launchFragment(@Nullable Fragment target, Fragment fragment) {
        if (target != null) {
            fragment.setTargetFragment(target, 0);
        }
        String tag = UUID.randomUUID().toString();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }

    public void cancel() {
        int count = getSupportFragmentManager()
                .getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
