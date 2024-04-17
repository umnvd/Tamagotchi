package ru.umnvd.tamagotchi.presentation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TamagotchiService : Service() {

    private var window: TamagotchiWindow? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("TEST", "TamagotchiService onCreate")
        window = TamagotchiWindow(this)
        window?.open()
    }

    override fun onDestroy() {
        Log.d("TEST", "TamagotchiService onDestroy")
        super.onDestroy()
        window?.close()
    }
}