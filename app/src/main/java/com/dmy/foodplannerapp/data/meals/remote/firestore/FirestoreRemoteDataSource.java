package com.dmy.foodplannerapp.data.meals.remote.firestore;

import android.util.Log;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;


public class FirestoreRemoteDataSource {
    private static final String TAG = "FirestoreService";
    final String USERS_COLLECTION = "users";
    final String FAV_COLLECTION = "favorites";
    final String PLANS_COLLECTION = "plans";

    FirebaseFirestore db;

    public FirestoreRemoteDataSource() {
        db = FirebaseFirestore.getInstance();
    }

    public void syncFavorites(List<MealEntity> favMeals) {
        if (favMeals == null || favMeals.isEmpty()) return;

        String userId = "f6xNsmOkkjPfgvrPSi4lMVuusR43";
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollectionReference favRef = FirebaseFirestore.getInstance()
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(FAV_COLLECTION);

        WriteBatch batch = FirebaseFirestore.getInstance().batch();

        for (MealEntity meal : favMeals) {
            DocumentReference doc = favRef.document(meal.getId());
            batch.set(doc, meal);
        }

        batch.commit()
                .addOnSuccessListener(unused ->
                        Log.d(TAG, "Favorites synced successfully"))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Sync failed", e));
    }

    public void getFavorites(MyCallBack<List<MealEntity>> callback) {
        String userId = "f6xNsmOkkjPfgvrPSi4lMVuusR43";

//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(FAV_COLLECTION)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<MealEntity> meals = new ArrayList<>();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        MealEntity meal = doc.toObject(MealEntity.class);
                        if (meal != null) {
                            meals.add(meal);
                        }
                    }
                    Log.i(TAG, "getFavorites: " + meals);
//                    callback.onSuccess(meals);
                })
                .addOnFailureListener(e -> {
//                            callback.onFailure(new Failure(e.getMessage()));
                        }
                );
    }

}
