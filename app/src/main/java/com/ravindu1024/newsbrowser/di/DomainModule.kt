package com.ravindu1024.newsbrowser.di

import com.ravindu1024.data.repository.NewsRepository
import com.ravindu1024.domain.usecases.HeadLinesUseCase
import com.ravindu1024.domain.usecases.SavedHeadlinesUseCase
import com.ravindu1024.domain.usecases.SourcesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideSourcesUseCase(newsRepository: NewsRepository) =
        SourcesUseCase(newsRepository)

    @Provides
    fun provideSavedHeadlinesUseCase(newsRepository: NewsRepository) =
        SavedHeadlinesUseCase(newsRepository)

    @Provides
    fun provideHeadlinesUseCase(newsRepository: NewsRepository) =
        HeadLinesUseCase(newsRepository)

}