package com.example.catchallenge.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.remote.BreedRemoteMediator
import com.example.catchallenge.data.remote.api.BreedApi
import com.example.catchallenge.data.remote.interceptors.AuthorizationInterceptor
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.utils.Constants
import com.example.catchallenge.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesJson() = Json{
        explicitNulls = false
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesCatApi(json: Json): BreedApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
            .create(BreedApi::class.java)
    }

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

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager(
        remoteMediator: BreedRemoteMediator,
        repository: BreedRepository
    ): Pager<Int, BreedEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = 3 * Constants.PAGE_SIZE,
                initialLoadSize = 2 * Constants.PAGE_SIZE,
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                repository.getPagingSource()
            }
        )
    }
}