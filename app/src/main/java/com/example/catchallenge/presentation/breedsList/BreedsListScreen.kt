package com.example.catchallenge.presentation.breedsList

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catchallenge.R
import com.example.catchallenge.presentation.componnents.BreedItem
import com.example.catchallenge.presentation.componnents.ListScreen

@Composable
fun BreedsListScreen(
    viewModel: BreedsListViewModel = hiltViewModel(),
    onNavigateToDetails: (id: String) -> Unit,
    onNavigateToFavourites: () -> Unit
) {
    val breedList = viewModel.breedPagingFlow.collectAsLazyPagingItems()

    ListScreen(
        title = stringResource(id = R.string.catBreeds) ,
        bottomItemIndex = 0,
        onChange = {
            onNavigateToFavourites()
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        },
        content = {
            items(
                count = breedList.itemCount,
            ){pos ->
                breedList[pos]?.let {breed ->
                    BreedItem(
                        breed = breed,
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