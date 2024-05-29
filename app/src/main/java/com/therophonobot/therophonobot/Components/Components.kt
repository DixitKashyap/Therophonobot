package com.therophonobot.therophonobot.Components

import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.therophonobot.therophonobot.R
import com.therophonobot.therophonobot.ui.theme.PurpleGrey40
import com.therophonobot.therophonobot.ui.theme.TealGreen

fun Components() {
}


@Composable
fun CustomDialog(openDialogCustom: MutableState<Boolean>,onDismiss:()->Unit,onClick:()->Unit) {
    Dialog(onDismissRequest = { openDialogCustom.value = false}) {
        CustomDialogUI(openDialogCustom = openDialogCustom, onDismiss = {onDismiss()}, onClick = {onClick()})
    }
}

//Layout
@Composable
fun CustomDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>,onDismiss:()->Unit,onClick:()->Unit){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier
                .background(Color.White)) {

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.network_error),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    text = stringResource(R.string.internet_is_not_connected),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(TealGreen),
                horizontalArrangement = Arrangement.SpaceAround) {

                androidx.compose.material3.TextButton(onClick = {
                  onDismiss()
                }) {

                    androidx.compose.material3.Text(
                        text = stringResource(R.string.close),
                        fontWeight = FontWeight.Bold,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                androidx.compose.material3.TextButton(onClick = {
                    onClick()
                }) {
                    androidx.compose.material3.Text(
                        stringResource(R.string.refresh),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

fun ButtonTapSound(context : Context){
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK,1.0f)
}