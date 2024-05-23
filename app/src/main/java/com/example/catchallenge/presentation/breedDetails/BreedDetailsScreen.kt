package com.example.catchallenge.presentation.breedDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.catchallenge.R
import com.example.catchallenge.presentation.breedDetails.componnents.BioItem
import com.example.catchallenge.presentation.breedDetails.componnents.DescritpionItem
import com.example.catchallenge.presentation.breedDetails.componnents.TemperamentRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    viewModel: BreedDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.breedDetailsState.collectAsState()
    Text(text = uiState.breed.name)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.breedDetails),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavourite(uiState.breed) }) {
                        Icon(
                            imageVector = if(uiState.breed.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.favourites)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                AsyncImage(
                    model = uiState.breed.imageUrl,
                    contentDescription = uiState.breed.name,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            item {
                Text(
                    text = uiState.breed.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            item {
                BioItem(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(id = R.string.origin),
                    text = uiState.breed.origin
                )
            }
            item {
                BioItem(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = stringResource(id = R.string.lifeSpan),
                    text = "${uiState.breed.lifeSpan} ${stringResource(R.string.years)}"
                )
            }
            item {
                val temperaments = uiState.breed.temperament.replace(" ","").split(",")
                TemperamentRow(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    temperaments = temperaments
                )
            }
            item { 
                DescritpionItem(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    description = uiState.breed.description
                )
            }
        }
    }
}