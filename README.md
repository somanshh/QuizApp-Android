# Quiz App

A modern quiz application built with Kotlin, Jetpack Compose, and Firebase Firestore. This app provides users with a simple and intuitive interface to test their knowledge on various topics.

## Features

- Clean, modern UI built with Jetpack Compose
- Question data stored in Firebase Firestore
- Navigation between welcome, quiz, and results screens
- Real-time score tracking
- Progress indicator showing question progress
- Support for multiple-choice questions
- Ability to play again after completing the quiz

## Screenshots

*[Place screenshots of your app here]*

## Tech Stack

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern UI toolkit for Android
- **Firebase Firestore** - Cloud-based NoSQL database
- **Navigation Compose** - For handling app navigation
- **Material 3** - For implementing Material Design
- **ViewModel & StateFlow** - For state management
- **Coroutines** - For asynchronous operations

## Setup Instructions

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or newer
- Firebase account
- JDK 11 or later

### Firebase Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Create a new project
3. Add an Android app to your Firebase project:
   - Use your app's package name (com.example.quizapp)
   - Download the `google-services.json` file
   - Place it in the app directory
4. Enable Firestore Database:
   - Go to "Firestore Database" in the Firebase Console
   - Click "Create Database"
   - Choose "Start in test mode" (for development)
   - Select a location and click "Enable"

### Project Setup

1. Clone the repository: `git clone https://github.com/yourusername/quiz-app.git`

   2. Open the project in Android Studio

3. Add Firebase dependencies to your `build.gradle` files:
- In project-level build.gradle, add:
  ```gradle
  classpath 'com.google.gms:google-services:4.3.15'
  ```
- In app-level build.gradle, add:
  ```gradle
  implementation 'com.google.firebase:firebase-firestore-ktx:24.5.0'
  apply plugin: 'com.google.gms.google-services'
  ```

4. Sync your project with Gradle files

5. Build and run the application

## Database Structure

The app uses the following Firestore structure:

Collection: `questions`
Documents: Question IDs (1, 2, 3, etc.)
Fields:
- `id` (number): Question identifier
- `questionText` (string): The text of the question
- `options` (array): List of possible answers
- `correctAnswerIndex` (number): Index of the correct answer (0-based)

## Adding Custom Questions

You can add custom questions through:

1. **Firebase Console**:
- Navigate to Firestore Database
- Select the "questions" collection
- Click "Add Document"
- Set the document ID (sequential number)
- Add the required fields:
  - id (number)
  - questionText (string)
  - options (array of strings)
  - correctAnswerIndex (number)

2. **In the App Code**:
- Modify the `getSampleQuestions()` function in `FirebaseQuizRepository.kt`


## Acknowledgments

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Material Design](https://material.io/design)
