package com.example.graph

import com.example.navigation.NavigationHolder
import com.example.navigation.NavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideNavigationProvider(): NavigationProvider = NavigationHolder()
}
