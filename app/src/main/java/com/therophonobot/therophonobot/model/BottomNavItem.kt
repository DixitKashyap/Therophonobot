package com.therophonobot.therophonobot.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.therophonobot.therophonobot.navigation.ScreensName

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label : String
)

fun getBottomNavItem() :List<BottomNavItem>{
    return listOf(
        BottomNavItem(
            title = ScreensName.HomeScreen.name,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            label = "Home"
        ),
        BottomNavItem(
            title = ScreensName.MenuScreen.name,
            selectedIcon = Icons.AutoMirrored.Filled.MenuOpen,
            unselectedIcon = Icons.AutoMirrored.Filled.MenuOpen,
            label = "Menu"
        ),
        BottomNavItem(
            title = ScreensName.PlansScreen.name,
            selectedIcon = Icons.Filled.MonetizationOn,
            unselectedIcon = Icons.Outlined.MonetizationOn,
            label = "Plans"
        ),
        BottomNavItem(
            title = ScreensName.ProfileScreen.name,
            selectedIcon = Icons.Default.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            label = "Profile"
        )
    )
}