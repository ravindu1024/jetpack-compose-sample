package com.ravindu1024.newsbrowser.di

import android.content.Context
import com.google.gson.Gson
import com.ravindu1024.data.AppSharedPrefs
import com.ravindu1024.data.HeaderInterceptor
import com.ravindu1024.data.NewsDatabase
import com.ravindu1024.data.NewsDatabaseBuilder
import com.ravindu1024.data.RetrofitClient
import com.ravindu1024.data.repository.NewsRepository
import com.ravindu1024.data.sources.local.HeadlinesDao
import com.ravindu1024.data.sources.local.NewsSourcesDao
import com.ravindu1024.data.sources.remote.NewsApi
import com.ravindu1024.newsbrowser.BuildConfig
import com.ravindu1024.newsbrowser.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class DataSourcesModule {

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context) =
        AppSharedPrefs(context)

    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val headerInterceptor = HeaderInterceptor(BuildConfig.NEWS_API_KEY)
        return RetrofitClient.init(
            enableLogging = BuildConfig.DEBUG,
            baseUrl = context.getString(R.string.base_url_newsapi),
            gson = Gson(),
            headerInterceptor = headerInterceptor
        )
    }

    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsSourcesDao,
        headlinesDao: HeadlinesDao
    ): NewsRepository {
        return NewsRepository(newsApi, newsDao, headlinesDao)
    }

    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabaseBuilder.build(context)
    }

    @Provides
    fun provideNewsDao(newsDb: NewsDatabase): NewsSourcesDao {
        return newsDb.newsDao()
    }

    @Provides
    fun provideHeadlineDao(newsDb: NewsDatabase): HeadlinesDao {
        return newsDb.headlineDao()
    }
}