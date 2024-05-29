package com.example.swing.core.network.di

import com.example.swing.core.network.SwNetworkDataSource
import com.example.swing.core.network.retrofit.RetrofitSwNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RetrofitNetworkModule {

    @Binds
    fun binds(impl: RetrofitSwNetwork): SwNetworkDataSource

}