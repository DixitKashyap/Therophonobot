package com.therophonobot.therophonobot.screens.HomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.therophonobot.therophonobot.R
import com.therophonobot.therophonobot.model.getOptions
import com.therophonobot.therophonobot.navigation.ScreensName
import com.therophonobot.therophonobot.ui.theme.BrightYellow
import com.therophonobot.therophonobot.ui.theme.DarkBrown
import com.therophonobot.therophonobot.ui.theme.LightBrown

@Composable
fun HomeScreen(navController: NavController,mainScreenNavController: NavController) {
        HomeScreenUi(navController,mainScreenNavController)

}

@Composable
fun HomeScreenUi(navController: NavController? = null,mainScreenNavController: NavController) {



    val optionsItems = getOptions()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightBrown
        ) {

        Column{

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        border = BorderStroke(width = 2.dp, color = BrightYellow),
                        modifier = Modifier.size(60.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = "Application Logo",
                            contentScale = ContentScale.Fit
                        )
                    }

                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 10.dp,)
                    ){
                        Text(text = "Welcome!",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Gray)


                        Text(text ="Therophonebot",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black)
                    }


                }
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout Button",
                    tint = DarkBrown,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.CenterEnd)
                        .clickable {
                            FirebaseAuth
                                .getInstance()
                                .signOut()
                            mainScreenNavController.navigate(route = ScreensName.LoginScreen.name) {
                                popUpTo(0)
                            }
                        })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 10.dp)
                    .background(
                        color = BrightYellow.copy(0.5f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(6.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Today's Good Habit",
                            fontSize = 15.sp,
                            color = Color.DarkGray)

                        Text(text = "Quotes ",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(alpha = 0.7f ),
                            lineHeight = 22.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(250.dp)
                                .padding(10.dp))


                        Text(text = "\"Quotes Auther\"",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(alpha = 0.7f ),
                            lineHeight = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(250.dp)
                                .padding(10.dp))

                    }

                    Image(painter = painterResource(id = R.drawable.cute_bee),
                         contentDescription ="Bee Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(90.dp)
                    )
                }
            }
            
           //Button Tiles Options for kids
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(optionsItems){item ->
                        OptionsItem(image =item.image , label =item.label ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptionsItem(navController: NavController?=null,image : Painter,label : String,onClick : () ->Unit ){
   Card (
       modifier = Modifier.padding(4.dp),
       shape = RoundedCornerShape(15.dp),
       colors = CardDefaults.cardColors(Color.White),
       border = BorderStroke(width = 2.dp, color = Color.White),
       elevation = CardDefaults.cardElevation(4.dp)
   ){
       Column(
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally,
       ) {
           Image(painter =image ,
               contentDescription = "Content Image",
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .height(170.dp)
                   .width(150.dp)
           )
           Text(text = "${label}",
               fontSize = 16.sp,
               color = DarkBrown,
               modifier =Modifier
                   .padding(5.dp))
       }
   }
}
