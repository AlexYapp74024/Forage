package com.example.forage.feature_forage.presentation.item_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.model.exampleForageItem
import com.example.forage.feature_forage.presentation.destinations.EditForageItemScreenDestination
import com.example.forage.feature_forage.presentation.util.BitmapWithDefault
import com.example.forage.feature_forage.presentation.util.ForageTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: ForageItemDetailViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun ForageItemDetailScreen(
    itemId: Int,
    navigatorIn: DestinationsNavigator,
    viewModelIn: ForageItemDetailViewModel = hiltViewModel(),
) {
    navigator = navigatorIn
    viewModel = viewModelIn

    viewModel.retrieveItem(LocalContext.current, itemId)
    ForageItemDetailScreen()
}

@Composable
fun ForageItemDetailScreen() {
    val item by viewModel.item
    ForageItemDetailContent(item)
}

@Preview
@Composable
fun ForageItemDetailPreview() {
    ForageItemDetailContent(
        ForageItemWithImage(exampleForageItem)
    )
}

@Composable
fun ForageItemDetailContent(
    itemWithImage: ForageItemWithImage,
    modifier: Modifier = Modifier
) {
    val item = itemWithImage.item

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            EditItemFloatingActionButton(floatingActionBtnOnClick = {
                navigator.navigate(
                    EditForageItemScreenDestination(
                        item.id
                    )
                )
            })
        },
        topBar = {
            ForageTopAppBar(
                Title = "Details",
                canNavigateBack = true,
                navigateUp = {
                    navigator.navigateUp()
                },
            )
        }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val bitmap = itemWithImage.bitmap
            BitmapWithDefault(
                bitmap = bitmap,
                contentDescription = "Change picture",
                modifier = Modifier.aspectRatio(2f),
                contentScaleIfNotNull = ContentScale.Fit,
            )

            Text(text = item.name, style = MaterialTheme.typography.h5)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.padding(4.dp),
                )
                Text(text = item.location, style = MaterialTheme.typography.body1)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp),
                )
                Text(
                    text = if (item.inSeason) "Currently in Season" else "Not in Season",
                    style = MaterialTheme.typography.body1
                )
            }
            if (item.notes.isNotEmpty()) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp),
                    )
                    Text(
                        text = item.notes,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
fun EditItemFloatingActionButton(
    floatingActionBtnOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = floatingActionBtnOnClick,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Icon(
            Icons.Filled.Edit, contentDescription = "Edit Item"
        )
    }
}