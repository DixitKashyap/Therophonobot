package com.therophonobot.therophonobot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.therophonobot.therophonobot.screens.HomeScreen.HomeScreen

@Composable
fun BottomBarNavigation(bottomNavController: NavHostController, mainScreenNavController: NavController) {
 
    NavHost(navController = bottomNavController, startDestination = ScreensName.HomeScreen.name ){
        
        composable(route = ScreensName.HomeScreen.name){
            HomeScreen(navController = bottomNavController,mainScreenNavController = mainScreenNavController)
        }
    }
  
}