package ru.umnvd.tamagotchi

import android.app.Application
import ru.umnvd.tamagotchi.di.DaggerApplicationComponent

class TamagotchiApplication: Application() {
    val applicationComponent = DaggerApplicationComponent.create()
}
