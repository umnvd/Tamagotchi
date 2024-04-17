package ru.umnvd.tamagotchi.domain.usecases.userinteraction

import ru.umnvd.tamagotchi.domain.models.LastTamagotchiSleep
import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Выключить свет тамагочи.
 *
 * Для упрощения, выключение света сразу приводит ко сну.
 * */
class TurnOffLightUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
) {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentRoom = currentTamagotchi.room
        val currentMemory = currentTamagotchi.memory

        val newRoom = currentRoom.copy(
            light = false,
        )

        val newMemory = currentMemory.copy(
            lastSleep = LastTamagotchiSleep(
                start = LocalDateTime.now(),
            ),
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                memory = newMemory,
                room = newRoom,
            )
        )
    }
}