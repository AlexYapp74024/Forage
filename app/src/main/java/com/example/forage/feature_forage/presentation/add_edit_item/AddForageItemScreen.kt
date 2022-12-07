package com.example.forage.feature_forage.presentation.add_edit_item

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.exampleForageItem
import com.example.forage.feature_forage.presentation.destinations.ForageItemListScreenDestination
import com.example.forage.feature_forage.presentation.util.ForageTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: AddEditViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private var showDelete: Boolean = false

@Destination
@Composable
fun AddForageItemScreen(
    navigatorIn: DestinationsNavigator,
    viewModelIn: AddEditViewModel = hiltViewModel(),
) {
    viewModel = viewModelIn
    navigator = navigatorIn
    showDelete = false

    val item by viewModel.item
    AddEditForageItemContent(item)
}

@Destination
@Composable
fun EditForageItemScreen(
    itemId: Int,
    navigatorIn: DestinationsNavigator,
    viewModelIn: AddEditViewModel = hiltViewModel(),
) {
    viewModel = viewModelIn
    navigator = navigatorIn
    showDelete = true

    viewModel.retrieveItem(itemId)

    val item by viewModel.item
    AddEditForageItemContent(item)
}

@Preview(showBackground = true)
@Composable
fun AddEditForageItemPreview() {
    AddEditForageItemContent(exampleForageItem)
}

@Composable
fun AddEditForageItemContent(
    item: ForageItem,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ForageTopAppBar(
                Title = "Add Item",
                canNavigateBack = false,
                navigateUp = { },
            )
        }) { innerPadding ->
        AddEditItem(item, modifier.padding(innerPadding))
    }
}

@Composable
fun AddEditItem(
    item: ForageItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val defaultModifier = Modifier.fillMaxWidth()

        OutlinedTextField(
            modifier = defaultModifier,
            value = item.name,
            onValueChange = { viewModel.updateItemState(item.copy(name = it)) },
            singleLine = true,
            label = { Text(text = "Name") })

        OutlinedTextField(
            modifier = defaultModifier,
            value = item.location,
            onValueChange = { viewModel.updateItemState(item.copy(location = it)) },
            singleLine = true,
            label = { Text(text = "Location") })

        Row(
            modifier = defaultModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Is In Season:", modifier = Modifier.weight(1f))

            Switch(
                checked = item.inSeason,
                onCheckedChange = { viewModel.updateItemState(item.copy(inSeason = it)) },
            )
        }

        OutlinedTextField(
            modifier = defaultModifier,
            value = item.notes,
            onValueChange = { viewModel.updateItemState(item.copy(notes = it)) },
            label = { Text(text = "Notes") })

        Button(
            onClick = {
                viewModel.addItem()
                navigator.navigate(ForageItemListScreenDestination)
            },
            modifier = defaultModifier
        ) {
            Text(text = "Save")
        }

        if (showDelete) {
            OutlinedButton(
                onClick = {
                    viewModel.deleteItem()
                    navigator.navigate(ForageItemListScreenDestination)
                },
                modifier = defaultModifier
            ) {
                Text(text = "Delete")
            }
        }
    }
}