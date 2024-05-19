package com.example.catchallenge.di

import android.app.Application
import androidx.room.Room
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.remote.api.BreedApi
import com.example.catchallenge.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Provides
    @Singleton
    fun providesCatApi(): BreedApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideBreedDao(database: BreedDatabase): BreedDao {
        return database.breedDao
    }
}