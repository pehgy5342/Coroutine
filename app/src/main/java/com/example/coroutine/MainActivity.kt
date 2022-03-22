package com.example.coroutine


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = job

    val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val number: TextView = findViewById(R.id.tx_number)

        lifecycleScope.launch {
            //suspend涵式需跑在Coroutine環境下
            delayTime()
        }
        //不需要等到lambda裡程式執行完，會先往下執行
        Toast.makeText(this, "我先顯示", Toast.LENGTH_SHORT).show()


        //取消協程
        val job = lifecycleScope.launch(Dispatchers.Main) {
            for (i in 10 downTo 1) {
                number.text = "count down $i ..." // update text
                delay(1000)
            }
            number.text = "Done!"
        }
        job.cancel()

    }

    //類似 Thread.sleep
    suspend fun delayTime() {
        delay(7000)
        showToast()
    }

    fun showToast() {
        Toast.makeText(this, "7秒後顯示", Toast.LENGTH_SHORT).show()
    }
}