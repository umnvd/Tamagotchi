package ru.umnvd.tamagotchi.common.domain.usecases.user

import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Почистить туалет тамагочи.
 * */
class CleanDuckUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository,
) {
    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentRoom = currentTamagotchi.room
        val currentMemory = currentTamagotchi.memory

        val newRoom = currentRoom.copy(
            poopQuantity = 0,
        )

        val newMemory = currentMemory.copy(
            lastCleaning = LocalDateTime.now(),
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                memory = newMemory,
                room = newRoom,
            )
        )
    }
}