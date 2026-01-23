package com.dmy.foodplannerapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.navBar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_navHost);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNav, navController);

            bottomNav.setOnItemSelectedListener(item -> {
                // 1. Perform the standard navigation
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);

                // 2. Add the "Pop" Animation
                View itemView = bottomNav.findViewById(item.getItemId());
                if (itemView != null) {
                    itemView.animate()
                            .scaleX(1.15f)
                            .scaleY(1.15f)
                            .setDuration(100)
                            .withEndAction(() ->
                                    itemView.animate()
                                            .scaleX(1.0f)
                                            .scaleY(1.0f)
                                            .setDuration(100)
                                            .start()
                            ).start();
                }

                return handled;
            });
        }
    }

}