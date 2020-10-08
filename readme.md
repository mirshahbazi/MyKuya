# Machine Ventures Challenge


# Demo

![Hero Image](https://github.com/mirshahbazi/MyKuya/blob/master/demo/demo.gif)

# Task

We need you to implement MyKuya’s pages like home and map:

  - Design: Please implement all designs of the screen folder of the zip file. We've also shared a screen recording for you to better understand the User Experience
  - API: Create API call with mock data
  - Test: The code must have acceptable test coverage
  - Architecture: Use MVP

## Implementation
- Modular Base
- MVP (Model View Presenter)
- Clean Architecture
- Kotlin
- RxJava
- Navigation Component
- Koin
- JUnit4
- MockK
- Google Maps

## Description

Hello everyone! 👋 In this app I want to give a brief introduction to Modularization and why i used.

### First of all, what is App Modularization?
App Modularization is the process of applying Modular Programming to our Android App.

Modular programming is a software design technique to separate functionality into independent, interchangeable modules, so that each contains everything necessary to execute a specific functionality.
We can achieve this through the creation of Feature Modules.

### Why should I Modularize my app?

 - Faster build times.
 - Fine-grained dependency control.
 - Improve reusability across other apps.
 - Improves the ownership & the quality of the codebase.
 - Stricter boundaries when compared to packages.
 - Encourages Open Source of the newly created libraries.
 - Makes Instant Apps & Dynamic Features possible (improving discoverability).


### When should I Modularize my app?
When it makes sense but always having in mind that the goal is to separate functionality into independent, interchangeable modules.
The sooner, the better. This is to avoid higher amounts of refactoring later on. A similar experience that we might all have is jumping to our initial god activity projects and adding Presenters/Repositories into it.

### How does a Modularized this App look like?
![Hero Image](https://github.com/mirshahbazi/MyKuya/blob/master/demo/Diagram.png)

### Base Modules: Avoid a single Base Module.

 - Base Modules → (network, auth, cache, presentation).

 for more details :
 
 As you can see in diagram everything in this app controlled by modules.
 this is a Single activity project and uses `Navigation Component` to navigate between fragments.
 `base` module contains MVP files and `RxRule` for testing.
  every module can see the `base`.
 `data` module contains Repos of the project. we use `Remote` prefix for repositories that implement
 repos that calls API's (Mock API's).
 `map` module is a feature module and contains fragment and presenter for the Google Map stuff.
 `network` module is a place that you define your retrofit and services and make API calls.
 `service` module is a feature module for the home page.
 All the Presenters have `Unit Test`s. We use `MockK` because it is well suited for `Kotlin`. `MockK`
 enables us to easily mock our dependencies. We use `JUnit4` for assertions. Also, you can see
 some of the tests use `Stub` for testing.


### How to deal with multiple build.gradle files?

With App Modularization we want to gain fine-grained dependency control but we also need to make sure we don’t end up maintaining multiple configuration files.
These common gradle files are very useful for shared configurations, plugins & dependencies across different modules but it can also be a double-edged sword if overused. Having unused dependencies propagated to some modules that aren’t making use of them.

As you can see thanks to the common gradle files we ended up with a concise and small module.gradle.

My only advice when Modularizing is to use common sense trying to take only the near future into consideration.

### Finally

I tried to explain everything i used in my code but if you need to explained any thing i can help.
Thanks for reading this document.