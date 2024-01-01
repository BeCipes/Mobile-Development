<p align="center"><img align="center" src="https://raw.githubusercontent.com/BeCipes/Resources/main/assets/banner-becipes.png" alt="Becipes Logo"/></p>

![Github Actions](https://circleci.com/gh/BeCipes/Mobile-Development.svg?style=shield)
[![Issues](https://img.shields.io/github/issues/BeCipes/Mobile-Development)](https://github.com/BeCipes/Mobile-Development/issues)
[![Kotlin](https://img.shields.io/badge/kotlin-1.8.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-8-green?style=flat)](https://gradle.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![GitHub Account](https://img.shields.io/static/v1?label=GitHub&message=Becipes&color=C5116)](https://github.com/Becipes)

<hr>

# BeCipes - Mobile Development
A platform that makes it easy for you to get healthy food. Use this platform to help you easily serve healthy food, get information about nutritional health, and cooking techniques.

## Architecture
The project follows the MVVM (Model-View-ViewModel) design pattern with a Clean Architecture approach. This architecture promotes separation of concerns and provides a scalable and maintainable codebase. Here's an overview of the project's architecture:

- **Presentation Layer**: Contains the UI components, including activities and fragments. It interacts with the ViewModel layer to retrieve and display data to the user.

- **Domain Layer**: Contains the business logic of the application. It defines the various use cases and orchestrates the interactions between the data layer and the presentation layer.

- **Data Layer**: Contains the repositories and data sources responsible for retrieving and persisting data. It abstracts the underlying data storage implementation and provides a clean interface for the Use Case layer.

## Requirements
To build and run the BeCipes Android application, ensure you have the following software installed:

- Android Studio: 2022.3.1 (Giraffe)
- Minimum SDK: 24
- Target SDK: 33
- JDK: 17
- Kotlin: 1.8.0
- Android Gradle Plugin: 8.1.0
- Gradle: 8.0

## Getting Started
To build and run the project locally, follow these steps:

1. Clone the repository: `git clone https://github.com/BeCipes/Mobile-Development.git`
2. Open the project in Android Studio.
3. Ensure that the required SDK versions and dependencies are installed.
5. Add `BASE_URL=https://backend-development-becipes.fly.dev/api/` to `local.properties` file.
6. Build and run the app on an emulator or physical device.

Feel free to explore the codebase and make any contributions or improvements as needed. We appreciate your contributions to BeCipes!
