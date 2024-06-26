package ru.umnvd.tamagotchi.common.domain.usecases.user

import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

class PlayWithTamagotchiUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
)  {
    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: FeedTamagotchiSweetsUseCase.Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentState = currentTamagotchi.state
        val currentMemory = currentTamagotchi.memory

        val newState = currentState.copy(
            joy = currentState.joy + JOY_STEP,
            weight = currentState.weight - WEIGHT_STEP,
            energy = currentState.energy - ENERGY_STEP,
        )

        val newMemory = currentMemory.copy(
            lastPlay = LocalDateTime.now(),
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {
        private const val JOY_STEP = 15
        private const val WEIGHT_STEP = 5
        private const val ENERGY_STEP = 5
    }
}