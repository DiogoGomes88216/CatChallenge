package com.example.catchallenge.presentation.breedsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catchallenge.R
import com.example.catchallenge.domain.models.Breed
import com.example.catchallenge.presentation.breedsList.componnents.BottomNavigationBar
import com.example.catchallenge.presentation.breedsList.componnents.BreedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsListScreen(
    viewModel: BreedsListViewModel = hiltViewModel(),
    onNavigateToDetails: (id: String) -> Unit
) {
    val breedList = viewModel.breedPager.collectAsLazyPagingItems()
    val uiState by viewModel.breedsListState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomNavigationBar(
                selectedIconIndex = uiState.selectedBottomItemIndex,
                onChange = {index ->
                    viewModel.changeBottomBar(index)
                }
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.catBreeds),
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(innerPadding),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(12.dp),
        ){
            items(
                count = breedList.itemCount,
                key = {
                    breedList[it]?.name ?: it
                }
            ){pos ->
                breedList[pos]?.let {breed ->
                    BreedItem(
                        breed = breed,
                        onClick = {
                            onNavigateToDetails(breed.id)
                        },
                        onToggleFavourite = {

                        }
                    )
                }
            }
        }
    }
}