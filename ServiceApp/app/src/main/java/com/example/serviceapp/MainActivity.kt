package com.example.serviceapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.security.Provider.Service

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MyService::class.java)
        startService(intent);
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, Service::class.java)
        stopService(intent)
    }
}