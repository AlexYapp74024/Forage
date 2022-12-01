package com.example.forage.di

import android.app.Application
import androidx.room.Room
import com.example.forage.feature_forage.data.data_source.ForageItemDatabase
import com.example.forage.feature_forage.data.repository.ForageItemRepositoryImpl
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import com.example.forage.feature_forage.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideForageItemDatabase(app: Application): ForageItemDatabase {
        return Room.databaseBuilder(
            app,
            ForageItemDatabase::class.java,
            ForageItemDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideForageItemRepository(db: ForageItemDatabase): ForageItemRepository {
        return ForageItemRepositoryImpl(db.forageItemDao)
    }

    @Provides
    @Singleton
    fun provideForageItemUseCases(repository: ForageItemRepository): ForageItemUseCases {
        return ForageItemUseCases(
            getAllForageItems = GetAllForageItems(repository),
            getForageItem = GetForageItem(repository),
            deleteForageItem = DeleteForageItem(repository),
            addForageItem = AddForageItem(repository),
        )
    }
}