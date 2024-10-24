package com.tssenola.androidtools

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.tssenola.debugtool.server.LogServer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : Activity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sendData = findViewById<Button>(R.id.sendData)
        sendData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        GlobalScope.launch {
            for (i in 1..30){
                delay(1000)
                LogServer.getIns().sendMsg("World From AndroidHello World "+ i)
            }
        }
        Log.d("vbvb", "onClick: ")
    }

    override fun onDestroy() {
        LogServer.getIns().disconnect()
        super.onDestroy()
    }
}