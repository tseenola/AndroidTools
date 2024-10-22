package com.tssenola.androidtools

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.tssenola.debugtool.server.LogServer
import kotlin.random.Random

class MainActivity : Activity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sendData = findViewById<Button>(R.id.sendData)
        sendData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        LogServer.getIns().sendMsg("Hello World From Android")
        Log.d("vbvb", "onClick: ")
    }

    override fun onDestroy() {
        LogServer.getIns().disconnect()
        super.onDestroy()
    }
}