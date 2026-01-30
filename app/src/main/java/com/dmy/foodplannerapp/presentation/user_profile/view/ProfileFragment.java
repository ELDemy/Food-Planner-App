package com.dmy.foodplannerapp.presentation.user_profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.presentation.auth.view.AuthActivity;
import com.dmy.foodplannerapp.presentation.reusable_components.CustomSnackBar;
import com.dmy.foodplannerapp.presentation.user_profile.prsenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileView {
    CardView signOutBtn;
    CardView syncButton;
    ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signOutBtn = view.findViewById(R.id.card_logout);
        syncButton = view.findViewById(R.id.card_sync);

        presenter = new ProfilePresenter(requireContext(), this);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnView) {
                presenter.signOut();
            }
        });

        syncButton.setOnClickListener(cardView -> {
            presenter.sync();
        });
    }

    @Override
    public void onSync() {
        CustomSnackBar.showSuccess(requireView(), "Synced Successfully");
    }

    @Override
    public void onSignOut() {
        if (getActivity() == null) return;
        CustomSnackBar.showInfo(requireView(), "Signed Out Successfully");

        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onFailure(String message) {
        CustomSnackBar.showFailure(requireView(), message);
    }
}