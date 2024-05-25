
# Cat Breeds App
![joined](https://github.com/DiogoGomes88216/CatChallenge/assets/51420877/d4d0c5f9-7514-48da-ae68-195cda4c1c0e)

## Overview

The Cat Breeds App is an Android application built using Kotlin that interacts with the Cat API (https://thecatapi.com/) to display information about different cat breeds. The app follows the MVVM architecture and utilizes Jetpack Compose for UI building. It provides functionalities for listing cat breeds, searching and marking them as favorites, viewing detailed breed information, and offline data persistence using Room.

## Features

1. **Cat Breeds List**:
   - Displays a list of cat breeds with images and breed names.
   - Includes a search bar to filter breeds by name.
   - Provides a button to mark breeds as favorites.

2. **Favorite Breeds**:
   - Shows a list of favorite cat breeds.
   - Displays the average lifespan of all favorite breeds.

3. **Breed Details**:
   - Detailed view of a selected breed including name, origin, temperament, and description.
   - Button to add or remove the breed from favorites.

## Development Strategy (Steps Taken)

1. **Project Setup**:
   - Initialize the project with necessary dependencies including Retrofit, Room, Jetpack Compose, and Hilt.
   - Set up the Package Structure.

2. **Pagination**:
   - Started By creating data and domain componnents for pagination
   - Added presentation elements (ViewModel and ComposeScren) for pagination (Quick Check if Pagination is working correctly)
   - Added Authorization Key Interceptor which changed a bit of the response from the API
  
3. **BreedListScreen & BreedDetailsScreen**:
   - Added common composables.
   - Added ViewModels and compose screens.
   - Added Navigation with Kotlin serialization
   - Implement Repositories to interact with the Cat API and Room database.

4. **Favourites Feature**:
   - Added favourites Entity to database.
   - Added ViewModel and compose screen.
   - Integrated with existing screens (Was not Final)

6. **Search Feature**:
   - Used the Cat Api "breeds/search" to search.
   - Integrated with Pagination using a different Paging Source (Was not Final)

7. **Fixes**:
   - Favourite Icons where not updating correctly so "isFavourite" was addded to BreedEntity
   - Used Flows from FavouritesDao to make the Ui update Automaticaly
   - Search was integrated into the existing RemoteMediator, removing the paging source.(fixed problems with showing details of searched breeds)
   - Forgot cachedIn(ViewModelScope) which was having weird effects on recompositions.

6. **Testing**:
   - Added Unit tests for ViewModels, Daos and Repository.
