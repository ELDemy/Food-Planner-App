# 🍽️ Food Planner App

<p align="center">
  <img src="screenshots/app_logo.png" alt="Food Planner Logo" width="200"/>
</p>

<p align="center">
  <strong>Plan your meals, discover new recipes, and organize your weekly menu!</strong>
</p>

<p align="center">
  <a href="#features">Features</a> •
  <a href="#screenshots">Screenshots</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#tech-stack">Tech Stack</a> •
  <a href="#getting-started">Getting Started</a>
</p>

---

## 📋 About

Food Planner is an Android mobile application developed as part of the **ITI (Information Technology
Institute) 9-Month Professional Training Program**. This project demonstrates practical
implementation of Android development concepts using Java.

The app allows users to:

- 🔍 **Search meals** based on ingredients they have in their fridge
- 🌍 **Filter by countries/cuisines** to explore international dishes
- 📂 **Browse by categories** to find specific meal types
- 🎯 **Combine multiple filters** for precise meal discovery

---

## ✨ Features

| Feature                        | Description                                                        |
|--------------------------------|--------------------------------------------------------------------|
| 🍳 **Meal of the Day**         | Daily meal suggestions to inspire your cooking                     |
| 🔎 **Smart Search**            | Search meals by name with real-time results                        |
| 🥕 **Ingredient-Based Search** | Find meals based on ingredients you already have                   |
| 🌎 **Country Filter**          | Explore cuisines from around the world                             |
| 📁 **Category Filter**         | Browse meals by categories (Beef, Chicken, Dessert, etc.)          |
| 🎛️ **Combined Filters**       | Apply multiple filters simultaneously                              |
| ❤️ **Favorites**               | Add meals to favorites and access them anytime                     |
| 📅 **Meal Planning**           | Schedule meals to your calendar with specific day                  |
| 🍳 **Meal Type Selection**     | Specify if meal is for Breakfast, Lunch, or Dinner                 |
| 📆 **Weekly Calendar**         | Organize your meals for each day of the week                       |
| 📴 **Offline Mode**            | Access saved meals and plans without internet                      |
| 👤 **Guest Mode**              | Browse meals without creating an account                           |
| ☁️ **Cloud Sync**              | Sync favorites and meal plans to Firestore for multi-device access |
| 🔄 **Cross-Device Access**     | Access your data from any device with your account                 |
| 🔐 **Authentication**          | Sign up/Sign in with Email or Google                               |

---

## 📱 Screenshots

<!-- Add your screenshots to the screenshots folder -->

### Authentication

|              Splash               |             Auth          |
|:---------------------------------:|:-------------------------:|
|  ![Splash](https://github.com/user-attachments/assets/3a112ad1-4bd2-4c20-8f85-67576c2495b4)|  ![WhatsApp Image 2026-01-31 at 11 59 40 PM (1)](https://github.com/user-attachments/assets/c5fe9706-e096-4d67-bd18-23bb21b3dbe9)|

### Home & Discovery

|             Home              | 
|:-----------------------------:|
| ![home](https://github.com/user-attachments/assets/08b52dd9-8693-4a6e-86a5-4afdd0459183)|

### Search & Filters

|              Search               |                By Item             |  
|:---------------------------------:|:-------------------------------------------:|
| ![WhatsApp Image 2026-02-01 at 1 20 42 AM](https://github.com/user-attachments/assets/96b0f0d3-836c-48f5-b630-45cdfa1de03c)| ![WhatsApp Image 2026-02-01 at 1 21 00 AM](https://github.com/user-attachments/assets/fbe5665c-1d99-4ac7-ba00-d587d627e760)| 

### Meal Details & Planning

|               Meal Details               |                Favorites                |             Meal Plan              |
|:----------------------------------------:|:---------------------------------------:|:----------------------------------:|
|![WhatsApp Image 2026-02-01 at 1 19 29 AM](https://github.com/user-attachments/assets/e44b2f68-c8ae-4be7-9753-5dfe44ea7cbd)| ![WhatsApp Image 2026-01-31 at 11 59 39 PM](https://github.com/user-attachments/assets/ea3d26ea-d60b-47c0-aed7-8897290cc48a)| ![WhatsApp Image 2026-01-31 at 11 59 39 PM (1)](https://github.com/user-attachments/assets/000cb18e-1193-4515-bfbc-1d326cca136d)
|

### User Profile

|               Profile               | 
|:-----------------------------------:|
| ![WhatsApp Image 2026-02-01 at 1 19 55 AM](https://github.com/user-attachments/assets/490e6fbb-6f0a-4280-900c-f7c1789d9cbe) |


-

## 🏗️ Architecture

This project implements the **MVP (Model-View-Presenter)** architectural pattern for clean
separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                          VIEW                               │
│         (Activities, Fragments, Adapters, XML)              │
│                           │                                 │
│                           ▼                                 │
├─────────────────────────────────────────────────────────────┤
│                       PRESENTER                             │
│              (Business Logic & UI Logic)                    │
│                           │                                 │
│                           ▼                                 │
├─────────────────────────────────────────────────────────────┤
│                         MODEL                               │
│    ┌─────────────────────────────────────────────────┐      │
│    │                  Repository                     │      │
│    │         (Single Source of Truth)                │      │
│    └───────────────┬─────────────────┬───────────────┘      │
│                    │                 │                      │
│            ┌───────▼───────┐ ┌───────▼───────┐              │
│            │ Local Source  │ │ Remote Source │              │
│            │    (Room)     │ │ (Retrofit +   │              │
│            │               │ │  Firestore)   │              │
│            └───────────────┘ └───────────────┘              │
└─────────────────────────────────────────────────────────────┘
```

### RxJava Integration

- Reactive streams for handling asynchronous operations
- Seamless data flow between data sources and UI
- Efficient handling of network calls and database operations

---

## 🛠️ Tech Stack

| Category                 | Technology                                         |
|--------------------------|----------------------------------------------------|
| **Language**             | Java 11                                            |
| **Platform**             | Android SDK (Min SDK 24, Target SDK 36)            |
| **Architecture**         | MVP (Model-View-Presenter)                         |
| **Reactive Programming** | RxJava 3, RxAndroid                                |
| **Local Database**       | Room Database                                      |
| **Network**              | Retrofit 2 + Gson Converter                        |
| **Authentication**       | Firebase Auth (Email & Google)                     |
| **Cloud Storage**        | Firebase Firestore                                 |
| **Image Loading**        | Glide                                              |
| **Animations**           | Lottie                                             |
| **Navigation**           | Android Navigation Component + SafeArgs            |
| **UI**                   | Material Design Components, CardView, RecyclerView |
| **View Binding**         | Android View Binding                               |

---

## 📂 Project Structure

```
com.dmy.foodplannerapp/
│
├── 📱 MyApp.java                    # Application class
│
├── 📦 data/                         # Data Layer
│   ├── auth/
│   │   ├── local/                   # Local auth data source
│   │   ├── remote/
│   │   │   ├── data_source/         # Firebase Auth implementation
│   │   │   └── model/               # Auth credentials model
│   │   └── repo/                    # Auth repository
│   │
│   ├── db/
│   │   └── AppDatabase.java         # Room database configuration
│   │
│   ├── failure/                     # Error handling
│   │   ├── Failure.java
│   │   └── FailureHandler.java
│   │
│   ├── meals/
│   │   ├── local/
│   │   │   ├── daos/                # Room DAOs
│   │   │   └── data_source/         # Local data source
│   │   └── remote/
│   │       ├── firestore/           # Firestore data source
│   │       └── meals_data_source/   # API data source
│   │
│   ├── model/
│   │   ├── dto/                     # Data Transfer Objects (API responses)
│   │   ├── entity/                  # Room entities
│   │   └── mapper/                  # DTO to Entity mappers
│   │
│   └── network/
│       └── MealsNetwork.java        # Retrofit configuration
│
├── 🎨 presentation/                 # Presentation Layer
│   ├── auth/                        # Authentication screens
│   │   ├── presenter/
│   │   └── view/
│   │
│   ├── favourite/                   # Favorites feature
│   │   ├── presenter/
│   │   └── view/
│   │
│   ├── home/                        # Home screen
│   │   ├── categories_list_fragment/
│   │   ├── meal_of_the_day_fragment/
│   │   ├── suggested_meals_fragment/
│   │   └── view/
│   │
│   ├── main_activity/               # Main container
│   │   ├── presenter/
│   │   └── view/
│   │
│   ├── meal_profile/                # Meal details screen
│   │   ├── presenter/
│   │   └── view/
│   │
│   ├── plan/                        # Meal planning feature
│   │   ├── presenter/
│   │   └── view/
│   │
│   ├── search/                      # Search & filters
│   │   ├── presenter/
│   │   └── view/
│   │
│   ├── splash/                      # Splash screen
│   │   ├── presenter/
│   │   └── view/
│   │
│   └── user_profile/                # User profile
│       ├── presenter/
│       └── view/
│
└── 🔧 utils/                        # Utility classes
    ├── CustomSnackBar.java
    ├── NetworkObserver.java         # Connectivity monitoring
    └── TextFormField.java
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 11** or higher
- **Android SDK** 24+ (Android 7.0)
- **Firebase Account**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/MahmoudELDemerdash/food_planner_app.git
   cd food_planner_app
   ```

2. **Firebase Setup**
    - Go to [Firebase Console](https://console.firebase.google.com/)
    - Create a new project or use an existing one
    - Add an Android app with package name: `com.dmy.foodplannerapp`
    - Download `google-services.json` and place it in the `app/` directory
    - Enable **Authentication** (Email/Password and Google Sign-In)
    - Enable **Cloud Firestore**

3. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an existing project"
    - Navigate to the cloned repository folder
    - Wait for Gradle sync to complete

4. **Run the app**
    - Connect an Android device or start an emulator
    - Click the "Run" button or press `Shift + F10`

### Build APK

```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

The APK will be generated in `app/build/outputs/apk/`

---

## 🔑 Key Implementation Highlights

### Offline Support

- All favorite meals and meal plans are cached locally using **Room Database**
- `NetworkObserver` monitors connectivity status in real-time
- Automatic data sync when connection is restored

### Guest Mode

- Users can explore the app without creating an account
- Limited features: no favorites sync, no cloud backup
- Easy transition to full account when ready

### Data Synchronization

- **Firestore** handles real-time sync for authenticated users
- Favorites and meal plans are automatically backed up to the cloud
- **Access your data from any device** by signing into your account
- Conflict resolution for changes made while offline
- Seamless sync when switching between devices

### Reactive Data Flow

- **RxJava 3** manages all asynchronous operations
- Seamless data streaming from API/Database to UI
- Proper error handling and retry mechanisms

---

## 📄 API Reference

This app uses [**TheMealDB API**](https://www.themealdb.com/api.php) for meal data:

| Endpoint           | Description            |
|--------------------|------------------------|
| `/search.php?s=`   | Search meals by name   |
| `/filter.php?i=`   | Filter by ingredient   |
| `/filter.php?c=`   | Filter by category     |
| `/filter.php?a=`   | Filter by area/country |
| `/random.php`      | Get random meal        |
| `/categories.php`  | List all categories    |
| `/list.php?a=list` | List all areas         |
| `/list.php?i=list` | List all ingredients   |

---

## 👨‍💻 Author

**Mahmoud ELDemerdash**

[![GitHub](https://img.shields.io/badge/GitHub-MahmoudELDemerdash-181717?style=flat&logo=github)](https://github.com/MahmoudELDemerdash)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Mahmoud%20ELDemerdash-0A66C2?style=flat&logo=linkedin)](https://linkedin.com/in/mahmoud-eldemerdash)

---

<p align="center">
  Made with ❤️ by Mahmoud ELDemerdash
</p>
