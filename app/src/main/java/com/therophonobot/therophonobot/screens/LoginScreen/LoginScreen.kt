package com.therophonobot.therophonobot.screens.LoginScreen

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.therophonobot.therophonobot.Components.ButtonTapSound
import com.therophonobot.therophonobot.Components.CustomDialog
import com.therophonobot.therophonobot.R
import com.therophonobot.therophonobot.navigation.ScreensName
import com.therophonobot.therophonobot.ui.theme.Brown
import com.therophonobot.therophonobot.ui.theme.LightBrown
import com.therophonobot.therophonobot.ui.theme.TealGreen
import com.therophonobot.therophonobot.utils.checkNetwork
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun LoginScreen(navController: NavController,viewModel: LoginScreenViewModel = hiltViewModel()){
  LoginScreenUi(navController = navController,viewModel = viewModel)
}

@Composable
fun LoginScreenUi(navController: NavController?=null,viewModel: LoginScreenViewModel){

    //Required States
    val username = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }
    val passwordVisibility = remember{ mutableStateOf(false) }
    val showErrorMessage = remember{ mutableStateOf(false) }


    //Keyboard Controller
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val openDialogBox = remember{mutableStateOf(false)}

    Surface(modifier = Modifier.fillMaxSize(), color = LightBrown) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = stringResource(R.string.application_logo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(15.dp)
            )

            Text(text = stringResource(id = R.string.welcome_string),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                modifier = Modifier.padding(start = 20.dp, end = 10.dp, top = 13.dp),
                color = Color.Black)
            Spacer(modifier = Modifier.height(30.dp))

            //Username Field
            TextInputField(
                textState =username ,
                placeholder = stringResource(id = R.string.email),
                onAction = KeyboardActions {
                    keyboardController?.hide()
                } ,
                keyboardType =KeyboardType.Email ,
                textStyle = TextStyle(color = Color.DarkGray, fontSize = 14.sp,fontWeight = FontWeight.W300) ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
            //Password Field
            TextInputField(
                textState = password,
                placeholder = stringResource(id = R.string.password),
                onAction = KeyboardActions {
                      keyboardController?.hide()
                },
                keyboardType = KeyboardType.Password,
                textStyle = TextStyle(color = Color.DarkGray, fontSize = 14.sp,fontWeight = FontWeight.W300),
                isPasswordField = true,
                passwordVisibility = passwordVisibility,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp)
            )

            //In Case If Any Error Occured While User Loggin In
            if(viewModel.error_message.value.isNotEmpty() || showErrorMessage.value ) {
                Text(text = if(viewModel.error_message.value.isNotEmpty()) viewModel.error_message.value else "Please Enter Valid Values in the fields",
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(5.dp))

                Handler().postDelayed({
                    viewModel.error_message.value = ""

                },4000)
            }
            //Forget Passowrd Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Right
            ){
                Text(
                    text = stringResource(id = R.string.forget_password),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.clickable {

                        ButtonTapSound(context=context)

                     if(username.value.isNotEmpty() && checkNetwork(context)){
                         viewModel.resetPassword(email = username.value.trim(), onSuccess = {
                             Toast.makeText(context,"Check Your Email",Toast.LENGTH_LONG).show()
                         },
                             onFailer = {
                                 Toast.makeText(context,"Error Occured While Sending Email ",Toast.LENGTH_LONG).show()
                             })
                     }else{
                         Toast.makeText(context,"Please Enter Valid Email Address",Toast.LENGTH_LONG).show()
                     }
                    }
                )
            }

            if(openDialogBox.value){
                CustomDialog(openDialogCustom = openDialogBox, onDismiss = {openDialogBox.value = false}) {
                    navController?.popBackStack()
                    navController?.navigate(route = ScreensName.LoginScreen.name)
                }
            }else{
                openDialogBox.value = false
            }
            Button(
                onClick = {

                    ButtonTapSound(context)

                    if(checkNetwork(context)){
                        if(username.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()){
                            viewModel.signInWithEmailAndPassword(username.value.trim(),password.value.trim()){
                                navController?.popBackStack()
                                navController?.navigate(route = ScreensName.BottomNavHost.name)
                            }
                        }else{
                            showErrorMessage.value = true
                        }
                    }else{
                        openDialogBox.value = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Brown),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 10.dp)
            ) {
                Text(text = stringResource(R.string.go),
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold)
            }


            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)){
                        append(stringResource(R.string.don_t_have_account_yet))
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TealGreen)){
                        append(stringResource(R.string.sign_up))
                    }
                },modifier = Modifier.clickable {
                     ButtonTapSound(context)
                    navController?.navigate(route = ScreensName.SignUpScreen.name)
                })
            }

          Row(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
          ){
              Divider(modifier = Modifier.weight(1.0f))
              Text(text = stringResource(R.string.or),
                  fontSize = 18.sp,
                  fontWeight = FontWeight.W400,
                  color = Color.DarkGray,
                  modifier = Modifier.padding(start = 10.dp, end = 10.dp))
              Divider(modifier = Modifier.weight(1.0f))

          }

            //Code Related To Google Logg In
            var user by remember { mutableStateOf(Firebase.auth.currentUser) }
            val launcher = rememberFirebaseAuthLauncher(
                onAuthComplete = { result ->
                    user = result.user
                    navController?.popBackStack()
                    navController?.navigate(route = ScreensName.SignUpScreen.name+"/${user?.email}")
                },
                onAuthError = {
                    user = null
                }
            )
            val token = stringResource(id = R.string.web_client_id)
            val context = LocalContext.current
            Button(onClick = {

                ButtonTapSound(context)
                val gso =
                    GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn
                    .getClient(context, gso)
                launcher
                    .launch(googleSignInClient.signInIntent)
                             },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = stringResource(R.string.google_login_icon),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp))

                    Text(text = stringResource(R.string.google_login),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.DarkGray)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField(
    textState : MutableState<String>,
    placeholder : String,
    onAction : KeyboardActions,
    keyboardType: KeyboardType,
    imeAction: ImeAction = ImeAction.Done,
    textStyle : TextStyle,
    isPasswordField : Boolean = false,
    passwordVisibility : MutableState<Boolean> = mutableStateOf(false),
    containerColor :Color = Color.White,
    focusedColor : Color = Color.White,
    unfocusedColor : Color = Color.White,
    focusedTextColor: Color = Color.Black,
    unfocusedTextColor : Color = Color.DarkGray,
    modifier : Modifier
    ){
    OutlinedTextField(
        value = textState.value,
        onValueChange = {newText->
            textState.value = newText
        },
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        },
        textStyle = textStyle,
        trailingIcon = {
            if(isPasswordField){

                val (icon, iconColor) = if (passwordVisibility.value) {
                    Pair(
                        Icons.Filled.Visibility,
                        Color.DarkGray
                    )
                } else {
                    Pair(Icons.Filled.VisibilityOff, Color.DarkGray)
                }

                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        icon,
                        contentDescription = stringResource(R.string.visibility),
                        tint = iconColor
                    )
                }
            }
        },
        visualTransformation = if(isPasswordField && !passwordVisibility.value)PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        keyboardActions =  onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor =containerColor,
            focusedIndicatorColor = focusedColor,
            unfocusedIndicatorColor = unfocusedColor,
            focusedTextColor = focusedTextColor,
            unfocusedTextColor = unfocusedTextColor
        )
    )
}



@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(
        ActivityResultContracts
        .StartActivityForResult()) { result ->
        val task = GoogleSignIn
            .getSignedInAccountFromIntent(result.data)
        try {
            val account = task
                .getResult(ApiException::class.java)!!
            Log.d("GoogleAuth", "account $account")
            val credential = GoogleAuthProvider
                .getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase
                    .auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            Log.d("GoogleAuth", e.toString())
            onAuthError(e)
        }
    }
}