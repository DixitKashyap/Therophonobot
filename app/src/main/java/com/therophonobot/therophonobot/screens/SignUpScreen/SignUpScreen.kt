package com.therophonobot.therophonobot.screens.SignUpScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.therophonobot.therophonobot.Components.ButtonTapSound
import com.therophonobot.therophonobot.Components.CustomDialog
import com.therophonobot.therophonobot.R
import com.therophonobot.therophonobot.navigation.ScreensName
import com.therophonobot.therophonobot.screens.LoginScreen.LoginScreenViewModel
import com.therophonobot.therophonobot.screens.LoginScreen.TextInputField
import com.therophonobot.therophonobot.ui.theme.DullYellow
import com.therophonobot.therophonobot.ui.theme.LightBrown
import com.therophonobot.therophonobot.ui.theme.SkyBlue
import com.therophonobot.therophonobot.utils.Constants
import com.therophonobot.therophonobot.utils.checkNetwork
import com.therophonobot.therophonobot.utils.generateDocumentId
import com.therophonobot.therophonobot.utils.getCountryName
import com.therophonobot.therophonobot.utils.getCurrentTime
import com.therophonobot.therophonobot.utils.getCurrentUserId
import com.therophonobot.therophonobot.utils.getScreenWidth

//@Preview
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SignUpScreen(navController: NavController? = null,email : String? = null , viewModel : LoginScreenViewModel= hiltViewModel()){
    // Various Font Feild Text State

    val guardianName = remember{ mutableStateOf("") }
    val childName = remember{mutableStateOf("")}
    val childAge = remember{mutableStateOf("")}
    val contactNumber = remember{mutableStateOf("")}
    val emailStateText = remember{mutableStateOf("")}
    emailStateText.value = email?: ""
    val emailState = remember{mutableStateOf("Email Already Exist!")}
    val password = remember{mutableStateOf("")}
    val passwordVisibilityState = remember{ mutableStateOf(false) }
    val openDialogBox = remember{ mutableStateOf(false) }
    val timezone = getCurrentTime()
    val location = getCountryName()

    val showEmptyBoxMessage = remember{ mutableStateOf(false) }

    //Check Box State
    val interestedInParentCounselling = remember{ mutableStateOf(false) }
    val interestedInParentGuidanceBook = remember { mutableStateOf(false) }

    //Keyboard Controller
    val keyboardController = LocalSoftwareKeyboardController.current

    //getting Context
    val context = LocalContext.current
    val showLoadingScreen = remember{ mutableStateOf(false) }

    //Space Between Every Text Field
    val spaceBtween = 15.dp

    val user_id = getCurrentUserId()
    val documentRef = generateDocumentId(Constants.USER_COLLECTION)

    Surface(modifier = Modifier.fillMaxSize(), color = LightBrown) {
        Column {

        Box(modifier = Modifier.fillMaxWidth()){

            val screenWidth = getScreenWidth()
            val spacerPercentage = (screenWidth/100)*90;

            Surface(modifier = Modifier.size(100.dp), shape = RoundedCornerShape(bottomEnd = 100.dp), color = DullYellow){}
            Spacer(modifier = Modifier.width(spacerPercentage.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, end = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                ){

                Text(text = stringResource(R.string.create_account),
                    fontWeight = FontWeight.W900,
                    color = Color.Black,
                    fontSize = 35.sp,
                    lineHeight = 43.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 60.dp, start = 20.dp))


                Image(painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = stringResource(id =R.string.welcome_string),
                    modifier = Modifier
                        .size(100.dp)
                        .rotate(17.37f)
                        .weight(1f))
            }
        }

        //User Creating Form
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp, top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            TextInputField(
                textState = guardianName,
                placeholder = stringResource(R.string.guardian_name),
                onAction = KeyboardActions{
                       keyboardController?.hide()
                },
                keyboardType = KeyboardType.Text ,
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 5.dp),
                containerColor = Color.LightGray,
                focusedColor = Color.LightGray,
                unfocusedColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
            TextInputField(
                textState = childName,
                placeholder = stringResource(R.string.child_name),
                onAction = KeyboardActions{
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Text ,
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 5.dp),
                containerColor = Color.LightGray,
                focusedColor = Color.LightGray,
                unfocusedColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )

            TextInputField(
                textState = childAge,
                placeholder = stringResource(R.string.child_age),
                onAction = KeyboardActions{
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Number ,
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 5.dp),
                containerColor = Color.LightGray,
                focusedColor = Color.LightGray,
                unfocusedColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )

            TextInputField(
                textState = contactNumber,
                placeholder = stringResource(R.string.contact_number),
                onAction = KeyboardActions{
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Phone ,
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 5.dp),
                containerColor = Color.LightGray,
                focusedColor = Color.LightGray,
                unfocusedColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )

            TextInputField(
                textState = emailStateText,
                placeholder = stringResource(R.string.email),
                onAction = KeyboardActions{
                   
                    if(!viewModel.email_exist.value){
                        viewModel.checkUserExist(emailStateText.value.trim())
                    }else{
                        viewModel.email_exist.value = false
                    }
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Email,
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 5.dp),
                containerColor = Color.LightGray,
                focusedColor = Color.LightGray,
                unfocusedColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )

            if (viewModel.email_exist.value) {
                Text(
                    text = emailState.value,
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            if(email.isNullOrEmpty()){
                TextInputField(
                    textState = password,
                    placeholder = stringResource(id =R.string.password),
                    onAction = KeyboardActions{
                        keyboardController?.hide()
                    },
                    keyboardType = KeyboardType.Password ,
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 5.dp),
                    containerColor = Color.LightGray,
                    focusedColor = Color.LightGray,
                    unfocusedColor = Color.LightGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    isPasswordField = true,
                    passwordVisibility = passwordVisibilityState

                )
            }
            if (showEmptyBoxMessage.value) {
                Text(
                    text = stringResource(R.string.text_fields_cannot_be_empty),
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Card(colors = CardDefaults.cardColors(containerColor = LightBrown),
                border = BorderStroke(width = 1.dp, color = Color.LightGray),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            ) {
                //Parent Counselling checkbox
                checkBoxRow(
                    label = stringResource(R.string.are_you_interested_in_parent_s_counselling),
                    checkState = interestedInParentCounselling
                )

                //Parent Guidance Book CheckBox
                checkBoxRow(
                    label = stringResource(R.string.are_you_looking_for_parent_s_guidance_book),
                    checkState = interestedInParentGuidanceBook
                )
            }

            val validationCondition = if(guardianName.value.isNotEmpty()&&
                childName.value.isNotEmpty() &&
                childAge.value.isNotEmpty() &&
                contactNumber.value.isNotEmpty() &&
                emailStateText.value.isNotEmpty() &&
                password.value.isNotEmpty() &&
                location?.isNotEmpty() == true &&
                timezone?.isNotEmpty() == true
            ) true else false

            val validationConditionForCreatingExistingUser = if(guardianName.value.isNotEmpty()&&
                childName.value.isNotEmpty() &&
                childAge.value.isNotEmpty() &&
                contactNumber.value.isNotEmpty() &&
                emailStateText.value.isNotEmpty() &&
                location?.isNotEmpty() == true &&
                timezone?.isNotEmpty() == true
            ) true else false

            //Displaying a Dialog Box in Case Internet is Not Avaliable
            if(openDialogBox.value == true){
                CustomDialog(openDialogCustom = openDialogBox, onDismiss = {openDialogBox.value = false}, onClick = {
                    navController?.popBackStack()
                    navController?.navigate(route = ScreensName.SignUpScreen.name)
                })
            }

/*

 */


            if (showLoadingScreen.value) {
                Dialog(
                    onDismissRequest = { showLoadingScreen.value = false },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment= Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End){

                Column (modifier = Modifier
                    .width(150.dp)
                    .padding(bottom = 10.dp)
                    .clickable(enabled = true, onClick = {

                        showLoadingScreen.value = true
                        ButtonTapSound(context = context)
                        if (email.isNullOrEmpty()) {
                            if (checkNetwork(context)) {

                                if (validationCondition) {
                                    viewModel.createUserWithEmailAndPassword(
                                        guardianName.value.trim(),
                                        childName.value.trim(),
                                        childAge.value.trim(),
                                        contactNumber.value.trim(),
                                        emailStateText.value.trim(),
                                        password.value.trim(),
                                        interestedInParentCounselling.value.toString(),
                                        interestedInParentGuidanceBook.value.toString(),
                                        timezone.toString(),
                                        location.toString()
                                    ) {
                                        showLoadingScreen.value = false
                                        navController?.popBackStack()
                                        navController?.navigate(route = ScreensName.BottomNavHost.name)
                                    }
                                } else {
                                    showEmptyBoxMessage.value = true
                                }
                            } else {
                                openDialogBox.value = true
                            }
                        } else {
                            if (checkNetwork(context)) {
                                showLoadingScreen.value = true
                                if (validationConditionForCreatingExistingUser) {
                                    viewModel.createUser(
                                        guardianName.value.trim(),
                                        childName.value.trim(),
                                        childAge.value.trim(),
                                        contactNumber.value.trim(),
                                        emailStateText.value.trim(),
                                        interestedInParentCounselling.value.toString(),
                                        interestedInParentGuidanceBook.value.toString(),
                                        timezone.toString(),
                                        location.toString(),
                                        documentRef
                                    ) {
                                        showLoadingScreen.value = false
                                        navController?.popBackStack()
                                        navController?.navigate(route = ScreensName.BottomNavHost.name)
                                    }
                                } else {
                                    showEmptyBoxMessage.value = true
                                }
                            } else {
                                openDialogBox.value = true
                            }
                        }

                    }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){

                    Text(text = stringResource(id = R.string.sign_up),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        fontSize = 30.sp)
                    Surface (shape = RectangleShape, color = DullYellow, modifier = Modifier
                        .height(20.dp)
                        .width(150.dp)){}
                }
            }
        }
      }
    }
}


@Composable
fun checkBoxRow(
    label : String,
    checkState : MutableState<Boolean>
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 2.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(checked = checkState.value,
            onCheckedChange ={
                checkState.value = it
            },
            colors = CheckboxDefaults.colors(checkedColor = SkyBlue, checkmarkColor = Color.White),
            modifier = Modifier.background(LightBrown, shape = CircleShape))

        Text(text = label,
            fontWeight = FontWeight.W400,
            color = Color.Black,
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis)
    }
}
