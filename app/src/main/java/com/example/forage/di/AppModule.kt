package com.example.forage.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.core.image_processing.ImageRepositoryImpl
import com.example.forage.data.data_source.ForageItemDatabase
import com.example.forage.data.repository.ForageItemRepositoryImpl
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import com.example.forage.feature_forage.domain.use_case.*
import com.example.forage.feature_forage.domain.use_case.ForageItem_UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideForageItemDatabase(app: Application): ForageItemDatabase {
        return Room.databaseBuilder(
            app, ForageItemDatabase::class.java, ForageItemDatabase.DATABASE_NAME
        ).addMigrations(ForageItemDatabase.migration1to2).build()
    }

    @Provides
    @Singleton
    fun provideForageItemRepository(db: ForageItemDatabase): ForageItemRepository {
        return ForageItemRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideForageItemUseCases(
        repository: ForageItemRepository,
        imageRepository: ImageRepository,
    ): ForageItem_UseCases {
        return ForageItem_UseCases.instantiate(
            repository = repository,
            imageRepository = imageRepository
        )
    }
}