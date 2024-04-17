package ru.umnvd.tamagotchi.presentation

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class TamagotchiAccessibilityService : AccessibilityService() {

    private var window: TamagotchiWindow? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("TEST", "TamagotchiAccessibilityService: onServiceConnected")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("TEST", "TamagotchiAccessibilityService: onStartCommand")
        if (intent.hasExtra(SHOW_WINDOW_EXTRA_KEY)) {
            Log.d("TEST", "TamagotchiAccessibilityService: show window")
            window = TamagotchiWindow(this)
            window?.open()
        }
        if (intent.hasExtra(HIDE_WINDOW_EXTRA_KEY)) {
            Log.d("TEST", "TamagotchiAccessibilityService: hide window")
            window?.close()
        }

        return Service.START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TEST", "TamagotchiAccessibilityService: onCreate")
    }

    override fun onDestroy() {
        Log.d("TEST", "TamagotchiAccessibilityService: onDestroy")
        super.onDestroy()
        window?.close()
    }

    companion object {

        private const val SHOW_WINDOW_EXTRA_KEY = "show_window"
        private const val HIDE_WINDOW_EXTRA_KEY = "hide_window"

        fun getShowWindowIntent(context: Context): Intent =
            Intent(context, TamagotchiAccessibilityService::class.java).apply {
                putExtra(SHOW_WINDOW_EXTRA_KEY, true)
            }

        fun getHideWindowIntent(context: Context): Intent =
            Intent(context, TamagotchiAccessibilityService::class.java).apply {
                putExtra(HIDE_WINDOW_EXTRA_KEY, true)
            }
    }
}