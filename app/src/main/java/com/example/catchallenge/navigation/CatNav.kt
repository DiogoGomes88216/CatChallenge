package com.example.catchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catchallenge.presentation.breedDetails.BreedDetailsScreen
import com.example.catchallenge.presentation.breedsList.BreedsListScreen

@Composable
fun CatNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.BreedlistScreen,
    ){
        composable<Screens.BreedlistScreen>{
            BreedsListScreen(
                onNavigateToDetails = {id ->
                    navController.navigate(
                        Screens.BreedDetailsScreen(id = id)
                    )
                }
            )
        }
        composable<Screens.BreedDetailsScreen>(
        ){
            BreedDetailsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}