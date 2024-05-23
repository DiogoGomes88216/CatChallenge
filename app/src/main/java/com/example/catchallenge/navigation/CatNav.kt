package com.example.catchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catchallenge.presentation.breedDetails.BreedDetailsScreen
import com.example.catchallenge.presentation.breedsList.BreedsListScreen
import com.example.catchallenge.presentation.favourites.FavouritesScreen

@Composable
fun CatNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.BreedListScreen,
    ){
        composable<Screens.BreedListScreen>{
            BreedsListScreen(
                onNavigateToDetails = {id ->
                    navController.navigate(
                        Screens.BreedDetailsScreen(id = id)
                    )
                },
                onNavigateToFavourites = {
                    navController.navigate(
                        Screens.FavouritesScreen
                    )
                }
            )
        }

        composable<Screens.FavouritesScreen> {
            FavouritesScreen(
                onNavigateToDetails = { id ->
                    navController.navigate(
                        Screens.BreedDetailsScreen(id = id)
                    )
                },
                onNavigateToBreedsList = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screens.BreedDetailsScreen> {
            BreedDetailsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}