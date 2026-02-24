package com.linkup.android.network

import com.linkup.android.network.auth.pwChange.PwChangeService
import com.linkup.android.network.auth.refresh.RefreshService
import com.linkup.android.network.auth.signIn.SignInService
import com.linkup.android.network.auth.signUp.SignUpService
import com.linkup.android.network.client.AuthInterceptor
import com.linkup.android.network.client.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LinkUpUrl.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignUpService(
        retrofit: Retrofit
    ): SignUpService = retrofit.create(SignUpService::class.java)

    @Provides
    @Singleton
    fun provideSignInService(
        retrofit: Retrofit
    ): SignInService = retrofit.create(SignInService::class.java)

    @Provides
    @Singleton
    fun pwChangeService(
        retrofit: Retrofit
    ): PwChangeService = retrofit.create(PwChangeService::class.java)

    @Provides
    @Singleton
    @Named("refreshClient")
    fun provideRefreshOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("refreshRetrofit")
    fun provideRefreshRetrofit(
        @Named("refreshClient") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LinkUpUrl.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideRefreshService(
        @Named("refreshRetrofit") retrofit: Retrofit
    ): RefreshService =
        retrofit.create(RefreshService::class.java)


}

