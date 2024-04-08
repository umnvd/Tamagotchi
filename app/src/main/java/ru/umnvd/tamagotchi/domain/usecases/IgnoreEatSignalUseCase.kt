package ru.umnvd.tamagotchi.domain.usecases

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime

class IgnoreEatSignalUseCase constructor(
    private val tamagotchiRepository: TamagotchiRepository
)  {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state
        val currentMemory = tamagotchi.memory

        val newState = currentState.copy(
            satiety = currentState.satiety - SATIETY_STEP,
            weight = currentState.weight - WEIGHT_STEP,
            joy = currentState.joy - JOY_STEP,
            discipline = currentState.discipline - DISCIPLINE_STEP,
        )

        val newMemory = currentMemory.copy(
            lastUserInteraction = LocalDateTime.now(),
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = tamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {

        private const val SATIETY_STEP = 1
        private const val JOY_STEP = 1
        private const val WEIGHT_STEP = 1
        private const val DISCIPLINE_STEP = 1
    }
}