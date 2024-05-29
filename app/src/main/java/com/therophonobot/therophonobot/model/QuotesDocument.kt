package com.therophonobot.therophonobot.model

data class QuotesDocument(
    val documentRef : String ="",
    val quotesList :List<Quotes> = emptyList()
)
