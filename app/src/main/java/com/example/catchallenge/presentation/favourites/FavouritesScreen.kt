package com.example.catchallenge.presentation.favourites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catchallenge.R
import com.example.catchallenge.presentation.componnents.BreedItem
import com.example.catchallenge.presentation.componnents.ListScreen

@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel = hiltViewModel(),
    onNavigateToDetails: (id: String) -> Unit,
    onNavigateToBreedsList: () -> Unit
) {
    val uiState by viewModel.favouritesState.collectAsState()

    ListScreen(
        title = stringResource(id = R.string.favourites) ,
        bottomItemIndex = 1,
        onChange = {
            onNavigateToBreedsList()
        },
        content = {
            items(
                count = uiState.favourites.size,
            ){pos ->
                uiState.favourites[pos].let { breed ->
                    BreedItem(
                        breed = breed,
                        showLifeSpan = true,
                        onClick = {
                            onNavigateToDetails(breed.id)
                        },
                        onToggleFavourite = {
                            viewModel.toggleFavourite(breed)
                        }
                    )
                }
            }
        }
    )
}