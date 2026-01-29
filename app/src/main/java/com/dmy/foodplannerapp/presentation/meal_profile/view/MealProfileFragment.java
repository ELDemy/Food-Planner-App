package com.dmy.foodplannerapp.presentation.meal_profile.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.presentation.meal_profile.presenter.MealProfilePresenter;
import com.dmy.foodplannerapp.presentation.meal_profile.presenter.MealProfilePresenterImpl;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MealProfileFragment extends Fragment implements MealProfileView {
    private static final String TAG = "MealProfileFragment";
    MealEntity meal;
    CardView backButton;
    CardView favoriteButton;
    ImageView heartImage;
    FloatingActionButton addToWeeklyBtn;
    TextView titleText;
    TextView categoryText;
    TextView countryText;
    RecyclerView ingredientsRecyclerView;
    TextView descriptionText;
    ImageView mealImage;
    TextView sourceText;
    TextView tutorialText;
    YouTubePlayerView youtubePlayer;
    MealProfilePresenter presenter;

    private Date selectedDate = null;
    private MealPlan.MealType selectedMealType = MealPlan.MealType.BREAKFAST;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MealProfilePresenterImpl(requireContext(), this);
        meal = MealProfileFragmentArgs.fromBundle(getArguments()).getMeal();

        titleText = view.findViewById(R.id.tv_name);
        descriptionText = view.findViewById(R.id.tv_instructions);
        categoryText = view.findViewById(R.id.tv_category);
        countryText = view.findViewById(R.id.tv_country);
        mealImage = view.findViewById(R.id.img_meal);
        backButton = view.findViewById(R.id.btn_back_container);
        favoriteButton = view.findViewById(R.id.btn_favorite);
        heartImage = favoriteButton.findViewById(R.id.iv_heart);
        addToWeeklyBtn = view.findViewById(R.id.fab_calendar);
        tutorialText = view.findViewById(R.id.tv_Tutorials);
        youtubePlayer = view.findViewById(R.id.pv_youtubePlayer);
        sourceText = view.findViewById(R.id.tv_source);

        updateData(view);
        ingredientsRecyclerView = view.findViewById(R.id.recycler_ingredients);
        ingredientsRecyclerView.setAdapter(new MealIngredientListAdapter(getActivity(), meal.getIngredients()));
    }

    void updateData(View view) {
        titleText.setText(meal.getName());
        descriptionText.setText(meal.getInstructions());
        categoryText.setText(meal.getCategory());
        countryText.setText(meal.getArea());
        sourceText.setText(meal.getSource());
        Glide.with(view).load(meal.getThumbnail()).into(mealImage);
        changeFavoriteState(meal.isFavourite());

        setUpYoutube();

        favoriteButton.setOnClickListener((cardView) -> {
            changeFavoriteState(!meal.isFavourite());
            presenter.changeFavourite(meal);
        });

        backButton.setOnClickListener(view1 -> getParentFragmentManager().popBackStack());

        addToWeeklyBtn.setOnClickListener(view2 -> showAddToPlanDialog());
    }

    private void showAddToPlanDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_to_plan, null);
        bottomSheetDialog.setContentView(dialogView);

        selectedDate = null;
        selectedMealType = MealPlan.MealType.BREAKFAST;

        MaterialCardView cardDatePicker = dialogView.findViewById(R.id.card_date_picker);
        TextView tvSelectedDate = dialogView.findViewById(R.id.tv_selected_date);
        ChipGroup chipGroup = dialogView.findViewById(R.id.chip_group_meal_type);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnAdd = dialogView.findViewById(R.id.btn_add);

        cardDatePicker.setOnClickListener(v -> showDatePicker(tvSelectedDate));

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chip_breakfast)) {
                selectedMealType = MealPlan.MealType.BREAKFAST;
            } else if (checkedIds.contains(R.id.chip_lunch)) {
                selectedMealType = MealPlan.MealType.LUNCH;
            } else if (checkedIds.contains(R.id.chip_dinner)) {
                selectedMealType = MealPlan.MealType.DINNER;
            }
        });

        btnCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnAdd.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(requireContext(), R.string.please_select_date, Toast.LENGTH_SHORT).show();
                return;
            }

            Log.i(TAG, "showAddToPlanDialog: " + selectedDate);
            presenter.addMealToPlan(meal.getId(), selectedDate, selectedMealType);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void showDatePicker(TextView tvSelectedDate) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            selectedDate = calendar.getTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault());
            tvSelectedDate.setText(dateFormat.format(selectedDate));

            Log.i(TAG, "Selected date normalized: " + selectedDate);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    void setUpYoutube() {
        getLifecycle().addObserver(youtubePlayer);

        if (meal.getYoutubeVideoId() == null || meal.getYoutubeVideoId().isEmpty()) {
            youtubePlayer.setVisibility(View.GONE);
            tutorialText.setVisibility(View.GONE);
            return;
        }
        
        youtubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = meal.getYoutubeVideoId();
                Log.d(TAG, "Loading YouTube video: " + videoId);
                youTubePlayer.cueVideo(videoId, 0);
            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerError error) {
                Log.e(TAG, "YouTube player error: " + error.name());
                Toast.makeText(requireContext(), "Error loading video", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void changeFavoriteState(boolean isFavorite) {
        heartImage.setImageResource(isFavorite ? R.drawable.heart_filled : R.drawable.heart);
    }

    @Override
    public void onMealAddedToPlan() {
        Toast.makeText(requireContext(), R.string.meal_added_to_plan, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToPlanError(String message) {
        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
