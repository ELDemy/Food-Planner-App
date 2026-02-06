package com.dmy.foodplannerapp.presentation.user_profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.User;
import com.dmy.foodplannerapp.presentation.auth.view.AuthActivity;
import com.dmy.foodplannerapp.presentation.user_profile.prsenter.ProfilePresenter;
import com.dmy.foodplannerapp.utils.CustomSnackBar;

public class ProfileFragment extends Fragment implements ProfileView {
    CardView signOutBtn;
    CardView syncButton;
    ProfilePresenter presenter;

    TextView UserNameTxt, EmailTxt;

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
        UserNameTxt = view.findViewById(R.id.tv_user_name);
        EmailTxt = view.findViewById(R.id.tv_user_email);

        presenter = new ProfilePresenter(requireContext(), this);
        signOutBtn.setOnClickListener(btnView -> presenter.signOut());

        syncButton.setOnClickListener(cardView -> presenter.sync());
        presenter.getUserData();
        presenter.checkIfUserIsLoggedIn();
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

    @Override
    public void updateUserData(User user) {
        UserNameTxt.setText(user.getName());
        EmailTxt.setText(user.getEmail());
    }

    @Override
    public void updateUserStatue(Boolean isLoggedIn) {
        syncButton.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}