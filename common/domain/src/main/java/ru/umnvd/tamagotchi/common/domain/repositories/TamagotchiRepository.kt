package ru.umnvd.tamagotchi.common.domain.repositories

import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi

interface TamagotchiRepository {
    suspend fun getTamagotchi(): Result<Tamagotchi>

    suspend fun saveTamagotchi(tamagotchi: Tamagotchi): Result<Tamagotchi>
}