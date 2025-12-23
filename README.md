# TaskHolic: Productivity App

TaskHolic is an Android app that helps you stay productive by combining task management, a focus timer, and daily motivation in one place. The app is simple, fast, and easy to use.

## Core Features

### 1. Task Management

- Task Lists: Create different lists to organize your tasks  
- Task Control: Add tasks, mark them as done, or delete them  
- Offline Access: Tasks are stored on the device and available without internet

### 2. Pomodoro Timer

Stay focused using the Pomodoro technique.

- 25-Minute Sessions: Work in short focused intervals with breaks  
- Simple Controls: Start, pause, and reset the timer easily  
- Saved State: The timer continues correctly when you leave the screen

### 3. Motivational Quotes

Get motivation when you need it.

- Random Quotes: A new quote is loaded from an online API  
- Clean Design: Quotes are shown clearly with the author  
- Offline Support: The last quote is saved and shown without internet

## Technical Overview

The app is built with modern Android tools and follows clean architecture principles.

- Architecture: MVVM  
  The app separates UI, logic, and data to keep the code clean and easy to maintain.

- Concurrency: Kotlin Coroutines  
  Used for background tasks like loading data and network requests.

- Networking: Retrofit and Firebase  
  Retrofit loads motivational quotes. Firebase is used to sync tasks and task lists.

- Local Storage: Room Database  
  All data is stored locally to support offline usage.

- Dependency Management: Hilt  
  Hilt is used to provide repositories, databases, and services.

- Version Control: Git  
  Git is used to track changes and manage development.
