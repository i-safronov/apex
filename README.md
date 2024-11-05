<h1 align="center">Apex welcomes you</h1>
<h6 align="center">A library that fundamentally changes the way Android applications are developed ⚔🔥</h6>

### Connecting the library using build.gradle.kts
```Kotlin
    implementation("com.safronov.apex.architecture:1.0.0")
```

<img src="https://github.com/user-attachments/assets/f0aefb60-a32c-4b80-a8bb-c9aec0d85f64" alt="apex-logo" style="width: 100%;">

### Description of the architecture
An Android application is always built around interaction with the user. This is managed by the UI layer, which contains all our buttons, text, and other components. These elements play an essential role, and when the user interacts with them, the UI layer sends an EX, which represents the user’s action. The execute method in the implementation of APEXViewModel performs the user’s intent; it can also send an event that the UI layer will receive and execute the necessary actions (such as displaying a Toast message). However, its primary role is to update the state and send an effect. The affect method receives the sent effect and performs the corresponding actions. This method runs in the IO thread, allowing it to request data from the network, databases, and perform complex operations. Once its task is complete, the affect method can optionally send an EX.
