package ru.umnvd.tamagotchi.domain.repositories

import ru.umnvd.tamagotchi.domain.models.Tamagotchi

interface TamagotchiRepository {

    suspend fun getTamagotchi(): Result<Tamagotchi>

    suspend fun saveTamagotchi(tamagotchi: Tamagotchi): Result<Tamagotchi>
}