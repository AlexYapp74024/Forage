package com.example.forage.feature_forage.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.presentation.util.ForageTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun ForageItemListScreen(
//    viewModel: ForageItemViewModel = hiltViewModel()
) {
//    val state = viewModel.state.value
//    ForageItemListScreenContent(state.items)
    ForageItemListScreenContent(
        forageItemList = listOf(
            ForageItem(0, "wild gooseberry", "Mountain View", true, ""),
            ForageItem(2, "Blackberry (Rubus sp.)", "Forest", true, "")
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun ForageItemListPreview() {
    ForageItemListScreenContent(
        forageItemList = listOf(
            ForageItem(0, "wild gooseberry", "Mountain View", true, ""),
            ForageItem(2, "Blackberry (Rubus sp.)", "Forest", true, "")
        ),
    )
}

@Composable
fun ForageItemListScreenContent(
    forageItemList: List<ForageItem>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ForageTopAppBar(
                Title = "Items",
                canNavigateBack = false,
                navigateUp = { },
            )
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(forageItemList) { item ->
                ForageListItemEntry(item = item,
                    itemOnClick = { TODO() })
            }

        }
    }
}

@Composable
fun ForageListItemEntry(
    item: ForageItem,
    itemOnClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable {
        itemOnClick(item.id)
    }) {
        Text(text = item.name, style = MaterialTheme.typography.h5)
        Text(text = item.location, style = MaterialTheme.typography.body1)
    }
}