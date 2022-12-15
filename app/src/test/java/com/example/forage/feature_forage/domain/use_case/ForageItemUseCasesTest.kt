package com.example.forage.feature_forage.domain.use_case

import com.example.forage.core.image_processing.TestImageRepository
import com.example.forage.data.repository.ForageItemTestRepository
import com.example.forage.feature_forage.domain.model.exampleForageItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForageItemUseCasesTest {
    private lateinit var useCases: ForageItem_UseCases
    private lateinit var repository: ForageItemTestRepository

    @Before
    fun setup() {
        repository = ForageItemTestRepository()

        useCases = ForageItem_UseCases.instantiate(
            imageRepository = TestImageRepository(),
            repository = repository
        )
    }

    @Test
    fun `getForageItem retrieves item successfully`() = runTest {
        repository.insertItem(exampleForageItem)
        assertThat(repository.getAllItem().first()).contains(exampleForageItem)
    }

    @Test
    fun `getForageAllItem retrieves all item`() = runTest {
        val item1 = exampleForageItem
        val item2 = exampleForageItem.copy(id = 2)

        repository.insertItem(item1)
        repository.insertItem(item2)
        assertThat(repository.getAllItem().first()).contains(item1)
        assertThat(repository.getAllItem().first()).contains(item2)
    }

    @Test
    fun `addForageItem throws Exception when name is empty`() {
        assertThrows(
            IllegalArgumentException::class.java,
        ) {
            runTest {
                useCases.addForageItem(exampleForageItem.copy(name = ""))
            }
        }
    }

    @Test
    fun `addForageItem adds item to list`() = runTest {
        useCases.addForageItem(exampleForageItem)
        assertThat(repository.getAllItem().first()).contains(exampleForageItem)
    }

    @Test
    fun `deleteForageItem Removes Item`() = runTest {
        useCases.addForageItem(exampleForageItem)
        useCases.deleteForageItem(exampleForageItem)
        assertThat(repository.getAllItem().first()).doesNotContain(exampleForageItem)
    }
}