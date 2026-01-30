package com.dmy.foodplannerapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavHost();
        navBarVisibility();
    }

    private void navBarVisibility() {
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNav, navController);

            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.mealProfileFragment
                        || destination.getId() == R.id.mealsListScreenFragment
                        || destination.getId() == R.id.itemsScreenFragment) {
                    bottomNav.setVisibility(View.GONE);
                } else {
                    bottomNav.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    private void setUpNavHost() {
        bottomNav = findViewById(R.id.navBar);

        navHostFragment = (NavHostFragment) getSupportFragmentManager()
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