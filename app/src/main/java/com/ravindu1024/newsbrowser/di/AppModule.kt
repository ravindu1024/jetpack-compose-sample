package com.ravindu1024.newsbrowser.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.ravindu1024.newsbrowser.BuildConfig
import com.ravindu1024.newsbrowser.R
import com.ravindu1024.newsbrowser.data.HeaderInterceptor
import com.ravindu1024.newsbrowser.data.NewsDatabase
import com.ravindu1024.newsbrowser.data.RetrofitClient
import com.ravindu1024.newsbrowser.data.repository.NewsRepository
import com.ravindu1024.newsbrowser.data.sources.HeadlinesDao
import com.ravindu1024.newsbrowser.data.sources.NewsApi
import com.ravindu1024.newsbrowser.data.sources.NewsSourcesDao
import com.ravindu1024.newsbrowser.domain.AppSharedPrefs
import com.ravindu1024.newsbrowser.domain.HeadLinesUseCase
import com.ravindu1024.newsbrowser.domain.SavedHeadlinesUseCase
import com.ravindu1024.newsbrowser.domain.SourcesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit{
        val headerInterceptor = HeaderInterceptor(context.getString(R.string.news_api_key))
        return RetrofitClient.init(BuildConfig.DEBUG, "https://newsapi.org", Gson(), headerInterceptor)
    }

    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsSourcesDao, headlinesDao: HeadlinesDao) = NewsRepository(newsApi, newsDao, headlinesDao)

    @Provides
    fun provideSourcesUseCase(newsRepository: NewsRepository) = SourcesUseCase(newsRepository)

    @Provides
    fun provideSavedHeadlinesUseCase(newsRepository: NewsRepository) = SavedHeadlinesUseCase(newsRepository)

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context) = AppSharedPrefs(context)

    @Provides
    fun provideHeadlinesUseCase(newsRepository: NewsRepository) = HeadLinesUseCase(newsRepository)

    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase{
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "News"
        ).build()
    }

    @Provides
    fun provideNewsDao(newsDb: NewsDatabase): NewsSourcesDao{
        return newsDb.newsDao()
    }

    @Provides
    fun provideHeadlineDao(newsDb: NewsDatabase): HeadlinesDao{
        return newsDb.headlineDao()
    }
}