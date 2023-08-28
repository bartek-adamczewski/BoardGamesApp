package edu.put.inf151764.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.put.inf151764.data.api.GamesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun okHttpClient(@ApplicationContext application: Context): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(
                Cache(
                    directory = File(application.cacheDir, "http_cache"),
                    // $0.05 worth of phone storage in 2020
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://boardgamegeek.com/xmlapi2/")
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
            .build()
    }

    @Provides
    fun gamesApi(retrofit: Retrofit): GamesApi {
        return retrofit.create(GamesApi::class.java)
    }
}