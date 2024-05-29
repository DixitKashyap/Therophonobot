package com.therophonobot.therophonobot.screens.LoginScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.therophonobot.therophonobot.model.MUser
import com.therophonobot.therophonobot.utils.Constants
import com.therophonobot.therophonobot.utils.firebaseErrorMessage
import com.therophonobot.therophonobot.utils.generateDocumentId
import kotlinx.coroutines.launch


class LoginScreenViewModel : ViewModel(){
    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val error_message = mutableStateOf("")
    val email_exist = mutableStateOf(false)



    //For Creating User With Email And Password
    fun createUserWithEmailAndPassword(
        guardian_name : String,
        child_name : String,
        child_age : String,
        contact_number : String,
        email : String,
        password : String,
        parent_counselling : String,
        guidance_book : String,
        time_zone : String,
        location : String,
        login : ()-> Unit){


        val documentRef = generateDocumentId(Constants.USER_COLLECTION)
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                       createUser(guardian_name,child_name,child_age,contact_number,email,parent_counselling,guidance_book,time_zone,location,documentRef){}
                        login()
                    }else{
                        Log.e("Error ", "Create User with Email and Password Failed !")
                    }
                }.addOnFailureListener {
                    Log.e("Error " ,"Create User with Email and Password Failed !")
                }
                _loading.value = false
        }

    }

    fun signInWithEmailAndPassword(email : String,password: String ,signIn :() -> Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        signIn()
                        Log.d("SIGN IN" ," Successfull")
                    }else{
                        val exception  = it.exception as FirebaseAuthException
                        val erorMessage = firebaseErrorMessage(errorCode =  exception.errorCode)
                        error_message.value = erorMessage
                        Log.e("Error","Error Occured While Sign In")
                    }
                }
            }catch (e : Exception){
                error_message.value = e.localizedMessage.toString()
                Log.d("Error","Sign In With Email and Password Filed : ${e.localizedMessage.toString()}")
            }
        }
    }
     fun createUser(
        guardianName: String,
        childName: String,
        childAge: String,
        contactNumber: String,
        email: String,
        parentCounselling: String,
        guidanceBook: String,
        timeZone: String,
        location: String,
         documentReference: DocumentReference,
        onClick :() ->Unit
    ) {

         val user_id = auth.currentUser?.uid
        val user = MUser(
            user_id!!,
            guardianName,
            childName,
            childAge,
            contactNumber,
            email,
            parentCounselling,
            guidanceBook,
            timeZone,
            location,
            documentReference.id
        )
         Log.d("USER" , "USER ID ${user_id}")

         documentReference.set(user).addOnCompleteListener {
             onClick()
             Log.d("User","Scuccessfully Created User")
         }.addOnFailureListener {
             Log.e("Error" , "Error While Creating User")
         }

//        db.collection(Constants.USER_COLLECTION).add(user).addOnCompleteListener {
//            onClick()
//
//        }.addOnFailureListener {
//            Log.e("Error" , "Error While Creating User")
//        }
    }


    //To Check if Email Address Already Exist
        fun checkUserExist(email: String) {
            val fireRef = FirebaseFirestore.getInstance()
            val users_collection = fireRef.collection(Constants.USER_COLLECTION)

            users_collection.get().addOnCompleteListener {
                for(document in it.result){
                    if(email.isNotEmpty() && email.contentEquals(document.data["email"].toString())){
                        email_exist.value = true
                    }else{
                        Log.d("TAG","userDoes not exits")
                    }
                }
                }
            }


    //TO Reset Password incase user forget'it
    fun resetPassword(email: String,onSuccess :() -> Unit,onFailer: ()-> Unit){
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            onSuccess()
        }.addOnFailureListener {
            onFailer()
            Log.d("Login" , "Failed Sending Email Please Try Again After Some time")
        }
    }
    }
