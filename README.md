 # 🎬 Alpha Player

---

## 📱 Overview

**Alpha Player** is a modern Android media streaming application built using the latest Android development technologies. It provides a clean and responsive user experience while supporting online media playback through **M3U and JSON playlists**.

The application follows modern Android architecture using **MVVM, Jetpack Compose, Firebase, Coroutines, StateFlow, and Media3**.

---

## ✨ Features

* 🎥 Online Video Streaming
* 📺 M3U Playlist Support
* 📄 JSON Playlist Support
* 🔍 Search Movies
* ❤️ Watchlist
* 👤 Firebase Authentication
* ☁️ Cloud Firestore Integration
* ⚡ Media3 (ExoPlayer)
* 🎨 Material 3 UI
* 📱 Responsive Jetpack Compose Interface
* 🧩 MVVM Architecture
* ⚙️ Repository Pattern
* 🔄 StateFlow & Coroutines
* 🖼️ Async Image Loading
* 🚀 Optimized Performance

---

## 🛠 Tech Stack

| Technology                  | Description               |
| --------------------------- | ------------------------- |
| **Kotlin**                  | Programming Language      |
| **Jetpack Compose**         | Modern Android UI Toolkit |
| **Material 3**              | UI Design System          |
| **Firebase Authentication** | User Authentication       |
| **Cloud Firestore**         | Cloud Database            |
| **Media3 (ExoPlayer)**      | Video Playback            |
| **Kotlin Coroutines**       | Asynchronous Programming  |
| **StateFlow**               | Reactive State Management |
| **Navigation Compose**      | Application Navigation    |
| **Coil**                    | Image Loading             |

---

## 🏗 Architecture

```text
                Presentation
              Jetpack Compose
                     │
                     ▼
                 ViewModel
                     │
                     ▼
                    MVVM
                     │
                     ▼
              Repository Layer
                     │
             ┌───────┴────────┐
             ▼                ▼
         Firebase          Remote API
             │                │
             └───────┬────────┘
                     ▼
                M3U / JSON Data
```

---

## 📂 Project Structure

```text
app
│
├── data
│   ├── model
│   ├── repository
│   └── parser
│
├── ui
│   ├── screens
│   ├── components
│   └── theme
│
├── navigation
│
├── viewmodel
│
├── utils
│
└── MainActivity.kt
```

---

# 📸 Screenshots

## 🔐 Login Screen

<p align="center">
  <img src="https://github.com/suryakushwaha3/Alpha_Player/blob/master/login%20page.png?raw=true" width="280" alt="Alpha Player Login Screen">
</p>

---

## 📝 Sign Up Screen

<p align="center">
  <img src="https://github.com/suryakushwaha3/Alpha_Player/blob/master/sign_page.png?raw=true" width="280" alt="Alpha Player Sign Up Screen">
</p>

---

## 🏠 Home Screen

<p align="center">
  <img src="https://github.com/suryakushwaha3/Alpha_Player/blob/master/HomeScreen.png?raw=true" width="280" alt="Alpha Player Home Screen">
</p>

---

## 👤 Profile Screen

<p align="center">
  <img src="screenshots/profile.png" width="280" alt="Alpha Player Profile Screen">
</p>

---

## 🎬 Movies Screen

<p align="center">
  <img src="screenshots/movies.png" width="280" alt="Alpha Player Movies Screen">
</p>

---

## ▶️ Player Screen

<p align="center">
  <img src="screenshots/player.png" width="280" alt="Alpha Player Video Player Screen">
</p>

---

# 🚀 Getting Started

## 1. Clone Repository

```bash
git clone https://github.com/suryakushwaha3/Alpha_Player.git
```

## 2. Open Project

Open the project using **Android Studio**.

## 3. Firebase Setup

1. Create a Firebase Project.
2. Enable **Firebase Authentication**.
3. Enable **Cloud Firestore**.
4. Download `google-services.json`.
5. Place the file inside the `app/` folder.

> ⚠️ Never upload private API keys or sensitive credentials to GitHub.

## 4. Run

Build and run the application on an Android device or emulator.

---

# 📋 Requirements

* Android Studio — Latest Stable
* Android SDK 24+
* Kotlin
* JDK 17+
* Firebase Project
* Internet Connection

---

# 📦 Main Dependencies

* Jetpack Compose
* Material 3
* Firebase Authentication
* Cloud Firestore
* Media3 (ExoPlayer)
* Coil
* Navigation Compose
* Kotlin Coroutines
* StateFlow

---

# 🎯 Future Improvements

* 📥 Download Manager
* 📡 Offline Playback
* 📺 Chromecast Support
* ▶️ Continue Watching
* ❤️ Favorites Sync
* 🔎 Advanced Search Filters
* 💬 Subtitle Support
* 🖼️ Picture-in-Picture (PiP)
* 📋 Multiple Playlist Support

---

# 🤝 Contributing

Contributions, issues, and feature requests are welcome.

If you'd like to improve the project, feel free to **fork the repository** and submit a **pull request**.

---

# 📄 License

This project is licensed under the **MIT License**.

See the [LICENSE](LICENSE) file for more information.

---

# 👨‍💻 Developer

### Alpha Player

**Android Developer**

`Kotlin` • `Jetpack Compose` • `Firebase` • `Media3` • `MVVM`

---

## ⭐ Support

If you like this project, don't forget to give it a ⭐ **Star** on GitHub!
