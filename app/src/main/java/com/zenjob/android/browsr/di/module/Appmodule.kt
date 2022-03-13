package com.zenjob.android.browsr.di.module

import com.zenjob.android.browsr.BuildConfig
import com.zenjob.android.browsr.model.data.TMDBApi
import com.zenjob.android.browsr.model.repo.MovieRepositoryImpl
import com.zenjob.android.browsr.model.repo.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Appmodule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {

        val tmdbApiInterceptor = Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

            val reqBuilder = original.newBuilder()
                .url(url)
            chain.proceed(reqBuilder.build())
        }

        return OkHttpClient
            .Builder()
            .addInterceptor(tmdbApiInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideTMDBService(retrofit: Retrofit): TMDBApi = retrofit.create(TMDBApi::class.java)


    @Provides
    @Singleton
    fun provideMovieRepository(api: TMDBApi): MoviesRepository = MovieRepositoryImpl(api)
}