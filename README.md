# Android CrunchBase company listings

This project is an implementation of [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) + [FRP](https://en.wikipedia.org/wiki/Functional_reactive_programming) architecture in Kotlin simply using Activities and Fragments, using RxJava/RxKotlin to connect the views to viewModels.

I' m using a few open source libraries to help manage state as
 - Reactive
  - [RxJava](https://github.com/ReactiveX/RxJava)
  - [RxKotlin](https://github.com/ReactiveX/RxKotlin)
  - [RxLifecycle](https://github.com/trello/RxLifecycle) (manage observables lyfecicle)
 - Network
  - [Retrofit](https://github.com/square/retrofit)
  - Retrofit [adapter](https://github.com/square/retrofit/wiki/Call-Adapters) RxJava
  - Retrofit [converter](https://github.com/square/retrofit/wiki/Converters) GSON
  - [Glide](https://github.com/bumptech/glide) (image loading)
- [Parcelable](https://github.com/nsk-mironov/smuggler) helper to make data classes parcelabe

