package com.dmy.foodplannerapp.data.meals.remote.firestore;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class FirestoreRemoteDataSource {

    private static final String TAG = "FirestoreRemoteDataSource";
    private static final String USERS_COLLECTION = "users";
    private static final String FAV_COLLECTION = "favorites";
    private static final String PLANS_COLLECTION = "plans";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private boolean isLoggedIn() {
        FirebaseUser user = getUser();
        return user != null && !user.isAnonymous();
    }

    private CollectionReference getFavRef() {
        return db.collection(USERS_COLLECTION)
                .document(getUser().getUid())
                .collection(FAV_COLLECTION);
    }

    private CollectionReference getPlansRef() {
        return db.collection(USERS_COLLECTION)
                .document(getUser().getUid())
                .collection(PLANS_COLLECTION);
    }

    public void clearFavorites(MyCallBack<Boolean> callback) {
        if (!isLoggedIn()) {
            callback.onFailure(new Failure("User not logged in"));
            return;
        }

        getFavRef().get()
                .addOnSuccessListener(querySnapshot -> {
                    WriteBatch batch = db.batch();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        batch.delete(doc.getReference());
                    }

                    batch.commit()
                            .addOnSuccessListener(unused -> callback.onSuccess(true))
                            .addOnFailureListener(e -> callback.onFailure(new Failure(e.getMessage())));
                })
                .addOnFailureListener(e -> callback.onFailure(new Failure(e.getMessage())));
    }

    public void uploadFavorites(List<MealEntity> favMeals, MyCallBack<Boolean> callback) {

        if (!isLoggedIn()) {
            callback.onFailure(new Failure("User not logged in"));
            return;
        }

        if (favMeals == null || favMeals.isEmpty()) {
            callback.onFailure(new Failure("No favorites found"));
            return;
        }

        WriteBatch batch = db.batch();

        for (MealEntity meal : favMeals) {
            batch.set(getFavRef().document(meal.getId()), meal);
        }

        batch.commit()
                .addOnSuccessListener(x -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onFailure(FailureHandler.handle(e, TAG)));
    }

    public void getFavorites(MyCallBack<List<MealEntity>> callback) {
        if (!isLoggedIn()) {
            callback.onFailure(new Failure("User not logged in"));
            return;
        }

        getFavRef().get()
                .addOnSuccessListener(querySnapshot -> {
                    List<MealEntity> meals = new ArrayList<>();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        MealEntity meal = doc.toObject(MealEntity.class);
                        if (meal != null) meals.add(meal);
                    }

                    callback.onSuccess(meals);
                })
                .addOnFailureListener(
                        e -> callback.onFailure(FailureHandler.handle(e, TAG))
                );
    }

    public void clearPlans(MyCallBack<Boolean> callback) {
        if (!isLoggedIn()) {
            callback.onFailure(new Failure("User not logged in"));
            return;
        }

        getPlansRef().get()
                .addOnSuccessListener(querySnapshot -> {
                    WriteBatch batch = db.batch();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        batch.delete(doc.getReference());
                    }
                    batch.commit()
                            .addOnSuccessListener(unused -> callback.onSuccess(true))
                            .addOnFailureListener(e -> callback.onFailure(FailureHandler.handle(e, TAG)));
                })
                .addOnFailureListener(e -> callback.onFailure(FailureHandler.handle(e, TAG)));
    }

    public void uploadPlans(List<MealPlan> plans, MyCallBack<Boolean> callback) {
        if (!isLoggedIn()) {
            callback.onFailure(new Failure("User not logged in"));
            return;
        }

        if (plans == null || plans.isEmpty()) {
            callback.onFailure(new Failure("No plans found"));
            return;
        }

        WriteBatch batch = db.batch();
        for (MealPlan plan : plans) {
            batch.set(getPlansRef().document(Integer.toString(plan.getId())), plan);
        }

        batch.commit()
                .addOnSuccessListener(x -> callback.onSuccess(true))
                .addOnFailureListener(e -> callback.onFailure(FailureHandler.handle(e, TAG)));
    }

    public void getPlans(MyCallBack<List<MealPlan>> callback) {
        if (!isLoggedIn()) {
            callback.onFailure(new Failure("User not logged in"));
            return;
        }

        getPlansRef().get()
                .addOnSuccessListener(querySnapshot -> {
                    List<MealPlan> plans = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        MealPlan plan = doc.toObject(MealPlan.class);
                        if (plan != null) plans.add(plan);
                    }
                    callback.onSuccess(plans);
                })
                .addOnFailureListener(
                        e -> callback.onFailure(FailureHandler.handle(e, TAG))
                );
    }
}
