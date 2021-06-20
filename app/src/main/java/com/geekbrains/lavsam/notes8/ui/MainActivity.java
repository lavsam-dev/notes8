package com.geekbrains.lavsam.notes8.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.lavsam.notes8.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MainRouter router;

    public MainRouter getRouter() {
        return router;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        router = new MainRouter(getSupportFragmentManager());

        if (savedInstanceState == null) {
            router.showNotes();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_notes) {
                    router.showNotes();
                }

                if (item.getItemId() == R.id.action_info) {
                    router.showInfo();
                }
                return true;
            }
        });
    }
}