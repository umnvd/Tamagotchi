package ru.umnvd.tamagotchi.presentation

import android.accessibilityservice.AccessibilityService
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

    override fun onCreate() {
        super.onCreate()
        Log.d("TEST", "TamagotchiAccessibilityService: onCreate")
        window = TamagotchiWindow(this)
        window?.open()
    }

    override fun onDestroy() {
        Log.d("TEST", "TamagotchiAccessibilityService: onDestroy")
        super.onDestroy()
        window?.close()
    }
}