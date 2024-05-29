package com.therophonobot.therophonobot.screens.BottomNavHost

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.therophonobot.therophonobot.model.getBottomNavItem
import com.therophonobot.therophonobot.navigation.BottomBarNavigation
import com.therophonobot.therophonobot.ui.theme.DarkBrown
import com.therophonobot.therophonobot.ui.theme.LightBrown


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavHostScreen (navController: NavController){

    val bottomNavController = rememberNavController()
    val bottomNavItem = getBottomNavItem()
    var selectedItemIndex = rememberSaveable {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                Card(elevation = CardDefaults.cardElevation(12.dp),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(containerColor = LightBrown)) {
                    NavigationBar(containerColor = Color.White) {
                    bottomNavItem.forEachIndexed{index,item->
                        NavigationBarItem(
                            selected = selectedItemIndex.value == index ,
                            onClick = {
                                selectedItemIndex.value = index

                            },
                            label = {
                                  Text(text = item.label,
                                      color = if(index == selectedItemIndex.value) DarkBrown else DarkBrown)
                            },
                            alwaysShowLabel = true,
                            icon = {
                                Icon(imageVector = if(index == selectedItemIndex.value)item.selectedIcon else item.unselectedIcon ,
                                    contentDescription = item.label,
                                    tint = if(index == selectedItemIndex.value) Color.White else DarkBrown
                                    )
                            },
                                      colors = NavigationBarItemDefaults.colors(
                                          indicatorColor = DarkBrown.copy(0.1f)
                                      )
                        )
                    }
                    }
                }
            }
        ) {
            BottomBarNavigation(bottomNavController = bottomNavController, mainScreenNavController =navController )
        }
    }
}