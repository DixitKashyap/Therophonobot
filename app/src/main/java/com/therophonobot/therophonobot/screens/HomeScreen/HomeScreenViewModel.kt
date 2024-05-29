package com.therophonobot.therophonobot.screens.HomeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.therophonobot.therophonobot.data.DataOrException
import com.therophonobot.therophonobot.model.QuotesDocument
import com.therophonobot.therophonobot.repository.FirebaseQuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FirebaseQuotesRepository) : ViewModel(){
   val quotesData : MutableState<DataOrException<List<QuotesDocument>,Boolean,Exception>> = mutableStateOf(
       DataOrException(listOf(),true,Exception(""))
   )

    init {
        getAllQuotesFromDatabase()
    }
    private fun getAllQuotesFromDatabase(){
        viewModelScope.launch {
            quotesData.value.loading = false
            quotesData.value = repository.getAllQuotesFromDatabase()

            if(!quotesData.value.data.toString().isNullOrEmpty()) quotesData.value.loading = false
        }
        Log.d("TAG","get All Quotes From the Data : ${quotesData.value.data.toString()}")
    }

}