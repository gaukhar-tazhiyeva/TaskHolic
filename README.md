# TaskHolic: Productivity & Focus App

TaskHolic is a comprehensive Android application designed to boost productivity by combining task management, a focus timer, and daily motivation into a single, intuitive interface. Built with modern Android development practices, it provides a seamless and efficient user experience.

## Core Features

### 1. Task Management (To-Do List)
Organize your life with a powerful and easy-to-use to-do list.

*   **Task Lists:** Create multiple lists to categorize your tasks (e.g., "Work," "Personal," "Groceries").
*   **Add & Manage Tasks:** Quickly add new tasks to any list, mark them as complete, or delete them.
*   **Offline Support:** All your tasks and lists are saved locally, so you can manage them even without an internet connection. Changes are automatically synced when you go online.

### 2. Pomodoro Timer
Enhance your focus and manage your time effectively using the built-in Pomodoro timer.

*   **Classic Technique:** Work in focused 25-minute intervals, separated by short breaks, to maximize concentration and prevent burnout.
*   **Simple Interface:** Start, pause, and reset the timer with clean and simple controls.
*   **Persistent State:** The timer state is saved, so it continues accurately even if you navigate away from the app.

### 3. Motivational Quotes
Get a fresh dose of inspiration every day to keep you motivated.

*   **Random Daily Quote:** The app fetches a new motivational quote from a remote API each time you visit the quote screen.
*   **Clean UI:** Displays the quote and its author in a clear, easy-to-read format.
*   **Offline Cache:** The last fetched quote is saved locally, ensuring you always have a motivational message ready, even when offline.

## Technical Architecture & Implementation

This project is built using modern, industry-standard tools and a robust architecture to ensure it is scalable, maintainable, and efficient.

*   **Architecture: MVVM (Model-View-ViewModel)**
    *   The app follows a clear MVVM pattern, separating the UI (Views), UI logic and state (ViewModels), and data handling (Model/Repository). This creates a loosely-coupled and testable codebase.

*   **Asynchronous Programming: Kotlin Coroutines**
    *   All asynchronous operations, such as network requests and database access, are handled efficiently using Kotlin Coroutines. This keeps the UI responsive and smooth.

*   **Networking: Retrofit & Firebase**
    *   **Retrofit:** Used for fetching motivational quotes from a third-party REST API.
    *   **Firebase Realtime Database:** The `FirebaseApiService` leverages Retrofit to act as a client for the Firebase Realtime Database, enabling real-time synchronization of tasks and task lists.

*   **Offline Mode: Room Database**
    *   The app implements an offline-first approach using the **Room Persistence Library**. All tasks, lists, and quotes are stored in a local SQLite database, providing seamless access to data even without an internet connection.

*   **Dependency Injection: Hilt**
    *   Hilt is used to manage dependencies throughout the application, simplifying the creation and provision of objects like the `TaskRepository`, Daos, and API services.

*   **Version Control: Git**
    *   The project is managed using Git, with a structured approach to branching and commits, facilitating organized development and code reviews.
