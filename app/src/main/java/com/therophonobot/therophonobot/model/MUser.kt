package com.therophonobot.therophonobot.model

data class MUser(
    val user_id : String = "",
    val guardian_name : String ="",
    val child_name : String = "",
    val child_age : String = "",
    val contact_number : String = "",
    val email : String = "",
    val parent_counselling : String = "",
    val guidance_book : String = "",
    val time_zone  : String = "",
    val location : String = "",
    val document_id : String = ""
)
