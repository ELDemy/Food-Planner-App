package com.dmy.foodplannerapp.presentation.plan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;
import com.dmy.foodplannerapp.presentation.plan.presenter.MealsPlanPresenter;
import com.dmy.foodplannerapp.presentation.plan.presenter.MealsPlanPresenterImpl;
import com.dmy.foodplannerapp.presentation.reusable_components.CustomSnackBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlanFragment extends Fragment implements PlanView, PlannedMealsAdapter.OnMealActionListener {

    private static final String TAG = "PlanFragment";

    private MealsPlanPresenter presenter;

    private CalendarView calendarView;
    private TextView tvDayMealsTitle;

    private View slotBreakfast;
    private View slotLunch;
    private View slotDinner;

    private PlannedMealsAdapter breakfastAdapter;
    private PlannedMealsAdapter lunchAdapter;
    private PlannedMealsAdapter dinnerAdapter;

    private Date selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealsPlanPresenterImpl(requireContext(), this);
        selectedDate = getToday();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupAdapters();
        setupCalendar();
        loadMealsForSelectedDate();
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        tvDayMealsTitle = view.findViewById(R.id.tv_day_meals_title);

        slotBreakfast = view.findViewById(R.id.slot_breakfast);
        slotLunch = view.findViewById(R.id.slot_lunch);
        slotDinner = view.findViewById(R.id.slot_dinner);

        setupMealSlot(slotBreakfast, "Breakfast", R.color.orange);
        setupMealSlot(slotLunch, "Lunch", R.color.green);
        setupMealSlot(slotDinner, "Dinner", R.color.purple);
    }

    private void setupMealSlot(View slot, String title, int colorRes) {
        TextView tvTitle = slot.findViewById(R.id.tv_slot_title);
        View indicator = slot.findViewById(R.id.indicator_slot);

        tvTitle.setText(title);
        indicator.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), colorRes));
    }

    private void setupAdapters() {
        breakfastAdapter = new PlannedMealsAdapter(requireContext(), this);
        lunchAdapter = new PlannedMealsAdapter(requireContext(), this);
        dinnerAdapter = new PlannedMealsAdapter(requireContext(), this);

        setupMealSlotRecyclerView(slotBreakfast, breakfastAdapter);
        setupMealSlotRecyclerView(slotLunch, lunchAdapter);
        setupMealSlotRecyclerView(slotDinner, dinnerAdapter);
    }

    private void setupMealSlotRecyclerView(View slot, PlannedMealsAdapter adapter) {
        RecyclerView rv = slot.findViewById(R.id.rv_slot_meals);
        rv.setAdapter(adapter);
    }

    private void setupCalendar() {
        calendarView.setDate(System.currentTimeMillis(), true, true);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            selectedDate = calendar.getTime();
            loadMealsForSelectedDate();
        });
    }

    private Date getToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void loadMealsForSelectedDate() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayName = dayFormat.format(selectedDate);

        Calendar today = Calendar.getInstance();
        Calendar selected = Calendar.getInstance();
        selected.setTime(selectedDate);

        if (today.get(Calendar.YEAR) == selected.get(Calendar.YEAR)
                && today.get(Calendar.DAY_OF_YEAR) == selected.get(Calendar.DAY_OF_YEAR)
        ) {
            tvDayMealsTitle.setText("Today's Meals");
        } else {
            tvDayMealsTitle.setText(dayName + "'s Meals");
        }

        presenter.getMealsByDate(selectedDate);
    }

    @Override
    public void onMealsLoaded(List<MealPlanWithDetails> meals) {
        List<MealPlanWithDetails> breakfastMeals = new ArrayList<>();
        List<MealPlanWithDetails> lunchMeals = new ArrayList<>();
        List<MealPlanWithDetails> dinnerMeals = new ArrayList<>();

        if (meals != null) {
            for (MealPlanWithDetails meal : meals) {
                if (meal.plan == null || meal.meal == null) {
                    continue;
                }
                switch (meal.plan.getMealType()) {
                    case BREAKFAST:
                        breakfastMeals.add(meal);
                        break;
                    case LUNCH:
                        lunchMeals.add(meal);
                        break;
                    case DINNER:
                        dinnerMeals.add(meal);
                        break;
                }
            }
        }

        breakfastAdapter.setMeals(breakfastMeals);
        lunchAdapter.setMeals(lunchMeals);
        dinnerAdapter.setMeals(dinnerMeals);

        updateMealSlotVisibility(slotBreakfast, breakfastMeals);
        updateMealSlotVisibility(slotLunch, lunchMeals);
        updateMealSlotVisibility(slotDinner, dinnerMeals);
    }

    private void updateMealSlotVisibility(View slot, List<MealPlanWithDetails> meals) {
        RecyclerView rv = slot.findViewById(R.id.rv_slot_meals);
        LinearLayout emptyState = slot.findViewById(R.id.empty_state_container);

        if (meals != null && !meals.isEmpty()) {
            rv.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDatesWithMealsLoaded(List<Date> dates) {
        // da ana mesh chat -_-
        // to show dot in days with meals in it later
        // hope to have time :)
    }

    @Override
    public void onMealRemoved() {
        CustomSnackBar.showInfo(requireView(), "Meal removed from plan");
    }

    @Override
    public void onError(String message) {
        CustomSnackBar.showFailure(requireView(), message);
    }

    @Override
    public void onMealRemove(MealPlanWithDetails mealPlan) {
        if (mealPlan.plan != null) {
            presenter.removeMealPlanById(mealPlan.plan.getId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.dispose();
        }
    }
}
