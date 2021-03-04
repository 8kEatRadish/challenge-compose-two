package com.example.androiddevchallenge.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.CountdownViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.app_bg_color
import com.example.androiddevchallenge.ui.theme.button_bg

@Composable
fun CountdownPage() {

    val viewModel: CountdownViewModel = viewModel()

    var times = remember { viewModel.counts }

    var currentPage = remember { viewModel.isRun }

    Surface(color = app_bg_color,modifier = Modifier.fillMaxSize(1f)) {
        Column(Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.SpaceEvenly) {
            Row(
                Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.setTime(CountdownViewModel.TimeType.ONE_MINUTE)
                        },
                    backgroundColor = button_bg,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "1min",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Card(
                    Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.setTime(CountdownViewModel.TimeType.TWO_MINUTE)
                        },
                    backgroundColor = button_bg
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "2min",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Card(
                    Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.setTime(CountdownViewModel.TimeType.THREE_MINUTE)
                        },
                    backgroundColor = button_bg
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "3min",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    Modifier
                        .width(100.dp)
                        .height(100.dp), border = BorderStroke(1.dp, Color(0xff0000))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${times.value[0]}",
                            style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Card(
                    Modifier
                        .width(100.dp)
                        .height(100.dp), border = BorderStroke(1.dp, Color(0xff0000))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${times.value[1]}",
                            style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Image(painter = painterResource(id = R.drawable.time), contentDescription = null,Modifier.width(20.dp))
                Card(
                    Modifier
                        .width(100.dp)
                        .height(100.dp), border = BorderStroke(1.dp, Color(0xff0000))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${times.value[2]}",
                            style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Card(
                    Modifier
                        .width(100.dp)
                        .height(100.dp), border = BorderStroke(1.dp, Color(0xff0000))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${times.value[3]}",
                            style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(onClick = {
                    if (currentPage.value) {
                        viewModel.stopDownCount()
                    } else {
                        viewModel.downFromCount()
                    }
                }) {
                    Crossfade(targetState = currentPage) { screen ->
                        if (screen.value) {
                            Text(text = "PAUSE")
                        } else {
                            Text(text = "START")
                        }
                    }
                }
                Button(onClick = { viewModel.resetTime() }) {
                    Text(text = "RESET")
                }
            }
        }
    }
}

@Preview
@Composable
fun showCountdownPage() {
    CountdownPage()
}