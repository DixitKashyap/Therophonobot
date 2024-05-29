package com.therophonobot.therophonobot.screens.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.therophonobot.therophonobot.R
import com.therophonobot.therophonobot.navigation.ScreensName
import com.therophonobot.therophonobot.ui.theme.LightBrown
import com.therophonobot.therophonobot.ui.theme.SkyBlue
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController){
   SplashScreenUi(navController=navController)
}

@Composable
fun SplashScreenUi(
navController: NavController? = null
) {

    //Splash Screen Timeout
    val splashScreenTimeout = 1500L
    //Moving to the login screen after the delay
    LaunchedEffect(key1 = true) {
        delay(splashScreenTimeout)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController?.popBackStack()
            navController?.navigate(route = ScreensName.LoginScreen.name)
        }else{
            navController?.popBackStack()
            navController?.navigate(route = ScreensName.BottomNavHost.name)
        }
    }
    Surface(modifier = Modifier.fillMaxSize(), color = LightBrown) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.app_logo) ,
                contentDescription ="SplashScreen Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .padding(8.dp))

           Text(text = stringResource(id = R.string.welcome_string),
               fontSize = 45.sp,
               fontWeight = FontWeight.Bold,
               color = Color.Black,
               modifier = Modifier.padding(bottom = 10.dp, top = 20.dp))

            Spacer(modifier = Modifier.height(100.dp))

            CircularProgressIndicator(color = SkyBlue)


        }
    }
}
