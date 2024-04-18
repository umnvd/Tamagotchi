package ru.umnvd.tamagotchi.common.domain.usecases.user

import ru.umnvd.tamagotchi.common.domain.models.LastTamagotchiSleep
import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

class IgnoreSleepSignalUseCase @Inject constructor(
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
            energy = currentState.energy - ENERGY_STEP,
            discipline = currentState.discipline - DISCIPLINE_STEP,
        )

        val newMemory = if (newState.energy <= ENERGY_THRESHOLD) {
            currentMemory.copy(
                lastSleep = LastTamagotchiSleep(
                    start = LocalDateTime.now(),
                ),
            )
        } else {
            currentMemory
        }

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {
        private const val ENERGY_STEP = 10
        private const val DISCIPLINE_STEP = 10
        private const val ENERGY_THRESHOLD = 50
    }
}