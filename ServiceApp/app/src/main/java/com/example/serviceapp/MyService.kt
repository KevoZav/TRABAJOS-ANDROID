package com.example.serviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.os.Handler


class MyService : Service() {

    private val TAG = "service";

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "start")
        var contador= 0
        Handler().apply{
            val runnable = object: Runnable {
                override fun run() {
                    contador++
                    Log.d(TAG, "El valor del contador es $contador")
                    postDelayed(this, 1000)
                }
            }
            postDelayed(runnable, 1000)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "destroy")
        super.onDestroy()
    }
}