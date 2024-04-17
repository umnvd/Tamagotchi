package ru.umnvd.tamagotchi.domain.usecases.userinteraction

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Покормить тамагочи сладостями.
 * */
class FeedTamagotchiSweetsUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
)  {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentState = currentTamagotchi.state
        val currentMemory = currentTamagotchi.memory

        val newState = currentState.copy(
            joy = currentState.joy + JOY_STEP,
            weight = currentState.weight + WEIGHT_STEP,
        )

        val newMemory = currentMemory.copy(
            lastMeal = LocalDateTime.now(),
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {

        private const val JOY_STEP = 10
        private const val WEIGHT_STEP = 10
    }
}