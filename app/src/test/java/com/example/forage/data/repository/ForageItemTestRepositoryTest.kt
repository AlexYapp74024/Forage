package com.example.forage.data.repository

import com.example.forage.data.data_source.relations.CategoryWithForageItems
import com.example.forage.feature_forage.domain.model.exampleCategory
import com.example.forage.feature_forage.domain.model.exampleForageItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForageItemTestRepositoryTest {

    private val testRepository = ForageItemTestRepository()
    private val testItem = exampleForageItem
    private val testCategory = exampleCategory

    @Test
    fun insertItem() = runTest {
        with(testRepository) {
            insertItem(testItem)
            assertThat(getAllItem().first()).contains(testItem)
            assertThat(getItem(testItem.id).first()).isEqualTo(testItem)
        }
    }

    @Test
    fun deleteItem() = runTest {
        with(testRepository) {
            insertItem(testItem)
            deleteItem(testItem)

            assertThat(getAllItem().first()).doesNotContain(testItem)
        }
    }

    @Test
    fun insertCategory() = runTest {
        with(testRepository) {
            insertCategory(testCategory)

            assertThat(getAllCategory().first()).contains(testCategory)
            assertThat(getCategory(testCategory.id).first()).isEqualTo(testCategory)
        }
    }

    @Test
    fun deleteCategory() = runTest {
        with(testRepository) {
            insertCategory(testCategory)
            deleteCategory(testCategory)

            assertThat(getAllCategory().first()).doesNotContain(testCategory)
        }
    }

    @Test
    fun getCategoryWithItems() = runTest {
        with(testRepository) {
            insertItem(testItem)
            insertCategory(testCategory)

            val results = getCategoryWithItems(testCategory)
            assertThat(results).isEqualTo(
                listOf(
                    CategoryWithForageItems(
                        testCategory,
                        listOf(testItem)
                    )
                )
            )
        }
    }
}