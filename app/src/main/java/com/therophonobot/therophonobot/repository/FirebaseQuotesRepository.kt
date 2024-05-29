package com.therophonobot.therophonobot.repository

import android.util.Log
import com.google.firebase.firestore.Query
import com.therophonobot.therophonobot.data.DataOrException
import com.therophonobot.therophonobot.model.QuotesDocument
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseQuotesRepository @Inject constructor(private val query: Query) {

    suspend fun getAllQuotesFromDatabase() : DataOrException<List<QuotesDocument>,Boolean,Exception>{
        val dataOrException = DataOrException<List<QuotesDocument>,Boolean,Exception>()

        try{
            dataOrException.loading = true
            dataOrException.data = query.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(QuotesDocument::class.java)!!
            }
            Log.d("QUOTES","Quotes ${dataOrException.data}")
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        }catch (e : Exception){
            dataOrException.e = e
        }
        return  dataOrException
    }
}