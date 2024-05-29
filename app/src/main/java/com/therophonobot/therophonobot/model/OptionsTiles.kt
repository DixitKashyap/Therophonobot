package com.therophonobot.therophonobot.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.therophonobot.therophonobot.R

data class OptionsTiles(
    val label : String,
    val image : Painter
)

@Composable
fun getOptions() : List<OptionsTiles>{
    return listOf(
        OptionsTiles(
            label = "Alphabets",
            image = painterResource(id = R.drawable.alphabets),
        ),
        OptionsTiles(
            label = "Animals",
            image = painterResource(id = R.drawable.animals)
        ),
        OptionsTiles(
            label = "Fruits",
            image = painterResource(id = R.drawable.fruits)
        ),
        OptionsTiles(
            label = "Vegetables",
            image = painterResource(id = R.drawable.vegetables)
        ),
        OptionsTiles(
            label = "Numbers",
            image = painterResource(id = R.drawable.numbers)
        ),
        OptionsTiles(
            label = "Shapes",
            image = painterResource(id = R.drawable.shapes)
        )
    )
}
