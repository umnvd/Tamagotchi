package ru.umnvd.tamagotchi.common.domain.usecases.user

import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Полечить тамагочи.
 * */
class TreatTamagotchiUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
) {
    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentState = currentTamagotchi.state
        val currentMemory = currentTamagotchi.memory

        val newState = currentState.copy(
            health = currentState.health + HEALTH_STEP,
        )

        val newMemory = currentMemory.copy(
            lastTreatment = LocalDateTime.now()
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {
        private const val HEALTH_STEP = 50
    }
}