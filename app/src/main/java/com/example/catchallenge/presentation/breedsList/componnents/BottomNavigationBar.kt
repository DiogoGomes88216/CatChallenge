package com.example.catchallenge.presentation.breedsList.componnents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.catchallenge.R

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedIconIndex: Int,
    onChange: (index: Int) -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.breeds),
            selectedIcon = ImageVector.vectorResource(R.drawable.icon_cat_filled),
            unselectedIcon = ImageVector.vectorResource(R.drawable.icon_cat_outlined),
        ),
        BottomNavigationItem(
            title = stringResource(R.string.favourites),
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Filled.FavoriteBorder,
        )
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIconIndex == index,
                onClick = {
                    if(index != selectedIconIndex)
                        onChange(index)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedIconIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}