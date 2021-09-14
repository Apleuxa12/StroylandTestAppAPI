package com.ddmukhin.stroylandtestappapi.di

import com.ddmukhin.stroylandtestappapi.domain.GiphyConverter
import com.ddmukhin.stroylandtestappapi.remote.GiphyRepository
import com.ddmukhin.stroylandtestappapi.remote.GiphyRepositoryImpl
import com.ddmukhin.stroylandtestappapi.remote.GiphyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    @ViewModelScoped
    fun provideGiphyRepository(
        giphyService: GiphyService,
        giphyConverter: GiphyConverter
    ): GiphyRepository = GiphyRepositoryImpl(
        giphyService, giphyConverter
    )

}