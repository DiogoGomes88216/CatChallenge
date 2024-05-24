package com.example.catchallenge.presentation.breedsList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catchallenge.R
import com.example.catchallenge.presentation.componnents.BreedItem
import com.example.catchallenge.presentation.componnents.ListScreen
import com.example.catchallenge.presentation.componnents.SearchBar

@Composable
fun BreedsListScreen(
    viewModel: BreedsListViewModel = hiltViewModel(),
    onNavigateToDetails: (id: String) -> Unit,
    onNavigateToFavourites: () -> Unit
) {
    val uiState by viewModel.breedsListState.collectAsState()
    val breedList = viewModel.breedPagingFlow.collectAsLazyPagingItems()

    ListScreen(
        title = stringResource(id = R.string.catBreeds) ,
        bottomItemIndex = 0,
        onChange = {
            onNavigateToFavourites()
        },
        actions = {
            if(uiState.isSearchShowing) {
                SearchBar(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    query = uiState.search,
                    onQueryChange = {
                        viewModel.setSearch(it)
                    },
                    onCancelSearch = {
                        viewModel.clearSearch()
                        viewModel.toggleIsSearchShowing()
                    }
                )
            } else {
                IconButton(onClick = {
                    viewModel.toggleIsSearchShowing()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
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
                            viewModel.saveBreed(breed)
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