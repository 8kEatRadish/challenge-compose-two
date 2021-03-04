package com.example.androiddevchallenge.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
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

    Surface(color = app_bg_color, modifier = Modifier.fillMaxSize(1f)) {
        Column(Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.SpaceEvenly) {

            //倒计时时间设定器
            Row(
                Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimeCard(viewModel = viewModel, CountdownViewModel.TimeType.ONE_MINUTE)
                TimeCard(viewModel = viewModel, CountdownViewModel.TimeType.TWO_MINUTE)
                TimeCard(viewModel = viewModel, CountdownViewModel.TimeType.THREE_MINUTE)
            }

            //倒计时面板
            Row(
                Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CountCard(count = times.value[0])
                CountCard(count = times.value[1])

                Image(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    Modifier.width(20.dp)
                )
                CountCard(count = times.value[2])
                CountCard(times.value[3])
            }

            //开始和复位按钮
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


/**
 * 倒计时单个数字卡片
 * @param count 显示的数字
 */
@Composable
fun CountCard(count: Int) {

    val shakingOffset = remember { Animatable(0f) }
    LaunchedEffect(key1 = count, block = {
        if (count != 0) {
            shakingOffset.animateTo(
                360f,
                animationSpec = spring(0.2f, 600f),
                initialVelocity = -2000f
            )
        }
    })

    Card(
        Modifier
            .width(100.dp)
            .height(100.dp)
            .graphicsLayer(
                rotationZ = shakingOffset.value,
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "$count",
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun TimeCard(viewModel: CountdownViewModel, type: CountdownViewModel.TimeType) {
    Card(
        Modifier
            .width(100.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                viewModel.setTime(type)
            },
        backgroundColor = button_bg
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = when (type) {
                    CountdownViewModel.TimeType.ONE_MINUTE -> {
                        "1MIN"
                    }
                    CountdownViewModel.TimeType.TWO_MINUTE -> {
                        "2MIN"
                    }
                    CountdownViewModel.TimeType.THREE_MINUTE -> {
                        "3MIN"
                    }
                    else -> {
                        ""
                    }
                },
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}


@Preview
@Composable
fun showCountdownPage() {
    CountdownPage()
}