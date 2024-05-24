package com.example.catchallenge.presentation.breedsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catchallenge.R
import com.example.catchallenge.presentation.componnents.BreedItem
import com.example.catchallenge.presentation.componnents.ErrorMessage
import com.example.catchallenge.presentation.componnents.ListScreen
import com.example.catchallenge.presentation.componnents.SearchBar
import kotlinx.coroutines.delay

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
        content = {innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                if(breedList.loadState.hasError && breedList.loadState.append.endOfPaginationReached){
                    ErrorMessage(
                        modifier = Modifier
                            .align(Alignment.Center),
                        message = stringResource(id = R.string.genericError),
                        retry = true,
                        onClick = {
                            viewModel.retry()
                        }
                    )
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 16.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(12.dp),
                        ){
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
                }
            }
        }
    )
}