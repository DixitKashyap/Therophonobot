package com.therophonobot.therophonobot.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


fun firebaseErrorMessage(errorCode : String):String{
    Log.d("TAG",errorCode)
    return when(errorCode){
        "ERROR_USER_DISABLED" -> "Account is Disabled"
        "ERROR_INVALID_EMAIL" -> "Invalid Email Enter Valid Email Address."
        "ERROR_INVALID_CREDENTIAL" ->"Invalid Email or Password."

        else -> "Login Failed.Please Try Again"
    }
}

fun getCurrentUserId() : String{
    return FirebaseAuth.getInstance().currentUser?.uid.toString()
}

fun generateDocumentId(collectionName : String) : DocumentReference{
    val collectionRef = FirebaseFirestore.getInstance().collection(collectionName);
    val  documentRef = collectionRef.document()
    return documentRef
}