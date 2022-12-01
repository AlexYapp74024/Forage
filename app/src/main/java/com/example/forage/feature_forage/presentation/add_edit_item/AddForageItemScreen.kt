package com.example.forage.feature_forage.presentation.add_edit_item

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.presentation.destinations.ForageItemListScreenDestination
import com.example.forage.feature_forage.presentation.util.ForageTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun AddForageItemScreen(
    itemId: Int,
    navigator: DestinationsNavigator,
    viewModel: AddEditViewModel = hiltViewModel(),
) {
    val item by viewModel.retrieveItem(itemId).collectAsState(initial = ForageItem())
    AddForageItemContent(item = item, viewModel = viewModel, navigator = navigator)
}

@Preview(showBackground = true)
@Composable
fun AddForageItemPreview() {
    AddForageItemContent(
        ForageItem(name = "HoneyBerry", location = "Red Cert Shop"),
        navigator = EmptyDestinationsNavigator
    )
}

@Composable
fun AddForageItemContent(
    item: ForageItem,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: AddEditViewModel = hiltViewModel(),
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
        EditItem(item, viewModel, navigator, modifier.padding(innerPadding))
    }
}


@Composable
fun EditItem(
    item: ForageItem,
    viewModel: AddEditViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    var itemName by remember { mutableStateOf(item.name) }
    var itemLocation by remember { mutableStateOf(item.location) }
    var itemInSeason by remember { mutableStateOf(item.inSeason) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val defaultModifier = Modifier.fillMaxWidth()

        OutlinedTextField(
            modifier = defaultModifier,
            value = itemName,
            onValueChange = { itemName = it },
            singleLine = true,
            label = { Text(text = "Item Name") })

        OutlinedTextField(
            modifier = defaultModifier,
            value = itemLocation,
            onValueChange = { itemLocation = it },
            singleLine = true,
            label = { Text(text = "Item Location") })

        Row(
            modifier = defaultModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Is In Season:", modifier = Modifier.weight(1f))

            Switch(
                checked = itemInSeason,
                onCheckedChange = { itemInSeason = it },
            )
        }

        Button(
            onClick = {
                viewModel.addItem(
                    item.copy(
                        name = itemName,
                        location = itemLocation,
                        inSeason = itemInSeason
                    )
                )
                navigator.navigate(ForageItemListScreenDestination)
            },
            modifier = defaultModifier.padding(top = 16.dp)
        ) {
            Text(text = "Save")
        }
    }
}