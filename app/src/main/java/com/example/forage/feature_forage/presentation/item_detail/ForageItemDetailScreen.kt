package com.example.forage.feature_forage.presentation.item_list

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.presentation.destinations.EditForageItemScreenDestination
import com.example.forage.feature_forage.presentation.item_detail.ForageItemDetailViewModel
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

    viewModel.retrieveItem(itemId)
    val item by viewModel.item
    ForageItemDetailScreen(item)
}

@Preview
@Composable
fun ForageItemDetailPreview() {
    ForageItemDetailScreen(
        ForageItem(
            name = "Honeyberry",
            location = "CertShop",
            notes = "cute af"
        )
    )
}

@Composable
fun ForageItemDetailScreen(
    item: ForageItem,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            EditItemFloatingActionButton(floatingActionBtnOnClick = {
                navigator.navigate(EditForageItemScreenDestination(item.id))
            })
        },
        topBar = {
            ForageTopAppBar(
                Title = "Details",
                canNavigateBack = true,
                navigateUp = { },
            )
        })
    { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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