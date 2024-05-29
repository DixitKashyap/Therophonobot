package com.therophonobot.therophonobot.di

import com.google.firebase.firestore.FirebaseFirestore
import com.therophonobot.therophonobot.repository.FirebaseQuotesRepository
import com.therophonobot.therophonobot.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseQuotesRepository() : FirebaseQuotesRepository{
        return FirebaseQuotesRepository(query = FirebaseFirestore.getInstance().collection(Constants.QUOTES_COLLECTION))
    }

}