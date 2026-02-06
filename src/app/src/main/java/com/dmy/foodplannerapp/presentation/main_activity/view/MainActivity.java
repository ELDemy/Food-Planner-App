package com.dmy.foodplannerapp.presentation.main_activity.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.presentation.main_activity.presenter.MainPresenter;
import com.dmy.foodplannerapp.presentation.main_activity.presenter.MainPresenterImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements MainView {
    Menu menu;
    MainPresenter presenter;
    private BottomNavigationView bottomNav;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();
        observeDestinationChanges();
        
        presenter = new MainPresenterImpl(this);
        presenter.getUserLoginStatus();
    }

    private void setupNavigation() {
        bottomNav = findViewById(R.id.navBar);
        menu = bottomNav.getMenu();
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_navHost);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNav, navController);

            bottomNav.setOnItemSelectedListener(item -> {
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);

                View itemView = bottomNav.findViewById(item.getItemId());
                if (itemView != null) {
                    itemView.animate()
                            .scaleX(1.15f)
                            .scaleY(1.15f)
                            .setDuration(100)
                            .withEndAction(() -> itemView.animate()
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

    private void observeDestinationChanges() {
        if (navController == null) return;

        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> {

                    int destId = destination.getId();

                    boolean hideBottomNav =
                            destId == R.id.mealProfileFragment ||
                                    destId == R.id.mealsListScreenFragment ||
                                    destId == R.id.itemsScreenFragment;

                    bottomNav.setVisibility(hideBottomNav ? View.GONE : View.VISIBLE);
                }
        );
    }

    @Override
    public void onLoad(boolean isLoggedIn) {
        menu.findItem(R.id.favouriteFragment).setVisible(isLoggedIn);
        menu.findItem(R.id.planFragment).setVisible(isLoggedIn);
    }
}
