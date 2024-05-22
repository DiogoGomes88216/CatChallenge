package com.example.catchallenge.presentation.breedsList.componnents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.catchallenge.R
import com.example.catchallenge.domain.models.Breed

@Composable
fun BreedItem(
    modifier: Modifier = Modifier,
    breed: Breed,
    showLifeSpan: Boolean = false,
    onClick: () -> Unit,
    onToggleFavourite: () -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        AsyncImage(
            model = breed.imageUrl,
            contentDescription = breed.name,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(){
                Text(
                    modifier = Modifier
                        .padding(horizontal = 6.dp),
                    text = breed.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                if(showLifeSpan){
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 6.dp),
                        text = "${breed.lifeSpan} Years",
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onToggleFavourite() }
            ) {
                Icon(
                    imageVector = if(breed.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        }


    }
}