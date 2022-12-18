package com.example.forage.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.exampleCategory
import com.example.forage.feature_forage.domain.model.exampleForageItem
import com.example.forage.feature_forage.domain.model.relations.CategoryWithForageItems
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class ForageItemDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ForageItemDatabase
    private lateinit var dao: ForageItemDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ForageItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertForageItem() = runTest {
        val forageItem = exampleForageItem
        dao.insertItem(forageItem)
        dao.getAllItem().collect {
            assertThat(it).contains(forageItem)
        }
    }


    @Test
    fun deleteForageItem() = runTest {
        val forageItem = exampleForageItem
        dao.insertItem(forageItem)
        dao.deleteItem(forageItem)
        dao.getAllItem().test {
            assertThat(awaitItem()).doesNotContain(forageItem)
        }
    }

    @Test
    fun insertCategory() = runTest {
        val category = exampleCategory
        dao.insertCategory(category)
        dao.getAllCategory().test {
            assertThat(awaitItem()).contains(category)
        }
    }

    @Test
    fun deleteCategory() = runTest {
        val category = exampleCategory
        dao.insertCategory(category)
        dao.deleteCategory(category)
        dao.getAllCategory().test {
            assertThat(awaitItem()).doesNotContain(category)
        }
    }

    @Test
    fun getAllCategoryWithItems() = runTest {
        val expectedList = listOf(
            CategoryWithForageItems(
                Category(1),
                listOf(ForageItem(1, categoryID = 1), ForageItem(2, categoryID = 1)),
            ),
            CategoryWithForageItems(
                Category(2),
                listOf(ForageItem(3, categoryID = 2), ForageItem(4, categoryID = 2))
            ),
        )

        expectedList.onEach { (category, items) ->
            dao.insertCategory(category)
            items.onEach { dao.insertItem(it) }
        }

        assertThat(dao.getCategoryWithItems())
    }
}