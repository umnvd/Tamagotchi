package ru.umnvd.tamagotchi.domain.usecases

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime

class FeedTamagotchiMealUseCase constructor(
    private val tamagotchiRepository: TamagotchiRepository
) {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: FeedTamagotchiSweetsUseCase.Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state
        val currentMemory = tamagotchi.memory

        val newState = currentState.copy(
            satiety = currentState.satiety + SATIETY_STEP,
        )

        val newMemory = currentMemory.copy(
            lastMeal = LocalDateTime.now()
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
    }
}