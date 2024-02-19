package com.example.starterproject.di

import com.example.starterproject.data.ApiService
import com.example.starterproject.data.Repository
import com.example.starterproject.data.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .writeTimeout(0, TimeUnit.MINUTES)
            .readTimeout(0, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRepository(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }
}

