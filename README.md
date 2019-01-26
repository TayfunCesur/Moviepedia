# Moviepedia

This is my personal project to improve my skills and learn much detail about [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/). If you are Android Dev and don't hear this till now, you must hurry up to learn it :)

![enter image description here](https://firebasestorage.googleapis.com/v0/b/events-c4167.appspot.com/o/rsz_group.png?alt=media&token=7947ce64-7799-410b-86e4-faaf3f87ad1b)

# Libraries

 - [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)
 - [Android Data Binding](https://developer.android.com/topic/libraries/data-binding/)
 - [Dagger](https://google.github.io/dagger/)
 - [Retrofit](http://square.github.io/retrofit/)
 - [Glide](https://github.com/bumptech/glide)
 - [EventBus](https://github.com/greenrobot/EventBus)
 - [Rx](https://github.com/ReactiveX/RxJava)
 - [Lottie](https://github.com/airbnb/lottie-android)
 - [LeakCanary](https://github.com/square/leakcanary)
 - [Mockito](https://github.com/mockito/mockito)
 - [Fastlane](https://github.com/fastlane/fastlane)

# Structure

This project written by Kotlin Language and uses MVVM architecture.

## Known Issues

 - There is some memory leak on Android Data Binding Library. Even if I clear manually when onDestroy, I didn't figure it out yet. Here is the issue ([Data Binding Memory Leak](https://github.com/square/leakcanary/issues/1137))
 - If you want to use fastlane to automate building and releasing or etc, I guess you don't want to use Data Binding, because here is the issue about [ProcessDataBinding issue](https://github.com/googlesamples/android-architecture-components/issues/427)

## Submission
### - Explain the project in general and how to run it.
This project uses [TheMovieDB Api](https://www.themoviedb.org/documentation/api). It shows you the movies in three different categories. There are Top Rated, Upcoming and Now Playing categories and the other step is 
when you click on some movie, it takes the selected movie id and gets the detail of that movie. 
If you want to run this project, just clone it and put your API KEY to build.gradle file. There is a variable that is called API_KEY. You must put your api key to there. You can get your api key from [here](https://www.themoviedb.org/)

### - Why you have selected that software architecture?
This project uses the Android MVVM architecture. In order to support both orientation of this project, prevent possible memory leaks or weird behaviors, the lifecycle awareness is very needed. The ViewModel component takes care of that responsibility via its lifecycle owner(Activities or Fragments).

### - Which software design principles you have followed in your project?
SRP and Dependency Inversion via [Dagger](https://google.github.io/dagger/)


### - Any assumptions/comments/notes about any particular decision?
I faced a few high level problems about Data Binding. Both of these issues are still open. I'll follow that issues but no idea when they resolve. You can read Known Issues part to details. 

### - What would you change if you had more time?
I must have used the official way that is called [Android Paging Library](https://developer.android.com/topic/libraries/architecture/paging/) to infinite scroll but didn't have any experience and also didn't have much time to research and implement it. I'll work on it ASAP.

### - What to do to make the app production ready?
 

 - Didn't write UI tests of this project. It must be done before production. 
 - Fastlane scripts must be compatible to apk singing.
 - Researching of generating app bundle with Fastlane.
 - Minified apk must test on manually. Apk with proguard, may have some strange behavior.
 
## Does your project require any particular tool to be able to run?

No. Just get your api key from [here](https://www.themoviedb.org/). Clone this project to Android Studio and paste your key. Then you are good to go!

## Licence
```
Copyright 2019 Tayfun CESUR

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
