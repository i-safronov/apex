<h1 align="center">Apex welcomes you</h1>
<h6 align="center">A library that fundamentally changes the way Android applications are developed ⚔🔥</h6>

![apex-logo 2](https://github.com/user-attachments/assets/d61faf55-7feb-45fb-aaf3-c4551ea69a5e)

### How to connect the library:
1. `build.gradle.kts (:app)`
```Kotlin
dependencies { 
    //...
    val apexVersion = "1.0.2" // or newer
    implementation("com.safronov.apex:architecture:$apexVersion")
    //...   
}
```
2. `settings.gradle.kts`
```Kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) 
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/i-safronov/apex")
        }
    }
}
```
3. Synchronize the project by clicking on the `Sync Now` button

### What is Apex?

<img src="https://github.com/user-attachments/assets/f0aefb60-a32c-4b80-a8bb-c9aec0d85f64" alt="apex-logo" style="width: 100%;">

The diagram presents the architecture of the interaction between the user interface (UI) and the ViewModel, which helps structure data processing and state management within the application. The central component in this system is the State — an object that holds data and the current UI state, dynamically changing based on user actions and the app's internal logic. State allows control over which data is displayed on the screen and in what form, ensuring synchronization with the user interface. The UI layer monitors State changes and updates interface components so that displayed information remains current and consistent with the data.

When a user interacts with the interface, for example by pressing a button, the UI layer sends an EX (Execute Command), which is intercepted by the Executor component. This component plays a key role in the architecture, as it is responsible for executing logic, updating the State, and processing commands. The Executor operates in the UI thread, so its tasks are limited to lightweight operations such as updating data or toggling element states. This approach supports a high responsiveness in the interface, avoiding blocks and delays.

In addition to state changes, the Executor can initiate two types of additional processes: Effect and Event.

Effect is a mechanism for carrying out complex or long-term operations that go beyond simple state changes and require background thread processing. These operations may include server requests, complex calculations, graph construction, or database operations. The Executor delegates Effect execution to auxiliary components, allowing them to work in parallel without blocking the main UI thread. After completion, an Effect can send one or more EX commands back to the Executor, which updates the State based on the operation's results. This enables asynchronous interactions where lengthy processes do not disrupt the core app functions or impact its performance.

Event represents messages sent to the UI layer, intended to trigger specific actions in the interface, such as displaying notifications, opening dialogs, or showing pop-ups. Event is used to send immediate instructions from the Executor that require a visual response in the interface. Unlike State, which is persistently tracked, an Event is a one-time signal that is not retained. For instance, upon operation completion, the Executor can send an Event prompting the UI to display a success message or show an error if something went wrong.

This architecture enables the separation of data and event handling logic from the user interface, making the code more structured, modular, and easier to test. Each element fulfills a clearly defined role: State stores data and state, Executor executes simple logic and is responsible for updating State, Effect delegates long-running processes, and Event manages the UI's response to events.
