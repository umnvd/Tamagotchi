package ru.umnvd.tamagotchi.domain.usecases.user

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime
import javax.inject.Inject

// TODO: Add tamagotchi answer. Tamagothi may not want to eat if he well-fed,
//  but post EAT signal if has bad discipline
/**
 * Покормить тамагочи едой.
 * */
class FeedTamagotchiMealUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
) {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: FeedTamagotchiSweetsUseCase.Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentState = currentTamagotchi.state
        val currentMemory = currentTamagotchi.memory

        val newWeight = currentState.weight + WEIGHT_STEP

        val newHealth = if (newWeight > WEIGHT_THRESHOLD) {
            currentState.health - HEALTH_STEP
        } else {
            currentState.health
        }

        val newState = currentState.copy(
            satiety = currentState.satiety + SATIETY_STEP,
            weight = newWeight,
            health = newHealth,
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

        private const val WEIGHT_STEP = 5
        private const val WEIGHT_THRESHOLD = 90
        private const val HEALTH_STEP = 10
        private const val SATIETY_STEP = 10
    }
}