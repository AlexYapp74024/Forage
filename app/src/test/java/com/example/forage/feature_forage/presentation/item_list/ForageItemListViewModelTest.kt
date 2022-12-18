package com.example.forage.feature_forage.presentation.item_list

import app.cash.turbine.test
import com.example.forage.MainCoroutineRule
import com.example.forage.core.image_processing.TestImageRepository
import com.example.forage.data.repository.ForageItemTestRepository
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.use_case.ForageItem_UseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForageItemListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var useCases: ForageItem_UseCases
    private lateinit var repository: ForageItemTestRepository
    private lateinit var viewModel: ForageItemListViewModel

    @Before
    fun setup() {
        repository = ForageItemTestRepository()

        useCases = ForageItem_UseCases.instantiate(
            imageRepository = TestImageRepository(),
            repository = repository
        )

        viewModel = ForageItemListViewModel(useCases)
    }

    private fun mockForageItem(id: Int, categoryID: Int?) =
        ForageItem(id, "Item $id", categoryID)


    private suspend fun addMapToRepository(map: Map<Category, List<ForageItem>>) {
        map.onEach { (category, items) ->
            repository.insertCategory(category)
            items.onEach { item ->
                repository.insertItem(item)
            }
        }
    }

    @Test
    fun `Flow Test Normal Items List`() = runTest {
        val expectedMap = mapOf(
            Category(1) to listOf(
                mockForageItem(1, 1),
                mockForageItem(2, 1),
            ),
            Category(2) to listOf(
                mockForageItem(1, 2),
                mockForageItem(2, 2),
            )
        )

        addMapToRepository(expectedMap)

        viewModel.refreshItems()

        viewModel.items.test {
            val resultMap = awaitItem().map { (category, items) ->
                val forageItems = items.map { (forageItem, _) -> forageItem }
                category to forageItems
            }.toMap()

            assertThat(resultMap).isEqualTo(expectedMap)
        }
    }
}