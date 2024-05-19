package com.example.catchallenge.presentation.CatBreedsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.catchallenge.domain.models.Breed
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun CatBreedsListScreen(
    viewModel: CatBreedsListViewModel = hiltViewModel(),
) {
    val breedList = viewModel.breedPager.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.padding(innerPadding)
        ){
            items(
                count = breedList.itemCount,
                key = {
                    breedList[it]?.name ?: it
                }
            ){pos ->
                breedList[pos]?.let {
                    Text(
                        text = it.name,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}