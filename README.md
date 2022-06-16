# BeautyLab - Android

Sample app for a fashion/beauty store showcasing @Nek-12's app development approach, FlowMVI implementation and AndroidUtils usage.

* The app has no design. I'm not a designer, so please don't blame me for poor design :)
* Initial load may take 40 seconds due to backend spinup. The app will show you a "not connected" message once. Just wait a bit and reload the screen.
* Some screens lack loading statuses, sorry about that. There are also some corners cut on the BE side.
* I used layer-based modularization here, and features are all in app module. Since then, I moved on to feature-based modularization.
* Your transactions are NOT processed, this is not a real store! You can add money for yourself infinitely.

Sample account to use:
```
Login: Nek12
Password: 1234
```
It may be deleted though if anything is cleaned up due to free backend hosting plans, just create a new account, you don't need to input your personal data and we don't store your passwords.

Stack:  
* Jetpack Compose + Accompanist + Material2
* Modularization (layer-based)
* Kotlin coroutines
* Arrow (Functional Programming) - for form validation
* [FlowMVI](https://github.com/Nek-12/FlowMVI)
* [AndroidUtils](https://github.com/Nek-12/AndroidUtils)
  * Used here: ApiResult, Compose extensions, android extensions, core, and some other.
* Ktor Client
* Jetpack Paging
* [Compose Destinations](https://composedestinations.rafaelcosta.xyz/) (Navigation)
* No tests here, but I generally use Kotest/Kaspresso.
