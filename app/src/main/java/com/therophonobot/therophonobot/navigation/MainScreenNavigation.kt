package com.therophonobot.therophonobot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.therophonobot.therophonobot.screens.BottomNavHost.BottomNavHostScreen
import com.therophonobot.therophonobot.screens.LoginScreen.LoginScreen
import com.therophonobot.therophonobot.screens.SignUpScreen.SignUpScreen
import com.therophonobot.therophonobot.screens.SplashScreen.SplashScreen

@Composable
fun MainScreenNavigation(){
    
    //This will allow us to navigate between different Screen
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = ScreensName.SplashScreen.name){
        composable(route = ScreensName.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        
        composable(route = ScreensName.LoginScreen.name){
            LoginScreen(navController = navController)
        }

        val signUpScreen = ScreensName.SignUpScreen.name
        composable(route = "${signUpScreen}/{email}" , arguments = listOf(navArgument("email"){
            type = NavType.StringType
            nullable= true
        })){backStackEntry ->
            backStackEntry.arguments?.getString("email").let{
                SignUpScreen(navController = navController,email = it.toString())
            }
        }

        composable(route = ScreensName.SignUpScreen.name){
            SignUpScreen(navController=navController)
        }

        composable(route = ScreensName.BottomNavHost.name){
            BottomNavHostScreen(navController = navController)
        }
    }
}