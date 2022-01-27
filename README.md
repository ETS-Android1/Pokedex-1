
# Pokedex (java)

An Android application to search and view the info of every Pokémon. This app developed with Paging3 library, Retrofit2, LiveData, DataBinding, Material Design2 and Hilt based on MVVM architecture.


## Screenshots

![App Screenshot](https://github.com/MkSoo01/Pokedex/screenshots/screenshot.PNG) ![App Screenshot](https://github.com/MkSoo01/Pokedex/screenshots/screenshot2.PNG)


## Tech Stack & Open-source libraries
- JetPack
    - DataBinding - binds UI components in layouts to data sources using a declarative format rather than programmatically
    - LiveData - data holder that notify observers that are in active lifecycle state on data changes
    - Navigation component - handle the in-app navigation
    - Paging3 - load and display pages of data from local storage or over network
    - ViewModel - UI-related data holder and lifecycle aware
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - used for dependency injection
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data
- [Material Design2](https://github.com/material-components/material-components-android)
- [Glide](https://github.com/bumptech/glide) - used for image loading
- [Mockito](https://github.com/mockito/mockito) - create test double in automated unit tests
- [LeakCanary](https://square.github.io/leakcanary/getting_started/) - used to detect memory leak
- [Awaitility](https://github.com/awaitility/awaitility) - used for testing the asynchronous methods


## Open API

Pokedex is using [PokéApi](https://pokeapi.co/docs/v2#pokemon-section) to get all the Pokémon information.


## Demo

![App Demo](https://github.com/MkSoo01/Pokedex/demo/demo1.gif) ![App Demo](https://github.com/MkSoo01/Pokedex/demo/demo2.gif)


## Acknowledgements

 - [Jetpack MVVM Best Practice](https://github.com/KunMinX/Jetpack-MVVM-Best-Practice)
 - [Android Architecture Blueprints v2](https://github.com/android/architecture-samples/tree/todo-mvvm-databinding)
 - [Paging3Java](https://github.com/ahsan-malik/Paging3Java)

## License

```
Copyright 2022 MkSoo01

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.