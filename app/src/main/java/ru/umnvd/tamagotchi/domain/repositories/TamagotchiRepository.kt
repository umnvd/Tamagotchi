package ru.umnvd.tamagotchi.domain.repositories

import ru.umnvd.tamagotchi.domain.models.Tamagotchi

interface TamagotchiRepository {

    fun getTamagotchi(): Result<Tamagotchi>

    fun saveTamagotchi(tamagotchi: Tamagotchi): Result<Tamagotchi>
}