package com.example.forage.feature_forage.presentation.add_edit_item

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.core.image_processing.getImageFromInternalStorageLauncher
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.exampleForageItem
import com.example.forage.feature_forage.presentation.destinations.ForageItemListScreenDestination
import com.example.forage.feature_forage.presentation.util.BitmapWithDefault
import com.example.forage.feature_forage.presentation.util.ForageTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: AddEditItemViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private var showDelete: Boolean = false

@Destination
@Composable
fun AddForageItemScreen(
    navigatorIn: DestinationsNavigator,
    viewModelIn: AddEditItemViewModel = hiltViewModel(),
) {
    viewModel = viewModelIn
    navigator = navigatorIn
    showDelete = false

    AddEditForageItemContent("Add Item")
}

@Destination
@Composable
fun EditForageItemScreen(
    itemId: Int,
    navigatorIn: DestinationsNavigator,
    viewModelIn: AddEditItemViewModel = hiltViewModel(),
) {
    viewModel = viewModelIn
    navigator = navigatorIn
    showDelete = true

    viewModel.retrieveItem(itemId, LocalContext.current)

    AddEditForageItemContent("Edit Item")
}

@Preview(showBackground = true)
@Composable
fun AddEditForageItemPreview(
) {
    Scaffold(
        topBar = {
            ForageTopAppBar(
                Title = "Add Item",
                canNavigateBack = true,
                navigateUp = { navigator.navigateUp() },
            )
        }) { innerPadding ->
        AddEditItem(
            exampleForageItem,
            bitmap = null,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AddEditForageItemContent(
    topBarString: String,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.item
    val bitmap = state.bitmap

    Scaffold(
        modifier = modifier,
        topBar = {
            ForageTopAppBar(
                Title = topBarString,
                canNavigateBack = true,
                navigateUp = { navigator.navigateUp() },
            )
        }) { innerPadding ->
        AddEditItem(
            item = state.item,
            bitmap = bitmap,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AddEditItem(
    item: ForageItem,
    modifier: Modifier = Modifier,
    bitmap: Bitmap? = null,
) {
    val launcher = getImageFromInternalStorageLauncher {
        viewModel.updateItemBitmap(it)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val defaultModifier = Modifier.fillMaxWidth()

        BitmapWithDefault(
            bitmap = bitmap,
            contentDescription = "Change picture",
            modifier = Modifier
                .aspectRatio(2f)
                .clickable {
                    launcher.launch("image/*")
                },
            contentScaleIfNotNull = ContentScale.Fit,
        )

        OutlinedTextField(
            modifier = defaultModifier,
            value = item.name,
            onValueChange = {
                viewModel.updateItemState(item.copy(name = it))
            },
            singleLine = true,
            label = { Text(text = "Name") })

        OutlinedTextField(
            modifier = defaultModifier,
            value = item.location,
            onValueChange = {
                viewModel.updateItemState(item.copy(location = it))
            },
            singleLine = true,
            label = { Text(text = "Location") })

        Row(
            modifier = defaultModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Is In Season:", modifier = Modifier.weight(1f))

            Switch(
                checked = item.inSeason,
                onCheckedChange = {
                    viewModel.updateItemState(item.copy(inSeason = it))
                },
            )
        }

        OutlinedTextField(
            modifier = defaultModifier,
            value = item.notes,
            onValueChange = { viewModel.updateItemState(item.copy(notes = it)) },
            label = { Text(text = "Notes") })

        val context = LocalContext.current

        Button(
            onClick = {
                viewModel.addItem(context)
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