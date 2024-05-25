package com.example.catchallenge.presentation.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catchallenge.R
import com.example.catchallenge.presentation.componnents.BreedItem
import com.example.catchallenge.presentation.componnents.ErrorMessage
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
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                if(uiState.hasError){
                    ErrorMessage(
                        modifier = Modifier
                            .align(Alignment.Center),
                        message = stringResource(id = R.string.genericError),
                        retry = false,
                    )
                } else if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 16.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(12.dp),
                    ){
                        items(
                            count = uiState.favourites.size,
                        ){pos ->
                            uiState.favourites[pos].let {breed ->
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
                }
            }
        }
    )
}