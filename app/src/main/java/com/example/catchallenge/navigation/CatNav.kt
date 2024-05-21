package com.example.catchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catchallenge.presentation.breedsList.CatBreedsListScreen

@Composable
fun CatNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.BreedlistScreen,
    ){
        composable<Screens.BreedlistScreen>{
            CatBreedsListScreen()
        }


    }
}