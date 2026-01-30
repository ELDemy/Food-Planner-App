package com.dmy.foodplannerapp.data.meals.remote.firestore;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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

    private DocumentReference getUserRef() {
        return db.collection(USERS_COLLECTION)
                .document(getUser().getUid());
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

    public void setUserData(User user, MyCallBack<Boolean> callBack) {
        getUserRef().set(user)
                .addOnSuccessListener(doc -> callBack.onSuccess(true))
                .addOnFailureListener(
                        e -> callBack.onFailure(FailureHandler.handle(e, TAG)));
    }

    public void getUserData(MyCallBack<User> callBack) {
        if (!isLoggedIn()) {
            callBack.onFailure(new Failure("User not logged in"));
            return;
        }
        getUserRef().get().addOnSuccessListener(doc -> {
                    if (doc == null) {
                        User anonymous = new User("Guest", "");
                        callBack.onSuccess(anonymous);
                        return;
                    }
                    User user = doc.toObject(User.class);
                    if (user == null) {
                        User anonymous = new User("Guest", "");
                        callBack.onSuccess(anonymous);
                        return;
                    }
                    callBack.onSuccess(user);
                })
                .addOnFailureListener(
                        e -> callBack.onFailure(FailureHandler.handle(e, TAG)));
    }

    public Completable clearFavorites() {
        return Completable.create(emitter -> {
            if (!isLoggedIn()) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }
            getFavRef().get()
                    .addOnSuccessListener(querySnapshot -> {
                        WriteBatch batch = db.batch();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            batch.delete(doc.getReference());
                        }
                        batch.commit()
                                .addOnSuccessListener(unused -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable uploadFavorites(List<MealEntity> favMeals) {
        return Completable.create(emitter -> {
            if (!isLoggedIn()) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }
            if (favMeals == null || favMeals.isEmpty()) {
                emitter.onComplete();
                return;
            }
            WriteBatch batch = db.batch();
            for (MealEntity meal : favMeals) {
                batch.set(getFavRef().document(meal.getId()), meal);
            }
            batch.commit()
                    .addOnSuccessListener(x -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<List<MealEntity>> getFavorites() {
        return Single.create(emitter -> {
            if (!isLoggedIn()) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }
            getFavRef().get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<MealEntity> meals = new ArrayList<>();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            MealEntity meal = doc.toObject(MealEntity.class);
                            if (meal != null)
                                meals.add(meal);
                        }
                        emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable clearPlans() {
        return Completable.create(emitter -> {
            if (!isLoggedIn()) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }
            getPlansRef().get()
                    .addOnSuccessListener(querySnapshot -> {
                        WriteBatch batch = db.batch();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            batch.delete(doc.getReference());
                        }
                        batch.commit()
                                .addOnSuccessListener(unused -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable uploadPlans(List<MealPlan> plans) {
        return Completable.create(emitter -> {
            if (!isLoggedIn()) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }
            if (plans == null || plans.isEmpty()) {
                emitter.onComplete();
                return;
            }
            WriteBatch batch = db.batch();
            for (MealPlan plan : plans) {
                batch.set(getPlansRef().document(Integer.toString(plan.getId())), plan);
            }
            batch.commit()
                    .addOnSuccessListener(x -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<List<MealPlan>> getPlans() {
        return Single.create(emitter -> {
            if (!isLoggedIn()) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }
            getPlansRef().get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<MealPlan> plans = new ArrayList<>();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            MealPlan plan = doc.toObject(MealPlan.class);
                            if (plan != null)
                                plans.add(plan);
                        }
                        emitter.onSuccess(plans);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
