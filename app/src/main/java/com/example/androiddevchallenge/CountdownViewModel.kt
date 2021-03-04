package com.example.androiddevchallenge

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class CountdownViewModel(application: Application) : AndroidViewModel(application) {

    private var job: Job? = null
    private var min = 0
    private var secondOne = 0
    private var secondTwo = 0

    enum class TimeType {
        NULL,
        ONE_MINUTE,
        TWO_MINUTE,
        THREE_MINUTE
    }

    var counts = mutableStateOf(
        arrayOf(0, 0, 0, 0)
    )

    var isRun = mutableStateOf(
        false
    )

    //倒计时暂停
    fun stopDownCount() {
        job?.apply {
            if (job.isActive) {
                job.cancel()
            }
        }

        isRun.value = false
    }

    //设置时间
    fun setTime(timeType: TimeType) {
        if (isRun.value) {
            "Please reset after setting".toast()
            return
        }
        when (timeType) {
            TimeType.NULL -> {
                counts.value = arrayOf(0, 0, 0, 0)
            }
            TimeType.ONE_MINUTE -> {
                counts.value = arrayOf(0, 1, 0, 0)
            }
            TimeType.TWO_MINUTE -> {
                counts.value = arrayOf(0, 2, 0, 0)
            }
            TimeType.THREE_MINUTE -> {
                counts.value = arrayOf(0, 3, 0, 0)
            }
        }
    }

    //复位
    fun resetTime() {
        if (isRun.value) {
            stopDownCount()
            isRun.value = false
        }

        setTime(TimeType.NULL)
    }

    //倒计时
    fun downFromCount() {
        //记录秒数
        var res = 0

        counts.value.forEachIndexed { index, i ->
            res += when (index) {
                1 -> {
                    i * 60
                }
                2 -> {
                    i * 10
                }
                else -> {
                    i
                }
            }
        }

        if (res == 0) {

            "Please set the time".toast()

            return
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            isRun.value = true
            while (res >= 0) {
                delay(1000)
                min = res / 60
                secondOne = (res % 60) / 10
                secondTwo = (res % 60) % 10

                withContext(Dispatchers.Main) {
//                    "0,$min,$secondOne,$secondTwo".toast()
                    counts.value = arrayOf(0, min, secondOne, secondTwo)
                    res -= 1
                    if (res <= 0) {
                        isRun.value = false
                    }
                }
            }
        }

        job?.start()
    }

    private fun String.toast() {
        Toast.makeText(getApplication(), this, Toast.LENGTH_SHORT).show()
    }
}