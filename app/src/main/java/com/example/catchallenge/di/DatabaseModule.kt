package com.example.catchallenge.di

import android.app.Application
import androidx.room.Room
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.db.BreedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesBreedDatabase(app: Application): BreedDatabase {
        return Room.databaseBuilder(
            app,
            BreedDatabase::class.java,
            "breeddb.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBreedDao(database: BreedDatabase): BreedDao {
        return database.breedDao
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(database: BreedDatabase): FavouriteDao {
        return database.favouriteDao
    }
}